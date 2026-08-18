package com.jodexindustries.donatecase.api.config.converter;

import com.jodexindustries.donatecase.api.data.config.ConfigSerializer;
import org.jetbrains.annotations.Nullable;

public interface ConfigType {
   int getLatestVersion();

   boolean isPermanent();

   ConfigMigrator getMigrator(int var1);

   default @Nullable ConfigSerializer getConfigSerializer() {
      return null;
   }
}
