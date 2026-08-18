package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class StriderMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 20;

   public StriderMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getTimeToBoost() {
      return (Integer)super.metadata.getIndex((byte)17, 0);
   }

   public void setTimeToBoost(int value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, value);
   }

   public boolean isShaking() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 1), false);
   }

   public void setShaking(boolean value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isHasSaddle() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 2), false);
   }

   public void setHasSaddle(boolean value) {
      super.metadata.setIndex(offset((byte)17, 2), EntityDataTypes.BOOLEAN, value);
   }
}
