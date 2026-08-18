package com.jodexindustries.donatecase.common.tools.updater;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.DonateCase;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker {
   private final DonateCase api;

   public UpdateChecker(DonateCase api) {
      this.api = api;
   }

   public void check() {
      this.getVersion().thenAcceptAsync((version) -> {
         if (version.isNew()) {
            this.api.getPlatform().getLogger().info("There is a new update " + version.getVersionNumber() + " available.");
            this.api.getPlatform().getLogger().info("Download - https://modrinth.com/plugin/donatecase");
         }

      });
   }

   public CompletableFuture<VersionInfo> getVersion() {
      return !this.api.getConfigManager().getConfig().updateChecker() ? CompletableFuture.completedFuture(new VersionInfo()) : CompletableFuture.supplyAsync(() -> {
         try {
            URL url = new URL("https://api.modrinth.com/v2/project/donatecase/version?featured=true");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() == 200) {
               return this.getLatest(connection);
            }

            this.api.getPlatform().getLogger().warning("Unable to check for updates. HTTP response code: " + connection.getResponseCode());
         } catch (Exception exception) {
            this.api.getPlatform().getLogger().warning("Unable to check for updates: " + exception.getMessage());
         }

         return null;
      });
   }

   private VersionInfo getLatest(HttpURLConnection connection) throws IOException {
      Gson gson = new Gson();
      InputStreamReader reader = new InputStreamReader(connection.getInputStream());

      JsonArray versions;
      try {
         versions = JsonParser.parseReader(reader).getAsJsonArray();
      } catch (Throwable var10) {
         try {
            reader.close();
         } catch (Throwable var9) {
            var10.addSuppressed(var9);
         }

         throw var10;
      }

      reader.close();
      VersionInfo latestVersionInfo = null;

      for(JsonElement versionElement : versions) {
         JsonObject versionObject = versionElement.getAsJsonObject();
         VersionInfo versionInfo = (VersionInfo)gson.fromJson(versionObject, VersionInfo.class);
         if (latestVersionInfo == null || versionInfo.getDatePublished().compareTo(latestVersionInfo.getDatePublished()) > 0) {
            latestVersionInfo = versionInfo;
         }
      }

      if (latestVersionInfo != null && DCTools.getPluginVersion(this.api.getPlatform().getVersion()) < DCTools.getPluginVersion(latestVersionInfo.getVersionNumber())) {
         latestVersionInfo.setNew(true);
      }

      return latestVersionInfo;
   }
}
