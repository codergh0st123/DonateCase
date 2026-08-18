package com.jodexindustries.donatecase.api.armorstand;

import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EulerAngle {
   public static final EulerAngle ZERO = new EulerAngle((double)0.0F, (double)0.0F, (double)0.0F);
   private final double x;
   private final double y;
   private final double z;

   public EulerAngle(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public EulerAngle() {
      this.x = (double)0.0F;
      this.y = (double)0.0F;
      this.z = (double)0.0F;
   }

   public @NotNull EulerAngle setX(double x) {
      return new EulerAngle(x, this.y, this.z);
   }

   public @NotNull EulerAngle setY(double y) {
      return new EulerAngle(this.x, y, this.z);
   }

   public @NotNull EulerAngle setZ(double z) {
      return new EulerAngle(this.x, this.y, z);
   }

   public @NotNull EulerAngle add(double x, double y, double z) {
      return new EulerAngle(this.x + x, this.y + y, this.z + z);
   }

   public @NotNull EulerAngle subtract(double x, double y, double z) {
      return this.add(-x, -y, -z);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EulerAngle that = (EulerAngle)o;
         return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0 && Double.compare(that.z, this.z) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = Double.hashCode(this.x);
      result = 31 * result + Double.hashCode(this.y);
      result = 31 * result + Double.hashCode(this.z);
      return result;
   }

   public String toString() {
      return "EulerAngle{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
   }

   @Generated
   public double getX() {
      return this.x;
   }

   @Generated
   public double getY() {
      return this.y;
   }

   @Generated
   public double getZ() {
      return this.z;
   }
}
