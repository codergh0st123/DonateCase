package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;

public class FallingBlockMeta extends EntityMeta implements ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 9;
   private int blockStateId;

   public FallingBlockMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Vector3i getSpawnPosition() {
      return (Vector3i)super.metadata.getIndex((byte)8, Vector3i.zero());
   }

   public void setSpawnPosition(Vector3i value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.BLOCK_POSITION, value);
   }

   public int getBlockStateId() {
      return this.blockStateId;
   }

   public void setBlockStateId(int blockStateId) {
      this.blockStateId = blockStateId;
   }

   public int getObjectData() {
      return this.blockStateId;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return false;
   }
}
