package com.jodexindustries.donatecase.api.config;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;

public interface Messages {
   @NotNull Config get();

   @NotNull String getString(Object... var1);

   @NotNull String getString(@NotNull String var1, @NotNull String var2);

   @NotNull List<String> getStringList(@NotNull String var1);

   void load(@NotNull String var1) throws ConfigurateException;
}
