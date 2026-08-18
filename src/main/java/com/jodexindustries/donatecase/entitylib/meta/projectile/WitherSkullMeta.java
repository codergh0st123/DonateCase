package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class WitherSkullMeta extends EntityMeta implements ObjectData, ProjectileMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 9;
   private int shooter = -1;

   public WitherSkullMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isInvulnerable() {
      return (Boolean)super.metadata.getIndex((byte)8, false);
   }

   public void setInvulnerable(boolean value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.BOOLEAN, value);
   }

   public int getObjectData() {
      return this.shooter == -1 ? 0 : this.shooter;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }

   public int getShooter() {
      return this.shooter;
   }

   public void setShooter(int entityId) {
      this.shooter = entityId;
   }
}
