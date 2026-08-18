package com.jodexindustries.donatecase.entitylib;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

public interface EntityIdProvider {
   int provide(@NotNull UUID var1, @NotNull EntityType var2);

   public static class DefaultEntityIdProvider implements EntityIdProvider {
      private final AtomicInteger integer = new AtomicInteger(100000);

      public int provide(@NotNull UUID entityUUID, @NotNull EntityType entityType) {
         return this.integer.incrementAndGet();
      }
   }
}
