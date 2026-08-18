package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.caching.SimpleCache;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class CaseKeyManager {
   public static final SimpleCache<String, Map<String, Integer>> cache = new SimpleCache<String, Map<String, Integer>>(20L);

   public abstract CompletableFuture<DatabaseStatus> set(String var1, String var2, int var3);

   public abstract CompletableFuture<DatabaseStatus> modify(String var1, String var2, int var3);

   public CompletableFuture<DatabaseStatus> add(String caseType, String player, int keys) {
      return this.modify(caseType, player, keys);
   }

   public CompletableFuture<DatabaseStatus> remove(String caseType, String player, int keys) {
      return this.modify(caseType, player, -keys);
   }

   public abstract CompletableFuture<DatabaseStatus> delete();

   public abstract CompletableFuture<DatabaseStatus> delete(String var1);

   public int get(String caseType, String player) {
      return (Integer)this.getAsync(caseType, player).join();
   }

   public Map<String, Integer> get(String player) {
      return (Map)this.getAsync(player).join();
   }

   public abstract CompletableFuture<Integer> getAsync(String var1, String var2);

   public abstract CompletableFuture<Map<String, Integer>> getAsync(String var1);

   public abstract int getCache(String var1, String var2);

   public abstract Map<String, Integer> getCache(String var1);
}
