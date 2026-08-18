package com.jodexindustries.donatecase.entitylib.meta.display;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class ItemDisplayMeta extends AbstractDisplayMeta {
   public static final byte OFFSET;
   public static final byte MAX_OFFSET;

   public ItemDisplayMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public ItemStack getItem() {
      return (ItemStack)super.metadata.getIndex(OFFSET, ItemStack.EMPTY);
   }

   public void setItem(ItemStack itemStack) {
      super.metadata.setIndex(OFFSET, EntityDataTypes.ITEMSTACK, itemStack);
   }

   public DisplayType getDisplayType() {
      return ItemDisplayMeta.DisplayType.VALUES[(Byte)super.metadata.getIndex(offset(OFFSET, 1), (byte)0)];
   }

   public void setDisplayType(DisplayType displayType) {
      super.metadata.setIndex(offset(OFFSET, 1), EntityDataTypes.BYTE, (byte)displayType.ordinal());
   }

   static {
      OFFSET = AbstractDisplayMeta.MAX_OFFSET;
      MAX_OFFSET = offset(OFFSET, 1);
   }

   public static enum DisplayType {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;

      private static final DisplayType[] VALUES = values();

      // $FF: synthetic method
      private static DisplayType[] $values() {
         return new DisplayType[]{NONE, THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND, HEAD, GUI, GROUND, FIXED};
      }
   }
}
