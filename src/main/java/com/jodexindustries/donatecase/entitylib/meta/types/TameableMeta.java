package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import java.util.Optional;
import java.util.UUID;

public class TameableMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 19;
   private static final byte SITTING_BIT = 1;
   private static final byte TAMED_BIT = 4;

   public TameableMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isSitting() {
      return this.getMaskBit((byte)17, (byte)1);
   }

   public void setSitting(boolean value) {
      this.setMaskBit(17, (byte)1, value);
   }

   public boolean isTamed() {
      return this.getMaskBit((byte)17, (byte)4);
   }

   public void setTamed(boolean value) {
      this.setMaskBit(17, (byte)4, value);
   }

   public Optional<UUID> getOwner() {
      return (Optional)super.metadata.getIndex(offset((byte)17, 1), Optional.empty());
   }

   public void setOwner(UUID value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.OPTIONAL_UUID, Optional.ofNullable(value));
   }
}
