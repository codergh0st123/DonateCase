package com.jodexindustries.donatecase.entitylib.meta.mobs.horse;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import org.jetbrains.annotations.NotNull;

public class HorseMeta extends BaseHorseMeta {
   public static final byte OFFSET = 18;
   public static final byte MAX_OFFSET = 19;

   public HorseMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Variant getVariant() {
      return getVariantFromID((Integer)super.metadata.getIndex((byte)18, 0));
   }

   public void setVariant(Variant variant) {
      super.metadata.setIndex((byte)18, EntityDataTypes.INT, getVariantID(variant.marking, variant.color));
   }

   public static int getVariantID(@NotNull Marking marking, @NotNull Color color) {
      return (marking.ordinal() << 8) + color.ordinal();
   }

   public static Variant getVariantFromID(int variantID) {
      return new Variant(HorseMeta.Marking.VALUES[variantID >> 8], HorseMeta.Color.VALUES[variantID & 255]);
   }

   public static class Variant {
      private Marking marking;
      private Color color;

      public Variant(@NotNull Marking marking, @NotNull Color color) {
         this.marking = marking;
         this.color = color;
      }

      public @NotNull Marking getMarking() {
         return this.marking;
      }

      public void setMarking(@NotNull Marking marking) {
         this.marking = marking;
      }

      public @NotNull Color getColor() {
         return this.color;
      }

      public void setColor(@NotNull Color color) {
         this.color = color;
      }
   }

   public static enum Marking {
      NONE,
      WHITE,
      WHITE_FIELD,
      WHITE_DOTS,
      BLACK_DOTS;

      private static final Marking[] VALUES = values();

      // $FF: synthetic method
      private static Marking[] $values() {
         return new Marking[]{NONE, WHITE, WHITE_FIELD, WHITE_DOTS, BLACK_DOTS};
      }
   }

   public static enum Color {
      WHITE,
      CREAMY,
      CHESTNUT,
      BROWN,
      BLACK,
      GRAY,
      DARK_BROWN;

      private static final Color[] VALUES = values();

      // $FF: synthetic method
      private static Color[] $values() {
         return new Color[]{WHITE, CREAMY, CHESTNUT, BROWN, BLACK, GRAY, DARK_BROWN};
      }
   }
}
