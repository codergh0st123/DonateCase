package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class WardenMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public WardenMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getAngerLevel() {
      return (Integer)super.metadata.getIndex((byte)16, 0);
   }

   public void setAngerLevel(int value) {
      if (this.getAngerLevel() != value) {
         super.metadata.setIndex((byte)16, EntityDataTypes.INT, value);
      }
   }
}
