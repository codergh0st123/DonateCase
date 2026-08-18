package com.jodexindustries.donatecase.entitylib;

import com.jodexindustries.donatecase.entitylib.event.EventHandler;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Platform<P> {
   @NotNull Stream<TrackedEntity> queryPlatformEntities();

   @Nullable TrackedEntity findPlatformEntity(int var1);

   @NotNull EntityIdProvider getEntityIdProvider();

   @NotNull EntityUuidProvider getEntityUuidProvider();

   void setEntityIdProvider(@NotNull EntityIdProvider var1);

   void setEntityUuidProvider(@NotNull EntityUuidProvider var1);

   @NotNull Logger getLogger();

   @NotNull EventHandler getEventHandler();

   void setupApi(@NotNull APIConfig var1);

   EntityLibAPI<?> getAPI();

   String getName();

   @NotNull P getHandle();

   default void logIfNeeded(String message) {
      if (this.getAPI().getSettings().isDebugMode()) {
         this.getLogger().info(message);
      }

   }
}
