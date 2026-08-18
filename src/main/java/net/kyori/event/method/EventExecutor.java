package net.kyori.event.method;

import java.lang.reflect.Method;
import org.checkerframework.checker.nullness.qual.NonNull;

@FunctionalInterface
public interface EventExecutor<E, L> {
   void invoke(final @NonNull L listener, final @NonNull E event) throws Throwable;

   @FunctionalInterface
   public interface Factory<E, L> {
      @NonNull EventExecutor<E, L> create(final @NonNull Object object, final @NonNull Method method) throws Exception;
   }
}
