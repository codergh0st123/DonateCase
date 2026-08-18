package com.jodexindustries.donatecase.entitylib.meta.mobs.minecart;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class FurnaceMinecartMeta extends BaseMinecartMeta {
   public static final byte OFFSET = 14;
   public static final byte MAX_OFFSET = 15;

   public FurnaceMinecartMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isHasFuel() {
      return (Boolean)super.metadata.getIndex((byte)14, false);
   }

   public void setHasFuel(boolean value) {
      super.metadata.setIndex((byte)14, EntityDataTypes.BOOLEAN, value);
   }

   public int getObjectData() {
      return 2;
   }
}
