package com.jodexindustries.donatecase.api.event.addon;

import com.jodexindustries.donatecase.api.addon.InternalAddon;
import com.jodexindustries.donatecase.api.addon.PowerReason;
import com.jodexindustries.donatecase.api.event.DCEvent;
import lombok.Generated;

public class AddonDisableEvent extends DCEvent {
   private final InternalAddon addon;
   private final PowerReason reason;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AddonDisableEvent)) {
         return false;
      } else {
         AddonDisableEvent other = (AddonDisableEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else {
            Object this$addon = this.addon();
            Object other$addon = other.addon();
            if (this$addon == null) {
               if (other$addon != null) {
                  return false;
               }
            } else if (!this$addon.equals(other$addon)) {
               return false;
            }

            Object this$reason = this.reason();
            Object other$reason = other.reason();
            if (this$reason == null) {
               if (other$reason != null) {
                  return false;
               }
            } else if (!this$reason.equals(other$reason)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof AddonDisableEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $addon = this.addon();
      result = result * 59 + ($addon == null ? 43 : $addon.hashCode());
      Object $reason = this.reason();
      result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
      return result;
   }

   @Generated
   public AddonDisableEvent(InternalAddon addon, PowerReason reason) {
      this.addon = addon;
      this.reason = reason;
   }

   @Generated
   public InternalAddon addon() {
      return this.addon;
   }

   @Generated
   public PowerReason reason() {
      return this.reason;
   }

   @Generated
   public String toString() {
      return "AddonDisableEvent(addon=" + this.addon() + ", reason=" + this.reason() + ")";
   }
}
