package com.jodexindustries.donatecase.entitylib.meta.mobs.horse;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class ChestedHorseMeta extends BaseHorseMeta {
   public static final byte OFFSET = 18;
   public static final byte MAX_OFFSET = 19;

   public ChestedHorseMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isHasChest() {
      return (Boolean)super.metadata.getIndex((byte)18, false);
   }

   public void setHasChest(boolean value) {
      super.metadata.setIndex((byte)18, EntityDataTypes.BOOLEAN, value);
   }
}
