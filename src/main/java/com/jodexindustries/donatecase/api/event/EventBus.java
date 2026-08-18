package com.jodexindustries.donatecase.api.event;

import org.jetbrains.annotations.NotNull;

public interface EventBus extends net.kyori.event.EventBus<DCEvent> {
   void register(@NotNull Subscriber var1);

   void unregister(@NotNull Subscriber var1);
}
