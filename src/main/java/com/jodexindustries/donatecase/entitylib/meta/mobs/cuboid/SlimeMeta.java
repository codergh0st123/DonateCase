package com.jodexindustries.donatecase.entitylib.meta.mobs.cuboid;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class SlimeMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public SlimeMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getSize() {
      return (Integer)super.metadata.getIndex((byte)16, 0);
   }

   public void setSize(int value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.INT, value);
   }
}
