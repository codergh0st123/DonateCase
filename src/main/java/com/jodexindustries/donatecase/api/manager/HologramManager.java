package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface HologramManager extends HologramDriver {
   void register(@NotNull String var1, @NotNull HologramDriver var2);

   void unregister(@NotNull String var1);

   default void unregister() {
      List<String> list = new ArrayList(this.get().keySet());
      list.forEach(this::unregister);
   }

   Map<String, HologramDriver> get();

   void set(@NotNull String var1);

   void load();
}
