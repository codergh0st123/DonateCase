package com.jodexindustries.donatecase.entitylib.packetconversion;

import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPainting;
import com.jodexindustries.donatecase.entitylib.meta.other.PaintingMeta;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperLivingEntity;

final class LegacySpawningMethods {
   private LegacySpawningMethods() {
   }

   static class Generic implements EntitySpawningMethod {
      public PacketWrapper<?> getSpawnPacket(WrapperEntity entity) {
         return null;
      }
   }

   static class LivingEntity implements EntitySpawningMethod {
      public PacketWrapper<?> getSpawnPacket(WrapperEntity entity) {
         if (!(entity instanceof WrapperLivingEntity)) {
            throw new IllegalArgumentException("Entity is not an instance of WrapperLivingEntity");
         } else {
            return new WrapperPlayServerSpawnLivingEntity(entity.getEntityId(), entity.getUuid(), entity.getEntityType(), entity.getLocation().getPosition(), entity.getLocation().getYaw(), entity.getLocation().getPitch(), entity.getLocation().getPitch(), (Vector3d)entity.createVeloPacket().get(), entity.getEntityMeta().entityData());
         }
      }
   }

   static class Painting implements EntitySpawningMethod {
      public PacketWrapper<?> getSpawnPacket(WrapperEntity entity) {
         if (entity.getEntityMeta() instanceof PaintingMeta) {
            PaintingMeta paintingMeta = (PaintingMeta)entity.getEntityMeta();
            return new WrapperPlayServerSpawnPainting(entity.getEntityId(), entity.getUuid(), entity.getLocation().getPosition().toVector3i(), paintingMeta.getDirection());
         } else {
            throw new IllegalArgumentException("EntityMeta is not an instance of PaintingMeta");
         }
      }
   }
}
