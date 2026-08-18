package com.jodexindustries.donatecase.api.caching;

import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;

public class SimpleCache<K, V> {
   private final Map<K, CacheEntry<V>> cache = new HashMap();
   private long maxAge;

   public SimpleCache(long maxAge) {
      this.maxAge = maxAge;
   }

   public @Nullable V get(K key) {
      CacheEntry<V> entry = (CacheEntry)this.cache.get(key);
      return (V)(entry != null && this.isValid(entry) ? entry.getValue() : null);
   }

   public @Nullable V getPrevious(K key) {
      CacheEntry<V> entry = (CacheEntry)this.cache.get(key);
      return (V)(entry == null ? null : entry.getValue());
   }

   public void put(K key, V value) {
      this.cache.put(key, new CacheEntry(value, System.currentTimeMillis()));
   }

   private boolean isValid(CacheEntry<V> entry) {
      return System.currentTimeMillis() - entry.getTimestamp() <= this.maxAge * 50L;
   }

   public void clear() {
      this.cache.clear();
   }

   @Generated
   public void setMaxAge(long maxAge) {
      this.maxAge = maxAge;
   }
}
