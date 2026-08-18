package com.jodexindustries.donatecase.api.data.storage;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.jodexindustries.donatecase.api.tools.NumberUtils;
import java.util.Random;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;

public class CaseVector implements Cloneable {
   private static final Random random = new Random();
   private static final double epsilon = 1.0E-6;
   protected double x;
   protected double y;
   protected double z;

   public CaseVector() {
      this.x = (double)0.0F;
      this.y = (double)0.0F;
      this.z = (double)0.0F;
   }

   public CaseVector(int x, int y, int z) {
      this.x = (double)x;
      this.y = (double)y;
      this.z = (double)z;
   }

   public CaseVector(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public CaseVector(float x, float y, float z) {
      this.x = (double)x;
      this.y = (double)y;
      this.z = (double)z;
   }

   public @NotNull CaseVector add(@NotNull CaseVector vec) {
      this.x += vec.x;
      this.y += vec.y;
      this.z += vec.z;
      return this;
   }

   public @NotNull CaseVector subtract(@NotNull CaseVector vec) {
      this.x -= vec.x;
      this.y -= vec.y;
      this.z -= vec.z;
      return this;
   }

   public @NotNull CaseVector multiply(@NotNull CaseVector vec) {
      this.x *= vec.x;
      this.y *= vec.y;
      this.z *= vec.z;
      return this;
   }

   public @NotNull CaseVector divide(@NotNull CaseVector vec) {
      this.x /= vec.x;
      this.y /= vec.y;
      this.z /= vec.z;
      return this;
   }

   public @NotNull CaseVector copy(@NotNull CaseVector vec) {
      this.x = vec.x;
      this.y = vec.y;
      this.z = vec.z;
      return this;
   }

   public double length() {
      return Math.sqrt(NumberUtils.square(this.x) + NumberUtils.square(this.y) + NumberUtils.square(this.z));
   }

   public double lengthSquared() {
      return NumberUtils.square(this.x) + NumberUtils.square(this.y) + NumberUtils.square(this.z);
   }

   public double distance(@NotNull CaseVector o) {
      return Math.sqrt(NumberUtils.square(this.x - o.x) + NumberUtils.square(this.y - o.y) + NumberUtils.square(this.z - o.z));
   }

   public double distanceSquared(@NotNull CaseVector o) {
      return NumberUtils.square(this.x - o.x) + NumberUtils.square(this.y - o.y) + NumberUtils.square(this.z - o.z);
   }

   public float angle(@NotNull CaseVector other) {
      double dot = Doubles.constrainToRange(this.dot(other) / (this.length() * other.length()), (double)-1.0F, (double)1.0F);
      return (float)Math.acos(dot);
   }

   public @NotNull CaseVector midpoint(@NotNull CaseVector other) {
      this.x = (this.x + other.x) / (double)2.0F;
      this.y = (this.y + other.y) / (double)2.0F;
      this.z = (this.z + other.z) / (double)2.0F;
      return this;
   }

   public @NotNull CaseVector getMidpoint(@NotNull CaseVector other) {
      double x = (this.x + other.x) / (double)2.0F;
      double y = (this.y + other.y) / (double)2.0F;
      double z = (this.z + other.z) / (double)2.0F;
      return new CaseVector(x, y, z);
   }

   public @NotNull CaseVector multiply(int m) {
      this.x *= (double)m;
      this.y *= (double)m;
      this.z *= (double)m;
      return this;
   }

   public @NotNull CaseVector multiply(double m) {
      this.x *= m;
      this.y *= m;
      this.z *= m;
      return this;
   }

   public @NotNull CaseVector multiply(float m) {
      this.x *= (double)m;
      this.y *= (double)m;
      this.z *= (double)m;
      return this;
   }

   public double dot(@NotNull CaseVector other) {
      return this.x * other.x + this.y * other.y + this.z * other.z;
   }

   public @NotNull CaseVector crossProduct(@NotNull CaseVector o) {
      double newX = this.y * o.z - o.y * this.z;
      double newY = this.z * o.x - o.z * this.x;
      double newZ = this.x * o.y - o.x * this.y;
      this.x = newX;
      this.y = newY;
      this.z = newZ;
      return this;
   }

   public @NotNull CaseVector getCrossProduct(@NotNull CaseVector o) {
      double x = this.y * o.z - o.y * this.z;
      double y = this.z * o.x - o.z * this.x;
      double z = this.x * o.y - o.x * this.y;
      return new CaseVector(x, y, z);
   }

   public @NotNull CaseVector normalize() {
      double length = this.length();
      this.x /= length;
      this.y /= length;
      this.z /= length;
      return this;
   }

   public @NotNull CaseVector zero() {
      this.x = (double)0.0F;
      this.y = (double)0.0F;
      this.z = (double)0.0F;
      return this;
   }

   @NotNull CaseVector normalizeZeros() {
      if (this.x == (double)-0.0F) {
         this.x = (double)0.0F;
      }

      if (this.y == (double)-0.0F) {
         this.y = (double)0.0F;
      }

      if (this.z == (double)-0.0F) {
         this.z = (double)0.0F;
      }

      return this;
   }

   public boolean isInAABB(@NotNull CaseVector min, @NotNull CaseVector max) {
      return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
   }

   public boolean isInSphere(@NotNull CaseVector origin, double radius) {
      return NumberUtils.square(origin.x - this.x) + NumberUtils.square(origin.y - this.y) + NumberUtils.square(origin.z - this.z) <= NumberUtils.square(radius);
   }

   public boolean isNormalized() {
      return Math.abs(this.lengthSquared() - (double)1.0F) < 1.0E-6;
   }

   public @NotNull CaseVector rotateAroundX(double angle) {
      double angleCos = Math.cos(angle);
      double angleSin = Math.sin(angle);
      double y = angleCos * this.y() - angleSin * this.z();
      double z = angleSin * this.y() + angleCos * this.z();
      return this.y(y).z(z);
   }

   public @NotNull CaseVector rotateAroundY(double angle) {
      double angleCos = Math.cos(angle);
      double angleSin = Math.sin(angle);
      double x = angleCos * this.x() + angleSin * this.z();
      double z = -angleSin * this.x() + angleCos * this.z();
      return this.x(x).z(z);
   }

   public @NotNull CaseVector rotateAroundZ(double angle) {
      double angleCos = Math.cos(angle);
      double angleSin = Math.sin(angle);
      double x = angleCos * this.x() - angleSin * this.y();
      double y = angleSin * this.x() + angleCos * this.y();
      return this.x(x).y(y);
   }

   public @NotNull CaseVector rotateAroundAxis(@NotNull CaseVector axis, double angle) throws IllegalArgumentException {
      Preconditions.checkArgument(axis != null, "The provided axis vector was null");
      return this.rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.clone().normalize(), angle);
   }

