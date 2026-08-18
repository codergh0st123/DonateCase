package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.material.CaseMaterial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MaterialManager {
   void register(CaseMaterial var1);

   void unregister(String var1);

   default void unregister(Addon addon) {
      List<CaseMaterial> list = new ArrayList<>(this.get(addon));
      list.stream().map(CaseMaterial::id).forEach(this::unregister);
   }

   void unregister();

   boolean isRegistered(String var1);

   @Nullable CaseMaterial get(@NotNull String var1);

   default List<CaseMaterial> get(Addon addon) {
      return this.getMap().values().stream().filter((material) -> material.addon().equals(addon)).collect(Collectors.toList());
   }

   @NotNull Map<String, CaseMaterial> getMap();

   default Optional<String> getByStart(@NotNull String string) {
      return this.getMap().keySet().stream().filter(string::startsWith).findFirst();
   }
}
