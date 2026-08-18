package com.jodexindustries.donatecase.common.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Generated;

@DatabaseTable(
   tableName = "open_info"
)
public class OpenInfoTable {
   @DatabaseField(
      canBeNull = false
   )
   private String player;
   @DatabaseField(
      columnName = "case_type"
   )
   private String caseType;
   @DatabaseField(
      defaultValue = "0"
   )
   private int count;

   @Generated
   public void setPlayer(String player) {
      this.player = player;
   }

   @Generated
   public void setCaseType(String caseType) {
      this.caseType = caseType;
   }

   @Generated
   public void setCount(int count) {
      this.count = count;
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
   public int getCount() {
      return this.count;
   }
}
