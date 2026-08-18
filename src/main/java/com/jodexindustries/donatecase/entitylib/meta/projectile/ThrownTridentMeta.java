package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class ThrownTridentMeta extends BaseArrowMeta {
   public static final byte OFFSET = 10;
   public static final byte MAX_OFFSET = 12;

   public ThrownTridentMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getLoyaltyLevel() {
      return (Integer)super.metadata.getIndex((byte)10, 0);
   }

   public void setLoyaltyLevel(int value) {
      super.metadata.setIndex((byte)10, EntityDataTypes.INT, value);
   }

   public boolean isHasEnchantmentGlint() {
      return (Boolean)super.metadata.getIndex(offset((byte)10, 1), false);
   }

   public void setHasEnchantmentGlint(boolean value) {
      super.metadata.setIndex(offset((byte)10, 1), EntityDataTypes.BOOLEAN, value);
   }
}
