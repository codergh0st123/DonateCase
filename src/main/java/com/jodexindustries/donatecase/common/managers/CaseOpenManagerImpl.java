package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import com.jodexindustries.donatecase.api.data.database.DatabaseType;
import com.jodexindustries.donatecase.api.manager.CaseOpenManager;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CaseOpenManagerImpl extends CaseOpenManager {
   private final DCAPI api;

   public CaseOpenManagerImpl(DCAPI api) {
      this.api = api;
   }

   public int get(String caseType, String player) {
      return (Integer)this.getAsync(caseType, player).join();
   }

   public Map<String, Integer> get(String player) {
      return (Map)this.getAsync(player).join();
   }

   public CompletableFuture<Integer> getAsync(String caseType, String player) {
      return this.api.getDatabase().getOpenCount(player, caseType);
   }

   public CompletableFuture<Map<String, Integer>> getAsync(String player) {
      return this.api.getDatabase().getOpenCount(player);
   }

   public int getCache(String caseType, String player) {
      Integer count = (Integer)this.getCache(player).get(caseType);
      return count == null ? 0 : count;
   }

   public Map<String, Integer> getCache(String player) {
      if (this.api.getDatabase().getType() == DatabaseType.SQLITE) {
         return this.get(player);
      } else {
         Map<String, Integer> cachedCount = cache.get(player);
         Map<String, Integer> count;
         if (cachedCount == null) {
            Map<String, Integer> previous = cache.getPrevious(player);
            count = previous != null ? previous : this.get(player);
            this.getAsync(player).thenAcceptAsync((map) -> cache.put(player, map));
         } else {
            count = cachedCount;
         }

         return count;
      }
   }

   public CompletableFuture<DatabaseStatus> set(String caseType, String player, int openCount) {
      return this.api.getDatabase().setCount(caseType, player, openCount);
   }

   public CompletableFuture<DatabaseStatus> add(String caseType, String player, int openCount) {
      return this.getAsync(caseType, player).thenComposeAsync((integer) -> this.set(caseType, player, integer + openCount));
   }
}
