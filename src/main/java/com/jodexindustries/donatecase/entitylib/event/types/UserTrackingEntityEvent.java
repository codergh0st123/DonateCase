package com.jodexindustries.donatecase.entitylib.event.types;

import com.github.retrooper.packetevents.protocol.player.User;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.event.EntityLibEvent;
import org.jetbrains.annotations.NotNull;

public final class UserTrackingEntityEvent extends GeneralTrackingEvent implements EntityLibEvent {
   public UserTrackingEntityEvent(@NotNull User user, @NotNull TrackedEntity entity) {
      super(user, entity);
   }
}
