package com.jodexindustries.donatecase.entitylib.meta.mobs.monster.raider;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class WitchMeta extends RaiderMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public WitchMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isDrinkingPotion() {
      return (Boolean)super.metadata.getIndex((byte)17, false);
   }

   public void setDrinkingPotion(boolean value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.BOOLEAN, value);
   }
}
