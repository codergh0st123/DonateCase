package net.kyori.event;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SimpleEventBus<E> implements EventBus<E> {
   private final Class<E> type;
   private final SubscriberRegistry<E> registry = new SubscriberRegistry<E>();

   public SimpleEventBus(final @NonNull Class<E> type) {
      this.type = (Class)Objects.requireNonNull(type, "type");
   }

   public @NonNull Class<E> eventType() {
      return this.type;
   }

   protected boolean eventCancelled(final @NonNull E event) {
      return event instanceof Cancellable && ((Cancellable)event).cancelled();
   }

   protected @Nullable Type eventGenericType(final @NonNull E event) {
      return event instanceof ReifiedEvent ? ((ReifiedEvent)event).type().getType() : null;
   }

   protected boolean shouldPost(final @NonNull E event, final @NonNull EventSubscriber<?> subscriber) {
      return !subscriber.consumeCancelledEvents() && this.eventCancelled(event) ? false : Objects.equals(this.eventGenericType(event), subscriber.genericType());
   }

   public @NonNull PostResult post(final @NonNull E event) {
      ImmutableMap.Builder<EventSubscriber<?>, Throwable> exceptions = null;

      for(EventSubscriber subscriber : this.registry.subscribers(event.getClass())) {
         if (this.shouldPost(event, subscriber)) {
            try {
               subscriber.invoke(event);
            } catch (Throwable e) {
               if (exceptions == null) {
                  exceptions = ImmutableMap.builder();
               }

               exceptions.put(subscriber, e);
            }
         }
      }

      if (exceptions == null) {
         return PostResult.success();
      } else {
         return PostResult.failure(exceptions.build());
      }
   }

   public <T extends E> void register(final @NonNull Class<T> clazz, final @NonNull EventSubscriber<? super T> subscriber) {
      Preconditions.checkArgument(this.type.isAssignableFrom(clazz), "clazz " + clazz + " cannot be casted to event type " + this.type);
      this.registry.register(clazz, subscriber);
   }

   public void unregister(final @NonNull EventSubscriber<?> subscriber) {
      this.registry.unregister(subscriber);
   }

   public void unregister(final @NonNull Predicate<EventSubscriber<?>> predicate) {
      this.registry.unregisterMatching(predicate);
   }

   public void unregisterAll() {
      this.registry.unregisterAll();
   }

   public <T extends E> boolean hasSubscribers(final @NonNull Class<T> clazz) {
      Preconditions.checkArgument(this.type.isAssignableFrom(clazz), "clazz " + clazz + " cannot be casted to event type " + this.type);
      return !this.registry.subscribers(clazz).isEmpty();
   }

   public @NonNull SetMultimap<Class<?>, EventSubscriber<?>> subscribers() {
      return this.registry.subscribers();
   }
}
