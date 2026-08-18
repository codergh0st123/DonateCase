package com.jodexindustries.donatecase.entitylib.meta.mobs.golem;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.world.Direction;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;
import java.util.Optional;

public class ShulkerMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 17;

   public ShulkerMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Direction getAttachFace() {
      return (Direction)super.metadata.getIndex((byte)16, Direction.DOWN);
   }

   public void setAttachFace(Direction value) {
      super.metadata.setIndex((byte)16, EntityDataTypes.INT, value.ordinal());
   }

   public Optional<Vector3i> getAttachmentPosition() {
      return (Optional)super.metadata.getIndex(offset((byte)16, 1), Optional.empty());
   }

   public void setAttachmentPosition(Vector3i value) {
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.OPTIONAL_BLOCK_POSITION, Optional.of(value));
   }

   public byte getShieldHeight() {
      return (Byte)super.metadata.getIndex(offset((byte)16, 2), (byte)0);
   }

   public void setShieldHeight(byte value) {
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.BYTE, value);
   }

   public byte getColor() {
      return (Byte)super.metadata.getIndex(offset((byte)16, 3), (byte)10);
   }

   public void setColor(byte value) {
      super.metadata.setIndex(offset((byte)16, 3), EntityDataTypes.BYTE, value);
   }
}
