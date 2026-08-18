package com.jodexindustries.donatecase.api.event.animation;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;

public class AnimationPreStartEvent extends DCEvent {
   private final DCPlayer player;
   private final CaseData caseData;
   private final CaseLocation block;
   private @NotNull CaseDataItem winItem;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AnimationPreStartEvent)) {
         return false;
      } else {
         AnimationPreStartEvent other = (AnimationPreStartEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else {
            Object this$player = this.player();
            Object other$player = other.player();
            if (this$player == null) {
               if (other$player != null) {
                  return false;
               }
            } else if (!this$player.equals(other$player)) {
               return false;
            }

            Object this$caseData = this.caseData();
            Object other$caseData = other.caseData();
            if (this$caseData == null) {
               if (other$caseData != null) {
                  return false;
               }
            } else if (!this$caseData.equals(other$caseData)) {
               return false;
            }

            Object this$block = this.block();
            Object other$block = other.block();
            if (this$block == null) {
               if (other$block != null) {
                  return false;
               }
            } else if (!this$block.equals(other$block)) {
               return false;
            }

            Object this$winItem = this.winItem();
            Object other$winItem = other.winItem();
            if (this$winItem == null) {
               if (other$winItem != null) {
                  return false;
               }
            } else if (!this$winItem.equals(other$winItem)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof AnimationPreStartEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      Object $caseData = this.caseData();
      result = result * 59 + ($caseData == null ? 43 : $caseData.hashCode());
      Object $block = this.block();
      result = result * 59 + ($block == null ? 43 : $block.hashCode());
      Object $winItem = this.winItem();
      result = result * 59 + ($winItem == null ? 43 : $winItem.hashCode());
      return result;
   }

   @Generated
   public AnimationPreStartEvent(DCPlayer player, CaseData caseData, CaseLocation block, @NotNull CaseDataItem winItem) {
      this.player = player;
      this.caseData = caseData;
      this.block = block;
      this.winItem = winItem;
   }

   @Generated
   public DCPlayer player() {
      return this.player;
   }

   @Generated
   public CaseData caseData() {
      return this.caseData;
   }

   @Generated
   public CaseLocation block() {
      return this.block;
   }

   @Generated
   public @NotNull CaseDataItem winItem() {
      return this.winItem;
   }

   @Generated
   public AnimationPreStartEvent winItem(@NotNull CaseDataItem winItem) {
      this.winItem = winItem;
      return this;
   }

   @Generated
   public String toString() {
      return "AnimationPreStartEvent(player=" + this.player() + ", caseData=" + this.caseData() + ", block=" + this.block() + ", winItem=" + this.winItem() + ")";
   }
}
