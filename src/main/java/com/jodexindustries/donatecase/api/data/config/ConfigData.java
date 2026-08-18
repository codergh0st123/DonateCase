package com.jodexindustries.donatecase.api.data.config;

import java.util.Map;
import lombok.Generated;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ConfigData {
   @Setting("UpdateChecker")
   private boolean updateChecker = true;
   @Setting("MySql")
   private MySQL mySQL = new MySQL();
   @Setting("Languages")
   private String languages = "en_US";
   @Setting("HologramDriver")
   private String hologramDriver = "DecentHolograms";
   @Setting("LevelGroups")
   private Map<String, Integer> levelGroups;
   @Setting("DateFormat")
   private String dateFormat = "dd.MM HH:mm:ss";
   @Setting("AddonsHelp")
   private boolean addonsHelp = true;
   @Setting("UsePackets")
   private boolean usePackets = false;
   @Setting("Caching")
   private long caching = 20L;
   @Comment("Set spawn-protection to 0 in server.properties")
   @Setting("DisableSpawnProtection")
   private boolean disableSpawnProtection = true;
   @Comment("If true, checks whether the player with the nickname exists on the server.")
   @Setting("CheckPlayerName")
   private boolean checkPlayerName = true;

   @Generated
   public boolean updateChecker() {
      return this.updateChecker;
   }

   @Generated
   public MySQL mySQL() {
      return this.mySQL;
   }

   @Generated
   public String languages() {
      return this.languages;
   }

   @Generated
   public String hologramDriver() {
      return this.hologramDriver;
   }

   @Generated
   public Map<String, Integer> levelGroups() {
      return this.levelGroups;
   }

   @Generated
   public String dateFormat() {
      return this.dateFormat;
   }

   @Generated
   public boolean addonsHelp() {
      return this.addonsHelp;
   }

   @Generated
   public boolean usePackets() {
      return this.usePackets;
   }

   @Generated
   public long caching() {
      return this.caching;
   }

   @Generated
   public boolean disableSpawnProtection() {
      return this.disableSpawnProtection;
   }

   @Generated
   public boolean checkPlayerName() {
      return this.checkPlayerName;
   }

   @Generated
   public ConfigData updateChecker(boolean updateChecker) {
      this.updateChecker = updateChecker;
      return this;
   }

   @Generated
   public ConfigData mySQL(MySQL mySQL) {
      this.mySQL = mySQL;
      return this;
   }

   @Generated
   public ConfigData languages(String languages) {
      this.languages = languages;
      return this;
   }

   @Generated
   public ConfigData hologramDriver(String hologramDriver) {
      this.hologramDriver = hologramDriver;
      return this;
   }

   @Generated
   public ConfigData levelGroups(Map<String, Integer> levelGroups) {
      this.levelGroups = levelGroups;
      return this;
   }

   @Generated
   public ConfigData dateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
      return this;
   }

   @Generated
   public ConfigData addonsHelp(boolean addonsHelp) {
      this.addonsHelp = addonsHelp;
      return this;
   }

   @Generated
   public ConfigData usePackets(boolean usePackets) {
      this.usePackets = usePackets;
      return this;
   }

   @Generated
   public ConfigData caching(long caching) {
      this.caching = caching;
      return this;
   }

   @Generated
   public ConfigData disableSpawnProtection(boolean disableSpawnProtection) {
      this.disableSpawnProtection = disableSpawnProtection;
      return this;
   }

   @Generated
   public ConfigData checkPlayerName(boolean checkPlayerName) {
      this.checkPlayerName = checkPlayerName;
      return this;
   }

   @ConfigSerializable
   public static class MySQL {
      @Setting("Enabled")
      private boolean enabled = false;
      @Setting("Host")
      private String host = "localhost";
      @Setting("Port")
      private int port = 3306;
      @Setting("DataBase")
      private String database = "donatecase";
      @Setting("User")
      private String user = "admin";
      @Setting("Password")
      private String password = "123456";

      @Generated
      public boolean enabled() {
         return this.enabled;
      }

      @Generated
      public String host() {
         return this.host;
      }

      @Generated
      public int port() {
         return this.port;
      }

      @Generated
      public String database() {
         return this.database;
      }

      @Generated
      public String user() {
         return this.user;
      }

      @Generated
      public String password() {
         return this.password;
      }

      @Generated
      public MySQL enabled(boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public MySQL host(String host) {
         this.host = host;
         return this;
      }

      @Generated
      public MySQL port(int port) {
         this.port = port;
         return this;
      }

      @Generated
      public MySQL database(String database) {
         this.database = database;
         return this;
      }

      @Generated
      public MySQL user(String user) {
         this.user = user;
         return this;
      }

      @Generated
      public MySQL password(String password) {
         this.password = password;
         return this;
      }
   }
}
