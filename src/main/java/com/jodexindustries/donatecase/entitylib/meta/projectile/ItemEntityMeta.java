package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;

public class ItemEntityMeta extends ItemContainerMeta implements ObjectData {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;

   public ItemEntityMeta(int entityId, Metadata metadata) {
      super(entityId, metadata, ItemStack.EMPTY);
   }

   public int getObjectData() {
      return 1;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }
}
