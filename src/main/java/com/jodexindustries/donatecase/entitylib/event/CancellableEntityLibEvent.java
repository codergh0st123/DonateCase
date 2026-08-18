package com.jodexindustries.donatecase.entitylib.event;

public interface CancellableEntityLibEvent extends EntityLibEvent {
   void setCancelled(boolean var1);

   boolean isCancelled();
}
