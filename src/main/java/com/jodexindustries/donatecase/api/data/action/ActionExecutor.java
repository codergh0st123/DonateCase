package com.jodexindustries.donatecase.api.data.action;

import com.jodexindustries.donatecase.api.platform.DCPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ActionExecutor {
   void execute(@Nullable DCPlayer var1, @NotNull String var2) throws ActionException;
}
