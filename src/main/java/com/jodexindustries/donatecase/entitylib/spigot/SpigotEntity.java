package com.jodexindustries.donatecase.entitylib.spigot;

import com.jodexindustries.donatecase.entitylib.common.AbstractTrackedEntity;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SpigotEntity extends AbstractTrackedEntity<Entity> {
   public SpigotEntity(@NotNull Entity platformEntity) {
      super(platformEntity);
   }

   public int getEntityId() {
      return ((Entity)this.getPlatformEntity()).getEntityId();
   }

   public @NotNull UUID getUuid() {
      return ((Entity)this.getPlatformEntity()).getUniqueId();
   }
}
