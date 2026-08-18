package com.jodexindustries.donatecase.common.config.converter;

import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import com.jodexindustries.donatecase.api.config.converter.ConfigType;
import com.jodexindustries.donatecase.api.data.config.ConfigData;
import com.jodexindustries.donatecase.api.data.config.ConfigSerializer;
import com.jodexindustries.donatecase.common.config.converter.migrators.AnimationsMigrator_1_4_to_1_5;
import com.jodexindustries.donatecase.common.config.converter.migrators.CaseMigrator_1_2_to_1_3;
import com.jodexindustries.donatecase.common.config.converter.migrators.CasesMigrator_1_0_to_1_1;
import com.jodexindustries.donatecase.common.config.converter.migrators.LanguageMigrator_2_6_to_2_7;
import com.jodexindustries.donatecase.common.config.converter.migrators.UnknownMigrator;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum DefaultConfigType implements ConfigType {
   OLD_CASE(13, new HashMap<Integer, ConfigMigrator>() {
      {
         this.put(12, new CaseMigrator_1_2_to_1_3());
      }
   }),
   CASE_GUI(10),
   CASE_SETTINGS(10),
   CASE_ITEMS(10),
   ANIMATIONS(15, new HashMap<Integer, ConfigMigrator>() {
      {
         this.put(14, new AnimationsMigrator_1_4_to_1_5());
      }
   }),
   CASES(11, new HashMap<Integer, ConfigMigrator>() {
      {
         this.put(10, new CasesMigrator_1_0_to_1_1());
      }
   }),
   CONFIG(25, new ConfigSerializer(ConfigData.class, new Object[]{"DonateCase"})),
   LANG(27, new HashMap<Integer, ConfigMigrator>() {
      {
         this.put(26, new LanguageMigrator_2_6_to_2_7());
      }
   }),
   UNKNOWN_CUSTOM(0),
   UNKNOWN(true, new UnknownMigrator());

   private int latestVersion;
   private boolean permanent;
   private ConfigMigrator permanentMigrator;
   private ConfigSerializer configSerializer;
   private Map<Integer, ConfigMigrator> migrations;

   private DefaultConfigType(int latestVersion) {
      this.latestVersion = latestVersion;
   }

   private DefaultConfigType(int latestVersion, ConfigSerializer configSerializer) {
      this(latestVersion);
      this.configSerializer = configSerializer;
   }

   private DefaultConfigType(int latestVersion, Map<Integer, ConfigMigrator> migrations) {
      this(latestVersion);
      this.migrations = migrations;
   }

   private DefaultConfigType(int latestVersion, Map<Integer, ConfigMigrator> migrations, ConfigSerializer configSerializer) {
      this(latestVersion, migrations);
      this.configSerializer = configSerializer;
   }

   private DefaultConfigType(boolean permanent, ConfigMigrator permanentMigrator) {
      this.permanent = permanent;
      this.permanentMigrator = permanentMigrator;
   }

   public ConfigMigrator getMigrator(int version) {
      return this.migrations == null ? this.permanentMigrator : (ConfigMigrator)this.migrations.get(version);
   }

   public @Nullable ConfigSerializer getConfigSerializer() {
      return this.configSerializer;
   }

   public boolean isPermanent() {
      return this.permanent;
   }

   public int getLatestVersion() {
      return this.latestVersion;
   }

   public static @NotNull DefaultConfigType getType(String name) {
      if (name != null) {
         try {
            return valueOf(name.toUpperCase());
         } catch (IllegalArgumentException var2) {
            return UNKNOWN_CUSTOM;
         }
      } else {
         return UNKNOWN;
      }
   }

   // $FF: synthetic method
   private static DefaultConfigType[] $values() {
      return new DefaultConfigType[]{OLD_CASE, CASE_GUI, CASE_SETTINGS, CASE_ITEMS, ANIMATIONS, CASES, CONFIG, LANG, UNKNOWN_CUSTOM, UNKNOWN};
   }
}
