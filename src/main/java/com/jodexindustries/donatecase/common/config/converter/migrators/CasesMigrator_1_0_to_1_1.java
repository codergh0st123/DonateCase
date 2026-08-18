package com.jodexindustries.donatecase.common.config.converter.migrators;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class CasesMigrator_1_0_to_1_1 implements ConfigMigrator {
   public void migrate(Config config) throws SerializationException {
      ConfigurationNode root = config.node("DonateCase", "Cases");
      Map<String, CaseInfo> cases = new HashMap<>();

      for(ConfigurationNode node : root.childrenMap().values()) {
         String key = String.valueOf(node.key());
         String caseType = node.node(new Object[]{"type"}).getString();
         String[] location = node.node(new Object[]{"location"}).getString("").split(";");
         if (location.length >= 4) {
            CaseInfo caseInfo = getCaseInfo(location, caseType);
            cases.put(key, caseInfo);
         }
      }

      config.node().removeChild("DonateCase");

      for(Map.Entry<String, CaseInfo> entry : cases.entrySet()) {
         root.node(new Object[]{entry.getKey()}).set(entry.getValue());
      }

      config.node("config", "version").set(11);
   }

   private static @NotNull CaseInfo getCaseInfo(String[] location, String caseType) {
      String world = location[0];
      double x = Double.parseDouble(location[1]);
      double y = Double.parseDouble(location[2]);
      double z = Double.parseDouble(location[3]);
      float pitch = location.length > 4 ? Float.parseFloat(location[4]) : 0.0F;
      float yaw = location.length > 5 ? Float.parseFloat(location[5]) : 0.0F;
      CaseLocation caseLocation = new CaseLocation(world, x, y, z, pitch, yaw);
      return new CaseInfo(caseType, caseLocation);
   }
}
