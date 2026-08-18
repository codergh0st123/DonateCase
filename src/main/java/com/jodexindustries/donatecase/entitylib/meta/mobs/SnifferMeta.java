package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.sniffer.SnifferState;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class SnifferMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public SnifferMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public SnifferState getState() {
      return (SnifferState)this.metadata.getIndex((byte)17, SnifferState.IDLING);
   }

   public void setState(SnifferState state) {
      super.metadata.setIndex((byte)17, EntityDataTypes.SNIFFER_STATE, state);
   }

   public int getDropSeedAtTick() {
      return (Integer)this.metadata.getIndex(offset((byte)17, 1), 0);
   }

   public void setDropSeedAtTick(int tick) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.INT, tick);
   }
}
