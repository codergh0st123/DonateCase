package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class LargeFireballMeta extends ItemContainerMeta implements ObjectData, ProjectileMeta {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;
   private int shooterId = -1;

   public LargeFireballMeta(int entityId, Metadata meta) {
      super(entityId, meta, ItemStack.EMPTY);
   }

   public int getObjectData() {
      return this.shooterId == -1 ? 0 : this.shooterId;
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
