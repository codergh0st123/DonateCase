package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import org.jetbrains.annotations.NotNull;

public class TropicalFishMeta extends BaseFishMeta implements ObjectData {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public TropicalFishMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Variant getVariant() {
      return getVariantFromID((Integer)super.metadata.getIndex((byte)17, 0));
   }

   public void setVariant(Variant variant) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, getVariantID(variant));
   }

   public static int getVariantID(Variant variant) {
      int id = 0;
      id |= variant.patternColor;
      id <<= 8;
      id |= variant.bodyColor;
      id <<= 8;
      id |= variant.pattern.ordinal();
      id <<= 8;
      id |= variant.type.ordinal();
      return id;
   }

   public static Variant getVariantFromID(int variantID) {
      Type type = TropicalFishMeta.Type.VALUES[variantID & 255];
      variantID >>= 8;
      Pattern pattern = TropicalFishMeta.Pattern.VALUES[variantID & 255];
      variantID >>= 8;
      byte bodyColor = (byte)(variantID & 255);
      variantID >>= 8;
      byte patternColor = (byte)(variantID & 255);
      return new Variant(type, pattern, bodyColor, patternColor);
   }

   public int getObjectData() {
      return 0;
   }

   public boolean requiresVelocityPacketAtSpawn() {
      return false;
   }

   public static class Variant {
      private Type type;
      private Pattern pattern;
      private byte bodyColor;
      private byte patternColor;

      public Variant(@NotNull Type type, @NotNull Pattern pattern, byte bodyColor, byte patternColor) {
         this.type = type;
         this.pattern = pattern;
         this.bodyColor = bodyColor;
         this.patternColor = patternColor;
      }

      public @NotNull Type getType() {
         return this.type;
      }

      public void setType(@NotNull Type type) {
         this.type = type;
      }

      public @NotNull Pattern getPattern() {
         return this.pattern;
      }

      public void setPattern(@NotNull Pattern pattern) {
         this.pattern = pattern;
      }

      public byte getBodyColor() {
         return this.bodyColor;
      }

      public void setBodyColor(byte bodyColor) {
         this.bodyColor = bodyColor;
      }

      public byte getPatternColor() {
         return this.patternColor;
      }

      public void setPatternColor(byte patternColor) {
         this.patternColor = patternColor;
      }
   }

   public static enum Type {
      SMALL,
      LARGE,
      INVISIBLE;

      private static final Type[] VALUES = values();

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{SMALL, LARGE, INVISIBLE};
      }
   }

   public static enum Pattern {
      KOB,
      SUNSTREAK,
      SNOOPER,
      DASHER,
      BRINELY,
      SPOTTY,
      NONE;

      private static final Pattern[] VALUES = values();

      // $FF: synthetic method
      private static Pattern[] $values() {
         return new Pattern[]{KOB, SUNSTREAK, SNOOPER, DASHER, BRINELY, SPOTTY, NONE};
      }
   }
}
