package com.jodexindustries.donatecase.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.MobMeta;

public class WitherMeta extends MobMeta {
   public static final byte OFFSET = 16;
   public static final byte MAX_OFFSET = 20;
   private int centerHead = -1;
   private int leftHead = -1;
   private int rightHead = -1;

   public WitherMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public void setCenterHead(int centerHead) {
      this.centerHead = centerHead;
      super.metadata.setIndex(offset((byte)16, 0), EntityDataTypes.INT, centerHead == -1 ? 0 : centerHead);
   }

   public void setLeftHead(int leftHead) {
      this.leftHead = leftHead;
      super.metadata.setIndex(offset((byte)16, 1), EntityDataTypes.INT, leftHead == -1 ? 0 : leftHead);
   }

   public void setRightHead(int rightHead) {
      this.rightHead = rightHead;
      super.metadata.setIndex(offset((byte)16, 2), EntityDataTypes.INT, rightHead == -1 ? 0 : rightHead);
   }

   public int getCenterHead() {
      return this.centerHead;
   }

   public int getLeftHead() {
      return this.leftHead;
   }

   public int getRightHead() {
      return this.rightHead;
   }

   public int getInvulnerableTime() {
      return (Integer)super.metadata.getIndex(offset((byte)16, 3), 0);
   }

   public void setInvulnerableTime(int value) {
      super.metadata.setIndex(offset((byte)16, 3), EntityDataTypes.INT, value);
   }
}
