package com.jodexindustries.donatecase.common.command;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandExecutor;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandTabCompleter;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import com.jodexindustries.donatecase.common.tools.LocalPlaceholder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class GlobalCommand implements SubCommandExecutor, SubCommandTabCompleter {
   private final BackendPlatform backend;

   public GlobalCommand(BackendPlatform backend) {
      this.backend = backend;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String @NotNull [] args) {
      if (args.length == 0) {
         this.sendHelp(sender, label);
      } else {
         String subCommandName = args[0];
         SubCommand subCommand = this.backend.getAPI().getSubCommandManager().get(subCommandName);
         if (subCommand == null) {
            this.sendHelp(sender, label);
            return false;
         }

         String permission = subCommand.permission();
         if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(DCTools.prefix(this.backend.getAPI().getConfigManager().getMessages().getString("no-permission")));
         } else {
            try {
               if (!subCommand.execute(sender, label, (String[])Arrays.copyOfRange(args, 1, args.length))) {
                  this.sendHelp(sender, label);
               }
            } catch (Exception e) {
               this.backend.getLogger().log(Level.WARNING, "Error with executing subcommand: " + subCommandName, e);
            }
         }
      }

      return true;
   }

   public void sendHelp(DCCommandSender sender, String label) {
      if (!sender.hasPermission("donatecase.player")) {
         sender.sendMessage(DCTools.prefix(this.backend.getAPI().getConfigManager().getMessages().getString("no-permission")));
      } else {
         sender.sendMessage(DCTools.rc("&aDonateCase &7v&6" + this.backend.getVersion() + " &7(&eAPI &7v&6" + "2.1.0.4" + "&7) by &c_Jodex__"));
         if (!sender.hasPermission("donatecase.mod")) {
            this.sendHelpMessages(sender, "help-player", label);
         } else {
            this.sendHelpMessages(sender, "help", label);
         }

         if (this.backend.getAPI().getConfigManager().getConfig().addonsHelp()) {
            Map<String, List<Map<String, SubCommand>>> addonsMap = this.buildAddonsMap();
            if (DCTools.isHasCommandForSender(sender, addonsMap)) {
               this.sendAddonHelpMessages(sender, addonsMap);
            }
         }

      }
   }

   private void sendHelpMessages(DCCommandSender sender, String path, String label) {
      for(String string : this.backend.getAPI().getConfigManager().getMessages().getStringList(path)) {
         sender.sendMessage(DCTools.rc(DCTools.rt(string, LocalPlaceholder.of("%cmd%", label))));
      }

   }

   private Map<String, List<Map<String, SubCommand>>> buildAddonsMap() {
      Map<String, List<Map<String, SubCommand>>> addonsMap = new HashMap<>();
      this.backend.getAPI().getSubCommandManager().getMap().forEach((subCommandName, subCommand) -> {
         Addon addon = subCommand.addon();
         ((List)addonsMap.computeIfAbsent(addon.getName(), (k) -> new ArrayList<>())).add(Collections.singletonMap(subCommandName, subCommand));
      });
      return addonsMap;
   }

   private void sendAddonHelpMessages(DCCommandSender sender, Map<String, List<Map<String, SubCommand>>> addonsMap) {
      addonsMap.forEach((addon, commands) -> {
         if (!addon.equalsIgnoreCase("DonateCase") && DCTools.isHasCommandForSender(sender, commands)) {
            String addonNameFormat = this.backend.getAPI().getConfigManager().getMessages().getString("help-addons", "format", "name");
            if (!addonNameFormat.isEmpty()) {
               sender.sendMessage(DCTools.rc(DCTools.rt(addonNameFormat, LocalPlaceholder.of("%addon%", addon))));
            }

            commands.forEach((command) -> command.forEach((commandName, subCommand) -> {
                  String description = subCommand.description();
                  description = description != null ? DCTools.rt(this.backend.getAPI().getConfigManager().getMessages().getString("help-addons", "format", "description"), LocalPlaceholder.of("%description%", description)) : "";
                  StringBuilder argsBuilder = compileSubCommandArgs(subCommand.args());
                  String permission = subCommand.permission();
                  if (permission == null || sender.hasPermission(permission)) {
                     sender.sendMessage(DCTools.rc(DCTools.rt(this.backend.getAPI().getConfigManager().getMessages().getString("help-addons", "format", "command"), LocalPlaceholder.of("%cmd%", commandName), LocalPlaceholder.of("%args%", argsBuilder), LocalPlaceholder.of("%description%", description))));
                  }

               }));
         }

      });
   }

   private static @NotNull StringBuilder compileSubCommandArgs(String[] args) {
      StringBuilder builder = new StringBuilder();
      if (args != null) {
         for(String arg : args) {
            builder.append(arg).append(" ");
         }

         builder.setLength(builder.length() - 1);
      }

      return builder;
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, @NotNull String[] args) {
      List<String> value = new ArrayList<>();
      if (args.length != 1) {
         SubCommand subCommand = this.backend.getAPI().getSubCommandManager().get(args[0].toLowerCase());
         return (List<String>)(subCommand == null ? new ArrayList<>() : subCommand.getTabCompletions(sender, label, (String[])Arrays.copyOfRange(args, 1, args.length)));
      } else {
         Map<String, SubCommand> subCommands = this.backend.getAPI().getSubCommandManager().getMap();

         for(Map.Entry<String, SubCommand> entry : subCommands.entrySet()) {
            String subCommandName = (String)entry.getKey();
            SubCommand subCommand = (SubCommand)entry.getValue();
            String permission = subCommand.permission();
            if (permission == null || sender.hasPermission(permission)) {
               value.add(subCommandName);
            }
         }

         if (args[args.length - 1].isEmpty()) {
            Collections.sort(value);
            return value;
         } else {
            return (List)value.stream().filter((tmp) -> tmp.startsWith(args[args.length - 1])).sorted().collect(Collectors.toList());
         }
      }
   }
}
