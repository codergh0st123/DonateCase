package com.jodexindustries.donatecase.api.data.config;

import lombok.Generated;

public class ConfigSerializer {
   private final Class<?> serializer;
   private final Object[] path;

   public ConfigSerializer(Class<?> serializer, Object... path) {
      this.serializer = serializer;
      this.path = path;
   }

   @Generated
   public Class<?> serializer() {
      return this.serializer;
   }

   @Generated
   public Object[] path() {
      return this.path;
   }
}
