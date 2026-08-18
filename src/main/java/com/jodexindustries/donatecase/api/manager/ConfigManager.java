package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.config.CaseStorage;
import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.Loadable;
import com.jodexindustries.donatecase.api.config.Messages;
import com.jodexindustries.donatecase.api.data.config.ConfigData;
import java.io.File;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

public interface ConfigManager extends Loadable {
   Config load(@NotNull File var1);

   @Nullable Config getConfig(@NotNull String var1);

   @Nullable ConfigurationNode getNode(@NotNull String var1);

   Map<String, ? extends Config> get();

   default ConfigData getConfig() {
      Config config = this.getConfig("Config.yml");
      return config == null ? null : (ConfigData)config.getSerialized();
   }

   default ConfigurationNode getAnimations() {
      return this.getNode("Animations.yml");
   }

   @NotNull Messages getMessages();

   @NotNull CaseStorage getCaseStorage();
}
