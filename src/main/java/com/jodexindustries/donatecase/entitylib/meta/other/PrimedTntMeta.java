package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class PrimedTntMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 9;

   public PrimedTntMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getFuseTime() {
      return (Integer)super.metadata.getIndex((byte)8, 80);
   }

   public void setFuseTime(int value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.INT, value);
   }
}