   public @NotNull CaseVector rotateAroundNonUnitAxis(@NotNull CaseVector axis, double angle) throws IllegalArgumentException {
      Preconditions.checkArgument(axis != null, "The provided axis vector was null");
      double x = this.x();
      double y = this.y();
      double z = this.z();
      double x2 = axis.x();
      double y2 = axis.y();
      double z2 = axis.z();
      double cosTheta = Math.cos(angle);
      double sinTheta = Math.sin(angle);
      double dotProduct = this.dot(axis);
      double xPrime = x2 * dotProduct * ((double)1.0F - cosTheta) + x * cosTheta + (-z2 * y + y2 * z) * sinTheta;
      double yPrime = y2 * dotProduct * ((double)1.0F - cosTheta) + y * cosTheta + (z2 * x - x2 * z) * sinTheta;
      double zPrime = z2 * dotProduct * ((double)1.0F - cosTheta) + z * cosTheta + (-y2 * x + x2 * y) * sinTheta;
      return this.x(xPrime).y(yPrime).z(zPrime);
   }

   public int blockX() {
      return NumberUtils.floor(this.x);
   }

   public int blockY() {
      return NumberUtils.floor(this.y);
   }

   public int blockZ() {
      return NumberUtils.floor(this.z);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof CaseVector)) {
         return false;
      } else {
         CaseVector other = (CaseVector)obj;
         return Math.abs(this.x - other.x) < 1.0E-6 && Math.abs(this.y - other.y) < 1.0E-6 && Math.abs(this.z - other.z) < 1.0E-6 && this.getClass().equals(obj.getClass());
      }
   }

   public int hashCode() {
      int hash = 7;
      hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.x));
      hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.y));
      hash = 79 * hash + Long.hashCode(Double.doubleToLongBits(this.z));
      return hash;
   }

   public @NotNull CaseVector clone() {
      try {
         return (CaseVector)super.clone();
      } catch (CloneNotSupportedException e) {
         throw new Error(e);
      }
   }

   public String toString() {
      return this.x + "," + this.y + "," + this.z;
   }

   public @NotNull CaseLocation toLocation(@NotNull String world) {
      return new CaseLocation(world, this.x, this.y, this.z);
   }

   public @NotNull CaseLocation toLocation(@NotNull String world, float yaw, float pitch) {
      return new CaseLocation(world, this.x, this.y, this.z, yaw, pitch);
   }

   public static @NotNull CaseVector getMinimum(@NotNull CaseVector v1, @NotNull CaseVector v2) {
      return new CaseVector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
   }

   public static @NotNull CaseVector getMaximum(@NotNull CaseVector v1, @NotNull CaseVector v2) {
      return new CaseVector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
   }

   public static @NotNull CaseVector getRandom() {
      return new CaseVector(random.nextDouble(), random.nextDouble(), random.nextDouble());
   }

   @Generated
   public double x() {
      return this.x;
   }

   @Generated
   public double y() {
      return this.y;
   }

   @Generated
   public double z() {
      return this.z;
   }

   @Generated
   public CaseVector x(double x) {
      this.x = x;
      return this;
   }

   @Generated
   public CaseVector y(double y) {
      this.y = y;
      return this;
   }

   @Generated
   public CaseVector z(double z) {
      this.z = z;
      return this;
   }
}
