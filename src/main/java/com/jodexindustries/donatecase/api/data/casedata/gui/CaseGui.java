package com.jodexindustries.donatecase.api.data.casedata.gui;

import com.jodexindustries.donatecase.api.data.casedata.CaseDataMaterial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

public class CaseGui implements Cloneable {
   private String title;
   private int size;
   private int updateRate;
   private transient Map<String, Item> items;

   public @Nullable String getItemTypeBySlot(int slot) {
      for(Item item : this.items.values()) {
         if (item.slots.contains(slot)) {
            return item.type;
         }
      }

      return null;
   }

   private Map<String, Item> cloneItemsMap(Map<String, Item> originalMap) {
      Map<String, Item> clonedMap = new HashMap<>();

      for(Map.Entry<String, Item> entry : originalMap.entrySet()) {
         clonedMap.put((String)entry.getKey(), ((Item)entry.getValue()).clone());
      }

      return clonedMap;
   }

   public CaseGui clone() {
      try {
         CaseGui clone = (CaseGui)super.clone();
         if (this.items != null) {
            clone.items = this.cloneItemsMap(this.items);
         }

         return clone;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   public String toString() {
      return "CaseGui{title='" + this.title + '\'' + ", size=" + this.size + ", updateRate=" + this.updateRate + ", items=" + this.items + '}';
   }

   @Generated
   public CaseGui title(String title) {
      this.title = title;
      return this;
   }

   @Generated
   public CaseGui size(int size) {
      this.size = size;
      return this;
   }

   @Generated
   public CaseGui updateRate(int updateRate) {
      this.updateRate = updateRate;
      return this;
   }

   @Generated
   public CaseGui items(Map<String, Item> items) {
      this.items = items;
      return this;
   }

   @Generated
   public String title() {
      return this.title;
   }

   @Generated
   public int size() {
      return this.size;
   }

   @Generated
   public int updateRate() {
      return this.updateRate;
   }

   @Generated
   public Map<String, Item> items() {
      return this.items;
   }

   public static class Item implements Cloneable {
      private ConfigurationNode node;
      private String type;
      private CaseDataMaterial material;
      private transient List<Integer> slots;

      public Item clone() {
         try {
            Item cloned = (Item)super.clone();
            cloned.material(this.material.clone());
            return cloned;
         } catch (CloneNotSupportedException var2) {
            throw new AssertionError();
         }
      }

      public String toString() {
         return "Item{node=" + this.node + ", type='" + this.type + '\'' + ", material=" + this.material + ", slots=" + this.slots + '}';
      }

      @Generated
      public ConfigurationNode node() {
         return this.node;
      }

      @Generated
      public String type() {
         return this.type;
      }

      @Generated
      public CaseDataMaterial material() {
         return this.material;
      }

      @Generated
      public List<Integer> slots() {
         return this.slots;
      }

      @Generated
      public Item node(ConfigurationNode node) {
         this.node = node;
         return this;
      }

      @Generated
      public Item type(String type) {
         this.type = type;
         return this;
      }

      @Generated
      public Item material(CaseDataMaterial material) {
         this.material = material;
         return this;
      }

      @Generated
      public Item slots(List<Integer> slots) {
         this.slots = slots;
         return this;
      }
   }
}
