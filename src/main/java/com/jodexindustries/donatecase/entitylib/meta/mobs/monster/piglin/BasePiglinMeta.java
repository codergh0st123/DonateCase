package com.jodexindustries.donatecase.entitylib.meta.mobs.monster.piglin;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public abstract class BasePiglinMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   protected BasePiglinMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isImmuneToZombification() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setImmuneToZombification(boolean value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, value);
   }
}
