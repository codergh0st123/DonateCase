package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandException;
import com.jodexindustries.donatecase.api.manager.SubCommandManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import com.jodexindustries.donatecase.common.command.sub.AddonCommand;
import com.jodexindustries.donatecase.common.command.sub.AddonsCommand;
import com.jodexindustries.donatecase.common.command.sub.CasesCommand;
import com.jodexindustries.donatecase.common.command.sub.CreateCommand;
import com.jodexindustries.donatecase.common.command.sub.DelKeyCommand;
import com.jodexindustries.donatecase.common.command.sub.DeleteCommand;
import com.jodexindustries.donatecase.common.command.sub.GiveKeyCommand;
import com.jodexindustries.donatecase.common.command.sub.HelpCommand;
import com.jodexindustries.donatecase.common.command.sub.KeysCommand;
import com.jodexindustries.donatecase.common.command.sub.OpenCaseCommand;
import com.jodexindustries.donatecase.common.command.sub.RegistryCommand;
import com.jodexindustries.donatecase.common.command.sub.ReloadCommand;
import com.jodexindustries.donatecase.common.command.sub.SetKeyCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubCommandManagerImpl implements SubCommandManager {
   private static final List<? extends Class<? extends DefaultCommand>> defaultCommands = Arrays.asList(AddonCommand.class, AddonsCommand.class, CasesCommand.class, CreateCommand.class, DeleteCommand.class, DelKeyCommand.class, GiveKeyCommand.class, HelpCommand.class, KeysCommand.class, OpenCaseCommand.class, RegistryCommand.class, ReloadCommand.class, SetKeyCommand.class);
   private static final Map<String, SubCommand> registeredSubCommands = new ConcurrentHashMap();
   private final DCAPI api;
   private final Platform platform;

   public SubCommandManagerImpl(DCAPI api) {
      this.api = api;
      this.platform = api.getPlatform();
      this.registerDefault();
   }

   public void register(SubCommand subCommand) {
      String name = subCommand.name().toLowerCase();
      if (this.isRegistered(name)) {
         throw new SubCommandException("Sub command with name " + name + " already registered!");
      } else {
         registeredSubCommands.put(name, subCommand);
      }
   }

   public void unregister(String name) {
      if (!this.isRegistered(name)) {
         throw new SubCommandException("Sub command with name " + name + " already unregistered!");
      } else {
         registeredSubCommands.remove(name.toLowerCase());
      }
   }

   public void unregister() {
      List<String> list = new ArrayList(registeredSubCommands.keySet());
      list.forEach(this::unregister);
   }

   public @Nullable SubCommand get(String commandName) {
      return (SubCommand)registeredSubCommands.get(commandName);
   }

   public @NotNull Map<String, SubCommand> getMap() {
      return registeredSubCommands;
   }

   private void registerDefault() {
      defaultCommands.forEach((commandClass) -> {
         try {
            DefaultCommand command = (DefaultCommand)commandClass.getDeclaredConstructor(DCAPI.class).newInstance(this.api);
            this.register(command.build());
         } catch (Exception e) {
            this.platform.getLogger().log(Level.WARNING, "Failed to register command: " + commandClass.getSimpleName(), e);
         }

      });
   }
}
