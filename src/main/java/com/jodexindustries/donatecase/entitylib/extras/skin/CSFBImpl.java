package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.function.Consumer;

final class CSFBImpl implements CachedSkinFetcherBuilder {
   private Consumer<MojangApiError> onErr;
   private long cacheDuration = -1L;

   CSFBImpl(Consumer<MojangApiError> onErr) {
      this.onErr = onErr;
   }

   public CachedSkinFetcherBuilder cacheDuration(long duration) {
      this.cacheDuration = duration;
      return this;
   }

   public CachedSkinFetcherBuilder cached() {
      return this;
   }

   public CachedSkinFetcherBuilder onErr(Consumer<MojangApiError> onErr) {
      this.onErr = onErr;
      return this;
   }

   public SkinFetcher build() {
      return new CachedSkinFetcherImpl(this.onErr, this.cacheDuration);
   }
}
