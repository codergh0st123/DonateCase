package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.function.Consumer;

public interface SkinFetcherBuilder {
   CachedSkinFetcherBuilder cached();

   SkinFetcherBuilder onErr(Consumer<MojangApiError> var1);

   SkinFetcher build();
}
