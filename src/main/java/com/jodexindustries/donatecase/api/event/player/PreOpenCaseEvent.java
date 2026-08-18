package com.jodexindustries.donatecase.api.event.player;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import net.kyori.event.Cancellable;

public class PreOpenCaseEvent extends DCEvent implements Cancellable {
   private final DCPlayer player;
   private final CaseData caseData;
   private final CaseLocation block;
   private boolean cancelled;
   private boolean ignoreKeys;

   public boolean cancelled() {
      return this.cancelled;
   }

   public void cancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PreOpenCaseEvent)) {
         return false;
      } else {
         PreOpenCaseEvent other = (PreOpenCaseEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else if (this.cancelled() != other.cancelled()) {
            return false;
         } else if (this.ignoreKeys() != other.ignoreKeys()) {
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

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof PreOpenCaseEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      result = result * 59 + (this.cancelled() ? 79 : 97);
      result = result * 59 + (this.ignoreKeys() ? 79 : 97);
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      Object $caseData = this.caseData();
      result = result * 59 + ($caseData == null ? 43 : $caseData.hashCode());
      Object $block = this.block();
      result = result * 59 + ($block == null ? 43 : $block.hashCode());
      return result;
   }

   @Generated
   public PreOpenCaseEvent(DCPlayer player, CaseData caseData, CaseLocation block) {
      this.player = player;
      this.caseData = caseData;
      this.block = block;
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
   public boolean ignoreKeys() {
      return this.ignoreKeys;
   }

   @Generated
   public PreOpenCaseEvent ignoreKeys(boolean ignoreKeys) {
      this.ignoreKeys = ignoreKeys;
      return this;
   }

   @Generated
   public String toString() {
      return "PreOpenCaseEvent(player=" + this.player() + ", caseData=" + this.caseData() + ", block=" + this.block() + ", cancelled=" + this.cancelled() + ", ignoreKeys=" + this.ignoreKeys() + ")";
   }
}
