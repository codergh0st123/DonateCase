package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FoxMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 21;
   private static final byte SITTING_BIT = 1;
   private static final byte CROUCHING_BIT = 4;
   private static final byte INTERESTED_BIT = 8;
   private static final byte POUNCING_BIT = 16;
   private static final byte SLEEPING_BIT = 32;
   private static final byte FACEPLANTED_BIT = 64;
   private static final byte DEFENDING_BIT = -128;

   public FoxMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Type getType() {
      return FoxMeta.Type.VALUES[(Integer)super.metadata.getIndex((byte)17, 0)];
   }

   public void setType(@NotNull Type type) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, type.ordinal());
   }

   public boolean isSitting() {
      return this.getMaskBit(offset((byte)17, 1), (byte)1);
   }

   public void setSitting(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)1, value);
   }

   public boolean isFoxSneaking() {
      return this.getMaskBit(offset((byte)17, 1), (byte)4);
   }

   public void setFoxSneaking(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)4, value);
   }

   public boolean isInterested() {
      return this.getMaskBit(offset((byte)17, 1), (byte)8);
   }

   public void setInterested(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)8, value);
   }

   public boolean isPouncing() {
      return this.getMaskBit(offset((byte)17, 1), (byte)16);
   }

   public void setPouncing(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)16, value);
   }

   public boolean isSleeping() {
      return this.getMaskBit(offset((byte)17, 1), (byte)32);
   }

   public void setSleeping(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)32, value);
   }

   public boolean isFaceplanted() {
      return this.getMaskBit(offset((byte)17, 1), (byte)64);
   }

   public void setFaceplanted(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)64, value);
   }

   public boolean isDefending() {
      return this.getMaskBit(offset((byte)17, 1), (byte)-128);
   }

   public void setDefending(boolean value) {
      this.setMaskBit(offset((byte)17, 1), (byte)-128, value);
   }

   public Optional<UUID> getFirstUUID() {
      return (Optional)super.metadata.getIndex(offset((byte)17, 2), Optional.empty());
   }

   public void setFirstUUID(@Nullable UUID value) {
      super.metadata.setIndex(offset((byte)17, 2), EntityDataTypes.OPTIONAL_UUID, Optional.of(value));
   }

   public Optional<UUID> getSecondUUID() {
      return (Optional)super.metadata.getIndex(offset((byte)17, 3), Optional.empty());
   }

   public void setSecondUUID(@Nullable UUID value) {
      super.metadata.setIndex(offset((byte)17, 3), EntityDataTypes.OPTIONAL_UUID, Optional.of(value));
   }

   public static enum Type {
      RED,
      SNOW;

      private static final Type[] VALUES = values();

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{RED, SNOW};
      }
   }
}
