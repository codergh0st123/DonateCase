package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.manager.CaseManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaseManagerImpl implements CaseManager {
   private static final Map<String, CaseData> caseData = new ConcurrentHashMap();

   public boolean hasByType(@NotNull String type) {
      return caseData.containsKey(type);
   }

   public @Nullable CaseData get(@NotNull String type) {
      return (CaseData)caseData.get(type);
   }

   public Map<String, CaseData> getMap() {
      return caseData;
   }
}
