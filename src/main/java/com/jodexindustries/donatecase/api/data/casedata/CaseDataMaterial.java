package com.jodexindustries.donatecase.api.data.casedata;

import com.jodexindustries.donatecase.api.DCAPI;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;

public class CaseDataMaterial implements MetaUpdater, Cloneable {
   private String id;
   private String displayName;
   private boolean enchanted;
   private List<String> lore;
   private int modelData;
   private String[] rgb;
   private Object itemStack;

   public void updateMeta() {
      this.updateMeta(this.itemStack, this.displayName, this.lore, this.modelData, this.enchanted, this.rgb);
   }

   public void updateMeta(Object itemStack, String displayName, List<String> lore, int modelData, boolean enchanted, String[] rgb) {
      DCAPI.getInstance().getPlatform().getMetaUpdater().updateMeta(itemStack, displayName, lore, modelData, enchanted, rgb);
   }

   public CaseDataMaterial clone() {
      try {
         CaseDataMaterial cloned = (CaseDataMaterial)super.clone();
         if (this.itemStack instanceof Cloneable) {
            cloned.itemStack = this.cloneItemStack(this.itemStack);
         } else {
            cloned.itemStack = this.itemStack;
         }

         return cloned;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   private Object cloneItemStack(Object itemStack) {
      try {
         return itemStack.getClass().getMethod("clone").invoke(itemStack);
      } catch (Exception e) {
         throw new IllegalStateException("Failed to clone itemStack", e);
      }
   }

   public String toString() {
      return "CaseDataMaterial{id='" + this.id + '\'' + ", displayName='" + this.displayName + '\'' + ", enchanted=" + this.enchanted + ", lore=" + this.lore + ", modelData=" + this.modelData + ", rgb=" + Arrays.toString(this.rgb) + ", itemStack=" + this.itemStack + '}';
   }

   @Generated
   public CaseDataMaterial id(String id) {
      this.id = id;
      return this;
   }

   @Generated
   public CaseDataMaterial displayName(String displayName) {
      this.displayName = displayName;
      return this;
   }

   @Generated
   public CaseDataMaterial enchanted(boolean enchanted) {
      this.enchanted = enchanted;
      return this;
   }

   @Generated
   public CaseDataMaterial lore(List<String> lore) {
      this.lore = lore;
      return this;
   }

   @Generated
   public CaseDataMaterial modelData(int modelData) {
      this.modelData = modelData;
      return this;
   }

   @Generated
   public CaseDataMaterial rgb(String[] rgb) {
      this.rgb = rgb;
      return this;
   }

   @Generated
   public CaseDataMaterial itemStack(Object itemStack) {
      this.itemStack = itemStack;
      return this;
   }

   @Generated
   public String id() {
      return this.id;
   }

   @Generated
   public String displayName() {
      return this.displayName;
   }

   @Generated
   public boolean enchanted() {
      return this.enchanted;
   }

   @Generated
   public List<String> lore() {
      return this.lore;
   }

   @Generated
   public int modelData() {
      return this.modelData;
   }

   @Generated
   public String[] rgb() {
      return this.rgb;
   }

   @Generated
   public Object itemStack() {
      return this.itemStack;
   }
}
