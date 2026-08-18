package com.jodexindustries.donatecase.entitylib.common;

import com.github.retrooper.packetevents.PacketEventsAPI;
import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityLibAPI;
import com.jodexindustries.donatecase.entitylib.Platform;
import com.jodexindustries.donatecase.entitylib.container.EntityContainer;
import com.jodexindustries.donatecase.entitylib.tick.TickContainer;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEntityLibAPI<P, T> implements EntityLibAPI<T> {
   protected final Platform<P> platform;
   protected final PacketEventsAPI<?> packetEvents;
   protected final APIConfig settings;
   protected final Collection<TickContainer<?, T>> tickContainers;
   protected final EntityContainer defaultEntityContainer = EntityContainer.basic();

   protected AbstractEntityLibAPI(Platform<P> platform, APIConfig settings) {
      this.platform = platform;
      this.packetEvents = settings.getPacketEvents();
      this.settings = settings;
      this.tickContainers = (Collection<TickContainer<?, T>>)(settings.shouldTickTickables() ? new HashSet() : Collections.emptyList());
   }

   public @Nullable WrapperEntity getEntity(int id) {
      return this.defaultEntityContainer.getEntity(id);
   }

   public @Nullable WrapperEntity getEntity(@NotNull UUID uuid) {
      return this.defaultEntityContainer.getEntity(uuid);
   }

   public @NotNull Collection<WrapperEntity> getAllEntities() {
      return this.defaultEntityContainer.getEntities();
   }

   public @NotNull EntityContainer getDefaultContainer() {
      return this.defaultEntityContainer;
   }

   public @NotNull APIConfig getSettings() {
      return this.settings;
   }

   public PacketEventsAPI<?> getPacketEvents() {
      return this.packetEvents;
   }

   public @NotNull Collection<TickContainer<?, T>> getTickContainers() {
      return this.tickContainers;
   }
}
