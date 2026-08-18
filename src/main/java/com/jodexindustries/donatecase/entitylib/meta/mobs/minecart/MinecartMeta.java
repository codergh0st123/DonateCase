package com.jodexindustries.donatecase.entitylib.meta.mobs.minecart;

import com.jodexindustries.donatecase.entitylib.meta.Metadata;

public class MinecartMeta extends BaseMinecartMeta {
   public static final byte OFFSET = 14;
   public static final byte MAX_OFFSET = 14;

   public MinecartMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public int getObjectData() {
      return 0;
   }
}
