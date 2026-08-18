package com.jodexindustries.donatecase.api.data.storage;

import lombok.Generated;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class CaseInfo {
   private String type;
   private CaseLocation location;

   public CaseInfo() {
   }

   @Generated
   public String type() {
      return this.type;
   }

   @Generated
   public CaseLocation location() {
      return this.location;
   }

   @Generated
   public CaseInfo type(String type) {
      this.type = type;
      return this;
   }

   @Generated
   public CaseInfo location(CaseLocation location) {
      this.location = location;
      return this;
   }

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CaseInfo)) {
         return false;
      } else {
         CaseInfo other = (CaseInfo)o;
         if (!other.canEqual(this)) {
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

            Object this$location = this.location();
            Object other$location = other.location();
            if (this$location == null) {
               if (other$location != null) {
                  return false;
               }
            } else if (!this$location.equals(other$location)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof CaseInfo;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $type = this.type();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $location = this.location();
      result = result * 59 + ($location == null ? 43 : $location.hashCode());
      return result;
   }

   @Generated
   public String toString() {
      return "CaseInfo(type=" + this.type() + ", location=" + this.location() + ")";
   }

   @Generated
   public CaseInfo(String type, CaseLocation location) {
      this.type = type;
      this.location = location;
   }
}
