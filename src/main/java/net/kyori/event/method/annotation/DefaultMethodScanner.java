package net.kyori.event.method.annotation;

import java.lang.reflect.Method;
import net.kyori.event.method.MethodScanner;
import org.checkerframework.checker.nullness.qual.NonNull;

public class DefaultMethodScanner<L> implements MethodScanner<L> {
   private static final DefaultMethodScanner INSTANCE = new DefaultMethodScanner();

   public static <L> @NonNull MethodScanner<L> get() {
      return INSTANCE;
   }

   protected DefaultMethodScanner() {
   }

   public boolean shouldRegister(final @NonNull L listener, final @NonNull Method method) {
      return method.getAnnotation(Subscribe.class) != null;
   }

   public int postOrder(final @NonNull L listener, final @NonNull Method method) {
      return method.isAnnotationPresent(PostOrder.class) ? ((PostOrder)method.getAnnotation(PostOrder.class)).value() : 0;
   }

   public boolean consumeCancelledEvents(final @NonNull L listener, final @NonNull Method method) {
      return !method.isAnnotationPresent(IgnoreCancelled.class);
   }
}
