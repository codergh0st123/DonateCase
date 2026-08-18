package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class BeeMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 19;
   private static final byte ANGRY_BIT = 2;
   private static final byte HAS_STUNG_BIT = 4;
   private static final byte HAS_NECTAR_BIT = 8;

   public BeeMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isAngry() {
      return this.getMaskBit((byte)17, (byte)2);
   }

   public void setAngry(boolean value) {
      this.setMaskBit(17, (byte)2, value);
   }

   public boolean hasStung() {
      return this.getMaskBit((byte)17, (byte)4);
   }

   public void setHasStung(boolean value) {
      this.setMaskBit(17, (byte)4, value);
   }

   public boolean hasNectar() {
      return this.getMaskBit((byte)17, (byte)8);
   }

   public void setHasNectar(boolean value) {
      this.setMaskBit(17, (byte)8, value);
   }

   public int getAngerTicks() {
      return (Integer)super.metadata.getIndex(offset((byte)17, 1), 0);
   }

   public void setAngerTicks(int value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.INT, value);
   }
}
