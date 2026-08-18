package com.jodexindustries.donatecase.api.config;

import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;

public interface CaseStorage extends Loadable {
   void save(@NotNull String var1, @NotNull CaseInfo var2) throws ConfigurateException;

   void delete(String var1);

   boolean delete(CaseLocation var1);

   @Nullable CaseInfo get(String var1);

   @Nullable CaseInfo get(CaseLocation var1);

   @NotNull Map<String, CaseInfo> get();

   boolean has(String var1);

   default boolean has(CaseLocation location) {
      return this.get(location) != null;
   }
}
