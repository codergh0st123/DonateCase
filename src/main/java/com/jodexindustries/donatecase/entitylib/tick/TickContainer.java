package com.jodexindustries.donatecase.entitylib.tick;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public class TickContainer<T extends Tickable, H> {
   private final Set<T> tickables = new HashSet<>();
   private H handle;

   public @NotNull Collection<T> getTickables() {
      return Collections.unmodifiableCollection(this.tickables);
   }

   public boolean addTickable(@NotNull T tickable) {
      return this.tickables.add(tickable);
   }

   public boolean removeTickable(T tickable) {
      return this.tickables.remove(tickable);
   }

   public void tick(long time) {
      for(T tickable : this.tickables) {
         tickable.tick(time);
      }

   }

   public @NotNull H getHandle() {
      return this.handle;
   }

   @Internal
   public void setHandle(@NotNull H handle) {
      this.handle = handle;
   }
}
