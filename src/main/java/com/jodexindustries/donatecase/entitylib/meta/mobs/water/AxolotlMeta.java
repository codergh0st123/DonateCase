package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.WaterMobMeta;

public class AxolotlMeta extends WaterMobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 19;

   public AxolotlMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Variant getVariant() {
      return AxolotlMeta.Variant.VALUES[(Integer)super.metadata.getIndex((byte)16, 0)];
   }

   public void setVariant(Variant variant) {
      this.metadata.setIndex((byte)16, EntityDataTypes.INT, variant.ordinal());
   }

   public boolean isPlayingDead() {
      return (Boolean)this.metadata.getIndex(offset((byte)16, 1), false);
   }

   public void setPlayingDead(boolean playingDead) {
      this.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.BOOLEAN, playingDead);
   }

   public boolean isFromBucket() {
      return (Boolean)this.metadata.getIndex(offset((byte)16, 2), false);
   }

   public void setFromBucket(boolean fromBucket) {
      this.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BOOLEAN, fromBucket);
   }

   public static enum Variant {
      LUCY,
      WILD,
      GOLD,
      CYAN,
      BLUE;

      private static final Variant[] VALUES = values();

      // $FF: synthetic method
      private static Variant[] $values() {
         return new Variant[]{LUCY, WILD, GOLD, CYAN, BLUE};
      }
   }
}
