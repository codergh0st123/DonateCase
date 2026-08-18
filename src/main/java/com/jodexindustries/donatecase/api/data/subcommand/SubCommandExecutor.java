package com.jodexindustries.donatecase.api.data.subcommand;

import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommandExecutor {
   boolean execute(@NotNull DCCommandSender var1, @NotNull String var2, @NotNull String[] var3) throws SubCommandException;
}
