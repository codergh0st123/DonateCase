package com.jodexindustries.donatecase.entitylib.event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

final class EventHandlerImpl implements EventHandler {
   private final Map<Class<? extends EntityLibEvent>, Collection<EventCallback>> eventCallbacks = new ConcurrentHashMap();

   public <T extends EntityLibEvent> void registerListener(EventListener<T> listener) {
      this.addEventCallback(listener.getEventClass(), EventListener.createEventCallback(listener));
   }

   public @NotNull Map<Class<? extends EntityLibEvent>, Collection<EventCallback>> getEventCallbacksMap() {
      return this.eventCallbacks;
   }
}
