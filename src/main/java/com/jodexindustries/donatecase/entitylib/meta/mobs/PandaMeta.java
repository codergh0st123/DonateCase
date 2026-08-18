package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;
import org.jetbrains.annotations.NotNull;

public class PandaMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 23;
   private static final byte SNEEZING_BIT = 2;
   private static final byte ROLLING_BIT = 4;
   private static final byte SITTING_BIT = 8;
   private static final byte ON_BACK_BIT = 16;

   public PandaMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getBreedTimer() {
      return (Integer)super.metadata.getIndex((byte)17, 0);
   }

   public void setBreedTimer(int value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, value);
   }

   public int getSneezeTimer() {
      return (Integer)super.metadata.getIndex(offset((byte)17, 1), 0);
   }

   public void setSneezeTimer(int value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.INT, value);
   }

   public int getEatTimer() {
      return (Integer)super.metadata.getIndex(offset((byte)17, 2), 0);
   }

   public void setEatTimer(int value) {
      super.metadata.setIndex(offset((byte)17, 2), EntityDataTypes.INT, value);
   }

   public @NotNull Gene getMainGene() {
      return PandaMeta.Gene.VALUES[(Byte)super.metadata.getIndex(offset((byte)17, 3), (byte)0)];
   }

   public void setMainGene(@NotNull Gene value) {
      super.metadata.setIndex(offset((byte)17, 3), EntityDataTypes.BYTE, (byte)value.ordinal());
   }

   public @NotNull Gene getHiddenGene() {
      return PandaMeta.Gene.VALUES[(Byte)super.metadata.getIndex(offset((byte)17, 4), (byte)0)];
   }

   public void setHiddenGene(@NotNull Gene value) {
      super.metadata.setIndex(offset((byte)17, 4), EntityDataTypes.BYTE, (byte)value.ordinal());
   }

   public boolean isSneezing() {
      return this.getMaskBit(offset((byte)17, 5), (byte)2);
   }

   public void setSneezing(boolean value) {
      this.setMaskBit(offset((byte)17, 5), (byte)2, value);
   }

   public boolean isRolling() {
      return this.getMaskBit(offset((byte)17, 5), (byte)4);
   }

   public void setRolling(boolean value) {
      this.setMaskBit(offset((byte)17, 5), (byte)4, value);
   }

   public boolean isSitting() {
      return this.getMaskBit(offset((byte)17, 5), (byte)8);
   }

   public void setSitting(boolean value) {
      this.setMaskBit(offset((byte)17, 5), (byte)8, value);
   }

   public boolean isOnBack() {
      return this.getMaskBit(offset((byte)17, 5), (byte)16);
   }

   public void setOnBack(boolean value) {
      this.setMaskBit(offset((byte)17, 5), (byte)16, value);
   }

   public static enum Gene {
      NORMAL,
      AGGRESSIVE,
      LAZY,
      WORRIED,
      PLAYFUL,
      WEAK,
      BROWN;

      private static final Gene[] VALUES = values();

      // $FF: synthetic method
      private static Gene[] $values() {
         return new Gene[]{NORMAL, AGGRESSIVE, LAZY, WORRIED, PLAYFUL, WEAK, BROWN};
      }
   }
}
