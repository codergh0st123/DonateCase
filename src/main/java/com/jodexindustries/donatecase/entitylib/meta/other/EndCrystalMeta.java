package com.jodexindustries.donatecase.entitylib.meta.other;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public class EndCrystalMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 10;

   public EndCrystalMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @Nullable Optional<Vector3i> getBeamTarget() {
      return (Optional)super.metadata.getIndex((byte)8, Optional.empty());
   }

   public void setBeamTarget(@Nullable Vector3i value) {
      super.metadata.setIndex((byte)8, EntityDataTypes.OPTIONAL_BLOCK_POSITION, Optional.ofNullable(value));
   }

   public boolean isShowingBottom() {
      return (Boolean)super.metadata.getIndex(offset((byte)8, 1), true);
   }

   public void setShowingBottom(boolean value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.BOOLEAN, value);
   }
}
