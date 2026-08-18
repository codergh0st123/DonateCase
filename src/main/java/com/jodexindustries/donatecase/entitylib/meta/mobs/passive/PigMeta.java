package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class PigMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 19;

   public PigMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean hasSaddle() {
      return (Boolean)super.metadata.getIndex((byte)17, false);
   }

   public void setHasSaddle(boolean value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.BOOLEAN, value);
   }

   public int getTimeToBoost() {
      isVersionNewer(ServerVersion.V_1_16);
      return (Integer)super.metadata.getIndex(offset((byte)17, 1), 0);
   }

   public void setTimeToBoost(int value) {
      isVersionNewer(ServerVersion.V_1_16);
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.INT, value);
   }
}
