package com.jodexindustries.donatecase.common.config;

import com.jodexindustries.donatecase.api.config.CaseStorage;
import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class CaseStorageImpl implements CaseStorage {
   private final ConfigManagerImpl configManager;
   private Config config;

   public CaseStorageImpl(ConfigManagerImpl configManager) {
      this.configManager = configManager;
   }

   public void load() {
      this.config = this.configManager.getConfig("Cases.yml");
   }

   public void save(@NotNull String name, @NotNull CaseInfo caseInfo) throws ConfigurateException {
      ConfigurationNode current = this.config.node("DonateCase", "Cases", name);
      current.set(CaseInfo.class, caseInfo);
      this.config.save();
   }

   public void delete(String name) {
      this.config.node("DonateCase", "Cases").removeChild(name);

      try {
         this.config.save();
      } catch (ConfigurateException e) {
         this.configManager.getPlatform().getLogger().log(Level.WARNING, "Error with deleting case: " + name, e);
      }

   }

   public boolean delete(CaseLocation location) {
      ConfigurationNode parent = this.config.node("DonateCase", "Cases");

      for(Map.Entry<Object, ? extends ConfigurationNode> entry : parent.childrenMap().entrySet()) {
         try {
            CaseInfo caseInfo = (CaseInfo)((ConfigurationNode)entry.getValue()).get(CaseInfo.class);
            if (caseInfo != null && location.equals(caseInfo.location())) {
               parent.removeChild(entry.getKey());
               this.config.save();
               return true;
            }
         } catch (ConfigurateException var6) {
         }
      }

      return false;
   }

   public @NotNull Map<String, CaseInfo> get() {
      Map<String, CaseInfo> map = new HashMap();
      ConfigurationNode parent = this.config.node("DonateCase", "Cases");

      for(Map.Entry<Object, ? extends ConfigurationNode> entry : parent.childrenMap().entrySet()) {
         String name = String.valueOf(entry.getKey());
         CaseInfo caseInfo = this.get((ConfigurationNode)entry.getValue());
         if (caseInfo != null) {
            map.put(name, caseInfo);
         }
      }

      return map;
   }

   public boolean has(String name) {
      return this.config.node().hasChild(new Object[]{"DonateCase", "Cases", name});
   }

   public CaseInfo get(String name) {
      return this.get(this.config.node("DonateCase", "Cases", name));
   }

   public CaseInfo get(CaseLocation location) {
      ConfigurationNode parent = this.config.node("DonateCase", "Cases");

      for(ConfigurationNode value : parent.childrenMap().values()) {
         CaseInfo caseInfo = this.get(value);
         if (caseInfo != null && location.equals(caseInfo.location())) {
            return caseInfo;
         }
      }

      return null;
   }

   private @Nullable CaseInfo get(ConfigurationNode node) {
      try {
         return (CaseInfo)node.get(CaseInfo.class);
      } catch (SerializationException e) {
         this.configManager.getPlatform().getLogger().log(Level.WARNING, "Error with getting info about case: " + node.key(), e);
         return null;
      }
   }
}
