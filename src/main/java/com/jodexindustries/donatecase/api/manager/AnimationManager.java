package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.ActiveCase;
import com.jodexindustries.donatecase.api.data.animation.CaseAnimation;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnimationManager {
   boolean register(CaseAnimation var1);

   void unregister(@NotNull String var1);

   default void unregister(Addon addon) {
      List<CaseAnimation> list = new ArrayList(this.get(addon));
      list.stream().map(CaseAnimation::getName).forEach(this::unregister);
   }

   void unregister();

   CompletableFuture<UUID> start(@NotNull DCPlayer var1, @NotNull CaseLocation var2, @NotNull CaseData var3);

   CompletableFuture<UUID> start(@NotNull DCPlayer var1, @NotNull CaseLocation var2, @NotNull CaseData var3, int var4);

   CompletableFuture<UUID> start(@NotNull DCPlayer var1, @NotNull CaseLocation var2, @NotNull CaseData var3, boolean var4, int var5);

   void preEnd(UUID var1);

   void preEnd(CaseData var1, DCPlayer var2, CaseDataItem var3);

   void end(UUID var1);

   boolean isRegistered(String var1);

   @Nullable CaseAnimation get(String var1);

   default List<CaseAnimation> get(Addon addon) {
      return (List)this.getMap().values().stream().filter((animation) -> animation.getAddon().equals(addon)).collect(Collectors.toList());
   }

   Map<String, CaseAnimation> getMap();

   Map<UUID, ActiveCase> getActiveCases();

   Map<CaseLocation, List<UUID>> getActiveCasesByBlock();

   default List<ActiveCase> getActiveCasesByBlock(CaseLocation block) {
      List<ActiveCase> activeCases = new ArrayList();
      List<UUID> uuidList = (List)this.getActiveCasesByBlock().entrySet().stream().filter((entry) -> ((CaseLocation)entry.getKey()).equals(block)).findFirst().map(Map.Entry::getValue).orElse((Object)null);
      if (uuidList == null) {
         return activeCases;
      } else {
         List<ActiveCase> var4 = (List)uuidList.stream().map((uuid) -> (ActiveCase)this.getActiveCases().get(uuid)).collect(Collectors.toList());
         return var4;
      }
   }

   default boolean isLocked(CaseLocation block) {
      return this.getActiveCasesByBlock(block).stream().anyMatch(ActiveCase::locked);
   }
}
