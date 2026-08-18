package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;

public class EyeOfEnderMeta extends ItemContainerMeta {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;
   public static final ItemStack EYE_OF_ENDER;

   public EyeOfEnderMeta(int entityId, Metadata metadata) {
      super(entityId, metadata, EYE_OF_ENDER);
   }

   static {
      EYE_OF_ENDER = ItemStack.builder().type(ItemTypes.ENDER_EYE).build();
   }
}
