package com.jodexindustries.donatecase.entitylib.meta.mobs.golem;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class IronGolemMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;
   private static final byte PLAYER_CREATED_BIT = 1;

   public IronGolemMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isPlayerCreated() {
      return this.getMaskBit((byte)16, (byte)1);
   }

   public void setPlayerCreated(boolean value) {
      this.setMaskBit(16, (byte)1, value);
   }
}
