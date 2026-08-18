package com.jodexindustries.donatecase.spigot.serializer;

import io.leangen.geantyref.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ConfigurationSectionImpl implements ConfigurationSection {
   private final ConfigurationNode node;

   public ConfigurationSectionImpl(ConfigurationNode node) {
      this.node = node;
   }

   public ConfigurationNode node(String path) {
      return this.node.node(path.split("\\."));
   }

   public @NotNull Set<String> getKeys(boolean deep) {
      return (Set)this.node.childrenMap().values().stream().map(ConfigurationNode::getString).collect(Collectors.toSet());
   }

   public @NotNull Map<String, Object> getValues(boolean deep) {
      Map<Object, ? extends ConfigurationNode> map = this.node.childrenMap();
      return (Map)map.entrySet().stream().collect(Collectors.toMap((entry) -> String.valueOf(entry.getKey()), (entry) -> new ConfigurationSectionImpl((ConfigurationNode)entry.getValue()), (a, b) -> b));
   }

   public boolean contains(@NotNull String path) {
      return !this.node(path).isNull();
   }

   public boolean contains(@NotNull String path, boolean ignoreDefault) {
      return this.contains(path);
   }

   public boolean isSet(@NotNull String path) {
      return this.node(path).isList();
   }

   public @Nullable String getCurrentPath() {
      return this.node.path().toString();
   }

   public @NotNull String getName() {
      return String.valueOf(this.node.key());
   }

   public @Nullable Configuration getRoot() {
      return null;
   }

   public @Nullable ConfigurationSection getParent() {
      return this.node.parent() == null ? null : new ConfigurationSectionImpl(this.node.parent());
   }

   public @Nullable Object get(@NotNull String path) {
      return this.node(path).raw();
   }

   public @Nullable Object get(@NotNull String path, @Nullable Object def) {
      return this.node(path).raw(def);
   }

   public void set(@NotNull String path, @Nullable Object value) {
   }

   public @NotNull ConfigurationSection createSection(@NotNull String path) {
      return null;
   }

   public @NotNull ConfigurationSection createSection(@NotNull String path, @NotNull Map<?, ?> map) {
      return null;
   }

   public @Nullable String getString(@NotNull String path) {
      return this.node(path).getString();
   }

   public @Nullable String getString(@NotNull String path, @Nullable String def) {
      String string = this.getString(path);
      return string == null ? def : string;
   }

   public boolean isString(@NotNull String path) {
      return false;
   }

   public int getInt(@NotNull String path) {
      return this.node(path).getInt();
   }

   public int getInt(@NotNull String path, int def) {
      return this.node(path).getInt(def);
   }

   public boolean isInt(@NotNull String path) {
      return this.node(path).raw() instanceof Integer;
   }

   public boolean getBoolean(@NotNull String path) {
      return this.node(path).getBoolean();
   }

   public boolean getBoolean(@NotNull String path, boolean def) {
      return this.node(path).getBoolean(def);
   }

   public boolean isBoolean(@NotNull String path) {
      return this.node(path).raw() instanceof Boolean;
   }

   public double getDouble(@NotNull String path) {
      return this.node(path).getDouble();
   }

   public double getDouble(@NotNull String path, double def) {
      return this.node(path).getDouble(def);
   }

   public boolean isDouble(@NotNull String path) {
      return this.node(path).raw() instanceof Double;
   }

   public long getLong(@NotNull String path) {
      return this.node(path).getLong();
   }

   public long getLong(@NotNull String path, long def) {
      return this.node(path).getLong(def);
   }

   public boolean isLong(@NotNull String path) {
      return this.node(path).raw() instanceof Long;
   }

   public @Nullable List<?> getList(@NotNull String path) {
      try {
         return this.node(path).getList(Object.class);
      } catch (SerializationException var3) {
         return null;
      }
   }

   public @Nullable List<?> getList(@NotNull String path, @Nullable List<?> def) {
      List<?> list = this.getList(path);
      return list == null ? def : list;
   }

   public boolean isList(@NotNull String path) {
      return false;
   }

   public @NotNull List<String> getStringList(@NotNull String path) {
      try {
         List<String> list = this.node(path).getList(String.class);
         return (List<String>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Integer> getIntegerList(@NotNull String path) {
      try {
         List<Integer> list = this.node(path).getList(Integer.class);
         return (List<Integer>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Boolean> getBooleanList(@NotNull String path) {
      try {
         List<Boolean> list = this.node(path).getList(Boolean.class);
         return (List<Boolean>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Double> getDoubleList(@NotNull String path) {
      try {
         List<Double> list = this.node(path).getList(Double.class);
         return (List<Double>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Float> getFloatList(@NotNull String path) {
      try {
         List<Float> list = this.node(path).getList(Float.class);
         return (List<Float>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Long> getLongList(@NotNull String path) {
      try {
         List<Long> list = this.node(path).getList(Long.class);
         return (List<Long>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Byte> getByteList(@NotNull String path) {
      try {
         List<Byte> list = this.node(path).getList(Byte.class);
         return (List<Byte>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Character> getCharacterList(@NotNull String path) {
      try {
         List<Character> list = this.node(path).getList(Character.class);
         return (List<Character>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Short> getShortList(@NotNull String path) {
      try {
         List<Short> list = this.node(path).getList(Short.class);
         return (List<Short>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var3) {
         return new ArrayList<>();
      }
   }

   public @NotNull List<Map<?, ?>> getMapList(@NotNull String path) {
      TypeToken<Map<?, ?>> typeToken = new TypeToken<Map<?, ?>>() {
      };

      try {
         List<Map<?, ?>> list = this.node.getList(typeToken);
         return (List<Map<?, ?>>)(list == null ? new ArrayList<>() : list);
      } catch (SerializationException var4) {
         return new ArrayList<>();
      }
   }

   public <T> @Nullable T getObject(@NotNull String path, @NotNull Class<T> clazz) {
      try {
         return (T)this.node(path).get(clazz);
      } catch (SerializationException var4) {
         return null;
      }
   }

   public <T> @Nullable T getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
      try {
         return (T)this.node(path).get(clazz, def);
      } catch (SerializationException var5) {
         return null;
      }
   }

   public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
      return null;
   }

   public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
      return null;
   }

   public @Nullable Vector getVector(@NotNull String path) {
      return null;
   }

   public @Nullable Vector getVector(@NotNull String path, @Nullable Vector def) {
      return null;
   }

   public boolean isVector(@NotNull String path) {
      return false;
   }

   public @Nullable OfflinePlayer getOfflinePlayer(@NotNull String path) {
      return null;
   }

   public @Nullable OfflinePlayer getOfflinePlayer(@NotNull String path, @Nullable OfflinePlayer def) {
      return null;
   }

   public boolean isOfflinePlayer(@NotNull String path) {
      return false;
   }

   public @Nullable ItemStack getItemStack(@NotNull String path) {
      return null;
   }

   public @Nullable ItemStack getItemStack(@NotNull String path, @Nullable ItemStack def) {
      return null;
   }

   public boolean isItemStack(@NotNull String path) {
      return false;
   }

   public @Nullable Color getColor(@NotNull String path) {
      return null;
   }

   public @Nullable Color getColor(@NotNull String path, @Nullable Color def) {
      return null;
   }

   public boolean isColor(@NotNull String path) {
      return false;
   }

   public @Nullable Location getLocation(@NotNull String path) {
      return null;
   }

   public @Nullable Location getLocation(@NotNull String path, @Nullable Location def) {
      return null;
   }

   public boolean isLocation(@NotNull String path) {
      return false;
   }

   public @Nullable ConfigurationSection getConfigurationSection(@NotNull String path) {
      return null;
   }

   public boolean isConfigurationSection(@NotNull String path) {
      return false;
   }

   public @Nullable ConfigurationSection getDefaultSection() {
      return null;
   }

   public @NotNull List<String> getComments(@NotNull String path) {
      return List.of();
   }

   public @NotNull List<String> getInlineComments(@NotNull String path) {
      return List.of();
   }

   public void setComments(@NotNull String path, @Nullable List<String> comments) {
   }

   public void setInlineComments(@NotNull String path, @Nullable List<String> comments) {
   }

   public void addDefault(@NotNull String path, @Nullable Object value) {
   }
}
