package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;

public class FishingHookMeta extends EntityMeta implements ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 10;
   private int shooterId;
   private int hookedId;

   public FishingHookMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isCatchable() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 1), false);
   }

   public void setCatchable(boolean value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.BOOLEAN, value);
   }

   public int getHookedEntity() {
      return this.hookedId;
   }

   public void setShooter(int entityId) {
      this.shooterId = entityId;
   }

   public void setHookedEntity(int entityId) {
      this.hookedId = entityId;
      super.metadata.setIndex((byte)8, EntityDataTypes.INT, entityId == -1 ? 0 : entityId + 1);
   }

   public int getObjectData() {
      return this.shooterId != -1 ? this.shooterId : 0;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return false;
   }
}
