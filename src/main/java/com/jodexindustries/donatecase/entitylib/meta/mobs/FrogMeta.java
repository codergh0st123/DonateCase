package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class FrogMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 19;

   public FrogMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Variant getVariant() {
      return (Variant)super.metadata.getIndex((byte)17, FrogMeta.Variant.TEMPERATE);
   }

   public void setVariant(@NotNull Variant value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.FROG_VARIANT, value.ordinal());
   }

   public Optional<Integer> getTongueTarget() {
      return (Optional)super.metadata.getIndex(offset((byte)17, 1), Optional.empty());
   }

   public void setTongueTarget(int value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.OPTIONAL_INT, Optional.of(value));
   }

   public static enum Variant {
      TEMPERATE,
      WARM,
      COLD;

      private static final Variant[] VALUES = values();

      // $FF: synthetic method
      private static Variant[] $values() {
         return new Variant[]{TEMPERATE, WARM, COLD};
      }
   }
}
