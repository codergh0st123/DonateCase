package com.jodexindustries.donatecase.common.serializer;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataMaterial;
import com.jodexindustries.donatecase.api.tools.DCTools;
import java.lang.reflect.Type;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class CaseDataMaterialSerializer implements TypeSerializer<CaseDataMaterial> {
   public CaseDataMaterial deserialize(Type type, ConfigurationNode node) throws SerializationException {
      CaseDataMaterial material = new CaseDataMaterial();
      material.id(node.node(new Object[]{"ID"}).getString());
      material.displayName(DCTools.rc(node.node(new Object[]{"DisplayName"}).getString()));
      material.enchanted(node.node(new Object[]{"Enchanted"}).getBoolean());
      material.lore(DCTools.rc(node.node(new Object[]{"Lore"}).getList(String.class)));
      material.modelData(node.node(new Object[]{"ModelData"}).getInt(-1));
      List<String> rgb = node.node(new Object[]{"Rgb"}).getList(String.class);
      if (rgb != null) {
         material.rgb((String[])rgb.toArray(new String[0]));
      }

      material.itemStack(DCAPI.getInstance().getPlatform().getTools().loadCaseItem(material.id()));
      return material;
   }

   public void serialize(Type type, CaseDataMaterial obj, ConfigurationNode node) {
   }

   public @Nullable CaseDataMaterial emptyValue(Type specificType, ConfigurationOptions options) {
      return new CaseDataMaterial();
   }
}
