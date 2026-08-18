package com.jodexindustries.donatecase.entitylib.spigot;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Server;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnExperienceOrb;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPainting;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnWeatherEntity;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.event.types.UserStopTrackingEntityEvent;
import com.jodexindustries.donatecase.entitylib.event.types.UserTrackingEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

final class InternalRegistryListener extends PacketListenerAbstract implements Listener {
   private SpigotEntityLibPlatform platform;

   InternalRegistryListener(SpigotEntityLibPlatform platform) {
      this.platform = platform;
   }

   public void onPacketSend(PacketSendEvent event) {
      User user = event.getUser();
      PacketTypeCommon type = event.getPacketType();
      if (type == Server.DESTROY_ENTITIES) {
         WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(event);
         int[] ids = packet.getEntityIds();
         Bukkit.getScheduler().runTaskLater((Plugin)this.platform.getHandle(), () -> {
            for(int id : ids) {
               TrackedEntity tracked = this.findTracker(id);
               if (tracked != null) {
                  this.platform.getEventHandler().callEvent(UserStopTrackingEntityEvent.class, new UserStopTrackingEntityEvent(user, tracked));
               }
            }

         }, 2L);
      } else if (type == Server.SPAWN_ENTITY) {
         WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      } else if (type == Server.SPAWN_EXPERIENCE_ORB) {
         WrapperPlayServerSpawnExperienceOrb packet = new WrapperPlayServerSpawnExperienceOrb(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      } else if (type == Server.SPAWN_LIVING_ENTITY) {
         WrapperPlayServerSpawnLivingEntity packet = new WrapperPlayServerSpawnLivingEntity(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      } else if (type == Server.SPAWN_PLAYER) {
         WrapperPlayServerSpawnPlayer packet = new WrapperPlayServerSpawnPlayer(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      } else if (type == Server.SPAWN_WEATHER_ENTITY) {
         WrapperPlayServerSpawnWeatherEntity packet = new WrapperPlayServerSpawnWeatherEntity(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      } else if (type == Server.SPAWN_PAINTING) {
         WrapperPlayServerSpawnPainting packet = new WrapperPlayServerSpawnPainting(event);
         int id = packet.getEntityId();
         this.trackEntity(user, id);
      }

   }

   private void trackEntity(User user, int id) {
      Bukkit.getScheduler().runTaskLater((Plugin)this.platform.getHandle(), () -> {
         TrackedEntity entity = this.findTracker(id);
         if (entity != null) {
            this.platform.getEventHandler().callEvent(UserTrackingEntityEvent.class, new UserTrackingEntityEvent(user, entity));
         }
      }, 2L);
   }

   private TrackedEntity findTracker(int id) {
      TrackedEntity entity = this.platform.findPlatformEntity(id);
      if (entity == null) {
         entity = this.platform.getAPI().getEntity(id);
      }

      if (entity == null && this.platform.getAPI().getSettings().isDebugMode()) {
         this.platform.getLogger().warning("Failed to find entity with id " + id);
      }

      return entity;
   }

   @EventHandler
   public void onEntitySpawn(EntitySpawnEvent event) {
      Entity e = event.getEntity();
      this.platform.getPlatformEntities().put(e.getEntityId(), e);
   }
}
