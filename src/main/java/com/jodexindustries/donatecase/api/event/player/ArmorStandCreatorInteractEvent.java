package com.jodexindustries.donatecase.api.event.player;

import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;

public class ArmorStandCreatorInteractEvent extends DCEvent {
   private final DCPlayer player;
   private final ArmorStandCreator armorStandCreator;
   private final EquipmentSlot hand;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ArmorStandCreatorInteractEvent)) {
         return false;
      } else {
         ArmorStandCreatorInteractEvent other = (ArmorStandCreatorInteractEvent)o;
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

            Object this$armorStandCreator = this.armorStandCreator();
            Object other$armorStandCreator = other.armorStandCreator();
            if (this$armorStandCreator == null) {
               if (other$armorStandCreator != null) {
                  return false;
               }
            } else if (!this$armorStandCreator.equals(other$armorStandCreator)) {
               return false;
            }

            Object this$hand = this.hand();
            Object other$hand = other.hand();
            if (this$hand == null) {
               if (other$hand != null) {
                  return false;
               }
            } else if (!this$hand.equals(other$hand)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof ArmorStandCreatorInteractEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      Object $armorStandCreator = this.armorStandCreator();
      result = result * 59 + ($armorStandCreator == null ? 43 : $armorStandCreator.hashCode());
      Object $hand = this.hand();
      result = result * 59 + ($hand == null ? 43 : $hand.hashCode());
      return result;
   }

   @Generated
   public ArmorStandCreatorInteractEvent(DCPlayer player, ArmorStandCreator armorStandCreator, EquipmentSlot hand) {
      this.player = player;
      this.armorStandCreator = armorStandCreator;
      this.hand = hand;
   }

   @Generated
   public DCPlayer player() {
      return this.player;
   }

   @Generated
   public ArmorStandCreator armorStandCreator() {
      return this.armorStandCreator;
   }

   @Generated
   public EquipmentSlot hand() {
      return this.hand;
   }

   @Generated
   public String toString() {
      return "ArmorStandCreatorInteractEvent(player=" + this.player() + ", armorStandCreator=" + this.armorStandCreator() + ", hand=" + this.hand() + ")";
   }
}
