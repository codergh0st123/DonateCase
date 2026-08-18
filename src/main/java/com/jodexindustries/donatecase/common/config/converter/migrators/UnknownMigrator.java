package com.jodexindustries.donatecase.common.config.converter.migrators;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import com.jodexindustries.donatecase.api.config.converter.ConfigType;
import com.jodexindustries.donatecase.common.config.converter.DefaultConfigType;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class UnknownMigrator implements ConfigMigrator {
   public void migrate(Config config) throws SerializationException {
      String name = config.file().getName().toLowerCase();
      ConfigurationNode node = config.node();
      ConfigType type = config.type();
      switch (name) {
         case "animations.yml":
            type = DefaultConfigType.ANIMATIONS;
            break;
         case "config.yml":
            type = DefaultConfigType.CONFIG;
            break;
         case "cases.yml":
            type = DefaultConfigType.CASES;
      }

      if (config.path().contains("/lang")) {
         type = DefaultConfigType.LANG;
      }

      if (type == DefaultConfigType.UNKNOWN) {
         type = DefaultConfigType.UNKNOWN_CUSTOM;
      }

      node.removeChild("config");
      node.node(new Object[]{"config", "version"}).set(config.version());
      node.node(new Object[]{"config", "type"}).set(type);
      config.type(type);
   }
}
