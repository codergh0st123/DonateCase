package com.jodexindustries.donatecase.entitylib.common;

import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityIdProvider;
import com.jodexindustries.donatecase.entitylib.EntityUuidProvider;
import com.jodexindustries.donatecase.entitylib.Platform;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.event.EventHandler;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPlatform<P> implements Platform<P> {
   protected final P handle;
   protected Logger logger;
   private EventHandler eventHandler;
   private EntityIdProvider entityIdProvider;
   private EntityUuidProvider entityUuidProvider;

   public AbstractPlatform(P handle) {
      this.handle = handle;
      this.entityIdProvider = new EntityIdProvider.DefaultEntityIdProvider();
      this.entityUuidProvider = new EntityUuidProvider.DefaultEntityUuidProvider();
   }

   public @NotNull Stream<TrackedEntity> queryPlatformEntities() {
      throw new UnsupportedOperationException("Platform does not support querying entities.");
   }

   public @Nullable TrackedEntity findPlatformEntity(int entityId) {
      throw new UnsupportedOperationException("Platform does not support querying entities.");
   }

   public void setupApi(@NotNull APIConfig settings) {
      this.eventHandler = EventHandler.create();
      this.entityIdProvider = new EntityIdProvider.DefaultEntityIdProvider();
      this.entityUuidProvider = new EntityUuidProvider.DefaultEntityUuidProvider();
   }

   public @NotNull Logger getLogger() {
      return this.logger;
   }

   public @NotNull EntityIdProvider getEntityIdProvider() {
      return this.entityIdProvider;
   }

   public @NotNull EntityUuidProvider getEntityUuidProvider() {
      return this.entityUuidProvider;
   }

   public void setEntityIdProvider(EntityIdProvider entityIdProvider) {
      this.entityIdProvider = entityIdProvider;
   }

   public void setEntityUuidProvider(EntityUuidProvider entityUuidProvider) {
      this.entityUuidProvider = entityUuidProvider;
   }

   public @NotNull EventHandler getEventHandler() {
      return this.eventHandler;
   }

   public @NotNull P getHandle() {
      return this.handle;
   }
}
