package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class SpectralArrowMeta extends BaseArrowMeta implements ProjectileMeta, ObjectData {
   public static final byte OFFSET = 10;
   public static final byte MAX_OFFSET = 10;
   private int shooterId = -1;

   public SpectralArrowMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getObjectData() {
      return this.shooterId == -1 ? 0 : this.shooterId + 1;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }

   public int getShooter() {
      return this.shooterId;
   }

   public void setShooter(int entityId) {
      this.shooterId = entityId;
   }
}
