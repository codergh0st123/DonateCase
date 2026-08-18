package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.extras.Rotation;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import org.jetbrains.annotations.NotNull;

public class ItemFrameMeta extends EntityMeta implements ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 10;
   private Orientation orientation;

   public ItemFrameMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
      this.orientation = ItemFrameMeta.Orientation.DOWN;
   }

   public @NotNull ItemStack getItem() {
      return (ItemStack)super.metadata.getIndex((byte)8, ItemStack.EMPTY);
   }

   public void setItem(@NotNull ItemStack value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.ITEMSTACK, value);
   }

   public @NotNull Rotation getRotation() {
      return Rotation.values()[(Integer)super.metadata.getIndex(offset((byte)8, 1), 0)];
   }

   public void setRotation(@NotNull Rotation value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value.ordinal());
   }

   public @NotNull Orientation getOrientation() {
      return this.orientation;
   }

   public void setOrientation(@NotNull Orientation orientation) {
      this.orientation = orientation;
   }

   public int getObjectData() {
      return this.orientation.ordinal();
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return false;
   }

   public static enum Orientation {
      DOWN,
      UP,
      NORTH,
      SOUTH,
      WEST,
      EAST;

      // $FF: synthetic method
      private static Orientation[] $values() {
         return new Orientation[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
      }
   }
}
