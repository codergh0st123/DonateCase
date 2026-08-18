package com.jodexindustries.donatecase.spigot;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.action.CaseAction;
import com.jodexindustries.donatecase.api.data.animation.CaseAnimation;
import com.jodexindustries.donatecase.api.data.casedata.MetaUpdater;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItem;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.material.CaseMaterial;
import com.jodexindustries.donatecase.api.data.storage.CaseWorld;
import com.jodexindustries.donatecase.api.event.player.ArmorStandCreatorInteractEvent;
import com.jodexindustries.donatecase.api.manager.ActionManager;
import com.jodexindustries.donatecase.api.manager.AnimationManager;
import com.jodexindustries.donatecase.api.manager.GUITypedItemManager;
import com.jodexindustries.donatecase.api.manager.HologramManager;
import com.jodexindustries.donatecase.api.manager.MaterialManager;
import com.jodexindustries.donatecase.api.platform.DCOfflinePlayer;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.scheduler.Scheduler;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.PAPI;
import com.jodexindustries.donatecase.common.DonateCase;
import com.jodexindustries.donatecase.common.gui.items.HISTORYItemHandlerImpl;
import com.jodexindustries.donatecase.common.gui.items.OPENItemClickHandlerImpl;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import com.jodexindustries.donatecase.spigot.actions.CommandActionExecutorImpl;
import com.jodexindustries.donatecase.spigot.actions.SoundActionExecutorImpl;
import com.jodexindustries.donatecase.spigot.actions.TitleActionExecutorImpl;
import com.jodexindustries.donatecase.spigot.animations.RainlyAnimation;
import com.jodexindustries.donatecase.spigot.animations.firework.FireworkAnimation;
import com.jodexindustries.donatecase.spigot.animations.pop.PopAnimation;
import com.jodexindustries.donatecase.spigot.animations.select.SelectAnimation;
import com.jodexindustries.donatecase.spigot.animations.select.SelectAnimationListener;
import com.jodexindustries.donatecase.spigot.animations.shape.ShapeAnimation;
import com.jodexindustries.donatecase.spigot.animations.wheel.WheelAnimation;
import com.jodexindustries.donatecase.spigot.api.platform.BukkitOfflinePlayer;
import com.jodexindustries.donatecase.spigot.holograms.CMIHologramsImpl;
import com.jodexindustries.donatecase.spigot.holograms.DecentHologramsImpl;
import com.jodexindustries.donatecase.spigot.holograms.FancyHologramsImpl;
import com.jodexindustries.donatecase.spigot.holograms.GDisplayHologramsImpl;
import com.jodexindustries.donatecase.spigot.holograms.HolographicDisplaysImpl;
import com.jodexindustries.donatecase.spigot.hook.packetevents.PacketEventsSupport;
import com.jodexindustries.donatecase.spigot.hook.papi.PAPISupport;
import com.jodexindustries.donatecase.spigot.listener.EventListener;
import com.jodexindustries.donatecase.spigot.materials.BASE64MaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.CHMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.HDBMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.HEADMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.IAMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.MCURLMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.materials.OraxenMaterialHandlerImpl;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.Metrics;
import com.jodexindustries.donatecase.spigot.tools.ToolsImpl;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Generated;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitBackend extends BackendPlatform {
   private final BukkitDonateCase plugin;
   private final DonateCase api;
   private final DCTools tools;
   private final BukkitScheduler scheduler;
   private PAPI papi;
   private PacketEventsSupport packetEventsSupport;
   private MetaUpdater metaUpdater;

   public BukkitBackend(BukkitDonateCase plugin) {
      this.plugin = plugin;
      this.api = new DonateCase(this);
      this.tools = new ToolsImpl(this);
      this.scheduler = new BukkitScheduler(this);
      DCAPI.setInstance(this.api);
   }

   public void load() {
      this.papi = new PAPISupport(this);
      this.papi.register();
      this.metaUpdater = new BukkitMetaUpdater();
      this.registerDefaultCommand();
      this.registerDefaultGUITypedItems();
      this.registerDefaultAnimations();
      this.registerDefaultActions();
      this.registerDefaultMaterials();
      this.loadHologramDrivers();
      Bukkit.getServer().getPluginManager().registerEvents(new EventListener(this), this.plugin);
      this.api.load();
      this.loadMetrics();
      this.loadPacketEventsAPI();
      this.loadLuckPerms();
   }

   public void unload() {
      this.api.unload();
      if (this.packetEventsSupport != null) {
         this.packetEventsSupport.unload();
      }

      Bukkit.getWorlds().stream().flatMap((world) -> world.getEntitiesByClass(ArmorStand.class).stream()).filter((stand) -> stand.hasMetadata("case")).forEach(Entity::remove);
   }

   public PAPI getPAPI() {
      return this.papi;
   }

   public MetaUpdater getMetaUpdater() {
      return this.metaUpdater;
   }

   public String getName() {
      return this.plugin.getName();
   }

   public String getIdentifier() {
      return "Bukkit";
   }

   public String getVersion() {
      return this.plugin.getDescription().getVersion();
   }

   public @NotNull File getDataFolder() {
      return this.plugin.getDataFolder();
   }

   public Logger getLogger() {
      return this.plugin.getLogger();
   }

   public DCTools getTools() {
      return this.tools;
   }

   public DonateCase getAPI() {
      return this.api;
   }

   public @NotNull Scheduler getScheduler() {
      return this.scheduler;
   }

   public DCPlayer getPlayer(String name) {
      Player player = Bukkit.getPlayerExact(name);
      return player == null ? null : BukkitUtils.fromBukkit(player);
   }

   public DCPlayer[] getOnlinePlayers() {
      return (DCPlayer[])Bukkit.getOnlinePlayers().stream().map(BukkitUtils::fromBukkit).toArray((x$0) -> new DCPlayer[x$0]);
   }

   public DCOfflinePlayer[] getOfflinePlayers() {
      return (DCOfflinePlayer[])Arrays.stream(Bukkit.getOfflinePlayers()).map(BukkitOfflinePlayer::new).toArray((x$0) -> new DCOfflinePlayer[x$0]);
   }

   public @Nullable CaseWorld getWorld(String world) {
      return BukkitUtils.fromBukkit(Bukkit.getWorld(world));
   }

   public boolean isWorldLoaded(String world) {
      return Bukkit.getWorld(world) != null;
   }

   public int getSpawnRadius() {
      return Bukkit.getSpawnRadius();
   }

   private void registerDefaultCommand() {
      PluginCommand command = this.plugin.getCommand("donatecase");
      BukkitCommand bukkitCommand = new BukkitCommand(this);
      if (command != null) {
         command.setExecutor(bukkitCommand);
         command.setTabCompleter(bukkitCommand);
      }

   }

   private void registerDefaultGUITypedItems() {
      GUITypedItemManager manager = this.api.getGuiTypedItemManager();
      manager.register(TypedItem.builder().id("HISTORY").addon(this).description("Type for displaying the history of case openings").handler(new HISTORYItemHandlerImpl()).build());
      manager.register(TypedItem.builder().id("OPEN").addon(this).description("Type to open the case").click(new OPENItemClickHandlerImpl()).updateMeta(true).loadOnCase(true).build());
      this.getLogger().info("Registered " + manager.getMap().size() + " gui typed items");
   }

   private void registerDefaultAnimations() {
      AnimationManager manager = this.api.getAnimationManager();
      manager.register(CaseAnimation.builder().name("SHAPE").addon(this).animation(ShapeAnimation.class).description("Items flip through and a shape appears").requireSettings(true).requireBlock(true).build());
      manager.register(CaseAnimation.builder().name("RAINLY").addon(this).animation(RainlyAnimation.class).description("Rain drips from the clouds").requireSettings(true).requireBlock(true).build());
      manager.register(CaseAnimation.builder().name("FIREWORK").addon(this).animation(FireworkAnimation.class).description("Fireworks fly to the skies and a prize appears").requireSettings(true).requireBlock(true).build());
      manager.register(CaseAnimation.builder().name("WHEEL").addon(this).animation(WheelAnimation.class).description("Items resolve around the case").requireSettings(true).requireBlock(true).build());
      manager.register(CaseAnimation.builder().name("SELECT").addon(this).animation(SelectAnimation.class).description("Select your prize manually").requireSettings(true).requireBlock(true).build());
      this.api.getEventBus().register(ArmorStandCreatorInteractEvent.class, new SelectAnimationListener());
      manager.register(CaseAnimation.builder().name("POP").addon(this).animation(PopAnimation.class).description("Items pop").requireSettings(true).requireBlock(true).build());
      this.getLogger().info("Registered " + manager.getMap().size() + " animations");
   }

   private void registerDefaultActions() {
      ActionManager manager = this.api.getActionManager();
      manager.register(CaseAction.builder().name("[command]").addon(this).executor(new CommandActionExecutorImpl()).description("Sends a command to the console").build());
      manager.register(CaseAction.builder().name("[title]").addon(this).executor(new TitleActionExecutorImpl()).description("Sends a title to the player").build());
      manager.register(CaseAction.builder().name("[sound]").addon(this).executor(new SoundActionExecutorImpl()).description("Sends a sound to the player").build());
      this.getLogger().info("Registered " + manager.getMap().size() + " actions");
   }

   private void registerDefaultMaterials() {
      MaterialManager manager = this.api.getMaterialManager();
      manager.register(CaseMaterial.builder().id("BASE64").addon(this).handler(new BASE64MaterialHandlerImpl()).description("Heads from Minecraft-heads by BASE64 value").build());
      manager.register(CaseMaterial.builder().id("MCURL").addon(this).handler(new MCURLMaterialHandlerImpl()).description("Heads from Minecraft-heads by Minecrat-URL").build());
      manager.register(CaseMaterial.builder().id("HEAD").addon(this).handler(new HEADMaterialHandlerImpl()).description("Default Minecraft heads by nickname").build());
      if (Bukkit.getServer().getPluginManager().isPluginEnabled("ItemsAdder")) {
         manager.register(CaseMaterial.builder().id("IA").addon(this).handler(new IAMaterialHandlerImpl()).description("Items from ItemsAdder plugin").build());
      }

      if (Bukkit.getServer().getPluginManager().isPluginEnabled("Oraxen")) {
         manager.register(CaseMaterial.builder().id("ORAXEN").addon(this).handler(new OraxenMaterialHandlerImpl()).description("Items from Oraxen plugin").build());
      }

      if (Bukkit.getServer().getPluginManager().isPluginEnabled("CustomHeads")) {
         manager.register(CaseMaterial.builder().id("CH").addon(this).handler(new CHMaterialHandlerImpl()).description("Heads from CustomHeads plugin").build());
      }

      if (Bukkit.getServer().getPluginManager().isPluginEnabled("HeadDatabase")) {
         manager.register(CaseMaterial.builder().id("HDB").addon(this).handler(new HDBMaterialHandlerImpl()).description("Heads from HeadDatabase plugin").build());
      }

      this.getLogger().info("Registered " + manager.getMap().size() + " materials");
   }

   private void loadHologramDrivers() {
      HologramManager manager = this.api.getHologramManager();
      PluginManager pluginManager = Bukkit.getServer().getPluginManager();
      Map<String, Supplier<Class<? extends HologramDriver>>> drivers = new HashMap<>();
      drivers.put("CMI", (Supplier)() -> CMIHologramsImpl.class);
      drivers.put("DecentHolograms", (Supplier)() -> DecentHologramsImpl.class);
      drivers.put("HolographicDisplays", (Supplier)() -> HolographicDisplaysImpl.class);
      drivers.put("FancyHolograms", (Supplier)() -> FancyHologramsImpl.class);
      drivers.put("GDisplayHologram", (Supplier)() -> GDisplayHologramsImpl.class);
      drivers.forEach((plugin, provider) -> {
         if (pluginManager.isPluginEnabled(plugin)) {
            Class<? extends HologramDriver> driver = (Class)provider.get();
            if (driver != null) {
               try {
                  manager.register(plugin.toLowerCase(), (HologramDriver)driver.newInstance());
               } catch (IllegalAccessException | InstantiationException e) {
                  this.getLogger().log(Level.WARNING, "Error with loading " + plugin + " hologram driver: ", e);
               }
            }
         }

      });
   }

   private void loadPacketEventsAPI() {
      if (!this.api.getConfigManager().getConfig().usePackets()) {
         return;
      }

      if (!Bukkit.getPluginManager().isPluginEnabled("packetevents")) {
         this.getLogger().warning("UsePackets включён, но плагин PacketEvents не найден или ещё не загружен.");
         return;
      }

      try {
         this.packetEventsSupport = new PacketEventsSupport(this);
      } catch (Throwable exception) {
         this.getLogger().log(Level.WARNING, "Не удалось включить пакетный режим DonateCase.", exception);
      }
   }

   private void loadLuckPerms() {
      if (Bukkit.getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
         this.getLuckPermsSupport().load();
      }

   }

   private void loadMetrics() {
      Metrics metrics = new Metrics(this.plugin, 18709);
      metrics.addCustomChart(new Metrics.SimplePie("language", () -> this.api.getConfigManager().getConfig().languages()));
   }

   @Generated
   public BukkitDonateCase getPlugin() {
      return this.plugin;
   }

   @Generated
   public PacketEventsSupport getPacketEventsSupport() {
      return this.packetEventsSupport;
   }
}
