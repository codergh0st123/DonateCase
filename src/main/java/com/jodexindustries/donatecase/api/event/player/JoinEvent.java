package com.jodexindustries.donatecase.api.event.player;

import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;

public class JoinEvent extends DCEvent {
   private final @NotNull DCPlayer player;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof JoinEvent)) {
         return false;
      } else {
         JoinEvent other = (JoinEvent)o;
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

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof JoinEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      return result;
   }

   @Generated
   public JoinEvent(@NotNull DCPlayer player) {
      this.player = player;
   }

   @Generated
   public @NotNull DCPlayer player() {
      return this.player;
   }

   @Generated
   public String toString() {
      return "JoinEvent(player=" + this.player() + ")";
   }
}
