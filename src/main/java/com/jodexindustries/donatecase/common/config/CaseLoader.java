package com.jodexindustries.donatecase.common.config;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.Loadable;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.event.plugin.DonateCaseReloadEvent;
import com.jodexindustries.donatecase.common.DonateCase;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class CaseLoader implements Loadable {
   private final DonateCase api;

   public CaseLoader(DonateCase api) {
      this.api = api;
   }

   public void load() {
      this.api.getCaseManager().getMap().clear();

      for(Map.Entry<String, List<Config>> entry : this.getCases().entrySet()) {
         String caseType = (String)entry.getKey();

         for(Config config : (List)entry.getValue()) {
            ConfigurationNode caseSection = config.node("case");
            if (caseSection != null && !caseSection.isNull()) {
               try {
                  CaseData caseData = (CaseData)caseSection.get(CaseData.class);
                  if (caseData == null) {
                     this.api.getPlatform().getLogger().warning("Something wrong with case \"" + caseType + "\" loading!");
                  } else {
                     caseData.caseType(caseType);
                     this.api.getCaseManager().getMap().put(caseType, caseData);
                  }
               } catch (SerializationException e) {
                  this.api.getPlatform().getLogger().log(Level.WARNING, "Error with loading case \"" + caseType + "\"", e);
               }
            } else {
               this.api.getPlatform().getLogger().warning("Case " + caseType + " has a broken case section, skipped.");
            }
         }
      }

      this.api.getEventBus().post((DCEvent)(new DonateCaseReloadEvent(DonateCaseReloadEvent.Type.CASES)));
      this.api.getPlatform().getLogger().info("Loaded " + this.api.getCaseManager().getMap().size() + " cases!");
   }

   private Map<String, List<Config>> getCases() {
      Map<String, List<Config>> cases = new HashMap();

      for(ConfigImpl config : this.api.getConfigManager().get().values()) {
         String path = config.path();
         String[] parts = path.split("/");
         if (parts.length >= 4 && parts[2].equals("cases")) {
            String caseType = parts[3].substring(0, parts[3].lastIndexOf(".yml"));
            ((List)cases.computeIfAbsent(caseType, (k) -> new ArrayList())).add(config);
         }
      }

      if (cases.isEmpty()) {
         cases.put("case", this.saveDefault());
      }

      return cases;
   }

   private List<Config> saveDefault() {
      this.api.getPlatform().saveResource("cases/case.yml", false);
      return Collections.singletonList(this.api.getConfigManager().load(new File(this.api.getPlatform().getDataFolder(), "cases/case.yml")));
   }
}
