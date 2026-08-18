package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;

public class ThrownExpBottleMeta extends ItemContainerMeta {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;
   private static final ItemStack EXP_BOTTLE;

   public ThrownExpBottleMeta(int entityId, Metadata metadata) {
      super(entityId, metadata, EXP_BOTTLE);
   }

   static {
      EXP_BOTTLE = ItemStack.builder().type(ItemTypes.EXPERIENCE_BOTTLE).build();
   }
}
