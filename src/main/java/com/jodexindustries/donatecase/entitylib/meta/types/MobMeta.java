package com.jodexindustries.donatecase.entitylib.meta.types;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class MobMeta extends LivingEntityMeta {
   public static final byte OFFSET = 15;
   public static final byte MAX_OFFSET = 16;
   private static final byte NO_AI_BIT = 1;
   private static final byte IS_LEFT_HANDED_BIT = 2;
   private static final byte IS_AGGRESSIVE_BIT = 4;

   public MobMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isNoAi() {
      return this.getMaskBit((byte)15, (byte)1);
   }

   public void setNoAi(boolean value) {
      this.setMaskBit(15, (byte)1, value);
   }

   public boolean isLeftHanded() {
      EntityMeta.isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit((byte)15, (byte)2);
   }

   public void setLeftHanded(boolean value) {
      EntityMeta.isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(15, (byte)2, value);
   }

   public boolean isAggressive() {
      EntityMeta.isVersionNewer(ServerVersion.V_1_14);
      return this.getMaskBit((byte)15, (byte)4);
   }

   public void setAggressive(boolean value) {
      EntityMeta.isVersionNewer(ServerVersion.V_1_14);
      this.setMaskBit(15, (byte)4, value);
   }
}
