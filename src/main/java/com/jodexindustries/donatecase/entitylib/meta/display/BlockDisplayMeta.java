package com.jodexindustries.donatecase.entitylib.meta.display;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class BlockDisplayMeta extends AbstractDisplayMeta {
   public static final byte OFFSET;
   public static final byte MAX_OFFSET;

   public BlockDisplayMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getBlockId() {
      return (Integer)super.metadata.getIndex(OFFSET, 0);
   }

   public void setBlockId(int blockId) {
      super.metadata.setIndex(OFFSET, EntityDataTypes.BLOCK_STATE, blockId);
   }

   static {
      OFFSET = AbstractDisplayMeta.MAX_OFFSET;
      MAX_OFFSET = offset(OFFSET, 1);
   }
}
