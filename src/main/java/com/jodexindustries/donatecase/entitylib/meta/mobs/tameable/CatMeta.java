package com.jodexindustries.donatecase.entitylib.meta.mobs.tameable;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.extras.DyeColor;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.TameableMeta;
import org.jetbrains.annotations.NotNull;

public class CatMeta extends TameableMeta {
   public static final byte OFFSET = 19;
   public static final byte MAX_OFFSET = 23;
   private static final DyeColor[] COLORS = DyeColor.values();

   public CatMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Variant getVariant() {
      return (Variant)super.metadata.getIndex((byte)19, CatMeta.Variant.BLACK);
   }

   public void setVariant(@NotNull Variant value) {
      super.metadata.setIndex((byte)19, EntityDataTypes.CAT_VARIANT, value.ordinal());
   }

   public boolean isLying() {
      return (Boolean)super.metadata.getIndex(offset((byte)19, 1), false);
   }

   public void setLying(boolean value) {
      super.metadata.setIndex(offset((byte)19, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isRelaxed() {
      return (Boolean)super.metadata.getIndex(offset((byte)19, 2), false);
   }

   public void setRelaxed(boolean value) {
      super.metadata.setIndex(offset((byte)19, 2), EntityDataTypes.BOOLEAN, value);
   }

   public @NotNull DyeColor getCollarColor() {
      return COLORS[(Integer)super.metadata.getIndex(offset((byte)19, 3), DyeColor.RED.ordinal())];
   }

   public void setCollarColor(@NotNull DyeColor value) {
      super.metadata.setIndex(offset((byte)19, 3), EntityDataTypes.INT, value.ordinal());
   }

   public static enum Variant {
      TABBY,
      BLACK,
      RED,
      SIAMESE,
      BRITISH_SHORTHAIR,
      CALICO,
      PERSIAN,
      RAGDOLL,
      WHITE,
      JELLIE,
      ALL_BLACK;

      private static final Variant[] VALUES = values();

      // $FF: synthetic method
      private static Variant[] $values() {
         return new Variant[]{TABBY, BLACK, RED, SIAMESE, BRITISH_SHORTHAIR, CALICO, PERSIAN, RAGDOLL, WHITE, JELLIE, ALL_BLACK};
      }
   }
}
