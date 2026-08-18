package com.jodexindustries.donatecase.common.managers;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.addon.InternalAddonClassLoader;
import com.jodexindustries.donatecase.api.addon.InternalAddonDescription;
import com.jodexindustries.donatecase.api.addon.InternalJavaAddon;
import com.jodexindustries.donatecase.api.addon.InvalidAddonException;
import com.jodexindustries.donatecase.api.addon.PowerReason;
import com.jodexindustries.donatecase.api.event.addon.AddonDisableEvent;
import com.jodexindustries.donatecase.api.event.addon.AddonEnableEvent;
import com.jodexindustries.donatecase.api.manager.AddonManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import com.jodexindustries.donatecase.api.tools.DCTools;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class AddonManagerImpl implements AddonManager {
   private static final Map<String, InternalJavaAddon> addons = new ConcurrentHashMap();
   private static final List<InternalAddonClassLoader> loaders = new CopyOnWriteArrayList();
   private MutableGraph<String> dependencyGraph = GraphBuilder.directed().build();
   private final DCAPI api;
   private final Platform platform;
   private final File folder;

   public AddonManagerImpl(DCAPI api) {
      this.api = api;
      this.platform = api.getPlatform();
      this.folder = new File(this.platform.getDataFolder(), "addons");
   }

   public void load() {
      File addonsDir = this.folder;
      if (addonsDir.exists() || addonsDir.mkdir()) {
         File[] files = addonsDir.listFiles();
         if (files != null) {
            Map<String, InternalAddonDescription> descriptions = new HashMap();

            for(File file : files) {
               if (file.isFile() && file.getName().endsWith(".jar")) {
                  try {
                     InternalAddonDescription description = new InternalAddonDescription(file);
                     descriptions.put(description.getName(), description);
                     Collection<String> depend = description.getDepend();
                     Collection<String> softDepend = description.getSoftDepend();

                     for(String dependency : depend) {
                        this.dependencyGraph.putEdge(description.getName(), dependency);
                     }

                     for(String softDependency : softDepend) {
                        this.dependencyGraph.putEdge(description.getName(), softDependency);
                     }
                  } catch (InvalidAddonException | IOException e) {
                     this.platform.getLogger().log(Level.SEVERE, "Failed to parse addon: " + file.getName(), e);
                  }
               }
            }

            List<String> loadOrder = this.resolveLoadOrder();
            if (loadOrder == null) {
               this.platform.getLogger().severe("Cyclic dependency detected! Aborting addon loading.");
            } else {
               for(String addonName : loadOrder) {
                  InternalAddonDescription description = (InternalAddonDescription)descriptions.get(addonName);
                  if (description != null) {
                     this.loadAddon(description);
                  }
               }

               for(InternalAddonDescription description : descriptions.values()) {
                  if (!addons.containsKey(description.getName())) {
                     this.loadAddon(description);
                  }
               }

            }
         }
      }
   }

   private List<String> resolveLoadOrder() {
      List<String> sorted = new ArrayList();
      Set<String> visited = new HashSet();
      Set<String> visiting = new HashSet();

      for(String addon : this.dependencyGraph.nodes()) {
         if (!visited.contains(addon) && this.topologicalSort(addon, sorted, visited, visiting)) {
            return null;
         }
      }

      return sorted;
   }

   private boolean topologicalSort(String addon, List<String> sorted, Set<String> visited, Set<String> visiting) {
      if (visiting.contains(addon)) {
         return true;
      } else if (visited.contains(addon)) {
         return false;
      } else {
         visiting.add(addon);

         for(String dependency : this.dependencyGraph.successors(addon)) {
            if (this.topologicalSort(dependency, sorted, visited, visiting)) {
               return true;
            }
         }

         visiting.remove(addon);
         visited.add(addon);
         sorted.add(addon);
         return false;
      }
   }

   private boolean loadAddon(InternalAddonDescription description) {
      this.platform.getLogger().info("Loading " + description.getName() + " addon v" + description.getVersion());
      if (addons.get(description.getName()) != null) {
         if (description.getName().equalsIgnoreCase("DonateCase")) {
            this.platform.getLogger().warning("Addon " + description.getName() + " trying to load with DonateCase name! Abort.");
            return false;
         } else {
            this.platform.getLogger().warning("Addon with name " + description.getName() + " already loaded!");
            return false;
         }
      } else {
         if (description.getApiVersion() != null) {
            int addonVersion = DCTools.getPluginVersion(description.getApiVersion());
            int pluginVersion = DCTools.getPluginVersion("2.1.0.4");
            int supportedVersion = DCTools.getPluginVersion("2.1.0.0");
            if (pluginVersion < addonVersion || addonVersion < supportedVersion) {
               this.platform.getLogger().warning("Addon " + description.getName() + " API version (" + description.getApiVersion() + ") incompatible with current API version (" + "2.1.0.4" + ")! Abort.");
               return false;
            }
         }

         if (!description.isSupport(this.platform.getIdentifier())) {
            this.platform.getLogger().warning("Addon " + description.getName() + " does not support " + this.platform.getIdentifier() + " platform!");
            return false;
         } else {
            try {
               InternalAddonClassLoader loader = new InternalAddonClassLoader(this.platform.getClass().getClassLoader(), description, this, this.platform);
               InternalJavaAddon addon = loader.getAddon();
               addon.onLoad();
               addons.put(description.getName(), addon);
               loaders.add(loader);
               return true;
            } catch (Throwable e) {
               this.platform.getLogger().log(Level.SEVERE, "Error occurred while loading addon " + description.getName() + " v" + description.getVersion(), e);
               return false;
            }
         }
      }
   }

   public boolean load(File file) {
      if (file.isFile() && file.getName().endsWith(".jar")) {
         InternalAddonDescription description;
         try {
            description = new InternalAddonDescription(file);
         } catch (InvalidAddonException | IOException e) {
            throw new RuntimeException(e);
         }

         return this.loadAddon(description);
      } else {
         return false;
      }
   }

   public void enable(PowerReason reason) {
      for(InternalJavaAddon internalJavaAddon : addons.values()) {
         this.enable(internalJavaAddon, reason);
      }

   }

   public boolean enable(@NotNull InternalJavaAddon addon, PowerReason reason) {
      try {
         if (!addon.isEnabled()) {
            this.platform.getLogger().info("Enabling " + addon.getName() + " addon v" + addon.getVersion());
            addon.setEnabled(true);
            this.api.getEventBus().post(new AddonEnableEvent(addon, reason));
            return true;
         }
      } catch (Throwable t) {
         this.platform.getLogger().log(Level.SEVERE, "Error occurred while enabling addon " + addon.getName() + " v" + addon.getVersion(), t);
      }

      return false;
   }

   public boolean disable(@NotNull InternalJavaAddon addon, PowerReason reason) {
      try {
         if (addon.isEnabled()) {
            this.platform.getLogger().info("Disabling " + addon.getName() + " addon v" + addon.getVersion());
            addon.setEnabled(false);
            this.api.getActionManager().unregister((Addon)addon);
            this.api.getAnimationManager().unregister((Addon)addon);
            this.api.getGuiTypedItemManager().unregister((Addon)addon);
            this.api.getMaterialManager().unregister((Addon)addon);
            this.api.getSubCommandManager().unregister((Addon)addon);
            this.api.getEventBus().post(new AddonDisableEvent(addon, reason));
            return true;
         }
      } catch (Throwable t) {
         this.platform.getLogger().log(Level.SEVERE, "Error occurred while disabling addon " + addon.getName() + " v" + addon.getVersion(), t);
      }

      return false;
   }

   public void unload(PowerReason reason) {
      for(InternalJavaAddon internalJavaAddon : new ArrayList(addons.values())) {
         this.unload(internalJavaAddon, reason);
      }

      this.dependencyGraph = GraphBuilder.directed().build();
      addons.clear();
      loaders.clear();
   }

   public boolean unload(@NotNull InternalJavaAddon addon, PowerReason reason) {
      try {
         this.disable(addon, reason);
         addons.remove(addon.getName());
         loaders.remove(addon.getUrlClassLoader());
         addon.getUrlClassLoader().close();
         return true;
      } catch (Throwable e) {
         this.platform.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e.getCause());
         return false;
      }
   }

   @Nullable
   public InternalJavaAddon get(String addon) {
      return (InternalJavaAddon)addons.get(addon);
   }

   public @NotNull Map<String, InternalJavaAddon> getMap() {
      return addons;
   }

   public @NotNull File getFolder() {
      return this.folder;
   }

   @Nullable
   public static InternalAddonClassLoader getAddonClassLoader(File file) {
      return (InternalAddonClassLoader)loaders.stream().filter((loader) -> loader.getFile().equals(file)).findFirst().orElse((Object)null);
   }

   @Nullable
   public Class<?> getClassByName(String name, boolean resolve) {
      for(InternalAddonClassLoader loader : loaders) {
         try {
            return loader.loadClass0(name, resolve, false);
         } catch (ClassNotFoundException var6) {
         }
      }

      return null;
   }
}
