package com.jodexindustries.donatecase.entitylib.event.types;

import com.github.retrooper.packetevents.protocol.player.User;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.event.EntityLibEvent;
import org.jetbrains.annotations.NotNull;

public class GeneralTrackingEvent implements EntityLibEvent {
   private final User user;
   private final TrackedEntity entity;

   public GeneralTrackingEvent(@NotNull User user, @NotNull TrackedEntity entity) {
      this.user = user;
      this.entity = entity;
   }

   public User getUser() {
      return this.user;
   }

   public TrackedEntity getEntity() {
      return this.entity;
   }
}
