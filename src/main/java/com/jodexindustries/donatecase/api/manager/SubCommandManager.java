package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SubCommandManager {
   void register(SubCommand var1);

   void unregister(String var1);

   default void unregister(Addon addon) {
      List<SubCommand> list = new ArrayList(this.get(addon));
      list.stream().map(SubCommand::name).forEach(this::unregister);
   }

   void unregister();

   default boolean isRegistered(@NotNull String name) {
      return this.getMap().containsKey(name);
   }

   @Nullable SubCommand get(String var1);

   default List<SubCommand> get(Addon addon) {
      return (List)this.getMap().values().stream().filter((subCommand) -> subCommand.addon().equals(addon)).collect(Collectors.toList());
   }

   @NotNull Map<String, SubCommand> getMap();
}
