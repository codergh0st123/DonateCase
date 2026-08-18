package com.jodexindustries.donatecase.common.event;

import com.google.common.collect.SetMultimap;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.event.EventBus;
import com.jodexindustries.donatecase.api.event.Subscriber;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.kyori.event.EventSubscriber;
import net.kyori.event.PostResult;
import net.kyori.event.SimpleEventBus;
import net.kyori.event.method.MethodHandleEventExecutorFactory;
import net.kyori.event.method.MethodSubscriptionAdapter;
import net.kyori.event.method.SimpleMethodSubscriptionAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class EventBusImpl implements EventBus {
   private final Logger logger;
   private final SimpleEventBus<DCEvent> bus = new SimpleEventBus<DCEvent>(DCEvent.class);
   private final MethodSubscriptionAdapter<Subscriber> methodAdapter = new SimpleMethodSubscriptionAdapter(this, new MethodHandleEventExecutorFactory());

   public EventBusImpl(Logger logger) {
      this.logger = logger;
   }

   public @NonNull Class<DCEvent> eventType() {
      return this.bus.eventType();
   }

   public @NonNull PostResult post(@NonNull DCEvent event) {
      PostResult postResult = this.bus.post(event);
      if (!postResult.wasSuccessful()) {
         this.logger.log(Level.WARNING, "Failed to post event: " + event.getClass().getSimpleName());
         postResult.exceptions().forEach((subscriber, exception) -> this.logger.log(Level.WARNING, "Subscriber: " + subscriber, exception));
      }

      return postResult;
   }

   public void register(@NotNull Subscriber listener) {
      try {
         this.methodAdapter.register(listener);
      } catch (Exception e) {
         this.logger.log(Level.WARNING, "Error with event listener " + listener.getClass() + " registration:", e);
      }

   }

   public <T extends DCEvent> void register(@NonNull Class<T> clazz, @NonNull EventSubscriber<? super T> subscriber) {
      try {
         this.bus.register(clazz, subscriber);
      } catch (Exception e) {
         this.logger.log(Level.WARNING, "Error with event subscriber " + subscriber.getClass() + " registration:", e);
      }

   }

   public void unregister(@NotNull Subscriber listener) {
      this.methodAdapter.unregister(listener);
   }

   public void unregister(@NonNull EventSubscriber<?> subscriber) {
      this.bus.unregister(subscriber);
   }

   public void unregister(@NonNull Predicate<EventSubscriber<?>> predicate) {
      this.bus.unregister(predicate);
   }

   public void unregisterAll() {
      this.bus.unregisterAll();
   }

   public <T extends DCEvent> boolean hasSubscribers(@NonNull Class<T> clazz) {
      return this.bus.hasSubscribers(clazz);
   }

   public @NonNull SetMultimap<Class<?>, EventSubscriber<?>> subscribers() {
      return this.bus.subscribers();
   }
}
