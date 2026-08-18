package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class VexMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;
   private static final byte ATTACKING_BIT = 1;

   public VexMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isAttacking() {
      return this.getMaskBit((byte)16, (byte)1);
   }

   public void setAttacking(boolean value) {
      this.setMaskBit(16, (byte)1, value);
   }
}
