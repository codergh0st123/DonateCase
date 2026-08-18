package com.jodexindustries.donatecase.spigot.hook.packetevents;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.spigot.SpigotEntityLibPlatform;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Generated;
import org.bukkit.Bukkit;

public class PacketEventsSupport {
   private final PacketEventsAPI<?> api = PacketEvents.getAPI();
   private final PacketEventsPacketListener listener = new PacketEventsPacketListener();
   private final BukkitBackend backend;
   private boolean usePackets;

   public PacketEventsSupport(BukkitBackend backend) {
      this.backend = backend;
      this.usePackets = backend.getAPI().getConfigManager().getConfig().usePackets();
      this.load();
   }

   public void load() {
      if (this.usePackets) {
         ServerVersion version = this.getServerVersion();
         this.backend.getLogger().info("Loading packetevents hooking...");
         this.backend.getLogger().info("Server version: " + version.getReleaseName());
         this.backend.getLogger().info("Server protocol version: " + version.getProtocolVersion());
         if (this.getServerVersion().isOlderThan(ServerVersion.V_1_18)) {
            this.backend.getLogger().warning("Server version older than V_1_18. PacketEvents hooking disabled!");
            return;
         }

         PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this.backend.getPlugin()));
         SpigotEntityLibPlatform platform = new SpigotEntityLibPlatform(this.backend.getPlugin());
         APIConfig settings = (new APIConfig(this.api)).tickTickables().trackPlatformEntities();
         EntityLib.init(platform, settings);
         this.api.getEventManager().registerListener(this.listener, PacketListenerPriority.NORMAL);
         if (this.api.isLoaded()) {
            this.backend.getLogger().info("Hooked to packetevents");
            this.usePackets = true;
         } else {
            this.usePackets = false;
         }
      }

   }

   public void unload() {
      this.api.terminate();
   }

   private ServerVersion getServerVersion() {
      String bukkitVersion = Bukkit.getBukkitVersion();

      for(ServerVersion val : ServerVersion.reversedValues()) {
         if (bukkitVersion.contains(val.getReleaseName())) {
            return val;
         }
      }

      return ServerVersion.ERROR;
   }

   @Generated
   public boolean isUsePackets() {
      return this.usePackets;
   }
}
