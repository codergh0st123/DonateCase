package com.jodexindustries.donatecase.entitylib.meta.mobs.horse;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class CamelMeta extends BaseHorseMeta {
   public static final byte OFFSET = 18;
   public static final byte MAX_OFFSET = 20;

   public CamelMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isDashing() {
      return (Boolean)super.metadata.getIndex((byte)18, false);
   }

   public void setDashing(boolean value) {
      super.metadata.setIndex((byte)18, EntityDataTypes.BOOLEAN, value);
   }

   public long getLastPoseChangeTick() {
      return (Long)super.metadata.getIndex(offset((byte)18, 1), 0L);
   }

   public void setLastPoseChangeTick(long value) {
      super.metadata.setIndex(offset((byte)18, 1), EntityDataTypes.LONG, value);
   }
}
