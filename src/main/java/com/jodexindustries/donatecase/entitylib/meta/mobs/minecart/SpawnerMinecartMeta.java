package com.jodexindustries.donatecase.entitylib.meta.mobs.minecart;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class SpawnerMinecartMeta extends BaseMinecartMeta {
   public static final byte OFFSET = 14;
   public static final byte MAX_OFFSET = 14;

   public SpawnerMinecartMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getObjectData() {
      return 4;
   }
}
