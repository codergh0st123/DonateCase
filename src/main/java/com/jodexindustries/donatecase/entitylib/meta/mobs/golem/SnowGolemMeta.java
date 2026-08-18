package com.jodexindustries.donatecase.entitylib.meta.mobs.golem;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class SnowGolemMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public SnowGolemMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isHasPumpkinHat() {
      return (Byte)super.metadata.getIndex((byte)16, (byte)16) == 16;
   }

   public void setHasPumpkinHat(boolean value) {
      byte var = (byte)(value ? 16 : 0);
      super.metadata.setIndex((byte)16, EntityDataTypes.BYTE, var);
   }
}
