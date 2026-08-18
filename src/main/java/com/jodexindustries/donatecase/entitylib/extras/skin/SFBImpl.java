package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.function.Consumer;

class SFBImpl implements SkinFetcherBuilder {
   private Consumer<MojangApiError> rateLimitErrorConsumer;

   public CachedSkinFetcherBuilder cached() {
      return new CSFBImpl(this.rateLimitErrorConsumer);
   }

   public SkinFetcherBuilder onErr(Consumer<MojangApiError> onErr) {
      this.rateLimitErrorConsumer = onErr;
      return this;
   }

   public SkinFetcher build() {
      return new SkinFetcherImpl(this.rateLimitErrorConsumer);
   }
}
