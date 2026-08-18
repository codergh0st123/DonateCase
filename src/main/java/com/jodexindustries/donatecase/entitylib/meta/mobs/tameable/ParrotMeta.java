package com.jodexindustries.donatecase.entitylib.meta.mobs.tameable;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.TameableMeta;
import org.jetbrains.annotations.NotNull;

public class ParrotMeta extends TameableMeta {
   public static final byte OFFSET = 19;
   public static final byte MAX_OFFSET = 20;

   public ParrotMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
      isVersionNewer(ServerVersion.V_1_14);
   }

   public @NotNull Color getColor() {
      return ParrotMeta.Color.VALUES[(Integer)super.metadata.getIndex((byte)19, 0)];
   }

   public void setColor(@NotNull Color value) {
      super.metadata.setIndex((byte)19, EntityDataTypes.INT, value.ordinal());
   }

   public static enum Color {
      RED_BLUE,
      BLUE,
      GREEN,
      YELLOW_BLUE,
      GREY;

      private static final Color[] VALUES = values();

      // $FF: synthetic method
      private static Color[] $values() {
         return new Color[]{RED_BLUE, BLUE, GREEN, YELLOW_BLUE, GREY};
      }
   }
}
