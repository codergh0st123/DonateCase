package com.jodexindustries.donatecase.entitylib.wrapper.ai;

import com.jodexindustries.donatecase.entitylib.tick.Tickable;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AIGroup implements Tickable {
   private final List<GoalSelector> goalSelectors = new GoalSelectorList(this);
   private GoalSelector currentGoalSelector;
   private boolean ticking = true;

   public @NotNull Collection<GoalSelector> getGoalSelectors() {
      return this.goalSelectors;
   }

   public @Nullable GoalSelector getCurrentGoal() {
      return this.currentGoalSelector;
   }

   public void addGoalSelector(@NotNull GoalSelector goalSelector) {
      this.goalSelectors.add(goalSelector);
   }

   public void setCurrentGoal(@Nullable GoalSelector goalSelector) {
      if (goalSelector != null && goalSelector.getAIGroup() != this) {
         throw new IllegalArgumentException("GoalSelector is not in this AIGroup");
      } else {
         this.currentGoalSelector = goalSelector;
      }
   }

   public boolean isTicking() {
      return this.ticking;
   }

   public void setTicking(boolean ticking) {
      this.ticking = ticking;
   }

   public void tick(long time) {
      GoalSelector currentGoalSelector = this.getCurrentGoal();
      if (currentGoalSelector != null && currentGoalSelector.shouldEnd()) {
         currentGoalSelector.end();
         currentGoalSelector = null;
         this.setCurrentGoal((GoalSelector)null);
      }

      for(GoalSelector selector : this.getGoalSelectors()) {
         if (selector == currentGoalSelector) {
            break;
         }

         if (selector.shouldStart()) {
            if (currentGoalSelector != null) {
               currentGoalSelector.end();
            }

            currentGoalSelector = selector;
            this.setCurrentGoal(selector);
            selector.start();
            break;
         }
      }

      if (currentGoalSelector != null) {
         currentGoalSelector.tick(time);
      }

   }
}
