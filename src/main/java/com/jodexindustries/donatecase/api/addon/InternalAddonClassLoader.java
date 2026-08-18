package com.jodexindustries.donatecase.api.addon;

import com.google.common.io.ByteStreams;
import com.jodexindustries.donatecase.api.manager.AddonManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InternalAddonClassLoader extends URLClassLoader {
   private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();
   private final InternalAddonDescription description;
   private final File file;
   private final JarFile jar;
   private final Manifest manifest;
   private final URL url;
   private final AddonManager manager;
   private final InternalJavaAddon addon;
   private final Platform platform;

   public InternalAddonClassLoader(@Nullable ClassLoader parent, InternalAddonDescription description, AddonManager manager, Platform platform) throws IOException, InvalidAddonException, ClassNotFoundException {
      super(new URL[]{description.getFile().toURI().toURL()}, parent);
      this.description = description;
      this.file = description.getFile();
      this.jar = new JarFile(this.file);
      this.manifest = this.jar.getManifest();
      this.url = this.file.toURI().toURL();
      this.manager = manager;
      this.platform = platform;

      try {
         Class<?> jarClass;
         try {
            jarClass = Class.forName(description.getMainClass(), true, this);
         } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Cannot find main class `" + description.getMainClass() + "'", ex);
         }

         Class<? extends InternalJavaAddon> pluginClass;
         try {
            pluginClass = jarClass.asSubclass(InternalJavaAddon.class);
         } catch (ClassCastException var8) {
            throw new ClassCastException("Main class `" + description.getMainClass() + "' does not extend JavaAddon");
         }

         this.addon = (InternalJavaAddon)pluginClass.getDeclaredConstructor().newInstance();
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
         throw new InvalidAddonException("No public constructor", e);
      } catch (InstantiationException ex) {
         throw new InvalidAddonException("Abnormal addon type", ex);
      }
   }

   public URL getResource(String name) {
      return this.findResource(name);
   }

   public Enumeration<URL> getResources(String name) throws IOException {
      return this.findResources(name);
   }

   synchronized void initialize(@NotNull InternalJavaAddon module) {
      if (module.getClass().getClassLoader() != this) {
         throw new IllegalArgumentException("Cannot initialize module outside of this class loader");
      } else {
         module.init(this.description, this.file, this, this.platform);
      }
   }

   public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      return this.loadClass0(name, resolve, true);
   }

   public Class<?> loadClass0(@NotNull String name, boolean resolve, boolean global) throws ClassNotFoundException {
      try {
         return super.loadClass(name, resolve);
      } catch (ClassNotFoundException var5) {
         if (global) {
            Class<?> result = this.manager.getClassByName(name, resolve);
            if (result != null && result.getClassLoader() instanceof InternalAddonClassLoader) {
               return result;
            }
         }

         throw new ClassNotFoundException(name);
      }
   }

   protected Class<?> findClass(String name) throws ClassNotFoundException {
      if (!name.startsWith("org.bukkit.") && !name.startsWith("net.minecraft.")) {
         Class<?> result = (Class)this.classes.get(name);
         if (result == null) {
            String path = name.replace('.', '/').concat(".class");
            JarEntry entry = this.jar.getJarEntry(path);
            if (entry != null) {
               byte[] classBytes;
               try {
                  InputStream is = this.jar.getInputStream(entry);

                  try {
                     classBytes = ByteStreams.toByteArray(is);
                  } catch (Throwable var10) {
                     if (is != null) {
                        try {
                           is.close();
                        } catch (Throwable var9) {
                           var10.addSuppressed(var9);
                        }
                     }

                     throw var10;
                  }

                  if (is != null) {
                     is.close();
                  }
               } catch (IOException ex) {
                  throw new ClassNotFoundException(name, ex);
               }

               int dot = name.lastIndexOf(46);
               if (dot != -1) {
                  String pkgName = name.substring(0, dot);
                  if (this.getPackage(pkgName) == null) {
                     try {
                        if (this.manifest != null) {
                           this.definePackage(pkgName, this.manifest, this.url);
                        } else {
                           this.definePackage(pkgName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (URL)null);
                        }
                     } catch (IllegalArgumentException var12) {
                        if (this.getPackage(pkgName) == null) {
                           throw new IllegalStateException("Cannot find package " + pkgName);
                        }
                     }
                  }
               }

               CodeSigner[] signers = entry.getCodeSigners();
               CodeSource source = new CodeSource(this.url, signers);
               result = this.defineClass(name, classBytes, 0, classBytes.length, source);
            }

            if (result == null) {
               result = super.findClass(name);
            }

            this.classes.put(name, result);
         }

         return result;
      } else {
         throw new ClassNotFoundException(name);
      }
   }

   public void close() throws IOException {
      try {
         super.close();
      } finally {
         this.jar.close();
      }

   }

   @Generated
   public File getFile() {
      return this.file;
   }

   @Generated
   public InternalJavaAddon getAddon() {
      return this.addon;
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
