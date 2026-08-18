package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import java.util.List;
import java.util.UUID;

public interface SkinFetcher {
   static SkinFetcherBuilder builder() {
      return new SFBImpl();
   }

   List<TextureProperty> getSkin(String var1);

   List<TextureProperty> getSkin(UUID var1);

   List<TextureProperty> getSkinOrDefault(String var1, List<TextureProperty> var2);

   List<TextureProperty> getSkinOrDefault(UUID var1, List<TextureProperty> var2);
}
