package com.jodexindustries.donatecase.api.data.storage;

import java.util.Objects;
import lombok.Generated;

public class CaseWorld {
   private final String name;
   private CaseLocation spawnLocation;

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         CaseWorld caseWorld = (CaseWorld)object;
         return caseWorld.name.equals(this.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.name);
   }

   public String toString() {
      return "CaseWorld{name='" + this.name + '\'' + '}';
   }

   @Generated
   public CaseWorld(String name) {
      this.name = name;
   }

   @Generated
   public String name() {
      return this.name;
   }

   @Generated
   public CaseLocation spawnLocation() {
      return this.spawnLocation;
   }

   @Generated
   public CaseWorld spawnLocation(CaseLocation spawnLocation) {
      this.spawnLocation = spawnLocation;
      return this;
   }
}
