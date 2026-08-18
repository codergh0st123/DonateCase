package com.jodexindustries.donatecase.entitylib.meta.mobs.horse;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class LlamaMeta extends ChestedHorseMeta {
   public static final byte OFFSET = 19;
   public static final byte MAX_OFFSET = 22;

   public LlamaMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getStrength() {
      return (Integer)super.metadata.getIndex((byte)19, 0);
   }

   public void setStrength(int value) {
      super.metadata.setIndex((byte)19, EntityDataTypes.INT, value);
   }

   public int getCarpetColor() {
      return (Integer)super.metadata.getIndex(offset((byte)19, 1), -1);
   }

   public void setCarpetColor(int value) {
      super.metadata.setIndex(offset((byte)19, 1), EntityDataTypes.INT, value);
   }

   public Variant getVariant() {
      return LlamaMeta.Variant.VALUES[(Integer)super.metadata.getIndex(offset((byte)19, 2), 0)];
   }

   public void setVariant(Variant value) {
      super.metadata.setIndex(offset((byte)19, 2), EntityDataTypes.INT, value.ordinal());
   }

   public static enum Variant {
      CREAMY,
      WHITE,
      BROWN,
      GRAY;

      private static final Variant[] VALUES = values();

      // $FF: synthetic method
      private static Variant[] $values() {
         return new Variant[]{CREAMY, WHITE, BROWN, GRAY};
      }
   }
}
