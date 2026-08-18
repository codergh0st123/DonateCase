package com.jodexindustries.donatecase.entitylib.wrapper.ai;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntityCreature;
import java.lang.ref.WeakReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GoalSelector {
   private WeakReference<AIGroup> aiGroupRef;
   protected WrapperEntityCreature entity;

   public GoalSelector(WrapperEntityCreature entity) {
      this.entity = entity;
   }

   public abstract boolean shouldStart();

   public abstract void start();

   public abstract void tick(long var1);

   public abstract boolean shouldEnd();

   public abstract void end();

   public @NotNull WrapperEntityCreature getEntityCreature() {
      return this.entity;
   }

   public void setEntityCreature(@NotNull WrapperEntityCreature entity) {
      this.entity = entity;
   }

   void setAIGroup(@NotNull AIGroup group) {
      this.aiGroupRef = new WeakReference(group);
   }

   protected @Nullable AIGroup getAIGroup() {
      return (AIGroup)this.aiGroupRef.get();
   }
}
