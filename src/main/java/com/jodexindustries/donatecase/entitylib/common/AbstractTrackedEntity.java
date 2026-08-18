package com.jodexindustries.donatecase.entitylib.common;

import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTrackedEntity<E> implements TrackedEntity {
   private final E platformEntity;

   protected AbstractTrackedEntity(@NotNull E platformEntity) {
      this.platformEntity = platformEntity;
   }

   public @NotNull E getPlatformEntity() {
      return this.platformEntity;
   }
}
