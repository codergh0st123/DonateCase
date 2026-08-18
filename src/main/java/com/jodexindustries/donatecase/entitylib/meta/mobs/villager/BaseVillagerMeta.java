package com.jodexindustries.donatecase.entitylib.meta.mobs.villager;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class BaseVillagerMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public BaseVillagerMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getHeadShakeTimer() {
      return (Integer)super.metadata.getIndex((byte)17, 0);
   }

   public void setHeadShakeTimer(int value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.INT, value);
   }
}
