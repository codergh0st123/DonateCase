package com.jodexindustries.donatecase.common.tools.updater;

import com.google.gson.annotations.SerializedName;
import lombok.Generated;

public class VersionInfo {
   @SerializedName("version_number")
   private String versionNumber;
   @SerializedName("date_published")
   private String datePublished;
   private String name;
   @SerializedName("version_type")
   private String versionType;
   private String status;
   private int downloads;
   private boolean isNew;

   @Generated
   public String getVersionNumber() {
      return this.versionNumber;
   }

   @Generated
   public String getDatePublished() {
      return this.datePublished;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getVersionType() {
      return this.versionType;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public int getDownloads() {
      return this.downloads;
   }

   @Generated
   public boolean isNew() {
      return this.isNew;
   }

   @Generated
   public void setVersionNumber(String versionNumber) {
      this.versionNumber = versionNumber;
   }

   @Generated
   public void setDatePublished(String datePublished) {
      this.datePublished = datePublished;
   }

   @Generated
   public void setName(String name) {
      this.name = name;
   }

   @Generated
   public void setVersionType(String versionType) {
      this.versionType = versionType;
   }

   @Generated
   public void setStatus(String status) {
      this.status = status;
   }

   @Generated
   public void setDownloads(int downloads) {
      this.downloads = downloads;
   }

   @Generated
   public void setNew(boolean isNew) {
      this.isNew = isNew;
   }
}
