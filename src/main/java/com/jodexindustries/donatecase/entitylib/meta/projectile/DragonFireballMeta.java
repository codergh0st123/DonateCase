package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class DragonFireballMeta extends EntityMeta implements ProjectileMeta, ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 8;
   private int shooter = -1;

   public DragonFireballMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
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
