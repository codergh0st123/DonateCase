package com.jodexindustries.donatecase.api.config;

import com.jodexindustries.donatecase.api.config.converter.ConfigType;
import java.io.File;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;

public interface Config {
   ConfigurationNode node();

   default ConfigurationNode node(Object... path) {
      return this.node().node(path);
   }

   default @Nullable Object getSerialized() {
      return null;
   }

   File file();

   String path();

   int version();

   ConfigType type();

   void type(ConfigType var1);

   void load() throws ConfigurateException;

   void save() throws ConfigurateException;
}
