package com.jodexindustries.donatecase.spigot.hook.papi;

import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.PAPI;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PAPISupport implements PAPI {
   private DonateCaseExpansion donateCaseExpansion = null;

   public PAPISupport(BukkitBackend backend) {
      if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
         this.donateCaseExpansion = new DonateCaseExpansion(backend);
         backend.getLogger().info("Hooked to PlaceholderAPI");
      }

   }

   public void register() {
      if (this.donateCaseExpansion != null) {
         this.donateCaseExpansion.register();
      }

   }

   public void unregister() {
      if (this.donateCaseExpansion != null) {
         this.donateCaseExpansion.unregister();
      }

   }

   public String setPlaceholders(@NotNull Object player, String text) {
      if (this.donateCaseExpansion == null) {
         return text;
      } else if (player instanceof OfflinePlayer) {
         return DCTools.rc(PlaceholderAPI.setPlaceholders((OfflinePlayer)player, text));
      } else {
         return player instanceof DCPlayer ? this.setPlaceholders((DCPlayer)player, text) : text;
      }
   }

   public String setPlaceholders(@NotNull DCPlayer player, String text) {
      return this.setPlaceholders(player.getHandler(), text);
   }
}
