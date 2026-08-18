package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.caching.SimpleCache;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class CaseOpenManager {
   public static final SimpleCache<String, Map<String, Integer>> cache = new SimpleCache<String, Map<String, Integer>>(20L);

   public abstract int get(String var1, String var2);

   public abstract Map<String, Integer> get(String var1);

   public abstract CompletableFuture<Integer> getAsync(String var1, String var2);

   public abstract CompletableFuture<Map<String, Integer>> getAsync(String var1);

   public abstract int getCache(String var1, String var2);

   public abstract Map<String, Integer> getCache(String var1);

   public abstract CompletableFuture<DatabaseStatus> set(String var1, String var2, int var3);

   public abstract CompletableFuture<DatabaseStatus> add(String var1, String var2, int var3);
}
