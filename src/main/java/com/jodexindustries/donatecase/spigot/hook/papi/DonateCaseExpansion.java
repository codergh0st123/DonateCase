package com.jodexindustries.donatecase.spigot.hook.papi;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class DonateCaseExpansion extends PlaceholderExpansion {
   private final BukkitBackend backend;

   public DonateCaseExpansion(BukkitBackend backend) {
      this.backend = backend;
   }

   public @NotNull String getAuthor() {
      return "JodexIndustries";
   }

   public @NotNull String getIdentifier() {
      return this.backend.getName();
   }

   public @NotNull String getVersion() {
      return this.backend.getVersion();
   }

   public boolean persist() {
      return true;
   }

   public String onRequest(OfflinePlayer player, @NotNull String params) {
      if (params.startsWith("keys")) {
         return this.processKeys(params, player);
      } else if (params.startsWith("open_count")) {
         return this.processOpenCount(params, player);
      } else {
         return params.startsWith("history_") ? this.processHistory(params.replaceFirst("history_", "")) : null;
      }
   }

   private String processKeys(@NotNull String params, OfflinePlayer player) {
      if (params.startsWith("keys")) {
         String[] parts = params.split("_", 2);
         Map<String, Integer> map = this.backend.getAPI().getCaseKeyManager().getCache(player.getName());
         int keys = map.values().stream().mapToInt((key) -> key).sum();
         if (parts.length == 1) {
            return String.valueOf(keys);
         }

         if (parts[1].equalsIgnoreCase("format")) {
            return NumberFormat.getNumberInstance().format((long)keys);
         }
      }

      if (params.startsWith("keys_")) {
         String[] parts = params.split("_", 3);
         int keys = this.backend.getAPI().getCaseKeyManager().getCache(parts[1], player.getName());
         if (parts.length == 2) {
            return String.valueOf(keys);
         } else {
            return parts[2].equalsIgnoreCase("format") ? NumberFormat.getNumberInstance().format((long)keys) : String.valueOf(keys);
         }
      } else {
         return null;
      }
   }

   private String processOpenCount(@NotNull String params, OfflinePlayer player) {
      if (params.startsWith("open_count")) {
         String[] parts = params.split("_", 3);
         Map<String, Integer> map = this.backend.getAPI().getCaseOpenManager().getCache(player.getName());
         int count = map.values().stream().mapToInt((c) -> c).sum();
         if (parts.length == 2) {
            return String.valueOf(count);
         } else {
            return parts[2].equalsIgnoreCase("format") ? NumberFormat.getNumberInstance().format((long)count) : String.valueOf(count);
         }
      } else if (params.startsWith("open_count_")) {
         String[] parts = params.split("_", 4);
         int count = this.backend.getAPI().getCaseOpenManager().getCache(parts[2], player.getName());
         if (parts.length == 3) {
            return String.valueOf(count);
         } else {
            return parts[3].equalsIgnoreCase("format") ? NumberFormat.getNumberInstance().format((long)count) : String.valueOf(count);
         }
      } else {
         return null;
      }
   }

   private String processHistory(@NotNull String params) {
      String[] parts = params.split("_");
      if (parts.length >= 3) {
         String caseType = parts[0];
         int index = this.parseInt(parts[1]);
         if (index >= 0) {
            List<CaseData.History> list = this.backend.getAPI().getDatabase().getCache(caseType);
            if (list.size() > index) {
               CaseData.History history = (CaseData.History)list.get(index);
               switch (parts[2].toLowerCase()) {
                  case "player":
                     return history.playerName();
                  case "casetype":
                     return history.caseType();
                  case "group":
                     return history.group();
                  case "action":
                     return history.action();
                  case "item":
                     return history.item();
                  case "time":
                     return DCTools.getDateFormat().format(new Date(history.time()));
               }
            }
         }
      }

      return null;
   }

   private int parseInt(String string) {
      try {
         return Integer.parseInt(string);
      } catch (NumberFormatException var3) {
         return -1;
      }
   }
}
