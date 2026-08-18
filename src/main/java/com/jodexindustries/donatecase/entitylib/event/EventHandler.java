package com.jodexindustries.donatecase.entitylib.event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface EventHandler {
   static @NotNull EventHandler create() {
      return new EventHandlerImpl();
   }

   @NotNull Map<Class<? extends EntityLibEvent>, Collection<EventCallback>> getEventCallbacksMap();

   default <E extends EntityLibEvent> boolean addEventCallback(@NotNull Class<E> eventClass, @NotNull EventCallback<E> eventCallback) {
      Collection<EventCallback> callbacks = this.getEventCallbacks(eventClass);
      return callbacks.add(eventCallback);
   }

   default <E extends EntityLibEvent> boolean removeEventCallback(@NotNull Class<E> eventClass, @NotNull EventCallback<E> eventCallback) {
      Collection<EventCallback> callbacks = this.getEventCallbacks(eventClass);
      return callbacks.remove(eventCallback);
   }

   default <E extends EntityLibEvent> @NotNull Collection<EventCallback> getEventCallbacks(@NotNull Class<E> eventClass) {
      return (Collection)this.getEventCallbacksMap().computeIfAbsent(eventClass, (clazz) -> new CopyOnWriteArraySet());
   }

   default @NotNull Stream<EventCallback> getEventCallbacks() {
      return this.getEventCallbacksMap().values().stream().flatMap(Collection::stream);
   }

   default <E extends EntityLibEvent> void callEvent(@NotNull Class<E> eventClass, @NotNull E event) {
      Collection<EventCallback> eventCallbacks = this.getEventCallbacks(eventClass);
      this.runEvent(eventCallbacks, event);
   }

   default <E extends EntityLibEvent & CancellableEntityLibEvent> void callCancellableEvent(@NotNull Class<E> eventClass, @NotNull E event, @NotNull Runnable successCallback) {
      this.callEvent(eventClass, event);
      if (!((CancellableEntityLibEvent)event).isCancelled()) {
         successCallback.run();
      }

   }

   default <E extends EntityLibEvent> void runEvent(@NotNull Collection<EventCallback> eventCallbacks, @NotNull E event) {
      for(EventCallback<E> eventCallback : eventCallbacks) {
         eventCallback.run(event);
      }

   }
}
