package com.jodexindustries.donatecase.entitylib.event;

import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface EventListener<E extends EntityLibEvent> {
   @NotNull Class<E> getEventClass();

   void handle(@NotNull E var1);

   static <T extends EntityLibEvent> EventCallback<T> createEventCallback(@NotNull EventListener<T> listener) {
      Objects.requireNonNull(listener);
      return listener::handle;
   }

   static <T extends EntityLibEvent> EventListener<T> generateListener(final Class<T> eventClass, final Consumer<T> consumer) {
      return new EventListener<T>() {
         public @NotNull Class<T> getEventClass() {
            return eventClass;
         }

         public void handle(@NotNull T event) {
            consumer.accept(event);
         }
      };
   }
}
