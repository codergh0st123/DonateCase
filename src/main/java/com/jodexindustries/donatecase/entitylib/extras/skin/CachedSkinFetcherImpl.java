package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

final class CachedSkinFetcherImpl implements SkinFetcher {
   private final long cacheDuration;
   private final Consumer<MojangApiError> onErr;
   private final Map<String, CachedData> cache = new HashMap();
   private final Map<UUID, CachedData> cacheUUID = new HashMap();

   CachedSkinFetcherImpl(Consumer<MojangApiError> onErr, long cacheDuration) {
      this.onErr = onErr;
      this.cacheDuration = cacheDuration;
   }

   public List<TextureProperty> getSkin(String playerName) {
      if (this.cache.containsKey(playerName)) {
         CachedData data = (CachedData)this.cache.get(playerName);
         if (data.expiration > System.currentTimeMillis()) {
            return data.skin;
         }

         this.cache.remove(playerName);
      }

      return this.putAndReturn(playerName);
   }

   public List<TextureProperty> getSkin(UUID uuid) {
      if (this.cacheUUID.containsKey(uuid)) {
         CachedData data = (CachedData)this.cacheUUID.get(uuid);
         if (data.expiration > System.currentTimeMillis()) {
            return data.skin;
         }

         this.cacheUUID.remove(uuid);
      }

      return this.putAndReturn(uuid);
   }

   public List<TextureProperty> getSkinOrDefault(String playerName, List<TextureProperty> defaults) {
      if (this.cache.containsKey(playerName)) {
         CachedData data = (CachedData)this.cache.get(playerName);
         if (data.expiration > System.currentTimeMillis()) {
            return data.skin;
         }

         this.cache.remove(playerName);
      }

      List<TextureProperty> skin = this.putAndReturn(playerName);
      if (skin.isEmpty()) {
         this.cache.remove(playerName);
         return defaults;
      } else {
         return skin;
      }
   }

   public List<TextureProperty> getSkinOrDefault(UUID uuid, List<TextureProperty> defaults) {
      if (this.cacheUUID.containsKey(uuid)) {
         CachedData data = (CachedData)this.cacheUUID.get(uuid);
         if (data.expiration > System.currentTimeMillis()) {
            return data.skin;
         }

         this.cacheUUID.remove(uuid);
      }

      List<TextureProperty> skin = this.putAndReturn(uuid);
      if (skin.isEmpty()) {
         this.cacheUUID.remove(uuid);
         return defaults;
      } else {
         return skin;
      }
   }

   private List<TextureProperty> putAndReturn(String playerName) {
      ErroredTextureProperties p = SFUtils.getTextures(playerName);
      if (p.didError()) {
         if (this.onErr != null) {
            this.onErr.accept(new MojangApiError(p.getError()));
         }

         return Collections.emptyList();
      } else {
         List<TextureProperty> skin = p.getTextureProperties();
         CachedData data;
         if (this.cacheDuration == -1L) {
            data = new CachedData(skin, Long.MAX_VALUE);
         } else {
            data = new CachedData(skin, System.currentTimeMillis() + this.cacheDuration);
         }

         this.cache.put(playerName, data);
         if (p.uuid != null) {
            this.cacheUUID.put(p.uuid, data);
         }

         return skin;
      }
   }

   private List<TextureProperty> putAndReturn(UUID uuid) {
      ErroredTextureProperties p = SFUtils.getTextures(uuid);
      if (p.didError()) {
         if (this.onErr != null) {
            this.onErr.accept(new MojangApiError(p.getError()));
         }

         return Collections.emptyList();
      } else {
         List<TextureProperty> skin = p.getTextureProperties();
         if (this.cacheDuration != -1L) {
            this.cacheUUID.put(uuid, new CachedData(skin, System.currentTimeMillis() + this.cacheDuration));
         } else {
            this.cacheUUID.put(uuid, new CachedData(skin, Long.MAX_VALUE));
         }

         return skin;
      }
   }

   static class CachedData {
      private final List<TextureProperty> skin;
      private final long expiration;

      CachedData(List<TextureProperty> skin, long expiration) {
         this.skin = skin;
         this.expiration = expiration;
      }
   }
}
