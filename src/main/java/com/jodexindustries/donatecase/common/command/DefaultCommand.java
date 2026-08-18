package com.jodexindustries.donatecase.common.command;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandException;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandExecutor;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandTabCompleter;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class DefaultCommand implements SubCommandExecutor, SubCommandTabCompleter {
   private final DCAPI api;
   private final String name;
   private final SubCommandType type;

   public DefaultCommand(DCAPI api, String name, SubCommandType type) {
      this.api = api;
      this.name = name;
      this.type = type;
   }

   public final SubCommand build() {
      return SubCommand.builder().addon(this.api.getPlatform()).name(this.name).permission(this.type.permission).tabCompleter(this).executor(this).build();
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, @NotNull String[] args) throws SubCommandException {
      return new ArrayList<>();
   }
}
