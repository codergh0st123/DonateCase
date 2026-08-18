package com.jodexindustries.donatecase.entitylib;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface TrackedEntity {
   int getEntityId();

   @NotNull UUID getUuid();
}
