package com.jodexindustries.donatecase.entitylib.meta.other;

import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;

public class LlamaSpitMeta extends EntityMeta implements ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 8;

   public LlamaSpitMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getObjectData() {
      return 0;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }
}
