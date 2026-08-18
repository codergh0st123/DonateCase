package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.InternalJavaAddon;
import com.jodexindustries.donatecase.api.addon.PowerReason;
import java.io.File;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AddonManager {
   void load();

   boolean load(File var1);

   void enable(PowerReason var1);

   boolean enable(@NotNull InternalJavaAddon var1, PowerReason var2);

   boolean disable(@NotNull InternalJavaAddon var1, PowerReason var2);

   void unload(PowerReason var1);

   boolean unload(@NotNull InternalJavaAddon var1, PowerReason var2);

   @Nullable InternalJavaAddon get(String var1);

   @NotNull Map<String, InternalJavaAddon> getMap();

   @NotNull File getFolder();

   @Nullable Class<?> getClassByName(String var1, boolean var2);
}
