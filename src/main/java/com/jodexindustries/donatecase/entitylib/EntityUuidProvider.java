package com.jodexindustries.donatecase.entitylib;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface EntityUuidProvider {
   @NotNull UUID provide(EntityType var1);

   public static class DefaultEntityUuidProvider implements EntityUuidProvider {
      public @NotNull UUID provide(EntityType entityType) {
         return UUID.randomUUID();
      }
   }
}
