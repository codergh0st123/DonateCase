package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class ArrowMeta extends BaseArrowMeta implements ProjectileMeta, ObjectData {
   public static final byte OFFSET = 10;
   public static final byte MAX_OFFSET = 11;
   private int shooterId = -1;

   public ArrowMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getColor() {
      return (Integer)super.metadata.getIndex((byte)10, -1);
   }

   public void setColor(int value) {
      super.metadata.setIndex((byte)10, EntityDataTypes.INT, value);
   }

   public int getShooter() {
      return this.shooterId;
   }

   public void setShooter(int entityId) {
      this.shooterId = entityId;
   }

   public int getObjectData() {
      return this.shooterId == -1 ? 0 : this.shooterId + 1;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }
}
