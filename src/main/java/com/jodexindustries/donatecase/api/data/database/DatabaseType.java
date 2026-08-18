package com.jodexindustries.donatecase.api.data.database;

public enum DatabaseType {
   MYSQL,
   SQLITE;

   // $FF: synthetic method
   private static DatabaseType[] $values() {
      return new DatabaseType[]{MYSQL, SQLITE};
   }
}
