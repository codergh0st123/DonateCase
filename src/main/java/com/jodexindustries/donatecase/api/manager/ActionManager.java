package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.action.ActionException;
import com.jodexindustries.donatecase.api.data.action.CaseAction;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ActionManager {
   void register(CaseAction var1) throws ActionException;

   void unregister(@NotNull String var1);

   default void unregister(Addon addon) {
      List<CaseAction> list = new ArrayList<>(this.get(addon));
      list.stream().map(CaseAction::name).forEach(this::unregister);
   }

   void unregister();

   default boolean isRegistered(@NotNull String name) {
      return this.getMap().containsKey(name);
   }

   default Optional<CaseAction> get(@NotNull String name) {
      return Optional.ofNullable((CaseAction)this.getMap().get(name));
   }

   default List<CaseAction> get(Addon addon) {
      return this.getMap().values().stream().filter((action) -> action.addon().equals(addon)).collect(Collectors.toList());
   }

   @NotNull Map<String, CaseAction> getMap();

   default Optional<String> getByStart(@NotNull String prefix) {
      return this.getMap().keySet().stream().filter(name -> name.startsWith(prefix)).sorted().findFirst();
   }

   void execute(@Nullable DCPlayer var1, @NotNull String var2, int var3);

   void execute(@Nullable DCPlayer var1, @NotNull List<String> var2);
}
