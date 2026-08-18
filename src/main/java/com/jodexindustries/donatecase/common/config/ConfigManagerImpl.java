package com.jodexindustries.donatecase.common.config;

import com.jodexindustries.donatecase.api.event.plugin.DonateCaseReloadEvent;
import com.jodexindustries.donatecase.api.manager.ConfigManager;
import com.jodexindustries.donatecase.common.config.converter.ConfigConverter;
import com.jodexindustries.donatecase.common.database.CaseDatabaseImpl;
import com.jodexindustries.donatecase.common.managers.CaseKeyManagerImpl;
import com.jodexindustries.donatecase.common.managers.CaseOpenManagerImpl;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;

public class ConfigManagerImpl implements ConfigManager {
   private final MessagesImpl messages;
   private final CaseStorageImpl caseStorage;
   private final ConfigConverter converter;
   private final Map<String, ConfigImpl> configurations = new HashMap<>();
   private static final String[] defaultFiles = new String[]{"Config.yml", "Cases.yml", "Animations.yml"};
   private final BackendPlatform platform;

   public ConfigManagerImpl(BackendPlatform platform) {
      this.platform = platform;
      this.caseStorage = new CaseStorageImpl(this);
      this.messages = new MessagesImpl(this);
      this.converter = new ConfigConverter(this);
   }

   public @Nullable ConfigImpl getConfig(@NotNull String name) {
      return (ConfigImpl)this.configurations.get("plugins/DonateCase/" + name);
   }

   public @Nullable ConfigurationNode getNode(@NotNull String name) {
      ConfigImpl config = this.getConfig(name);
      return config != null ? config.node() : null;
   }

   public Map<String, ? extends ConfigImpl> get() {
      return this.configurations;
   }

   public void load() {
      this.configurations.clear();
      this.createFiles();
      this.loadConfigurations(this.platform.getDataFolder().listFiles(), false);

      try {
         this.messages.load(this.getConfig().languages());
         this.caseStorage.load();
      } catch (ConfigurateException e) {
         this.platform.getLogger().log(Level.WARNING, "Error with loading configuration: ", e);
      }

      this.converter.convert();
      long caching = this.getConfig().caching();
      if (caching >= 0L) {
         CaseOpenManagerImpl.cache.setMaxAge(caching);
         CaseKeyManagerImpl.cache.setMaxAge(caching);
         CaseDatabaseImpl.cache.setMaxAge(caching);
      }

      this.platform.getAPI().getEventBus().post(new DonateCaseReloadEvent(DonateCaseReloadEvent.Type.CONFIG));
   }

   private void loadConfigurations(File[] files, boolean deep) {
      if (files != null) {
         for(File file : files) {
            if (file.isDirectory()) {
               String dirName = file.getName().toLowerCase();
               File[] subFiles = file.listFiles();
               if (!deep && !"cases".equals(dirName)) {
                  if ("lang".equals(dirName)) {
                     this.loadConfigurations(subFiles, false);
                  }
               } else {
                  this.loadConfigurations(subFiles, true);
               }
            } else {
               String fileName = file.getName().toLowerCase();
               if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
                  this.load(file);
               }
            }
         }

      }
   }

   public ConfigImpl load(@NotNull File file) {
      String path = file.getPath().replace("\\", "/");
      ConfigImpl exist = (ConfigImpl)this.configurations.get(path);
      if (exist != null) {
         return exist;
      } else {
         ConfigImpl config = new ConfigImpl(file);

         try {
            config.load();
            this.configurations.put(config.path(), config);
         } catch (ConfigurateException e) {
            this.platform.getLogger().log(Level.WARNING, "Error with loading configuration: ", e);
         }

         return config;
      }
   }

   private void createFiles() {
      for(String fileName : defaultFiles) {
         File file = new File(this.platform.getDataFolder(), fileName);
         if (!file.exists()) {
            this.platform.saveResource(fileName, false);
         }
      }

   }

   public @NotNull MessagesImpl getMessages() {
      return this.messages;
   }

   public @NotNull CaseStorageImpl getCaseStorage() {
      return this.caseStorage;
   }

   @Generated
   public BackendPlatform getPlatform() {
      return this.platform;
   }
}
