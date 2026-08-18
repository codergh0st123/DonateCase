package com.jodexindustries.donatecase.spigot.api.platform;

import com.jodexindustries.donatecase.api.platform.DCOfflinePlayer;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class BukkitOfflinePlayer implements DCOfflinePlayer {
   private final OfflinePlayer player;

   public BukkitOfflinePlayer(OfflinePlayer player) {
      this.player = player;
   }

   public String getName() {
      return this.player.getName();
   }

   public @NotNull OfflinePlayer getHandler() {
      return this.player;
   }

   public @NotNull UUID getUniqueId() {
      return this.player.getUniqueId();
   }
}
