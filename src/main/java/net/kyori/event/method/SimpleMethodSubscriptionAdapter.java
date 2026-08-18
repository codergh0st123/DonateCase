package net.kyori.event.method;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.kyori.event.EventBus;
import net.kyori.event.EventSubscriber;
import net.kyori.event.ReifiedEvent;
import net.kyori.event.method.annotation.DefaultMethodScanner;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SimpleMethodSubscriptionAdapter<E, L> implements MethodSubscriptionAdapter<L> {
   private final EventBus<E> bus;
   private final EventExecutor.Factory<E, L> factory;
   private final MethodScanner<L> methodScanner;

   public SimpleMethodSubscriptionAdapter(final @NonNull EventBus<E> bus, final EventExecutor.@NonNull Factory<E, L> factory) {
      this(bus, factory, DefaultMethodScanner.get());
   }

   public SimpleMethodSubscriptionAdapter(final @NonNull EventBus<E> bus, final EventExecutor.@NonNull Factory<E, L> factory, final @NonNull MethodScanner<L> methodScanner) {
      this.bus = bus;
      this.factory = factory;
      this.methodScanner = methodScanner;
   }

   public void register(final @NonNull L listener) {
      EventBus var10002 = this.bus;
      this.findSubscribers(listener, var10002::register);
   }

   public void unregister(final @NonNull L listener) {
      this.bus.unregister((Predicate)((h) -> h instanceof MethodEventSubscriber && ((MethodEventSubscriber)h).listener() == listener));
   }

   private void findSubscribers(final @NonNull L listener, final BiConsumer<@NonNull Class<? extends E>, @NonNull EventSubscriber<E>> consumer) {
      for(Method method : listener.getClass().getDeclaredMethods()) {
         if (this.methodScanner.shouldRegister(listener, method)) {
            if (method.getParameterCount() != 1) {
               throw new SubscriberGenerationException("Unable to create an event subscriber for method '" + method + "'. Method must have only one parameter.");
            }

            Class<?> methodParameterType = method.getParameterTypes()[0];
            if (!this.bus.eventType().isAssignableFrom(methodParameterType)) {
               throw new SubscriberGenerationException("Unable to create an event subscriber for method '" + method + "'. Method parameter type '" + methodParameterType + "' does not extend event type '" + this.bus.eventType() + '\'');
            }

            EventExecutor<E, L> executor;
            try {
               executor = this.factory.create(listener, method);
            } catch (Exception e) {
               throw new SubscriberGenerationException("Encountered an exception while creating an event subscriber for method '" + method + '\'', e);
            }

            int postOrder = this.methodScanner.postOrder(listener, method);
            boolean consumeCancelled = this.methodScanner.consumeCancelledEvents(listener, method);
            @SuppressWarnings("unchecked")
            Class<? extends E> eventType = (Class<? extends E>) methodParameterType;
            consumer.accept(eventType, new MethodEventSubscriber(eventType, method, executor, listener, postOrder, consumeCancelled));
         }
      }

   }

   public static final class SubscriberGenerationException extends RuntimeException {
      SubscriberGenerationException(final String message) {
         super(message);
      }

      SubscriberGenerationException(final String message, final Throwable cause) {
         super(message, cause);
      }
   }

   private static final class MethodEventSubscriber<E, L> implements EventSubscriber<E> {
      private final Class<? extends E> event;
      private final @Nullable Type generic;
      private final EventExecutor<E, L> executor;
      private final L listener;
      private final int postOrder;
      private final boolean includeCancelled;

      MethodEventSubscriber(final Class<? extends E> eventClass, final @NonNull Method method, final @NonNull EventExecutor<E, L> executor, final @NonNull L listener, final int postOrder, final boolean includeCancelled) {
         this.event = eventClass;
         this.generic = ReifiedEvent.class.isAssignableFrom(this.event) ? genericType(method.getGenericParameterTypes()[0]) : null;
         this.executor = executor;
         this.listener = listener;
         this.postOrder = postOrder;
         this.includeCancelled = includeCancelled;
      }

      private static @Nullable Type genericType(final Type type) {
         return type instanceof ParameterizedType ? ((ParameterizedType)type).getActualTypeArguments()[0] : null;
      }

      @NonNull L listener() {
         return this.listener;
      }

      public void invoke(final @NonNull E event) throws Throwable {
         this.executor.invoke(this.listener, event);
      }

      public int postOrder() {
         return this.postOrder;
      }

      public boolean consumeCancelledEvents() {
         return this.includeCancelled;
      }

      public @Nullable Type genericType() {
         return this.generic;
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.event, this.generic, this.executor, this.listener, this.postOrder, this.includeCancelled});
      }

      public boolean equals(final Object other) {
         if (this == other) {
            return true;
         } else if (other != null && other instanceof MethodEventSubscriber) {
            MethodEventSubscriber<?, ?> that = (MethodEventSubscriber)other;
            return Objects.equals(this.event, that.event) && Objects.equals(this.generic, that.generic) && Objects.equals(this.executor, that.executor) && Objects.equals(this.listener, that.listener) && Objects.equals(this.postOrder, that.postOrder) && Objects.equals(this.includeCancelled, that.includeCancelled);
         } else {
            return false;
         }
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("event", this.event).add("generic", this.generic).add("executor", this.executor).add("listener", this.listener).add("priority", this.postOrder).add("includeCancelled", this.includeCancelled).toString();
      }
   }
}
