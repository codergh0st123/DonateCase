package com.jodexindustries.donatecase.entitylib.meta.mobs.tameable;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.extras.DyeColor;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.TameableMeta;

public class WolfMeta extends TameableMeta {
   public static final byte OFFSET = 19;
   public static final byte MAX_OFFSET = 22;

   public WolfMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isBegging() {
      return (Boolean)super.metadata.getIndex((byte)19, false);
   }

   public void setBegging(boolean value) {
      super.metadata.setIndex((byte)19, EntityDataTypes.BOOLEAN, value);
   }

   public int getCollarColor() {
      return (Integer)super.metadata.getIndex(offset((byte)19, 1), 14);
   }

   public void setCollarColor(int value) {
      super.metadata.setIndex(offset((byte)19, 1), EntityDataTypes.INT, value);
   }

   public DyeColor getCollarColorAsDye() {
      return DyeColor.values()[(Integer)super.metadata.getIndex(offset((byte)19, 1), DyeColor.RED.ordinal())];
   }

   public void setCollarColor(DyeColor color) {
      super.metadata.setIndex(offset((byte)19, 1), EntityDataTypes.INT, color.ordinal());
   }

   public int getAngerTime() {
      return (Integer)super.metadata.getIndex(offset((byte)19, 2), 0);
   }

   public void setAngerTime(int value) {
      super.metadata.setIndex(offset((byte)19, 2), EntityDataTypes.INT, value);
   }
}
