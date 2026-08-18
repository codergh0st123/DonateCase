package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.function.Consumer;

public interface CachedSkinFetcherBuilder extends SkinFetcherBuilder {
   CachedSkinFetcherBuilder cacheDuration(long var1);

   CachedSkinFetcherBuilder onErr(Consumer<MojangApiError> var1);
}
