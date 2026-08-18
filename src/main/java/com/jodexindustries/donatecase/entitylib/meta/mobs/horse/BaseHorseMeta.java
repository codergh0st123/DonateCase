package com.jodexindustries.donatecase.entitylib.meta.mobs.horse;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseHorseMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 18;
   private static final byte TAMED_BIT = 2;
   private static final byte SADDLED_BIT = 4;
   private static final byte HAS_BRED_BIT = 8;
   private static final byte EATING_BIT = 16;
   private static final byte REARING_BIT = 32;
   private static final byte MOUTH_OPEN_BIT = 64;

   protected BaseHorseMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isTamed() {
      return this.getMaskBit((byte)16, (byte)2);
   }

   public void setTamed(boolean value) {
      this.setMaskBit(16, (byte)2, value);
   }

   public boolean isSaddled() {
      return this.getMaskBit((byte)16, (byte)4);
   }

   public void setSaddled(boolean value) {
      this.setMaskBit(16, (byte)4, value);
   }

   public boolean isHasBred() {
      return this.getMaskBit((byte)16, (byte)8);
   }

   public void setHasBred(boolean value) {
      this.setMaskBit(16, (byte)8, value);
   }

   public boolean isEating() {
      return this.getMaskBit((byte)16, (byte)16);
   }

   public void setEating(boolean value) {
      this.setMaskBit(16, (byte)16, value);
   }

   public boolean isRearing() {
      return this.getMaskBit((byte)16, (byte)32);
   }

   public void setRearing(boolean value) {
      this.setMaskBit(16, (byte)32, value);
   }

   public boolean isMouthOpen() {
      return this.getMaskBit((byte)16, (byte)64);
   }

   public void setMouthOpen(boolean value) {
      this.setMaskBit(16, (byte)64, value);
   }

   public Optional<UUID> getOwner() {
      return (Optional)super.metadata.getIndex(offset((byte)16, 1), Optional.empty());
   }

   public void setOwner(UUID value) {
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.OPTIONAL_UUID, Optional.of(value));
   }
}
