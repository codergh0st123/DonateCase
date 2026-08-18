package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class PufferFishMeta extends BaseFishMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public PufferFishMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public State getState() {
      return PufferFishMeta.State.VALUES[(Integer)super.metadata.getIndex((byte)17, 0)];
   }

   public void setState(State state) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, state.ordinal());
   }

   public static enum State {
      UNPUFFED,
      SEMI_PUFFED,
      FULLY_PUFFED;

      private static final State[] VALUES = values();

      // $FF: synthetic method
      private static State[] $values() {
         return new State[]{UNPUFFED, SEMI_PUFFED, FULLY_PUFFED};
      }
   }
}
