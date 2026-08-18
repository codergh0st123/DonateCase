package com.jodexindustries.donatecase.entitylib.utils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentWeakIdentityHashMap<K, V> implements ConcurrentMap<K, V> {
   private final ConcurrentMap<WeakReference<K>, V> map = new ConcurrentHashMap();
   private final ReferenceQueue<K> queue = new ReferenceQueue();

   public V putIfAbsent(K key, V value) {
      this.purgeKeys();
      return (V)this.map.putIfAbsent(this.newKey(key), value);
   }

   public boolean remove(Object key, Object value) {
      this.purgeKeys();
      return this.map.remove(new WeakReference(key, (ReferenceQueue)null), value);
   }

   public boolean replace(K key, V oldValue, V newValue) {
      this.purgeKeys();
      return this.map.replace(this.newKey(key), oldValue, newValue);
   }

   public V replace(K key, V value) {
      this.purgeKeys();
      return (V)this.map.replace(this.newKey(key), value);
   }

   public int size() {
      this.purgeKeys();
      return this.map.size();
   }

   public boolean isEmpty() {
      this.purgeKeys();
      return this.map.isEmpty();
   }

   public boolean containsKey(Object key) {
      this.purgeKeys();
      return this.map.containsKey(new WeakReference(key, (ReferenceQueue)null));
   }

   public boolean containsValue(Object value) {
      this.purgeKeys();
      return this.map.containsValue(value);
   }

   public V get(Object key) {
      this.purgeKeys();
      return (V)this.map.get(new WeakReference(key, (ReferenceQueue)null));
   }

   public V put(K key, V value) {
      this.purgeKeys();
      return (V)this.map.put(this.newKey(key), value);
   }

   public V remove(Object key) {
      this.purgeKeys();
      return (V)this.map.remove(new WeakReference(key, (ReferenceQueue)null));
   }

   public void putAll(Map<? extends K, ? extends V> m) {
      this.purgeKeys();

      for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
         this.map.put(this.newKey(entry.getKey()), entry.getValue());
      }

   }

   public void clear() {
      this.purgeKeys();
      this.map.clear();
   }

   public Set<K> keySet() {
      return new AbstractSet<K>() {
         public Iterator<K> iterator() {
            ConcurrentWeakIdentityHashMap.this.purgeKeys();
            return new WeakSafeIterator<K, WeakReference<K>>(ConcurrentWeakIdentityHashMap.this.map.keySet().iterator()) {
               protected K extract(WeakReference<K> u) {
                  return (K)u.get();
               }
            };
         }

         public boolean contains(Object o) {
            return ConcurrentWeakIdentityHashMap.this.containsKey(o);
         }

         public int size() {
            return ConcurrentWeakIdentityHashMap.this.map.size();
         }
      };
   }

   public Collection<V> values() {
      this.purgeKeys();
      return this.map.values();
   }

   public Set<Map.Entry<K, V>> entrySet() {
      return new AbstractSet<Map.Entry<K, V>>() {
         public Iterator<Map.Entry<K, V>> iterator() {
            ConcurrentWeakIdentityHashMap.this.purgeKeys();
            return new WeakSafeIterator<Map.Entry<K, V>, Map.Entry<WeakReference<K>, V>>(ConcurrentWeakIdentityHashMap.this.map.entrySet().iterator()) {
               protected Map.Entry<K, V> extract(Map.Entry<WeakReference<K>, V> u) {
                  K key = (K)((WeakReference)u.getKey()).get();
                  return key == null ? null : new AbstractMap.SimpleEntry(key, u.getValue());
               }
            };
         }

         public int size() {
            return ConcurrentWeakIdentityHashMap.this.map.size();
         }
      };
   }

   private void purgeKeys() {
      Reference<? extends K> reference;
      while((reference = this.queue.poll()) != null) {
         this.map.remove(reference);
      }

   }

   private WeakReference<K> newKey(K key) {
      return new WeakReference<K>(key, this.queue);
   }

   private static class WeakReference<T> extends java.lang.ref.WeakReference<T> {
      private final int hashCode;

      private WeakReference(T referent, ReferenceQueue<? super T> q) {
         super(referent, q);
         this.hashCode = referent.hashCode();
      }

      public boolean equals(Object obj) {
         return obj != null && obj.getClass() == this.getClass() && (this == obj || this.get() == ((WeakReference)obj).get());
      }

      public int hashCode() {
         return this.hashCode;
      }
   }

   private abstract static class WeakSafeIterator<T, U> implements Iterator<T> {
      private final Iterator<U> weakIterator;
      protected T strongNext;

      public WeakSafeIterator(Iterator<U> weakIterator) {
         this.weakIterator = weakIterator;
         this.advance();
      }

      private void advance() {
         while(true) {
            if (this.weakIterator.hasNext()) {
               U nextU = (U)this.weakIterator.next();
               if ((this.strongNext = (T)this.extract(nextU)) == null) {
                  continue;
               }

               return;
            }

            this.strongNext = null;
            return;
         }
      }

      public boolean hasNext() {
         return this.strongNext != null;
      }

      public final T next() {
         T next = this.strongNext;
         this.advance();
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      protected abstract T extract(U var1);
   }
}
