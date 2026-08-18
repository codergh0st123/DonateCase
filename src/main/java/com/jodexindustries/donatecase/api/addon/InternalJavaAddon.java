package com.jodexindustries.donatecase.api.addon;

import com.jodexindustries.donatecase.api.platform.Platform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class InternalJavaAddon implements InternalAddon {
   private boolean isEnabled = false;
   private ClassLoader classLoader;
   private InternalAddonLogger internalAddonLogger;
   private File file;
   private InternalAddonClassLoader urlClassLoader;
   private InternalAddonDescription description;
   private Platform platform;

   public InternalJavaAddon() {
      ClassLoader classLoader = this.getClass().getClassLoader();
      if (classLoader instanceof InternalAddonClassLoader) {
         ((InternalAddonClassLoader)classLoader).initialize(this);
      } else {
         throw new IllegalArgumentException("InternalJavaAddon requires " + InternalAddonClassLoader.class.getName());
      }
   }

   void init(InternalAddonDescription description, File file, InternalAddonClassLoader loader, Platform platform) {
      this.description = description;
      this.file = file;
      this.classLoader = this.getClass().getClassLoader();
      this.urlClassLoader = loader;
      this.platform = platform;
      this.internalAddonLogger = new InternalAddonLogger(this);
   }

   public final void setEnabled(boolean enabled) {
      if (this.isEnabled != enabled) {
         this.isEnabled = enabled;
         if (this.isEnabled) {
            this.onEnable();
         } else {
            this.onDisable();
         }
      }

   }

   public boolean isEnabled() {
      return this.isEnabled;
   }

   public void onDisable() {
   }

   public void onEnable() {
   }

   public void onLoad() {
   }

   public final @NotNull File getDataFolder() {
      File data = new File(this.getPlatform().getDataFolder(), "addons/" + this.getDescription().getName());
      if (!data.exists()) {
         data.mkdir();
      }

      return data;
   }

   public final String getVersion() {
      return this.getDescription().getVersion();
   }

   public final @NotNull String getName() {
      return this.getDescription().getName();
   }

   public final void saveResource(@NotNull String resourcePath, boolean replace) {
      if (resourcePath.isEmpty()) {
         throw new IllegalArgumentException("ResourcePath cannot be empty");
      } else {
         resourcePath = resourcePath.replace('\\', '/');
         InputStream in = this.getResource(resourcePath);
         if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
         } else {
            File outFile = new File(this.getDataFolder(), resourcePath);
            int lastIndex = resourcePath.lastIndexOf(47);
            File outDir = new File(this.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));
            if (!outDir.exists()) {
               outDir.mkdirs();
            }

            try {
               if (outFile.exists() && !replace) {
                  this.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
               } else {
                  OutputStream out = Files.newOutputStream(outFile.toPath());
                  byte[] buf = new byte[1024];

                  int len;
                  while((len = in.read(buf)) > 0) {
                     out.write(buf, 0, len);
                  }

                  out.close();
                  in.close();
               }
            } catch (IOException ex) {
               this.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
            }

         }
      }
   }

   public final @Nullable InputStream getResource(@NotNull String filename) {
      try {
         URL url = this.getUrlClassLoader().getResource(filename);
         if (url == null) {
            return null;
         } else {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
         }
      } catch (IOException var4) {
         return null;
      }
   }

   public final ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public final @NotNull InternalAddonLogger getLogger() {
      return this.internalAddonLogger;
   }

   public final InternalAddonClassLoader getUrlClassLoader() {
      return this.urlClassLoader;
   }

   public final @NotNull InternalAddonDescription getDescription() {
      return this.description;
   }

   public final @NotNull Platform getPlatform() {
      return this.platform;
   }
}
