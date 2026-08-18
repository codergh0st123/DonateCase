package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;

public class ThrownEnderPearlMeta extends ItemContainerMeta {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;
   private static final ItemStack ENDER_PEARL;

   public ThrownEnderPearlMeta(int entityId, Metadata metadata) {
      super(entityId, metadata, ENDER_PEARL);
   }

   static {
      ENDER_PEARL = ItemStack.builder().type(ItemTypes.ENDER_PEARL).build();
   }
}
