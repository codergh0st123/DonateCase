package com.jodexindustries.donatecase.api.data.storage;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.tools.NumberUtils;
import java.lang.reflect.Type;
import java.util.Objects;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class CaseLocation implements Cloneable, TypeSerializer<CaseLocation> {
   private String world;
   private double x;
   private double y;
   private double z;
   private float pitch;
   private float yaw;

   public CaseLocation() {
   }

   public CaseLocation(double x, double y, double z) {
      this((String)null, x, y, z);
   }

   public CaseLocation(@Nullable String world, double x, double y, double z) {
      this(world, x, y, z, 0.0F, 0.0F);
   }

   public CaseLocation(@Nullable String world, double x, double y, double z, float pitch, float yaw) {
      this.world = world;
      this.x = x;
      this.y = y;
      this.z = z;
      this.pitch = pitch;
      this.yaw = yaw;
   }

   public @Nullable CaseWorld getWorld() {
      return this.world == null ? null : DCAPI.getInstance().getPlatform().getWorld(this.world);
   }

   public int hashCode() {
      int hash = 3;
      hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
      hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.x));
      hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.y));
      hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.z));
      hash = 19 * hash + Float.floatToIntBits(this.pitch);
      hash = 19 * hash + Float.floatToIntBits(this.yaw);
      return hash;
   }

   public @NotNull CaseLocation add(double x, double y, double z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public @NotNull CaseLocation add(@NotNull CaseLocation loc) {
      this.x += loc.x;
      this.y += loc.y;
      this.z += loc.z;
      return this;
   }

   public @NotNull CaseLocation add(@NotNull CaseVector vec) {
      this.x += vec.x();
      this.y += vec.y();
      this.z += vec.z();
      return this;
   }

   public @NotNull CaseVector getDirection() {
      CaseVector vector = new CaseVector();
      double rotX = (double)this.yaw();
      double rotY = (double)this.pitch();
      vector.y(-Math.sin(Math.toRadians(rotY)));
      double xz = Math.cos(Math.toRadians(rotY));
      vector.x(-xz * Math.sin(Math.toRadians(rotX)));
      vector.z(xz * Math.cos(Math.toRadians(rotX)));
      return vector;
   }

   public @NotNull CaseLocation setDirection(@NotNull CaseLocation vector) {
      double _2PI = (Math.PI * 2D);
      double x = vector.x;
      double z = vector.z();
      if (x == (double)0.0F && z == (double)0.0F) {
         this.pitch = vector.y() > (double)0.0F ? -90.0F : 90.0F;
         return this;
      } else {
         double theta = Math.atan2(-x, z);
         this.yaw = (float)Math.toDegrees((theta + (Math.PI * 2D)) % (Math.PI * 2D));
         double x2 = NumberUtils.square(x);
         double z2 = NumberUtils.square(z);
         double xz = Math.sqrt(x2 + z2);
         this.pitch = (float)Math.toDegrees(Math.atan(-vector.y() / xz));
         return this;
      }
   }

   public double distance(@NotNull CaseLocation o) {
      return Math.sqrt(this.distanceSquared(o));
   }

   public double distanceSquared(@NotNull CaseLocation o) {
      return NumberUtils.square(this.x - o.x) + NumberUtils.square(this.y - o.y) + NumberUtils.square(this.z - o.z);
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         CaseLocation other = (CaseLocation)object;
         return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x) && Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y) && Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z) && Objects.equals(this.world, other.world);
      } else {
         return false;
      }
   }

   public CaseLocation clone() {
      try {
         return (CaseLocation)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   public CaseLocation deserialize(Type type, ConfigurationNode node) {
      CaseLocation location = new CaseLocation();
      location.x = node.node(new Object[]{"x"}).getDouble();
      location.y = node.node(new Object[]{"y"}).getDouble();
      location.z = node.node(new Object[]{"z"}).getDouble();
      location.pitch = node.node(new Object[]{"pitch"}).getFloat();
      location.yaw = node.node(new Object[]{"yaw"}).getFloat();
      location.world = node.node(new Object[]{"world"}).getString();
      return location;
   }

   public void serialize(Type type, @org.checkerframework.checker.nullness.qual.Nullable CaseLocation obj, ConfigurationNode node) throws SerializationException {
      if (obj != null) {
         node.node(new Object[]{"x"}).set(obj.x);
         node.node(new Object[]{"y"}).set(obj.y);
         node.node(new Object[]{"z"}).set(obj.z);
         if (obj.pitch != 0.0F) {
            node.node(new Object[]{"pitch"}).set(obj.pitch);
         }

         if (obj.yaw != 0.0F) {
            node.node(new Object[]{"yaw"}).set(obj.yaw);
         }

         CaseWorld world = obj.getWorld();
         if (world != null) {
            node.node(new Object[]{"world"}).set(world.name());
         }

      }
   }

   public @org.checkerframework.checker.nullness.qual.Nullable CaseLocation emptyValue(Type specificType, ConfigurationOptions options) {
      return new CaseLocation();
   }

   public String toString() {
      return "CaseLocation{world='" + this.world + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", pitch=" + this.pitch + ", yaw=" + this.yaw + '}';
   }

   @Generated
   public String world() {
      return this.world;
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
   public float pitch() {
      return this.pitch;
   }

   @Generated
   public float yaw() {
      return this.yaw;
   }

   @Generated
   public CaseLocation world(String world) {
      this.world = world;
      return this;
   }

   @Generated
   public CaseLocation x(double x) {
      this.x = x;
      return this;
   }

   @Generated
   public CaseLocation y(double y) {
      this.y = y;
      return this;
   }

   @Generated
   public CaseLocation z(double z) {
      this.z = z;
      return this;
   }

   @Generated
   public CaseLocation pitch(float pitch) {
      this.pitch = pitch;
      return this;
   }

   @Generated
   public CaseLocation yaw(float yaw) {
      this.yaw = yaw;
      return this;
   }
}
