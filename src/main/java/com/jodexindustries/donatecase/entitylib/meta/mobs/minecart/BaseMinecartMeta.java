package com.jodexindustries.donatecase.entitylib.meta.mobs.minecart;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;

public abstract class BaseMinecartMeta extends EntityMeta implements ObjectData {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 14;

   protected BaseMinecartMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getShakingPower() {
      return (Integer)super.metadata.getIndex((byte)8, 0);
   }

   public void setShakingPower(int value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.INT, value);
   }

   public int getShakingDirection() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 1), 1);
   }

   public void setShakingDirection(int value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value);
   }

   public float getShakingMultiplier() {
      return (Float)super.metadata.getIndex(offset((byte)8, 2), 0.0F);
   }

   public void setShakingMultiplier(float value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.FLOAT, value);
   }

   public int getCustomBlockIdAndDamage() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 3), 0);
   }

   public void setCustomBlockIdAndDamage(int value) {
      super.metadata.setIndex(offset((byte)8, 3), EntityDataTypes.INT, value);
   }

   public int getCustomBlockYPosition() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 4), 6);
   }

   public void setCustomBlockYPosition(int value) {
      super.metadata.setIndex(offset((byte)8, 4), EntityDataTypes.INT, value);
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return true;
   }
}
