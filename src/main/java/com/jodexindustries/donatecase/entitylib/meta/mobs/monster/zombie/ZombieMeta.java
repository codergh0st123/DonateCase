package com.jodexindustries.donatecase.entitylib.meta.mobs.monster.zombie;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class ZombieMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 19;

   public ZombieMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isBaby() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setBaby(boolean value) {
      if (this.isBaby() != value) {
         super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, value);
      }
   }

   public boolean isBecomingDrowned() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 2), false);
   }

   public void setBecomingDrowned(boolean value) {
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BOOLEAN, value);
   }
}
