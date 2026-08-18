package com.jodexindustries.donatecase.api.addon;

import java.io.File;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public interface Addon {
   String getName();

   String getVersion();

   Logger getLogger();

   @NotNull File getDataFolder();
}
