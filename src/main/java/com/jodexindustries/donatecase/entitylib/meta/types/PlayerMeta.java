package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import org.jetbrains.annotations.Nullable;

public class PlayerMeta extends LivingEntityMeta {
   public static final byte OFFSET = 15;
   public static final byte MAX_OFFSET = 16;
   private static final byte CAPE_BIT = 1;
   private static final byte JACKET_BIT = 2;
   private static final byte LEFT_SLEEVE_BIT = 4;
   private static final byte RIGHT_SLEEVE_BIT = 8;
   private static final byte LEFT_LEG_BIT = 16;
   private static final byte RIGHT_LEG_BIT = 32;
   private static final byte HAT_BIT = 64;

   public PlayerMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public float getAdditionalHearts() {
      return (Float)super.metadata.getIndex((byte)15, 0.0F);
   }

   public void setAdditionalHearts(float value) {
      super.metadata.setIndex((byte)15, EntityDataTypes.FLOAT, value);
   }

   public int getScore() {
      return (Integer)super.metadata.getIndex(offset((byte)15, 1), 0);
   }

   public void setScore(int value) {
      super.metadata.setIndex(offset((byte)15, 1), EntityDataTypes.INT, value);
   }

   public boolean isCapeEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)1);
   }

   public void setCapeEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)1, value);
   }

   public boolean isJacketEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)2);
   }

   public void setJacketEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)2, value);
   }

   public boolean isLeftSleeveEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)4);
   }

   public void setLeftSleeveEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)4, value);
   }

   public boolean isRightSleeveEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)8);
   }

   public void setRightSleeveEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)8, value);
   }

   public boolean isLeftLegEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)16);
   }

   public void setLeftLegEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)16, value);
   }

   public boolean isRightLegEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)32);
   }

   public void setRightLegEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)32, value);
   }

   public boolean isHatEnabled() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit(offset((byte)15, 2), (byte)64);
   }

   public void setHatEnabled(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(offset((byte)15, 2), (byte)64, value);
   }

   public boolean isRightHandMain() {
      if (EntityLib.getApi().getPacketEvents().getServerManager().getVersion().isOlderThan(ServerVersion.V_1_9)) {
         return true;
      } else {
         return (Byte)super.metadata.getIndex(offset((byte)15, 3), (byte)1) == 1;
      }
   }

   public void setRightHandMain(boolean value) {
      if (!EntityLib.getApi().getPacketEvents().getServerManager().getVersion().isOlderThan(ServerVersion.V_1_9)) {
         super.metadata.setIndex(offset((byte)15, 3), EntityDataTypes.BYTE, (byte)(value ? 1 : 0));
      }
   }

   public @Nullable NBTCompound getLeftShoulderData() {
      isVersionNewer(ServerVersion.V_1_11);
      return (NBTCompound)super.metadata.getIndex(offset((byte)15, 4), (Object)null);
   }

   public void setLeftShoulderData(@Nullable NBTCompound value) {
      if (value == null) {
         value = new NBTCompound();
      }

      super.metadata.setIndex(offset((byte)15, 4), EntityDataTypes.NBT, value);
   }

   public @Nullable NBTCompound getRightShoulderData() {
      isVersionNewer(ServerVersion.V_1_11);
      return (NBTCompound)super.metadata.getIndex(offset((byte)15, 5), (Object)null);
   }

   public void setRightShoulderData(@Nullable NBTCompound value) {
      if (value == null) {
         value = new NBTCompound();
      }

      super.metadata.setIndex(offset((byte)15, 5), EntityDataTypes.NBT, value);
   }
}
