package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;
import java.util.Optional;

public class FireworkRocketMeta extends EntityMeta implements ProjectileMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 11;
   private int shooter = -1;

   public FireworkRocketMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public ItemStack getFireworkItem() {
      return (ItemStack)super.metadata.getIndex((byte)8, ItemStack.EMPTY);
   }

   public void setFireworkItem(ItemStack value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.ITEMSTACK, value);
   }

   public boolean isShotAtAngle() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 2), false);
   }

   public void setShotAtAngle(boolean value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.BOOLEAN, value);
   }

   public int getShooter() {
      return this.shooter;
   }

   public void setShooter(int entityId) {
      this.shooter = entityId;
      Optional<Integer> optional = Optional.ofNullable(entityId == -1 ? null : entityId);
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.OPTIONAL_INT, optional);
   }
}
