package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Quaternion4f;
import com.github.retrooper.packetevents.util.Vector3f;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class DisplayMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET;

   public DisplayMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
      isVersionNewer(ServerVersion.V_1_19_3);
   }

   public int getInterpolationDelay() {
      return (Integer)super.metadata.getIndex((byte)8, 0);
   }

   public void setInterpolationDelay(int value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.INT, value);
   }

   public int getTransformationInterpolationDuration() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 1), 0);
   }

   public void setTransformationInterpolationDuration(int value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value);
   }

   public int getPositionRotationInterpolationDuration() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 2), 0);
   }

   public void setPositionRotationInterpolationDuration(int value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.INT, value);
   }

   public Vector3f getTranslation() {
      byte offset = offset((byte)8, 3);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 2);
      }

      return (Vector3f)super.metadata.getIndex(offset, Vector3f.zero());
   }

   public void setTranslation(Vector3f value) {
      byte offset = offset((byte)8, 3);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 2);
      }

      super.metadata.setIndex(offset, EntityDataTypes.VECTOR3F, value);
   }

   public Vector3f getScale() {
      byte offset = offset((byte)8, 4);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 3);
      }

      return (Vector3f)super.metadata.getIndex(offset, new Vector3f(1.0F, 1.0F, 1.0F));
   }

   public void setScale(Vector3f value) {
      byte offset = offset((byte)8, 4);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 3);
      }

      super.metadata.setIndex(offset, EntityDataTypes.VECTOR3F, value);
   }

   public Quaternion4f getLeftRotation() {
      byte offset = offset((byte)8, 5);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 4);
      }

      return (Quaternion4f)super.metadata.getIndex(offset, new Quaternion4f(0.0F, 0.0F, 0.0F, 1.0F));
   }

   public void setLeftRotation(Quaternion4f value) {
      byte offset = offset((byte)8, 5);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 4);
      }

      super.metadata.setIndex(offset, EntityDataTypes.QUATERNION, value);
   }

   public Quaternion4f getRightRotation() {
      byte offset = offset((byte)8, 6);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 5);
      }

      return (Quaternion4f)super.metadata.getIndex(offset, new Quaternion4f(0.0F, 0.0F, 0.0F, 1.0F));
   }

   public void setRightRotation(Quaternion4f value) {
      byte offset = offset((byte)8, 6);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 5);
      }

      super.metadata.setIndex(offset, EntityDataTypes.QUATERNION, value);
   }

   public BillboardConstraints getBillboardConstraints() {
      byte offset = offset((byte)8, 7);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 6);
      }

      return DisplayMeta.BillboardConstraints.VALUES[(Byte)super.metadata.getIndex(offset, (byte)0)];
   }

   public void setBillboardConstraints(BillboardConstraints value) {
      byte offset = offset((byte)8, 7);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 6);
      }

      super.metadata.setIndex(offset, EntityDataTypes.BYTE, (byte)value.ordinal());
   }

   public int getBrightnessOverride() {
      byte offset = offset((byte)8, 8);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 7);
      }

      return (Integer)super.metadata.getIndex(offset, -1);
   }

   public void setBrightnessOverride(int value) {
      byte offset = offset((byte)8, 8);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 7);
      }

      super.metadata.setIndex(offset, EntityDataTypes.INT, value);
   }

   public float getViewRange() {
      byte offset = offset((byte)8, 9);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 8);
      }

      return (Float)super.metadata.getIndex(offset, 1.0F);
   }

   public void setViewRange(float value) {
      byte offset = offset((byte)8, 9);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 8);
      }

      super.metadata.setIndex(offset, EntityDataTypes.FLOAT, value);
   }

   public float getShadowRadius() {
      byte offset = offset((byte)8, 10);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 9);
      }

      return (Float)super.metadata.getIndex(offset, 0.0F);
   }

   public void setShadowRadius(float value) {
      byte offset = offset((byte)8, 10);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 9);
      }

      super.metadata.setIndex(offset, EntityDataTypes.FLOAT, value);
   }

   public float getShadowStrength() {
      byte offset = offset((byte)8, 11);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 10);
      }

      return (Float)super.metadata.getIndex(offset, 1.0F);
   }

   public void setShadowStrength(float value) {
      byte offset = offset((byte)8, 11);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 10);
      }

      super.metadata.setIndex(offset, EntityDataTypes.FLOAT, value);
   }

   public float getWidth() {
      byte offset = offset((byte)8, 12);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 11);
      }

      return (Float)super.metadata.getIndex(offset, 0.0F);
   }

   public void setWidth(float value) {
      byte offset = offset((byte)8, 12);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 11);
      }

      super.metadata.setIndex(offset, EntityDataTypes.FLOAT, value);
   }

   public float getHeight() {
      byte offset = offset((byte)8, 13);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 12);
      }

      return (Float)super.metadata.getIndex(offset, 0.0F);
   }

   public void setHeight(float value) {
      byte offset = offset((byte)8, 13);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 12);
      }

      super.metadata.setIndex(offset, EntityDataTypes.FLOAT, value);
   }

   public int getGlowColorOverride() {
      byte offset = offset((byte)8, 14);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 13);
      }

      return (Integer)super.metadata.getIndex(offset, -1);
   }

   public void setGlowColorOverride(int value) {
      byte offset = offset((byte)8, 14);
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.OLDER_THAN)) {
         offset = offset((byte)8, 13);
      }

      super.metadata.setIndex(offset, EntityDataTypes.INT, value);
   }

   static {
      if (isVersion(ServerVersion.V_1_20_2, VersionComparison.NEWER_THAN_OR_EQUALS)) {
         MAX_OFFSET = 23;
      } else {
         MAX_OFFSET = 22;
      }

   }

   public static enum BillboardConstraints {
      FIXED,
      VERTICAL,
      HORIZONTAL,
      CENTER;

      private static final BillboardConstraints[] VALUES = values();

      // $FF: synthetic method
      private static BillboardConstraints[] $values() {
         return new BillboardConstraints[]{FIXED, VERTICAL, HORIZONTAL, CENTER};
      }
   }
}
