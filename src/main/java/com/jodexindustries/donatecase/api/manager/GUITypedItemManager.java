package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GUITypedItemManager {
   boolean register(TypedItem var1);

   void unregister(String var1);

   default void unregister(Addon addon) {
      List<TypedItem> list = new ArrayList(this.get(addon));
      list.stream().map(TypedItem::id).forEach(this::unregister);
   }

   void unregister();

   @Nullable TypedItem get(@NotNull String var1);

   default List<TypedItem> get(Addon addon) {
      return (List)this.getMap().values().stream().filter((item) -> item.addon().equals(addon)).collect(Collectors.toList());
   }

   @NotNull Map<String, TypedItem> getMap();

   default Optional<String> getByStart(@NotNull String string) {
      Stream var10000 = this.getMap().keySet().stream();
      String var10001 = string.toLowerCase();
      Objects.requireNonNull(var10001);
      return var10000.filter(var10001::startsWith).findFirst();
   }

   Optional<TypedItem> getFromString(@NotNull String var1);
}
