package com.jodexindustries.donatecase.api.addon;

import com.jodexindustries.donatecase.api.platform.Platform;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InternalAddon extends Addon {
   boolean isEnabled();

   void onDisable();

   void onEnable();

   void onLoad();

   void saveResource(@NotNull String var1, boolean var2);

   @Nullable InputStream getResource(@NotNull String var1);

   @NotNull InternalAddonDescription getDescription();

   @NotNull Platform getPlatform();
}
