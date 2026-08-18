package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import com.jodexindustries.donatecase.common.tools.LocalPlaceholder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DeleteCommand extends DefaultCommand {
   private final DCAPI api;

   public DeleteCommand(DCAPI api) {
      super(api, "delete", SubCommandType.ADMIN);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      if (args.length == 0) {
         if (sender instanceof DCPlayer) {
            DCPlayer player = (DCPlayer)sender;
            CaseLocation location = player.getTargetBlock(5);
            if (this.api.getAnimationManager().isLocked(location)) {
               sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("case-opens")));
               return true;
            }

            CaseInfo caseInfo = this.api.getConfigManager().getCaseStorage().get(location);
            if (caseInfo != null) {
               this.api.getConfigManager().getCaseStorage().delete(location);
               this.api.getHologramManager().remove(caseInfo.location());
               sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("case-removed")));
            } else {
               sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("block-is-not-case")));
            }
         }
      } else if (args.length == 1) {
         String caseName = args[0];
         CaseInfo caseInfo = this.api.getConfigManager().getCaseStorage().get(caseName);
         if (caseInfo != null) {
            if (this.api.getAnimationManager().isLocked(caseInfo.location())) {
               sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("case-opens")));
               return true;
            }

            this.api.getConfigManager().getCaseStorage().delete(caseName);
            this.api.getHologramManager().remove(caseInfo.location());
            sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("case-removed")));
         } else {
            sender.sendMessage(DCTools.prefix(DCTools.rt(this.api.getConfigManager().getMessages().getString("case-does-not-exist"), LocalPlaceholder.of("%casename%", caseName))));
         }
      }

      return true;
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      return args.length == 1 ? new ArrayList(this.api.getConfigManager().getCaseStorage().get().keySet()) : new ArrayList();
   }
}
