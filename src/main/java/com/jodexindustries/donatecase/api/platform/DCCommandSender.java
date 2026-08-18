package com.jodexindustries.donatecase.api.platform;

import org.jetbrains.annotations.NotNull;

public interface DCCommandSender {
   @NotNull String getName();

   @NotNull Object getHandler();

   boolean hasPermission(String var1);

   void sendMessage(@NotNull String var1);
}
