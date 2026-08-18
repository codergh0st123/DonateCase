package com.jodexindustries.donatecase.entitylib.meta.mobs.monster.piglin;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class PiglinMeta extends BasePiglinMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 20;

   public PiglinMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isBaby() {
      return (Boolean)super.metadata.getIndex((byte)17, false);
   }

   public void setBaby(boolean value) {
      if (this.isBaby() != value) {
         super.metadata.setIndex((byte)17, EntityDataTypes.BOOLEAN, value);
      }
   }

   public boolean isChargingCrossbow() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 1), false);
   }

   public void setChargingCrossbow(boolean value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isDancing() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 2), false);
   }

   public void setDancing(boolean value) {
      super.metadata.setIndex(offset((byte)17, 2), EntityDataTypes.BOOLEAN, value);
   }
}
