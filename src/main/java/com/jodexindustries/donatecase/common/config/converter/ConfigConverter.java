package com.jodexindustries.donatecase.common.config.converter;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import com.jodexindustries.donatecase.api.config.converter.ConfigType;
import com.jodexindustries.donatecase.common.config.ConfigManagerImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongepowered.configurate.ConfigurateException;

public class ConfigConverter {
   private final ConfigManagerImpl configManager;

   public ConfigConverter(ConfigManagerImpl configManager) {
      this.configManager = configManager;
   }

   public void convert() {
      for(Config config : this.configManager.get().values()) {
         try {
            this.convert(config);
         } catch (ConfigurateException e) {
            this.configManager.getPlatform().getLogger().log(Level.WARNING, "Error with converting configuration: " + config, e);
         }
      }

   }

   public void convert(Config config) throws ConfigurateException, IllegalArgumentException {
      int version = config.version();
      ConfigType type = config.type();
      if (version != type.getLatestVersion() || type.isPermanent()) {
         while(version < type.getLatestVersion() || type.isPermanent()) {
            ConfigMigrator migrator = type.getMigrator(version);
            if (migrator == null) {
               break;
            }

            this.configManager.getPlatform().getLogger().info(config + " converting...");
            migrator.migrate(config);
            if (type.isPermanent()) {
               this.configManager.getPlatform().getLogger().info(config + " converted permanently from UNKNOWN to " + config.type());
               this.convert(config);
               break;
            }

            Logger var10000 = this.configManager.getPlatform().getLogger();
            StringBuilder var10001 = (new StringBuilder()).append(config).append(" converted from ").append(version).append(" to ");
            ++version;
            var10000.info(var10001.append(version).toString());
         }

         config.save();
      }
   }
}
