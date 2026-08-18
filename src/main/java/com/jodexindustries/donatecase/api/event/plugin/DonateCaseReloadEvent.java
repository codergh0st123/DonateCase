package com.jodexindustries.donatecase.api.event.plugin;

import com.jodexindustries.donatecase.api.event.DCEvent;
import lombok.Generated;

public class DonateCaseReloadEvent extends DCEvent {
   private final Type type;

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DonateCaseReloadEvent)) {
         return false;
      } else {
         DonateCaseReloadEvent other = (DonateCaseReloadEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else {
            Object this$type = this.type();
            Object other$type = other.type();
            if (this$type == null) {
               if (other$type != null) {
                  return false;
               }
            } else if (!this$type.equals(other$type)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof DonateCaseReloadEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $type = this.type();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      return result;
   }

   @Generated
   public DonateCaseReloadEvent(Type type) {
      this.type = type;
   }

   @Generated
   public Type type() {
      return this.type;
   }

   @Generated
   public String toString() {
      return "DonateCaseReloadEvent(type=" + this.type() + ")";
   }

   public static enum Type {
      CONFIG,
      CASES;

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{CONFIG, CASES};
      }
   }
}
