package com.jodexindustries.donatecase.entitylib.meta.mobs.water;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.WaterMobMeta;
import org.jetbrains.annotations.NotNull;

public class DolphinMeta extends WaterMobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 19;

   public DolphinMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull Vector3i getTreasurePosition() {
      return (Vector3i)super.metadata.getIndex((byte)16, Vector3i.zero());
   }

   public void setTreasurePosition(@NotNull Vector3i value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.BLOCK_POSITION, value);
   }

   public boolean isCanFindTreasure() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 1), false);
   }

   public void setCanFindTreasure(boolean value) {
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isHasFish() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 2), false);
   }

   public void setHasFish(boolean value) {
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BOOLEAN, value);
   }
}
