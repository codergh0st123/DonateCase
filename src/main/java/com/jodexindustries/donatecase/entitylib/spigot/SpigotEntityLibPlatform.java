package com.jodexindustries.donatecase.entitylib.spigot;

import com.github.retrooper.packetevents.PacketEventsAPI;
import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.common.AbstractPlatform;
import com.jodexindustries.donatecase.entitylib.utils.ConcurrentWeakIdentityHashMap;
import io.github.retrooper.packetevents.bstats.bukkit.Metrics;
import io.github.retrooper.packetevents.bstats.charts.SimplePie;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpigotEntityLibPlatform extends AbstractPlatform<JavaPlugin> {
   private SpigotEntityLibAPI api;
   private final Map<Integer, Entity> platformEntities = new ConcurrentWeakIdentityHashMap<Integer, Entity>();

   public SpigotEntityLibPlatform(@NotNull JavaPlugin plugin) {
      super(plugin);
   }

   public void setupApi(@NotNull APIConfig settings) {
      super.setupApi(settings);
      this.logger = settings.shouldUsePlatformLogger() ? ((JavaPlugin)this.handle).getLogger() : Logger.getLogger("EntityLib");
      this.api = new SpigotEntityLibAPI(this, settings);
      this.api.onLoad();
      this.api.onEnable();
      if (settings.shouldTrackPlatformEntities()) {
         InternalRegistryListener listener = new InternalRegistryListener(this);
         ((JavaPlugin)this.handle).getServer().getPluginManager().registerEvents(listener, (Plugin)this.handle);
         this.api.getPacketEvents().getEventManager().registerListener(listener);
      }

      if (settings.shouldUseBstats()) {
         PacketEventsAPI<Plugin> pe = this.api.getPacketEvents();
         Metrics metrics = new Metrics((Plugin)pe.getPlugin(), 21916);
         metrics.addCustomChart(new SimplePie("entitylib-version", () -> EntityLib.getVersion().toString()));
      }

   }

   Map<Integer, Entity> getPlatformEntities() {
      return this.platformEntities;
   }

   public @NotNull Stream<TrackedEntity> queryPlatformEntities() {
      return !this.api.getSettings().shouldTrackPlatformEntities() ? Stream.of() : this.platformEntities.values().stream().map(SpigotEntity::new);
   }

   public @Nullable TrackedEntity findPlatformEntity(int entityId) {
      if (!this.api.getSettings().shouldTrackPlatformEntities()) {
         return null;
      } else {
         for(World world : Bukkit.getWorlds()) {
            Entity e = (Entity)world.getEntities().stream().filter((entity) -> entity.getEntityId() == entityId).findFirst().orElse((Object)null);
            if (e != null) {
               return new SpigotEntity(e);
            }
         }

         return null;
      }
   }

   public SpigotEntityLibAPI getAPI() {
      return this.api;
   }

   public String getName() {
      return "Spigot";
   }
}
