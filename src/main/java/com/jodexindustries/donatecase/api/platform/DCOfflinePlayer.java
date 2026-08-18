package com.jodexindustries.donatecase.api.platform;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DCOfflinePlayer {
   @Nullable String getName();

   @NotNull Object getHandler();

   @NotNull UUID getUniqueId();
}
