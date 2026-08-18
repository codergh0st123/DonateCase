package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public abstract class ItemContainerMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 9;
   private final ItemStack baseItem;

   public ItemContainerMeta(int entityId, Metadata metadata, ItemStack baseItem) {
      super(entityId, metadata);
      this.baseItem = baseItem;
   }

   public ItemStack getItem() {
      return (ItemStack)super.metadata.getIndex((byte)8, this.baseItem);
   }

   public void setItem(ItemStack value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.ITEMSTACK, value);
   }
}
