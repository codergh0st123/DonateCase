package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class KeysCommand extends DefaultCommand {
   private final DCAPI api;

   public KeysCommand(DCAPI api) {
      super(api, "keys", SubCommandType.PLAYER);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      CompletableFuture.runAsync(() -> {
         if (args.length < 1) {
            if (sender instanceof DCPlayer) {
               DCPlayer player = (DCPlayer)sender;
               this.handlePlayer(sender, player);
            }
         } else {
            this.handleMod(sender, args[0]);
         }

      });
      return true;
   }

   private void handlePlayer(DCCommandSender sender, DCPlayer player) {
      if (sender.hasPermission("donatecase.player")) {
         for(String message : this.api.getConfigManager().getMessages().getStringList("my-keys")) {
            String formattedMessage = formatMessage(player.getName(), message, false, (String)null);
            sender.sendMessage(formattedMessage);
         }
      } else {
         sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("no-permission")));
      }

   }

   private void handleMod(DCCommandSender sender, String target) {
      if (sender.hasPermission("donatecase.mod")) {
         for(String message : this.api.getConfigManager().getMessages().getStringList("player-keys")) {
            sender.sendMessage(formatMessage(target, message.replace("%player%", target), false, (String)null));
         }
      } else {
         sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("no-permission")));
      }

   }

   public static String formatMessage(String player, String message, boolean cached, String caseType) {
      String placeholder = DCTools.getLocalPlaceholder(message);
      String result = "undefined";
      if (!placeholder.equals("keys") && !placeholder.equals("keys_format")) {
         if (placeholder.startsWith("keys_")) {
            String[] parts = placeholder.split("_");
            if (caseType == null && parts.length > 1) {
               caseType = parts[1];
            }

            if (caseType != null) {
               int keys = cached ? DCAPI.getInstance().getCaseKeyManager().getCache(caseType, player) : DCAPI.getInstance().getCaseKeyManager().get(caseType, player);
               if (parts.length == 2) {
                  result = String.valueOf(keys);
               } else if (parts.length == 3 && parts[2].equalsIgnoreCase("format")) {
                  result = NumberFormat.getNumberInstance().format((long)keys);
               }
            }
         }
      } else if (caseType != null) {
         int keys = cached ? DCAPI.getInstance().getCaseKeyManager().getCache(caseType, player) : DCAPI.getInstance().getCaseKeyManager().get(caseType, player);
         result = placeholder.equals("keys_format") ? NumberFormat.getNumberInstance().format((long)keys) : String.valueOf(keys);
      }

      return DCTools.rc(message.replace("%" + placeholder + "%", result));
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      return (List<String>)(args.length == 1 && sender.hasPermission("donatecase.mod") ? (List)Arrays.stream(this.api.getPlatform().getOnlinePlayers()).map(DCPlayer::getName).filter((px) -> px.startsWith(args[0])).collect(Collectors.toList()) : new ArrayList());
   }
}
