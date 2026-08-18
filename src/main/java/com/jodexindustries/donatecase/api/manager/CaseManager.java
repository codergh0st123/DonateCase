package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CaseManager {
   boolean hasByType(@NotNull String var1);

   @Nullable CaseData get(@NotNull String var1);

   Map<String, CaseData> getMap();
}
