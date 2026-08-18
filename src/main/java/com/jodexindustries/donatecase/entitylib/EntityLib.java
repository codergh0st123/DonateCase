package com.jodexindustries.donatecase.entitylib;

import com.github.retrooper.packetevents.util.PEVersion;
import com.jodexindustries.donatecase.entitylib.utils.ELVersions;
import com.jodexindustries.donatecase.entitylib.utils.GithubUpdater;
import java.util.Optional;

public final class EntityLib {
   private static Platform platform;
   private static EntityLibAPI api;

   private EntityLib() {
   }

   public static void init(Platform<?> platform, APIConfig settings) {
      EntityLib.platform = platform;
      platform.setupApi(settings);
      api = platform.getAPI();
      new GithubUpdater("Tofaa2", "EntityLib");
   }

   public static Optional<EntityLibAPI<?>> getOptionalApi() {
      return Optional.ofNullable(api);
   }

   public static <T> EntityLibAPI<T> getApi() {
      return api;
   }

   public static Platform<?> getPlatform() {
      return platform;
   }

   public static PEVersion getVersion() {
      return ELVersions.CURRENT;
   }
}
