package com.jodexindustries.donatecase.entitylib.meta.mobs;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.types.AgeableMeta;

public class OcelotMeta extends AgeableMeta {
   public static final byte OFFSET = 17;
   public static final byte MAX_OFFSET = 18;

   public OcelotMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isTrusting() {
      isVersionNewer(ServerVersion.V_1_14);
      return (Boolean)super.metadata.getIndex((byte)17, false);
   }

   public void setTrusting(boolean value) {
      isVersionNewer(ServerVersion.V_1_14);
      super.metadata.setIndex((byte)17, EntityDataTypes.BOOLEAN, value);
   }
}
