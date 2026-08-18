package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class GuardianMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 18;
   private int target = -1;

   public GuardianMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isRetractingSpikes() {
      return (Boolean)super.metadata.getIndex((byte)16, false);
   }

   public void setRetractingSpikes(boolean retractingSpikes) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BOOLEAN, retractingSpikes);
   }

   public int getTarget() {
      return this.target;
   }

   public void setTarget(int target) {
      this.target = target;
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.INT, target == -1 ? 0 : target);
   }
}
