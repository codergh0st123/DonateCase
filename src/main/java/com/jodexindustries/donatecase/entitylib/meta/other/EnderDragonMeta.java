package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;
import org.jetbrains.annotations.NotNull;

public class EnderDragonMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 16;

   public EnderDragonMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Phase getPhase() {
      return EnderDragonMeta.Phase.VALUES[(Integer)super.metadata.getIndex((byte)16, 0)];
   }

   public void setPhase(@NotNull Phase value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.INT, value.ordinal());
   }

   public static enum Phase {
      CIRCLING,
      STRAFING,
      FLYING_TO_THE_PORTAL,
      LANDING_ON_THE_PORTAL,
      TAKING_OFF_FROM_THE_PORTAL,
      BREATH_ATTACK,
      LOOKING_FOR_BREATH_ATTACK_PLAYER,
      ROAR,
      CHARGING_PLAYER,
      FLYING_TO_THE_PORTAL_TO_DIE,
      HOVERING_WITHOUT_AI;

      private static final Phase[] VALUES = values();

      // $FF: synthetic method
      private static Phase[] $values() {
         return new Phase[]{CIRCLING, STRAFING, FLYING_TO_THE_PORTAL, LANDING_ON_THE_PORTAL, TAKING_OFF_FROM_THE_PORTAL, BREATH_ATTACK, LOOKING_FOR_BREATH_ATTACK_PLAYER, ROAR, CHARGING_PLAYER, FLYING_TO_THE_PORTAL_TO_DIE, HOVERING_WITHOUT_AI};
      }
   }
}
