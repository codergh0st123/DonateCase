package com.jodexindustries.donatecase.entitylib;

import com.github.retrooper.packetevents.PacketEventsAPI;
import com.jodexindustries.donatecase.entitylib.container.EntityContainer;
import com.jodexindustries.donatecase.entitylib.tick.TickContainer;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityLibAPI<T> {
   PacketEventsAPI<?> getPacketEvents();

   void onLoad();

   void onEnable();

   <P extends WrapperEntity> @NotNull P cloneEntity(@NotNull Object var1);

   @Nullable WrapperEntity getEntity(int var1);

   @Nullable WrapperEntity getEntity(@NotNull UUID var1);

   @NotNull Collection<WrapperEntity> getAllEntities();

   @NotNull APIConfig getSettings();

   @NotNull Collection<TickContainer<?, T>> getTickContainers();

   void addTickContainer(@NotNull TickContainer<?, T> var1);

   @NotNull EntityContainer getDefaultContainer();
}
