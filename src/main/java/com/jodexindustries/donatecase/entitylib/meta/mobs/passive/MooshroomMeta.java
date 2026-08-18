package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class MooshroomMeta extends CowMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public MooshroomMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Variant getVariant() {
      return MooshroomMeta.Variant.valueOf(((String)super.metadata.getIndex((byte)17, "red")).toUpperCase(Locale.ROOT));
   }

   public void setVariant(@NotNull Variant value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.STRING, value.name().toLowerCase(Locale.ROOT));
   }

   public static enum Variant {
      RED,
      BROWN;

      // $FF: synthetic method
      private static Variant[] $values() {
         return new Variant[]{RED, BROWN};
      }
   }
}
