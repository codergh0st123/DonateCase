package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3f;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.LivingEntityMeta;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMeta extends LivingEntityMeta {
   public static final byte OFFSET = 15;
   public static final byte MAX_OFFSET = 22;
   private static final byte IS_SMALL_BIT = 1;
   private static final byte HAS_ARMS_BIT = 4;
   private static final byte HAS_NO_BASE_PLATE_BIT = 8;
   private static final byte IS_MARKER_BIT = 16;

   public ArmorStandMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isSmall() {
      return this.getMaskBit((byte)15, (byte)1);
   }

   public void setSmall(boolean value) {
      this.setMaskBit(15, (byte)1, value);
   }

   public boolean isHasArms() {
      return this.getMaskBit((byte)15, (byte)4);
   }

   public void setHasArms(boolean value) {
      this.setMaskBit(15, (byte)4, value);
   }

   public boolean isHasNoBasePlate() {
      return this.getMaskBit((byte)15, (byte)8);
   }

   public void setHasNoBasePlate(boolean value) {
      this.setMaskBit(15, (byte)8, value);
   }

   public boolean isMarker() {
      return this.getMaskBit((byte)15, (byte)16);
   }

   public void setMarker(boolean value) {
      this.setMaskBit(15, (byte)16, value);
   }

   public @NotNull Vector3f getHeadRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 1), Vector3f.zero());
   }

   public void setHeadRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 1), EntityDataTypes.ROTATION, value);
   }

   public @NotNull Vector3f getBodyRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 2), Vector3f.zero());
   }

   public void setBodyRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 2), EntityDataTypes.ROTATION, value);
   }

   public @NotNull Vector3f getLeftArmRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 3), new Vector3f(-10.0F, 0.0F, -10.0F));
   }

   public void setLeftArmRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 3), EntityDataTypes.ROTATION, value);
   }

   public @NotNull Vector3f getRightArmRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 4), new Vector3f(-15.0F, 0.0F, 10.0F));
   }

   public void setRightArmRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 4), EntityDataTypes.ROTATION, value);
   }

   public @NotNull Vector3f getLeftLegRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 5), new Vector3f(-1.0F, 0.0F, -1.0F));
   }

   public void setLeftLegRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 5), EntityDataTypes.ROTATION, value);
   }

   public @NotNull Vector3f getRightLegRotation() {
      return (Vector3f)super.metadata.getIndex(offset((byte)15, 6), new Vector3f(1.0F, 0.0F, 1.0F));
   }

   public void setRightLegRotation(@NotNull Vector3f value) {
      super.metadata.setIndex(offset((byte)15, 6), EntityDataTypes.ROTATION, value);
   }
}
