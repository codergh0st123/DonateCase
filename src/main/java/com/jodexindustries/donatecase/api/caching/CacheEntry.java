package com.jodexindustries.donatecase.api.caching;

import lombok.Generated;

public class CacheEntry<V> {
   private final V value;
   private final long timestamp;

   public CacheEntry(V value, long timestamp) {
      this.value = value;
      this.timestamp = timestamp;
   }

   @Generated
   public V getValue() {
      return this.value;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }
}
