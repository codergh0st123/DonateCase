package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import com.jodexindustries.donatecase.api.data.database.DatabaseType;
import com.jodexindustries.donatecase.api.event.plugin.KeysTransactionEvent;
import com.jodexindustries.donatecase.api.manager.CaseKeyManager;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CaseKeyManagerImpl extends CaseKeyManager {
   private final DCAPI api;

   public CaseKeyManagerImpl(DCAPI api) {
      this.api = api;
   }

   private CompletableFuture<DatabaseStatus> setKeysWithEvent(String caseType, String player, int newKeys, int before) {
      KeysTransactionEvent event = new KeysTransactionEvent(caseType, player, newKeys, before);
      this.api.getEventBus().post(event);
      return !event.cancelled() ? this.api.getDatabase().setKeys(caseType, player, event.after()) : CompletableFuture.completedFuture(DatabaseStatus.CANCELLED);
   }

   public CompletableFuture<DatabaseStatus> set(String caseType, String player, int keys) {
      return this.getAsync(caseType, player).thenComposeAsync((before) -> this.setKeysWithEvent(caseType, player, keys, before));
   }

   public CompletableFuture<DatabaseStatus> modify(String caseType, String player, int keys) {
      return this.getAsync(caseType, player).thenComposeAsync((before) -> this.setKeysWithEvent(caseType, player, before + keys, before));
   }

   public CompletableFuture<DatabaseStatus> delete() {
      return this.api.getDatabase().delAllKeys();
   }

   public CompletableFuture<DatabaseStatus> delete(String caseType) {
      return this.api.getDatabase().delKeys(caseType);
   }

   public CompletableFuture<Integer> getAsync(String caseType, String player) {
      return this.api.getDatabase().getKeys(caseType, player);
   }

   public CompletableFuture<Map<String, Integer>> getAsync(String player) {
      return this.api.getDatabase().getKeys(player);
   }

   public int getCache(String caseType, String player) {
      Integer keys = (Integer)this.getCache(player).get(caseType);
      return keys == null ? 0 : keys;
   }

   public Map<String, Integer> getCache(String player) {
      if (this.api.getDatabase().getType() == DatabaseType.SQLITE) {
         return this.get(player);
      } else {
         Map<String, Integer> cachedKeys = cache.get(player);
         Map<String, Integer> keys;
         if (cachedKeys == null) {
            Map<String, Integer> previous = cache.getPrevious(player);
            keys = previous != null ? previous : this.get(player);
            this.getAsync(player).thenAcceptAsync((map) -> cache.put(player, map));
         } else {
            keys = cachedKeys;
         }

         return keys;
      }
   }
}
