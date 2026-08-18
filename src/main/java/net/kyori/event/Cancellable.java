package net.kyori.event;

public interface Cancellable {
   boolean cancelled();

   void cancelled(final boolean cancelled);

   public abstract static class Impl implements Cancellable {
      protected boolean cancelled;

      public boolean cancelled() {
         return this.cancelled;
      }

      public void cancelled(final boolean cancelled) {
         this.cancelled = cancelled;
      }
   }
}
