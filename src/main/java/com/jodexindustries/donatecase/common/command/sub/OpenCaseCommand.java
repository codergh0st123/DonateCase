package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.animation.CaseAnimation;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import com.jodexindustries.donatecase.common.tools.LocalPlaceholder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class OpenCaseCommand extends DefaultCommand {
   private final DCAPI api;

   public OpenCaseCommand(DCAPI api) {
      super(api, "opencase", SubCommandType.PLAYER);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      if (args.length < 1) {
         return false;
      } else {
         if (sender instanceof DCPlayer) {
            String playerName = sender.getName();
            DCPlayer player = (DCPlayer)sender;
            String caseType = args[0];
            CaseData data = this.api.getCaseManager().get(caseType);
            if (data != null) {
               CaseAnimation animation = this.api.getAnimationManager().get(data.animation());
               if (animation == null) {
                  return true;
               }

               this.api.getCaseKeyManager().getAsync(caseType, playerName).thenAccept((keys) -> {
                  if (keys >= 1) {
                     this.api.getCaseKeyManager().remove(caseType, playerName, 1).thenAccept((status) -> {
                        if (status == DatabaseStatus.COMPLETE) {
                           if (animation.isRequireBlock()) {
                              this.api.getAnimationManager().preEnd(data, player, data.getRandomItem());
                           } else {
                              this.api.getAnimationManager().start(player, player.getLocation(), data);
                           }
                        }

                     });
                  } else {
                     sender.sendMessage(DCTools.prefix(this.api.getConfigManager().getMessages().getString("no-keys")));
                  }

               });
            } else {
               sender.sendMessage(DCTools.prefix(DCTools.rt(this.api.getConfigManager().getMessages().getString("case-does-not-exist"), LocalPlaceholder.of("%casetype%", caseType))));
            }
         }

         return true;
      }
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      List<String> list = new ArrayList(this.api.getCaseManager().getMap().keySet());
      return (List<String>)(args.length >= 2 ? new ArrayList() : list);
   }
}
