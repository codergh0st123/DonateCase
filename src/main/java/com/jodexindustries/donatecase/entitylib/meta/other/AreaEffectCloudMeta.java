package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class AreaEffectCloudMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 12;

   public AreaEffectCloudMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public float getRadius() {
      return (Float)super.metadata.getIndex((byte)8, 0.5F);
   }

   public void setRadius(float value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.FLOAT, value);
   }

   public int getColor() {
      return (Integer)super.metadata.getIndex(offset((byte)8, 1), 0);
   }

   public void setColor(int value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value);
   }

   public boolean isSinglePoint() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 2), false);
   }

   public void setSinglePoint(boolean value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.BOOLEAN, value);
   }
}
