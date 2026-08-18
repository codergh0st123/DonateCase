package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public class EndermanMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 19;

   public EndermanMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Integer getCarriedBlockID() {
      return (Integer)super.metadata.getIndex((byte)16, (Object)null);
   }

   public void setCarriedBlockID(@Nullable Integer value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.OPTIONAL_INT, Optional.ofNullable(value));
   }

   public boolean isScreaming() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 1), false);
   }

   public void setScreaming(boolean value) {
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isStaring() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 2), false);
   }

   public void setStaring(boolean value) {
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BOOLEAN, value);
   }
}
