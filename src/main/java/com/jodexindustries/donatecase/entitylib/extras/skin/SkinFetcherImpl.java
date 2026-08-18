package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

final class SkinFetcherImpl implements SkinFetcher {
   private final Consumer<MojangApiError> rateLimitErrorConsumer;

   public SkinFetcherImpl(Consumer<MojangApiError> rateLimitErrorConsumer) {
      this.rateLimitErrorConsumer = rateLimitErrorConsumer;
   }

   public List<TextureProperty> getSkin(String playerName) {
      ErroredTextureProperties p = SFUtils.getTextures(playerName);
      if (p.didError()) {
         this.rateLimitErrorConsumer.accept(p.getError());
         return Collections.emptyList();
      } else {
         return p.getTextureProperties();
      }
   }

   public List<TextureProperty> getSkin(UUID uuid) {
      ErroredTextureProperties p = SFUtils.getTextures(uuid);
      if (p.didError()) {
         this.rateLimitErrorConsumer.accept(p.getError());
         return Collections.emptyList();
      } else {
         return p.getTextureProperties();
      }
   }

   public List<TextureProperty> getSkinOrDefault(String playerName, List<TextureProperty> defaults) {
      ErroredTextureProperties p = SFUtils.getTextures(playerName);
      if (p.didError()) {
         this.rateLimitErrorConsumer.accept(p.getError());
         return defaults;
      } else {
         return p.getTextureProperties();
      }
   }

   public List<TextureProperty> getSkinOrDefault(UUID uuid, List<TextureProperty> defaults) {
      ErroredTextureProperties p = SFUtils.getTextures(uuid);
      if (p.didError()) {
         this.rateLimitErrorConsumer.accept(p.getError());
         return defaults;
      } else {
         return p.getTextureProperties();
      }
   }
}
