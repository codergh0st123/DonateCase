package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class GlowSquidMeta extends SquidMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public GlowSquidMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getDarkTicksRemaining() {
      return (Integer)this.metadata.getIndex((byte)16, 0);
   }

   public void setDarkTicksRemaining(int ticks) {
      this.metadata.setIndex((byte)16, EntityDataTypes.INT, ticks);
   }
}
