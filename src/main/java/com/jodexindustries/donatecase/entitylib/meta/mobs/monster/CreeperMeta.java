package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;
import org.jetbrains.annotations.NotNull;

public class CreeperMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 19;

   public CreeperMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull State getState() {
      int id = (Integer)super.metadata.getIndex((byte)16, -1);
      return id == -1 ? CreeperMeta.State.IDLE : CreeperMeta.State.FUSE;
   }

   public void setState(@NotNull State value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.INT, value == CreeperMeta.State.IDLE ? -1 : 1);
   }

   public boolean isCharged() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 1), false);
   }

   public void setCharged(boolean value) {
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isIgnited() {
      return (Boolean)super.metadata.getIndex(offset((byte)16, 2), false);
   }

   public void setIgnited(boolean value) {
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BOOLEAN, value);
   }

   public static enum State {
      IDLE,
      FUSE;

      // $FF: synthetic method
      private static State[] $values() {
         return new State[]{IDLE, FUSE};
      }
   }
}
