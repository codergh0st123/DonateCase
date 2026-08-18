package com.jodexindustries.donatecase.api.addon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.Generated;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class InternalAddonDescription {
   private final File file;
   private final String name;
   private final String mainClass;
   private final String version;
   private final String apiVersion;
   private final List<String> authors;
   private final List<String> depend;
   private final List<String> softDepend;
   private final List<String> platforms;

   public InternalAddonDescription(File file) throws IOException, InvalidAddonException {
      this.file = file;
      JarFile jar = new JarFile(file);
      JarEntry entry = jar.getJarEntry("addon.yml");
      if (entry == null) {
         throw new InvalidAddonException("Addon " + file.getName() + " trying to load without addon.yml! Abort.");
      } else {
         YamlConfigurationLoader loader = ((YamlConfigurationLoader.Builder)YamlConfigurationLoader.builder().source(() -> {
            InputStream input = jar.getInputStream(entry);
            InputStreamReader reader = new InputStreamReader(input);
            return new BufferedReader(reader);
         })).build();
         ConfigurationNode config = loader.load();
         this.name = config.node(new Object[]{"name"}).getString();
         this.mainClass = config.node(new Object[]{"main"}).getString();
         this.version = config.node(new Object[]{"version"}).getString();
         this.apiVersion = config.node(new Object[]{"api"}).getString();
         this.authors = config.node(new Object[]{"authors"}).getList(String.class, new ArrayList());
         if (config.node(new Object[]{"author"}) != null) {
            this.authors.add(config.node(new Object[]{"author"}).getString());
         }

         this.softDepend = config.node(new Object[]{"softdepend"}).getList(String.class, new ArrayList());
         this.depend = config.node(new Object[]{"depend"}).getList(String.class, new ArrayList());
         this.platforms = config.node(new Object[]{"platforms"}).getList(String.class, new ArrayList());
         jar.close();
      }
   }

   public boolean isSupport(String platform) {
      if (platform.isEmpty()) {
         return true;
      } else {
         for(String current : this.platforms) {
            if (current.equalsIgnoreCase(platform)) {
               return true;
            }
         }

         return false;
      }
   }

   @Generated
   public File getFile() {
      return this.file;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getMainClass() {
      return this.mainClass;
   }

   @Generated
   public String getVersion() {
      return this.version;
   }

   @Generated
   public String getApiVersion() {
      return this.apiVersion;
   }

   @Generated
   public List<String> getAuthors() {
      return this.authors;
   }

   @Generated
   public List<String> getDepend() {
      return this.depend;
   }

   @Generated
   public List<String> getSoftDepend() {
      return this.softDepend;
   }

   @Generated
   public List<String> getPlatforms() {
      return this.platforms;
   }
}
