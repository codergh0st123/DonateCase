package com.jodexindustries.donatecase.common.config.converter.migrators;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class CaseMigrator_1_2_to_1_3 implements ConfigMigrator {
   public void migrate(Config config) throws SerializationException {
      ConfigurationNode root = config.node();

      for(ConfigurationNode node : root.node(new Object[]{"case", "Gui", "Items"}).childrenMap().values()) {
         migrateGuiItem(node);
      }

      for(ConfigurationNode node : root.node(new Object[]{"case", "Items"}).childrenMap().values()) {
         migrateItem(node);
      }

      root.removeChild("config");
      root.node(new Object[]{"config", "version"}).set(13);
      root.node(new Object[]{"config", "type"}).set(config.type());
      root.node(new Object[]{"case", "CooldownBeforeAnimation"}).set(0);
      ConfigurationNode titleNode = root.node(new Object[]{"case", "Gui", "Title"});
      if (titleNode.virtual()) {
         titleNode.set(root.node(new Object[]{"case", "Title"}).getString());
      }

   }

   private static void migrateItem(ConfigurationNode node) {
      ConfigurationNode itemNode = node.node(new Object[]{"Item"}).copy();
      node.removeChild("Item");
      node.node(new Object[]{"Material"}).from(itemNode);
   }

   private static void migrateGuiItem(ConfigurationNode node) throws SerializationException {
      Object displayName = node.node(new Object[]{"DisplayName"}).raw();
      Object enchanted = node.node(new Object[]{"Enchanted"}).raw();
      Object lore = node.node(new Object[]{"Lore"}).raw();
      Object material = node.node(new Object[]{"Material"}).raw();
      node.removeChild("DisplayName");
      node.removeChild("Enchanted");
      node.removeChild("Lore");
      node.removeChild("Material");
      ConfigurationNode materialNode = node.node(new Object[]{"Material"});
      materialNode.node(new Object[]{"ID"}).set(material);
      materialNode.node(new Object[]{"DisplayName"}).set(displayName);
      materialNode.node(new Object[]{"Enchanted"}).set(enchanted);
      materialNode.node(new Object[]{"Lore"}).set(lore);
   }
}
