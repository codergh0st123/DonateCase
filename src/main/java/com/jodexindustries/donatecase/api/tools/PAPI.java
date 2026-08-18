package com.jodexindustries.donatecase.api.tools;

import com.jodexindustries.donatecase.api.platform.DCPlayer;
import org.jetbrains.annotations.NotNull;

public interface PAPI {
   void register();

   void unregister();

   String setPlaceholders(@NotNull Object var1, String var2);

   String setPlaceholders(@NotNull DCPlayer var1, String var2);
}
