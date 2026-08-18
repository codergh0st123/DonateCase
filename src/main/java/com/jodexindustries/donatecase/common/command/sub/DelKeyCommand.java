package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import com.jodexindustries.donatecase.common.tools.LocalPlaceholder;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DelKeyCommand extends DefaultCommand {
   private final DCAPI api;

   public DelKeyCommand(DCAPI api) {
      super(api, "delkey", SubCommandType.ADMIN);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      if (args.length == 0) {
         return false;
      } else {
         String playerName = args[0];
         if (playerName.equalsIgnoreCase("all")) {
            if (args.length == 1) {
               this.api.getCaseKeyManager().delete().thenAcceptAsync((status) -> sender.sendMessage(DCTools.rc(this.api.getConfigManager().getMessages().getString("all-keys-cleared"))));
               return true;
            }

            if (args.length == 2) {
               this.api.getCaseKeyManager().delete(args[1]).thenAcceptAsync((status) -> sender.sendMessage(DCTools.rc(this.api.getConfigManager().getMessages().getString("all-keys-cleared"))));
               return true;
            }
         }

         if (args.length < 2) {
            return false;
         } else {
            String caseType = args[1];
            if (!DCTools.isValidPlayerName(playerName)) {
               sender.sendMessage(DCTools.rt(this.api.getConfigManager().getMessages().getString("player-not-found"), LocalPlaceholder.of("%player%", playerName)));
               return true;
            } else {
               CaseData data = this.api.getCaseManager().get(caseType);
               if (data != null) {
                  int keys;
                  if (args.length == 2) {
                     keys = this.api.getCaseKeyManager().get(caseType, playerName);
                     this.api.getCaseKeyManager().set(caseType, playerName, 0);
                  } else {
                     try {
                        keys = Integer.parseInt(args[2]);
                     } catch (NumberFormatException var9) {
                        sender.sendMessage(DCTools.rt(this.api.getConfigManager().getMessages().getString("number-format-exception"), LocalPlaceholder.of("%string%", args[2])));
                        return true;
                     }

                     this.api.getCaseKeyManager().remove(caseType, playerName, keys);
                  }

                  Collection<LocalPlaceholder> placeholders = LocalPlaceholder.of(data);
                  placeholders.add(LocalPlaceholder.of("%player%", playerName));
                  placeholders.add(LocalPlaceholder.of("%key%", keys));
                  sender.sendMessage(DCTools.rt(this.api.getConfigManager().getMessages().getString("keys-cleared"), placeholders));
               } else {
                  sender.sendMessage(DCTools.rt(this.api.getConfigManager().getMessages().getString("case-does-not-exist"), LocalPlaceholder.of("%casetype%", caseType)));
               }

               return true;
            }
         }
      }
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      return DCTools.resolveSDGCompletions(args);
   }
}
