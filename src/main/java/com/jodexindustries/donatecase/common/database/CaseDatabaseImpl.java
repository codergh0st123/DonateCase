package com.jodexindustries.donatecase.common.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.config.ConfigData;
import com.jodexindustries.donatecase.api.data.database.DatabaseStatus;
import com.jodexindustries.donatecase.api.data.database.DatabaseType;
import com.jodexindustries.donatecase.api.database.CaseDatabase;
import com.jodexindustries.donatecase.common.DonateCase;
import com.jodexindustries.donatecase.common.database.entities.OpenInfoTable;
import com.jodexindustries.donatecase.common.database.entities.PlayerKeysTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class CaseDatabaseImpl extends CaseDatabase {
   private Dao<CaseData.History, String> historyDataTables;
   private Dao<PlayerKeysTable, String> playerKeysTables;
   private Dao<OpenInfoTable, String> openInfoTables;
   private JdbcConnectionSource connectionSource;
   private final DonateCase api;
   private final Logger logger;
   private DatabaseType databaseType;

   public CaseDatabaseImpl(DonateCase api) {
      this.api = api;
      this.logger = api.getPlatform().getLogger();
   }

   public void connect(String path) {
      try {
         this.close();
         this.connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + path + "/database.db");
         this.databaseType = DatabaseType.SQLITE;
         this.init();
         this.logger.info("Using SQLITE database type!");
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void connect(String database, int port, String host, String user, String password) {
      try {
         this.close();
         this.connectionSource = new JdbcConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
         this.databaseType = DatabaseType.MYSQL;
         this.init();
         this.logger.info("Using MYSQL database type!");
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void connect() {
      ConfigData.MySQL mysql = this.api.getConfigManager().getConfig().mySQL();
      if (mysql != null && mysql.enabled()) {
         this.connect(mysql.database(), mysql.port(), mysql.host(), mysql.user(), mysql.password());
      } else {
         this.connect(this.api.getPlatform().getDataFolder().getAbsolutePath());
      }
   }

   private void init() throws SQLException {
      com.j256.ormlite.logger.Logger.setGlobalLogLevel(Level.WARNING);
      TableUtils.createTableIfNotExists(this.connectionSource, CaseData.History.class);
      TableUtils.createTableIfNotExists(this.connectionSource, PlayerKeysTable.class);
      TableUtils.createTableIfNotExists(this.connectionSource, OpenInfoTable.class);
      this.historyDataTables = DaoManager.createDao(this.connectionSource, CaseData.History.class);
      this.playerKeysTables = DaoManager.createDao(this.connectionSource, PlayerKeysTable.class);
      this.openInfoTables = DaoManager.createDao(this.connectionSource, OpenInfoTable.class);
   }

   public CompletableFuture<Map<String, Integer>> getKeys(String player) {
      return CompletableFuture.supplyAsync(() -> {
         Map<String, Integer> keys = new HashMap<>();

         try {
            for(PlayerKeysTable result : this.playerKeysTables.queryBuilder().where().eq("player", player).query()) {
               keys.put(result.getCaseType(), result.getKeys());
            }
         } catch (SQLException e) {
            this.warning(e);
         }

         return keys;
      });
   }

   public CompletableFuture<Integer> getKeys(String name, String player) {
      return CompletableFuture.supplyAsync(() -> {
         int keys = 0;

         try {
            List<PlayerKeysTable> results = this.playerKeysTables.queryBuilder().where().eq("player", player).and().eq("case_name", name).query();
            if (!results.isEmpty()) {
               keys = ((PlayerKeysTable)results.get(0)).getKeys();
            }
         } catch (SQLException e) {
            this.warning(e);
         }

         return keys;
      });
   }

   public CompletableFuture<DatabaseStatus> setKeys(String name, String player, int keys) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            List<PlayerKeysTable> results = this.playerKeysTables.queryBuilder().where().eq("player", player).and().eq("case_name", name).query();
            PlayerKeysTable playerKeysTable = null;
            if (!results.isEmpty()) {
               playerKeysTable = (PlayerKeysTable)results.get(0);
            }

            if (playerKeysTable == null) {
               playerKeysTable = new PlayerKeysTable();
               playerKeysTable.setPlayer(player);
               playerKeysTable.setCaseType(name);
               playerKeysTable.setKeys(keys);
               this.playerKeysTables.create(playerKeysTable);
            } else {
               UpdateBuilder<PlayerKeysTable, String> updateBuilder = this.playerKeysTables.updateBuilder();
               updateBuilder.updateColumnValue("keys", keys);
               updateBuilder.where().eq("player", player).and().eq("case_name", name);
               updateBuilder.update();
            }
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }

         return DatabaseStatus.COMPLETE;
      });
   }

   public CompletableFuture<Integer> getOpenCount(String player, String caseType) {
      return CompletableFuture.supplyAsync(() -> {
         OpenInfoTable openInfoTable = null;

         try {
            List<OpenInfoTable> results = this.openInfoTables.queryBuilder().where().eq("player", player).and().eq("case_type", caseType).query();
            if (!results.isEmpty()) {
               openInfoTable = (OpenInfoTable)results.get(0);
            }
         } catch (SQLException e) {
            this.warning(e);
         }

         return openInfoTable != null ? openInfoTable.getCount() : 0;
      });
   }

   public CompletableFuture<Map<String, Integer>> getOpenCount(String player) {
      return CompletableFuture.supplyAsync(() -> {
         Map<String, Integer> opens = new HashMap<>();

         try {
            for(OpenInfoTable result : this.openInfoTables.queryBuilder().where().eq("player", player).query()) {
               opens.put(result.getCaseType(), result.getCount());
            }
         } catch (SQLException e) {
            this.warning(e);
         }

         return opens;
      });
   }

   public CompletableFuture<DatabaseStatus> setCount(String caseType, String player, int count) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            List<OpenInfoTable> results = this.openInfoTables.queryBuilder().where().eq("player", player).and().eq("case_type", caseType).query();
            OpenInfoTable openInfoTable = null;
            if (!results.isEmpty()) {
               openInfoTable = (OpenInfoTable)results.get(0);
            }

            if (openInfoTable == null) {
               openInfoTable = new OpenInfoTable();
               openInfoTable.setPlayer(player);
               openInfoTable.setCaseType(caseType);
               openInfoTable.setCount(count);
               this.openInfoTables.create(openInfoTable);
            } else {
               UpdateBuilder<OpenInfoTable, String> updateBuilder = this.openInfoTables.updateBuilder();
               updateBuilder.updateColumnValue("count", count);
               updateBuilder.where().eq("player", player).and().eq("case_type", caseType);
               updateBuilder.update();
            }
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }

         return DatabaseStatus.COMPLETE;
      });
   }

   public CompletableFuture<DatabaseStatus> addHistory(String caseType, CaseData.History newEntry, int maxSize) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            List<CaseData.History> entries = this.historyDataTables.queryBuilder().orderBy("time", true).where().eq("case_type", caseType).query();
            if (entries.size() >= maxSize) {
               CaseData.History oldest = (CaseData.History)entries.get(0);
               DeleteBuilder<CaseData.History, String> deleteBuilder = this.historyDataTables.deleteBuilder();
               deleteBuilder.where().eq("time", oldest.time()).and().eq("case_type", caseType);
               deleteBuilder.delete();
            }

            this.historyDataTables.create(newEntry);
            return DatabaseStatus.COMPLETE;
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }
      });
   }

   private void setHistoryDataTable(CaseData.History historyDataTable, CaseData.History data) throws SQLException {
      if (historyDataTable == null) {
         this.historyDataTables.create(data);
      } else {
         UpdateBuilder<CaseData.History, String> updateBuilder = this.historyDataTables.updateBuilder();
         updateBuilder.updateColumnValue("player_name", data.playerName());
         updateBuilder.updateColumnValue("time", data.time());
         updateBuilder.updateColumnValue("group", data.group());
         updateBuilder.updateColumnValue("action", data.action());
         updateBuilder.where().eq("case_type", data.caseType()).and().eq("time", historyDataTable.time());
         updateBuilder.update();
      }

   }

   public CompletableFuture<DatabaseStatus> setHistoryData(String caseType, int index, CaseData.History data) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            QueryBuilder<CaseData.History, String> queryBuilder = this.historyDataTables.queryBuilder();
            queryBuilder.where().eq("case_type", caseType);
            List<CaseData.History> results = queryBuilder.query();
            CaseData.History historyDataTable = results.isEmpty() ? null : (CaseData.History)results.get(index);
            this.setHistoryDataTable(historyDataTable, data);
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }

         return DatabaseStatus.COMPLETE;
      });
   }

   public CompletableFuture<DatabaseStatus> removeHistoryData(String caseType) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            DeleteBuilder<CaseData.History, String> deleteBuilder = this.historyDataTables.deleteBuilder();
            deleteBuilder.where().eq("case_type", caseType);
            deleteBuilder.delete();
            return DatabaseStatus.COMPLETE;
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }
      });
   }

   public CompletableFuture<DatabaseStatus> removeHistoryData(String caseType, int index) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            DeleteBuilder<CaseData.History, String> deleteBuilder = this.historyDataTables.deleteBuilder();
            deleteBuilder.where().eq("case_type", caseType).and().eq("id", index);
            deleteBuilder.delete();
            return DatabaseStatus.COMPLETE;
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }
      });
   }

   public CompletableFuture<List<CaseData.History>> getHistoryData() {
      List<CaseData.History> result = new ArrayList<>();
      return CompletableFuture.supplyAsync(() -> {
         try {
            result.addAll(this.historyDataTables.queryForAll());
         } catch (SQLException e) {
            this.warning(e);
         }

         return result;
      });
   }

   public CompletableFuture<List<CaseData.History>> getHistoryData(String caseType) {
      List<CaseData.History> result = new ArrayList<>();
      return CompletableFuture.supplyAsync(() -> {
         try {
            result.addAll(this.historyDataTables.queryBuilder().orderBy("time", true).where().eq("case_type", caseType).query());
         } catch (SQLException e) {
            this.warning(e);
         }

         return result;
      });
   }

   public List<CaseData.History> getCache() {
      if (this.databaseType == DatabaseType.SQLITE) {
         return (List)this.getHistoryData().join();
      } else {
         List<CaseData.History> cachedList = cache.get("all!");
         if (cachedList != null) {
            return cachedList;
         } else {
            List<CaseData.History> previousList = cache.getPrevious("all!");
            this.getHistoryData().thenAcceptAsync((historyData) -> cache.put("all!", historyData));
            return previousList != null ? previousList : (List)this.getHistoryData().join();
         }
      }
   }

   public List<CaseData.History> getCache(String caseType) {
      if (this.databaseType == DatabaseType.SQLITE) {
         return (List)this.getHistoryData(caseType).join();
      } else {
         List<CaseData.History> cachedList = cache.get(caseType);
         if (cachedList != null) {
            return cachedList;
         } else {
            List<CaseData.History> previousList = cache.getPrevious(caseType);
            this.getHistoryData(caseType).thenAcceptAsync((historyData) -> cache.put(caseType, historyData));
            return previousList != null ? previousList : (List)this.getHistoryData(caseType).join();
         }
      }
   }

   public CompletableFuture<DatabaseStatus> delAllKeys() {
      return CompletableFuture.supplyAsync(() -> {
         try {
            this.playerKeysTables.deleteBuilder().delete();
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }

         return DatabaseStatus.COMPLETE;
      });
   }

   public CompletableFuture<DatabaseStatus> delKeys(String caseType) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            DeleteBuilder<PlayerKeysTable, String> deleteBuilder = this.playerKeysTables.deleteBuilder();
            deleteBuilder.where().eq("case_name", caseType);
            deleteBuilder.delete();
         } catch (SQLException e) {
            this.warning(e);
            return DatabaseStatus.FAIL;
         }

         return DatabaseStatus.COMPLETE;
      });
   }

   public void close() {
      if (this.connectionSource != null) {
         try {
            this.connectionSource.close();
         } catch (Exception e) {
            this.logger.warning(e.getMessage());
         }
      }

   }

   public DatabaseType getType() {
      return this.databaseType;
   }

   private void warning(Throwable e) {
      this.logger.log(java.util.logging.Level.WARNING, "Error with database query:", e);
   }
}
