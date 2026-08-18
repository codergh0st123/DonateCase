package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.player.HumanoidArm;
import com.github.retrooper.packetevents.util.Vector3i;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import java.util.Optional;

public class LivingEntityMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 15;
   private static final byte IS_HAND_ACTIVE_BIT = 1;
   private static final byte ACTIVE_HAND_BIT = 2;
   private static final byte IS_IN_SPIN_ATTACK_BIT = 4;

   public LivingEntityMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public float getHealth() {
      return (Float)super.metadata.getIndex(offset((byte)8, 1), 1.0F);
   }

   public int getPotionEffectColor() {
      isVersionNewer(ServerVersion.V_1_9);
      return (Integer)super.metadata.getIndex(offset((byte)8, 2), 0);
   }

   public void setPotionEffectColor(int value) {
      isVersionNewer(ServerVersion.V_1_9);
      super.metadata.setIndex(offset((byte)8, 2), EntityDataTypes.INT, value);
   }

   public void setPotionEffectColor(int red, int green, int blue) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setPotionEffectColor(red << 16 + green << 8 + blue);
   }

   public boolean isPotionEffectAmbient() {
      isVersionNewer(ServerVersion.V_1_9);
      return (Boolean)super.metadata.getIndex(offset((byte)8, 3), false);
   }

   public void setPotionEffectAmbient(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      super.metadata.setIndex(offset((byte)8, 3), EntityDataTypes.BOOLEAN, value);
   }

   public int getArrowCount() {
      isVersionNewer(ServerVersion.V_1_9);
      return (Integer)super.metadata.getIndex(offset((byte)8, 4), 0);
   }

   public void setArrowCount(int value) {
      isVersionNewer(ServerVersion.V_1_9);
      super.metadata.setIndex(offset((byte)8, 4), EntityDataTypes.INT, value);
   }

   public void setHealth(float value) {
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.FLOAT, value);
   }

   public HumanoidArm getActiveHand() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit((byte)8, (byte)2) ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public void setActiveHand(HumanoidArm value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(8, (byte)2, value == HumanoidArm.LEFT);
   }

   public boolean isInRiptideSpinAttack() {
      isVersionNewer(ServerVersion.V_1_13);
      return this.getMaskBit((byte)8, (byte)4);
   }

   public void setInRiptideSpinAttack(boolean value) {
      isVersionNewer(ServerVersion.V_1_13);
      this.setMaskBit(8, (byte)4, value);
   }

   public Optional<Vector3i> getBedPosition() {
      isVersionNewer(ServerVersion.V_1_14);
      return (Optional)super.metadata.getIndex(offset((byte)8, 6), Optional.empty());
   }

   public void setBedPosition(Vector3i value) {
      isVersionNewer(ServerVersion.V_1_14);
      super.metadata.setIndex(offset((byte)8, 6), EntityDataTypes.OPTIONAL_BLOCK_POSITION, value == null ? Optional.empty() : Optional.of(value));
   }

   public int getBeeStingerCount() {
      isVersionNewer(ServerVersion.V_1_15);
      return (Integer)super.metadata.getIndex(offset((byte)8, 5), 0);
   }

   public void setBeeStingerCount(int value) {
      isVersionNewer(ServerVersion.V_1_15);
      super.metadata.setIndex(offset((byte)8, 5), EntityDataTypes.INT, value);
   }

   public boolean isHandActive() {
      isVersionNewer(ServerVersion.V_1_15);
      return this.getMaskBit((byte)8, (byte)1);
   }

   public void setHandActive(boolean value) {
      isVersionNewer(ServerVersion.V_1_15);
      this.setMaskBit(8, (byte)1, value);
   }
}
