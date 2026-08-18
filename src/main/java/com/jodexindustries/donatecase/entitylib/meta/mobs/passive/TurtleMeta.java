package com.jodexindustries.donatecase.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class TurtleMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 23;

   public TurtleMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Vector3i getHomePosition() {
      return (Vector3i)super.metadata.getIndex((byte)17, Vector3i.zero());
   }

   public void setBlockPosition(Vector3i value) {
      super.metadata.setIndex((byte)17, EntityDataTypes.BLOCK_POSITION, value);
   }

   public boolean hasEgg() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 1), false);
   }

   public void setHasEgg(boolean value) {
      super.metadata.setIndex(offset((byte)17, 1), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isLayingEgg() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 2), false);
   }

   public void setLayingEgg(boolean value) {
      super.metadata.setIndex(offset((byte)17, 2), EntityDataTypes.BOOLEAN, value);
   }

   public Vector3i getTravelPosition() {
      return (Vector3i)super.metadata.getIndex(offset((byte)17, 3), Vector3i.zero());
   }

   public void setTravelPosition(Vector3i value) {
      super.metadata.setIndex(offset((byte)17, 3), EntityDataTypes.BLOCK_POSITION, value);
   }

   public boolean isGoingHome() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 4), false);
   }

   public void setGoingHome(boolean value) {
      super.metadata.setIndex(offset((byte)17, 4), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isTravelling() {
      return (Boolean)super.metadata.getIndex(offset((byte)17, 5), false);
   }

   public void setTravelling(boolean value) {
      super.metadata.setIndex(offset((byte)17, 4), EntityDataTypes.BOOLEAN, value);
   }
}
