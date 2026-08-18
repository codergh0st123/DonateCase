package com.jodexindustries.donatecase.api.data.material;

import org.jetbrains.annotations.NotNull;

public interface MaterialHandler {
   @NotNull Object handle(@NotNull String var1) throws CaseMaterialException;
}
