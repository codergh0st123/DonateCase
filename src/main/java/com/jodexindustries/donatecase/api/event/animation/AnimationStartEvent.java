package com.jodexindustries.donatecase.api.event.animation;

import com.jodexindustries.donatecase.api.data.ActiveCase;
import com.jodexindustries.donatecase.api.event.DCEvent;
import lombok.Generated;

public class AnimationStartEvent extends DCEvent {
   private final ActiveCase activeCase;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AnimationStartEvent)) {
         return false;
      } else {
         AnimationStartEvent other = (AnimationStartEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else {
            Object this$activeCase = this.activeCase();
            Object other$activeCase = other.activeCase();
            if (this$activeCase == null) {
               if (other$activeCase != null) {
                  return false;
               }
            } else if (!this$activeCase.equals(other$activeCase)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof AnimationStartEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $activeCase = this.activeCase();
      result = result * 59 + ($activeCase == null ? 43 : $activeCase.hashCode());
      return result;
   }

   @Generated
   public AnimationStartEvent(ActiveCase activeCase) {
      this.activeCase = activeCase;
   }

   @Generated
   public ActiveCase activeCase() {
      return this.activeCase;
   }

   @Generated
   public String toString() {
      return "AnimationStartEvent(activeCase=" + this.activeCase() + ")";
   }
}
