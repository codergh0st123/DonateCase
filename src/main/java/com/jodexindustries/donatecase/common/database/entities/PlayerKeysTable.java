package com.jodexindustries.donatecase.common.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Generated;

@DatabaseTable(
   tableName = "player_keys"
)
public class PlayerKeysTable {
   @DatabaseField(
      canBeNull = false
   )
   private String player;
   @DatabaseField(
      canBeNull = false,
      columnName = "case_name"
   )
   private String caseType;
   @DatabaseField(
      canBeNull = false,
      defaultValue = "0"
   )
   private int keys;

   @Generated
   public void setPlayer(String player) {
      this.player = player;
   }

   @Generated
   public void setCaseType(String caseType) {
      this.caseType = caseType;
   }

   @Generated
   public void setKeys(int keys) {
      this.keys = keys;
   }

   @Generated
   public String getPlayer() {
      return this.player;
   }

   @Generated
   public String getCaseType() {
      return this.caseType;
   }

   @Generated
   public int getKeys() {
      return this.keys;
   }
}
