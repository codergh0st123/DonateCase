package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class GhastMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public GhastMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isAttacking() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setAttacking(boolean value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, value);
   }
}
