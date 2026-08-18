package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ItemContainerMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.meta.types.ProjectileMeta;

public class SmallFireballMeta extends ItemContainerMeta implements ObjectData, ProjectileMeta {
   public static final byte OFFSET = 9;
   public static final byte MAX_OFFSET = 9;
   public static final ItemStack SMALL_FIREBALL;
   private int shooterId = -1;

   public SmallFireballMeta(int entityId, Metadata meta) {
      super(entityId, meta, SMALL_FIREBALL);
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

   static {
      SMALL_FIREBALL = ItemStack.builder().type(ItemTypes.FIRE_CHARGE).build();
   }
}
