package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import org.jetbrains.annotations.NotNull;

public class BoatMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 15;

   public BoatMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getTimeSinceLastHit() {
      return (Integer)super.metadata.getIndex((byte)8, 0);
   }

   public void setTimeSinceLastHit(int value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.INT, value);
   }

   public int getForwardDirection() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 1), 1);
   }

   public void setForwardDirection(int value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value);
   }

   public float getDamageTaken() {
      return (float)(Integer)super.metadata.getIndex(offset((byte)8, 2), 0);
   }

   public void setDamageTaken(float value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.FLOAT, value);
   }

   public @NotNull Type getType() {
      return BoatMeta.Type.VALUES[(Integer)super.metadata.getIndex(offset((byte)8, 3), 0)];
   }

   public void setType(@NotNull Type value) {
      super.metadata.setIndex(offset((byte)8, 3), EntityDataTypes.INT, value.ordinal());
   }

   public boolean isLeftPaddleTurning() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 4), false);
   }

   public void setLeftPaddleTurning(boolean value) {
      super.metadata.setIndex(offset((byte)8, 4), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isRightPaddleTurning() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 5), false);
   }

   public void setRightPaddleTurning(boolean value) {
      super.metadata.setIndex(offset((byte)8, 5), EntityDataTypes.BOOLEAN, value);
   }

   public int getSplashTimer() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 6), 0);
   }

   public void setSplashTimer(int value) {
      super.metadata.setIndex(offset((byte)8, 6), EntityDataTypes.INT, value);
   }

   public static enum Type {
      OAK,
      SPRUCE,
      BIRCH,
      JUNGLE,
      ACACIA,
      DARK_OAK;

      private static final Type[] VALUES = values();

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};
      }
   }
}
