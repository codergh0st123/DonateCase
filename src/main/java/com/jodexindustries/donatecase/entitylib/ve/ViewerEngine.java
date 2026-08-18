package com.jodexindustries.donatecase.entitylib.ve;

import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class ViewerEngine {
   private final List<ViewerRule> globalRules = new CopyOnWriteArrayList();
   private final Set<WrapperEntity> tracked = Collections.newSetFromMap(new WeakHashMap());
   private final ViewerEngineListener listener = new ViewerEngineListener(this);
   private Executor executor = Executors.newSingleThreadExecutor();

   public void enable() {
      EntityLib.getApi().getPacketEvents().getEventManager().registerListener(this.listener);
   }

   public void disable() {
      EntityLib.getApi().getPacketEvents().getEventManager().unregisterListener(this.listener);
   }

   public Executor getExecutor() {
      return this.executor;
   }

   public void setExecutor(Executor executor) {
      this.executor = executor;
   }

   public void track(@NotNull WrapperEntity entity) {
      this.tracked.add(entity);
   }

   public void clearTracked() {
      this.tracked.clear();
   }

   public @UnmodifiableView Collection<WrapperEntity> getTracked() {
      return Collections.unmodifiableCollection(this.tracked);
   }

   Set<WrapperEntity> getTracked0() {
      return this.tracked;
   }

   public void addViewerRule(@NotNull ViewerRule rule) {
      this.globalRules.add(rule);
   }

   public void removeViewerRule(@NotNull ViewerRule rule) {
      this.globalRules.remove(rule);
   }

   public void removeViewerRule(int index) {
      this.globalRules.remove(index);
   }

   public void clearViewerRules() {
      this.globalRules.clear();
   }

   public @UnmodifiableView Collection<ViewerRule> getViewerRules() {
      return Collections.unmodifiableCollection(this.globalRules);
   }

   public @Nullable ViewerRule getViewerRule(int index) {
      if (this.globalRules.size() >= index - 1) {
         return null;
      } else {
         return index < 0 ? null : (ViewerRule)this.globalRules.get(index);
      }
   }
}
