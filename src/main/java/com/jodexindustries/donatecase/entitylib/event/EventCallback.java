package com.jodexindustries.donatecase.entitylib.event;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EventCallback<E extends EntityLibEvent> {
   void run(@NotNull E var1);
}
