package com.jodexindustries.donatecase.entitylib.packetconversion;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.utils.VersionUtil;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Optional;

final class ModernSpawningMethods {
   private ModernSpawningMethods() {
   }

   static class Generic implements EntitySpawningMethod {
      public PacketWrapper<?> getSpawnPacket(WrapperEntity entity) {
         if (VersionUtil.isOlderThan(ServerVersion.V_1_19_3)) {
            throw new UnsupportedOperationException("This method is not supported in this version.");
         } else {
            return new WrapperPlayServerSpawnEntity(entity.getEntityId(), Optional.of(entity.getUuid()), entity.getEntityType(), entity.getLocation().getPosition(), entity.getLocation().getPitch(), entity.getLocation().getYaw(), entity.getLocation().getYaw(), entity.getEntityMeta() instanceof ObjectData ? ((ObjectData)entity.getEntityMeta()).getObjectData() : 0, entity.createVeloPacket());
         }
      }
   }
}
