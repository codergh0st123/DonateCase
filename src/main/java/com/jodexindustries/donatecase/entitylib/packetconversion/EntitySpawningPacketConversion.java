package com.jodexindustries.donatecase.entitylib.packetconversion;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntitySpawningPacketConversion {
   private static Map<EntityType, EntitySpawningMethod> methods = new ConcurrentHashMap<>();

   public PacketWrapper<?> getSpawnPacket(WrapperEntity entity) {
      EntityType type = entity.getEntityType();
      EntitySpawningMethod method = (EntitySpawningMethod)methods.get(type);
      return method.getSpawnPacket(entity);
   }

   private EntitySpawningPacketConversion() {
   }
}
