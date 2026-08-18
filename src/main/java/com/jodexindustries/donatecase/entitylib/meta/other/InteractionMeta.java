package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class InteractionMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 11;

   public InteractionMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public float getWidth() {
      return (Float)super.metadata.getIndex((byte)8, 1.0F);
   }

   public void setWidth(float value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.FLOAT, value);
   }

   public float getHeight() {
      return (Float)super.metadata.getIndex(offset((byte)8, 1), 1.0F);
   }

   public void setHeight(float value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.FLOAT, value);
   }

   public boolean isResponsive() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 2), false);
   }

   public void setResponsive(boolean value) {
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.BOOLEAN, value);
   }
}
