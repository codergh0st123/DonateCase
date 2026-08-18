package com.jodexindustries.donatecase.api.data.database;

public enum DatabaseStatus {
   COMPLETE,
   CANCELLED,
   FAIL;

   // $FF: synthetic method
   private static DatabaseStatus[] $values() {
      return new DatabaseStatus[]{COMPLETE, CANCELLED, FAIL};
   }
}
