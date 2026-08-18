package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class BatMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 16;
   private static final byte IS_HANGING_BIT = 1;

   public BatMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isHanging() {
      return this.getMaskBit((byte)16, (byte)1);
   }

   public void setHanging(boolean value) {
      this.setMaskBit(16, (byte)1, value);
   }
}
