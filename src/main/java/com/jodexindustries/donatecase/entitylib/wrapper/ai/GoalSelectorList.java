package com.jodexindustries.donatecase.entitylib.wrapper.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.UnaryOperator;

final class GoalSelectorList extends ArrayList<GoalSelector> {
   final AIGroup aiGroup;

   GoalSelectorList(AIGroup aiGroup) {
      this.aiGroup = aiGroup;
   }

   public GoalSelector set(int index, GoalSelector element) {
      element.setAIGroup(this.aiGroup);
      return (GoalSelector)super.set(index, element);
   }

   public boolean add(GoalSelector element) {
      element.setAIGroup(this.aiGroup);
      return super.add(element);
   }

   public void add(int index, GoalSelector element) {
      element.setAIGroup(this.aiGroup);
      super.add(index, element);
   }

   public boolean addAll(Collection<? extends GoalSelector> c) {
      c.forEach((goalSelector) -> goalSelector.setAIGroup(this.aiGroup));
      return super.addAll(c);
   }

   public boolean addAll(int index, Collection<? extends GoalSelector> c) {
      c.forEach((goalSelector) -> goalSelector.setAIGroup(this.aiGroup));
      return super.addAll(index, c);
   }

   public void replaceAll(UnaryOperator<GoalSelector> operator) {
      super.replaceAll((goalSelector) -> {
         goalSelector = (GoalSelector)operator.apply(goalSelector);
         goalSelector.setAIGroup(this.aiGroup);
         return goalSelector;
      });
   }
}
