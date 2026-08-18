package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class SheepMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;
   private static final byte COLOR_BITS = 15;
   private static final byte SHEARED_BIT = 16;

   public SheepMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getColor() {
      return this.getMask((byte)17) & 15;
   }

   public void setColor(byte color) {
      byte before = this.getMask((byte)17);
      byte mask = (byte)(before & -16);
      mask = (byte)(mask | color & 15);
      if (mask != before) {
         this.setMask((byte)17, mask);
      }

   }

   public boolean isSheared() {
      return this.getMaskBit((byte)17, (byte)16);
   }

   public void setSheared(boolean value) {
      this.setMaskBit(17, (byte)16, value);
   }
}
