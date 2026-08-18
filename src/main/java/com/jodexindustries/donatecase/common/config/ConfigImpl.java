package com.jodexindustries.donatecase.common.config;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigType;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataMaterial;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGui;
import com.jodexindustries.donatecase.api.data.config.ConfigSerializer;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.common.config.converter.DefaultConfigType;
import com.jodexindustries.donatecase.common.serializer.CaseDataMaterialSerializer;
import com.jodexindustries.donatecase.common.serializer.CaseGuiSerializer;
import java.io.File;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class ConfigImpl implements Config {
   public static final TypeSerializerCollection.Builder SERIALIZER_COLLECTION = TypeSerializerCollection.builder().register(CaseGui.class, new CaseGuiSerializer()).register(CaseGui.Item.class, new CaseGuiSerializer.Item()).register(CaseDataMaterial.class, new CaseDataMaterialSerializer()).register(CaseLocation.class, new CaseLocation());
   private final String path;
   private final File file;
   private final YamlConfigurationLoader loader;
   private int version;
   private ConfigType type;
   private ConfigurationNode node;
   private Object serialized;

   public ConfigImpl(File file) {
      this(file, (ConfigType)null);
   }

   public ConfigImpl(File file, ConfigType type) {
      this.path = file.getPath().replace("\\", "/");
      this.file = file;
      this.type = type;
      this.loader = ((YamlConfigurationLoader.Builder)((YamlConfigurationLoader.Builder)YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).defaultOptions((opts) -> opts.serializers((build) -> build.registerAll(SERIALIZER_COLLECTION.build())))).file(file)).build();
   }

   private void setMeta() throws SerializationException {
      ConfigurationNode metaNode = this.node.node(new Object[]{"config"});
      String version = metaNode.node(new Object[]{"version"}).getString();
      if (version != null) {
         this.version = this.parse(version);
         this.type = DefaultConfigType.getType(metaNode.node(new Object[]{"type"}).getString());
      } else {
         this.version = this.parse(metaNode.getString());
         this.type = this.node.hasChild(new Object[]{"case"}) ? DefaultConfigType.OLD_CASE : DefaultConfigType.UNKNOWN;
      }

      ConfigSerializer configSerializer = this.type.getConfigSerializer();
      if (configSerializer != null) {
         this.serialized = this.node(configSerializer.path()).get(configSerializer.serializer());
      }

   }

   private int parse(String string) {
      if (string == null) {
         return 0;
      } else {
         if (string.contains(".")) {
            string = string.replace(".", "");
         }

         return Integer.parseInt(string);
      }
   }

   public void type(ConfigType type) {
      this.type = type;
   }

   public ConfigurationNode node() {
      return this.node;
   }

   public @Nullable Object getSerialized() {
      return this.serialized;
   }

   public File file() {
      return this.file;
   }

   public void load() throws ConfigurateException {
      this.node = this.loader.load();
      this.setMeta();
   }

   public void save() throws ConfigurateException {
      this.loader.save(this.node);
   }

   public String toString() {
      return this.path;
   }

   @Generated
   public String path() {
      return this.path;
   }

   @Generated
   public YamlConfigurationLoader loader() {
      return this.loader;
   }

   @Generated
   public int version() {
      return this.version;
   }

   @Generated
   public ConfigType type() {
      return this.type;
   }

   @Generated
   public Object serialized() {
      return this.serialized;
   }

   @Generated
   public void version(int version) {
      this.version = version;
   }

   @Generated
   public void node(ConfigurationNode node) {
      this.node = node;
   }

   @Generated
   public void serialized(Object serialized) {
      this.serialized = serialized;
   }
}
