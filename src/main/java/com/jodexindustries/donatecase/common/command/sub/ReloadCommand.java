package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends DefaultCommand {
   private final DCAPI api;

   public ReloadCommand(DCAPI api) {
      super(api, "reload", SubCommandType.ADMIN);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      if (args.length == 0) {
         this.load();
         sender.sendMessage(DCTools.prefix(DCTools.rc(this.api.getConfigManager().getMessages().getString("config-reloaded"))));
      } else if (args[0].equalsIgnoreCase("cache")) {
         this.api.clear();
         this.load();
         sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("config-cache-reloaded", "&aReloaded all DonateCase Cache")));
      }

      return true;
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      List<String> list = new ArrayList<>();
      if (args.length == 1) {
         list.add("cache");
      }

      return list;
   }

   private void load() {
      this.api.getConfigManager().load();
      this.api.getCaseLoader().load();
      this.api.getHologramManager().load();
   }
}
