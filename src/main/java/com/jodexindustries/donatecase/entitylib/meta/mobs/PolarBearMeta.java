package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class PolarBearMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public PolarBearMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isStandingUp() {
      return (Boolean)super.metadata.getIndex((byte)17, false);
   }

   public void setStandingUp(boolean value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.BOOLEAN, value);
   }
}
