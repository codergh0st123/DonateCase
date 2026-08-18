package com.jodexindustries.donatecase.entitylib.event.types;

import com.github.retrooper.packetevents.protocol.player.User;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.event.EntityLibEvent;
import org.jetbrains.annotations.NotNull;

public class UserStopTrackingEntityEvent extends GeneralTrackingEvent implements EntityLibEvent {
   public UserStopTrackingEntityEvent(@NotNull User user, @NotNull TrackedEntity entity) {
      super(user, entity);
   }
}
