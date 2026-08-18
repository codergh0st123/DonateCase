package com.jodexindustries.donatecase.api.database;

import com.jodexindustries.donatecase.api.caching.SimpleCache;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import com.jodexindustries.donatecase.api.data.database.DatabaseType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class CaseDatabase {
   public static final SimpleCache<String, List<CaseData.History>> cache = new SimpleCache<String, List<CaseData.History>>(20L);

   public abstract void connect();

   public abstract void connect(String var1);

   public abstract void connect(String var1, int var2, String var3, String var4, String var5);

   public abstract CompletableFuture<Map<String, Integer>> getKeys(String var1);

   public abstract CompletableFuture<Integer> getKeys(String var1, String var2);

   public abstract CompletableFuture<DatabaseStatus> setKeys(String var1, String var2, int var3);

   public abstract CompletableFuture<DatabaseStatus> delAllKeys();

   public abstract CompletableFuture<DatabaseStatus> delKeys(String var1);

   public abstract CompletableFuture<Integer> getOpenCount(String var1, String var2);

   public abstract CompletableFuture<Map<String, Integer>> getOpenCount(String var1);

   public abstract CompletableFuture<DatabaseStatus> setCount(String var1, String var2, int var3);

   public abstract CompletableFuture<DatabaseStatus> addHistory(String var1, CaseData.History var2, int var3);

   public abstract CompletableFuture<DatabaseStatus> setHistoryData(String var1, int var2, CaseData.History var3);

   public abstract CompletableFuture<DatabaseStatus> removeHistoryData(String var1);

   public abstract CompletableFuture<DatabaseStatus> removeHistoryData(String var1, int var2);

   public abstract CompletableFuture<List<CaseData.History>> getHistoryData();

   public abstract CompletableFuture<List<CaseData.History>> getHistoryData(String var1);

   public abstract List<CaseData.History> getCache();

   public abstract List<CaseData.History> getCache(String var1);

   public abstract void close();

   public abstract DatabaseType getType();
}
