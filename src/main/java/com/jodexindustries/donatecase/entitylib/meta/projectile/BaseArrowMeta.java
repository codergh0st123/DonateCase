package com.jodexindustries.donatecase.entitylib.meta.projectile;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class BaseArrowMeta extends EntityMeta {
   public static final byte OFFSET = 8;
   public static final byte MAX_OFFSET = 10;
   private static final byte CRITICAL_BIT = 1;
   private static final byte NO_CLIP_BIT = 2;

   public BaseArrowMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isCritical() {
      return this.getMaskBit((byte)8, (byte)1);
   }

   public void setCritical(boolean value) {
      this.setMaskBit(8, (byte)1, value);
   }

   public boolean isNoClip() {
      isVersionNewer(ServerVersion.V_1_9);
      return this.getMaskBit((byte)8, (byte)2);
   }

   public void setNoClip(boolean value) {
      isVersionNewer(ServerVersion.V_1_9);
      this.setMaskBit(8, (byte)2, value);
   }

   public int getPierceLevel() {
      isVersionNewer(ServerVersion.V_1_14);
      return (Integer)super.metadata.getIndex(offset((byte)8, 1), 0);
   }

   public void setPierceLevel(int value) {
      isVersionNewer(ServerVersion.V_1_14);
      super.metadata.setIndex(offset((byte)8, 1), EntityDataTypes.INT, value);
   }
}
