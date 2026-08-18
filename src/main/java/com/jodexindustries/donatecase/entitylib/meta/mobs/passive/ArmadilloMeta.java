package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.armadillo.ArmadilloState;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class ArmadilloMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public ArmadilloMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public ArmadilloState getState() {
      return (ArmadilloState)this.metadata.getIndex((byte)17, ArmadilloState.IDLE);
   }

   public void setState(ArmadilloState state) {
      super.metadata.setIndex((byte)17, EntityDataTypes.ARMADILLO_STATE, state);
   }
}
