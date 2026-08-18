package com.jodexindustries.donatecase.common.config;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.Messages;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.SerializationException;

public class MessagesImpl implements Messages {
   private static final String DEFAULT_LANG = "lang/en_US.yml";
   private final ConfigManagerImpl configManager;
   private final BackendPlatform platform;
   private Config config;

   public MessagesImpl(ConfigManagerImpl configManager) {
      this.configManager = configManager;
      this.platform = configManager.getPlatform();
   }

   public @NotNull Config get() {
      return this.config;
   }

   public @NotNull String getString(Object... path) {
      String value = this.config.node(path).getString();
      return value == null ? "" : value;
   }

   public @NotNull String getString(@NotNull String path, @NotNull String def) {
      return this.config.node(path.split("\\.")).getString(def);
   }

   public @NotNull List<String> getStringList(@NotNull String path) {
      try {
         return this.config.node(path).getList(String.class, new ArrayList<>());
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public void load(@NotNull String language) throws ConfigurateException {
      String path = "lang/" + language + ".yml";
      Config config = this.configManager.getConfig(path);
      if (config == null) {
         if (this.platform.getResource(path) != null) {
            this.platform.saveResource(path, false);
            config = this.configManager.load(new File(this.platform.getDataFolder(), path));
         } else {
            config = this.loadDefault();
            this.platform.getLogger().warning("Language file \"" + language + "\" was not found! Using the default \"en_US\"");
         }
      }

      if (config == null) {
         throw new ConfigurateException("Failed to load messages configuration: " + path);
      } else {
         this.config = config;
      }
   }

   private Config loadDefault() {
      File defaultLang = new File(this.platform.getDataFolder(), "lang/en_US.yml");
      if (!defaultLang.exists()) {
         this.platform.saveResource("lang/en_US.yml", false);
      }

      return this.configManager.load(defaultLang);
   }
}
