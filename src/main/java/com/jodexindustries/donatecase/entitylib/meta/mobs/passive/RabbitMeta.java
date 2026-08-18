package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;
import org.jetbrains.annotations.NotNull;

public class RabbitMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public RabbitMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Type getType() {
      int id = (Integer)super.metadata.getIndex((byte)17, 0);
      return id == 99 ? RabbitMeta.Type.KILLER_BUNNY : RabbitMeta.Type.VALUES[id];
   }

   public void setType(@NotNull Type value) {
      int id = value == RabbitMeta.Type.KILLER_BUNNY ? 99 : value.ordinal();
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, id);
   }

   public static enum Type {
      BROWN,
      WHITE,
      BLACK,
      BLACK_AND_WHITE,
      GOLD,
      SALT_AND_PEPPER,
      KILLER_BUNNY;

      private static final Type[] VALUES = values();

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{BROWN, WHITE, BLACK, BLACK_AND_WHITE, GOLD, SALT_AND_PEPPER, KILLER_BUNNY};
      }
   }
}
