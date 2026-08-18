package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class AgeableMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public AgeableMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isBaby() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setBaby(boolean value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, value);
   }
}
