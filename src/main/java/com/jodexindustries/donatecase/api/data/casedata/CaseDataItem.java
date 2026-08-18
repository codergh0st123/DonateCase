package com.jodexindustries.donatecase.api.data.casedata;

import com.jodexindustries.donatecase.api.tools.ProbabilityCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class CaseDataItem implements Cloneable {
   @Setting(
      nodeFromParent = true
   )
   private ConfigurationNode node;
   @Setting("Group")
   private String group;
   @Setting("Chance")
   private double chance;
   @Setting("Index")
   private int index;
   @Setting("Material")
   private CaseDataMaterial material;
   @Setting("GiveType")
   private String giveType = "ONE";
   @Setting("Actions")
   private List<String> actions;
   @Setting("AlternativeActions")
   private List<String> alternativeActions;
   @Setting("RandomActions")
   private Map<String, RandomAction> randomActions;

   public String getName() {
      return String.valueOf(this.node.key());
   }

   public List<String> getActionsBasedOnChoice(RandomAction randomAction, boolean alternative) {
      if (randomAction != null) {
         return randomAction.actions();
      } else {
         return alternative ? this.alternativeActions() : this.actions();
      }
   }

   public RandomAction getRandomAction() {
      ProbabilityCollection<RandomAction> collection = new ProbabilityCollection<RandomAction>();

      for(RandomAction randomAction : this.randomActions().values()) {
         double chance = randomAction.chance();
         if (chance > (double)0.0F) {
            collection.add(randomAction, chance);
         }
      }

      return collection.get();
   }

   public CaseDataItem clone() {
      try {
         CaseDataItem clonedItem = (CaseDataItem)super.clone();
         clonedItem.randomActions = cloneRandomActionsMap(this.randomActions);
         return clonedItem;
      } catch (Throwable e) {
         throw new AssertionError(e);
      }
   }

   public String toString() {
      return "CaseDataItem{node=" + this.node + ", group='" + this.group + '\'' + ", chance=" + this.chance + ", index=" + this.index + ", material=" + this.material + ", giveType='" + this.giveType + '\'' + ", actions=" + this.actions + ", alternativeActions=" + this.alternativeActions + ", randomActions=" + this.randomActions + '}';
   }

   private static Map<String, RandomAction> cloneRandomActionsMap(Map<String, RandomAction> originalMap) {
      Map<String, RandomAction> clonedMap = new HashMap<>();

      for(Map.Entry<String, RandomAction> entry : originalMap.entrySet()) {
         clonedMap.put((String)entry.getKey(), ((RandomAction)entry.getValue()).clone());
      }

      return clonedMap;
   }

   @Generated
   public ConfigurationNode node() {
      return this.node;
   }

   @Generated
   public String group() {
      return this.group;
   }

   @Generated
   public double chance() {
      return this.chance;
   }

   @Generated
   public int index() {
      return this.index;
   }

   @Generated
   public CaseDataMaterial material() {
      return this.material;
   }

   @Generated
   public String giveType() {
      return this.giveType;
   }

   @Generated
   public List<String> actions() {
      return this.actions;
   }

   @Generated
   public List<String> alternativeActions() {
      return this.alternativeActions;
   }

   @Generated
   public Map<String, RandomAction> randomActions() {
      return this.randomActions;
   }

   @Generated
   public CaseDataItem node(ConfigurationNode node) {
      this.node = node;
      return this;
   }

   @Generated
   public CaseDataItem group(String group) {
      this.group = group;
      return this;
   }

   @Generated
   public CaseDataItem chance(double chance) {
      this.chance = chance;
      return this;
   }

   @Generated
   public CaseDataItem index(int index) {
      this.index = index;
      return this;
   }

   @Generated
   public CaseDataItem material(CaseDataMaterial material) {
      this.material = material;
      return this;
   }

   @Generated
   public CaseDataItem giveType(String giveType) {
      this.giveType = giveType;
      return this;
   }

   @Generated
   public CaseDataItem actions(List<String> actions) {
      this.actions = actions;
      return this;
   }

   @Generated
   public CaseDataItem alternativeActions(List<String> alternativeActions) {
      this.alternativeActions = alternativeActions;
      return this;
   }

   @Generated
   public CaseDataItem randomActions(Map<String, RandomAction> randomActions) {
      this.randomActions = randomActions;
      return this;
   }

   @ConfigSerializable
   public static class RandomAction implements Cloneable {
      @Setting(
         nodeFromParent = true
      )
      private ConfigurationNode node;
      @Setting("Chance")
      private double chance;
      @Setting("Actions")
      private List<String> actions;
      @Setting("DisplayName")
      private String displayName;

      public String getName() {
         return String.valueOf(this.node.key());
      }

      public RandomAction clone() {
         try {
            return (RandomAction)super.clone();
         } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
         }
      }

      public String toString() {
         return "RandomAction{chance=" + this.chance + ", actions=" + this.actions + ", displayName='" + this.displayName + '\'' + '}';
      }

      @Generated
      public RandomAction node(ConfigurationNode node) {
         this.node = node;
         return this;
      }

      @Generated
      public RandomAction chance(double chance) {
         this.chance = chance;
         return this;
      }

      @Generated
      public RandomAction actions(List<String> actions) {
         this.actions = actions;
         return this;
      }

      @Generated
      public RandomAction displayName(String displayName) {
         this.displayName = displayName;
         return this;
      }

      @Generated
      public ConfigurationNode node() {
         return this.node;
      }

      @Generated
      public double chance() {
         return this.chance;
      }

      @Generated
      public List<String> actions() {
         return this.actions;
      }

      @Generated
      public String displayName() {
         return this.displayName;
      }
   }
}
