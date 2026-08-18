package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.WaterMobMeta;

public class BaseFishMeta extends WaterMobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public BaseFishMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isFromBucket() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setFromBucket(boolean value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, value);
   }
}
