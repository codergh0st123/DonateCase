package com.jodexindustries.donatecase.entitylib.extras.skin;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.jodexindustries.donatecase.entitylib.extras.MojangApiError;
import java.util.List;
import java.util.UUID;

final class ErroredTextureProperties {
   UUID uuid;
   private MojangApiError error;
   private List<TextureProperty> textureProperties;

   ErroredTextureProperties(MojangApiError error) {
      this.error = error;
   }

   ErroredTextureProperties(List<TextureProperty> textureProperties) {
      this.textureProperties = textureProperties;
   }

   boolean didError() {
      return this.error != null;
   }

   MojangApiError getError() {
      return this.error;
   }

   List<TextureProperty> getTextureProperties() {
      return this.textureProperties;
   }
}
