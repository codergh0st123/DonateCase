package com.jodexindustries.donatecase.api.event.player;

import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import net.kyori.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class CaseInteractEvent extends DCEvent implements Cancellable {
   private final @NotNull DCPlayer player;
   private final @NotNull CaseInfo caseInfo;
   private final @NotNull Action action;
   private boolean cancelled;

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
      } else if (!(o instanceof CaseInteractEvent)) {
         return false;
      } else {
         CaseInteractEvent other = (CaseInteractEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else if (this.cancelled() != other.cancelled()) {
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

            Object this$caseInfo = this.caseInfo();
            Object other$caseInfo = other.caseInfo();
            if (this$caseInfo == null) {
               if (other$caseInfo != null) {
                  return false;
               }
            } else if (!this$caseInfo.equals(other$caseInfo)) {
               return false;
            }

            Object this$action = this.action();
            Object other$action = other.action();
            if (this$action == null) {
               if (other$action != null) {
                  return false;
               }
            } else if (!this$action.equals(other$action)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof CaseInteractEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      result = result * 59 + (this.cancelled() ? 79 : 97);
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      Object $caseInfo = this.caseInfo();
      result = result * 59 + ($caseInfo == null ? 43 : $caseInfo.hashCode());
      Object $action = this.action();
      result = result * 59 + ($action == null ? 43 : $action.hashCode());
      return result;
   }

   @Generated
   public CaseInteractEvent(@NotNull DCPlayer player, @NotNull CaseInfo caseInfo, @NotNull Action action) {
      this.player = player;
      this.caseInfo = caseInfo;
      this.action = action;
   }

   @Generated
   public @NotNull DCPlayer player() {
      return this.player;
   }

   @Generated
   public @NotNull CaseInfo caseInfo() {
      return this.caseInfo;
   }

   @Generated
   public @NotNull Action action() {
      return this.action;
   }

   @Generated
   public String toString() {
      return "CaseInteractEvent(player=" + this.player() + ", caseInfo=" + this.caseInfo() + ", action=" + this.action() + ", cancelled=" + this.cancelled() + ")";
   }

   public static enum Action {
      RIGHT,
      LEFT;

      // $FF: synthetic method
      private static Action[] $values() {
         return new Action[]{RIGHT, LEFT};
      }
   }
}
