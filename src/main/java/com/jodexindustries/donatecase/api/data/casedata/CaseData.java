package com.jodexindustries.donatecase.api.data.casedata;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGui;
import com.jodexindustries.donatecase.api.tools.ProbabilityCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class CaseData implements Cloneable {
   private transient String caseType;
   @Setting("DisplayName")
   private String caseDisplayName;
   @Setting("Animation")
   @Required
   private String animation;
   @Setting("Items")
   private Map<String, CaseDataItem> items;
   @Setting("Hologram")
   private Hologram hologram;
   @Setting("LevelGroups")
   private Map<String, Integer> levelGroups;
   @Setting("Gui")
   private CaseGui caseGui;
   @Setting("NoKeyActions")
   private List<String> noKeyActions;
   @Setting("OpenType")
   private OpenType openType;
   @Setting("AnimationSettings")
   private ConfigurationNode animationSettings;
   @Setting("CooldownBeforeAnimation")
   private int cooldownBeforeStart;
   @Setting("HistoryDataSize")
   private int historyDataSize;

   public CaseData() {
      this.openType = OpenType.GUI;
   }

   public @Nullable CaseDataItem getItem(String name) {
      return (CaseDataItem)this.items.getOrDefault(name, (Object)null);
   }

   public CaseDataItem getRandomItem() {
      ProbabilityCollection<CaseDataItem> collection = new ProbabilityCollection<CaseDataItem>();

      for(CaseDataItem item : this.items.values()) {
         double chance = item.chance();
         if (chance > (double)0.0F) {
            collection.add(item, chance);
         }
      }

      return collection.get();
   }

   public boolean hasRealItems() {
      return this.items.values().stream().anyMatch((item) -> item.chance() > (double)0.0F);
   }

   public CaseData clone() {
      try {
         CaseData clonedCaseData = (CaseData)super.clone();
         if (this.items != null) {
            clonedCaseData.items = cloneItemsMap(this.items);
         }

         if (this.caseGui != null) {
            clonedCaseData.caseGui = this.caseGui.clone();
         }

         return clonedCaseData;
      } catch (CloneNotSupportedException e) {
         throw new AssertionError(e);
      }
   }

   public String toString() {
      return "CaseData{caseType='" + this.caseType + '\'' + ", caseDisplayName='" + this.caseDisplayName + '\'' + ", animation='" + this.animation + '\'' + ", items=" + this.items + ", hologram=" + this.hologram + ", levelGroups=" + this.levelGroups + ", caseGui=" + this.caseGui + ", noKeyActions=" + this.noKeyActions + ", openType=" + this.openType + ", animationSettings=" + this.animationSettings + '}';
   }

   protected static Map<String, CaseDataItem> cloneItemsMap(Map<String, CaseDataItem> originalMap) {
      Map<String, CaseDataItem> clonedMap = new HashMap();

      for(Map.Entry<String, CaseDataItem> entry : originalMap.entrySet()) {
         clonedMap.put((String)entry.getKey(), ((CaseDataItem)entry.getValue()).clone());
      }

      return clonedMap;
   }

   @Generated
   public String caseType() {
      return this.caseType;
   }

   @Generated
   public String caseDisplayName() {
      return this.caseDisplayName;
   }

   @Generated
   public String animation() {
      return this.animation;
   }

   @Generated
   public Map<String, CaseDataItem> items() {
      return this.items;
   }

   @Generated
   public Hologram hologram() {
      return this.hologram;
   }

   @Generated
   public Map<String, Integer> levelGroups() {
      return this.levelGroups;
   }

   @Generated
   public CaseGui caseGui() {
      return this.caseGui;
   }

   @Generated
   public List<String> noKeyActions() {
      return this.noKeyActions;
   }

   @Generated
   public OpenType openType() {
      return this.openType;
   }

   @Generated
   public ConfigurationNode animationSettings() {
      return this.animationSettings;
   }

   @Generated
   public int cooldownBeforeStart() {
      return this.cooldownBeforeStart;
   }

   @Generated
   public int historyDataSize() {
      return this.historyDataSize;
   }

   @Generated
   public CaseData caseType(String caseType) {
      this.caseType = caseType;
      return this;
   }

   @Generated
   public CaseData caseDisplayName(String caseDisplayName) {
      this.caseDisplayName = caseDisplayName;
      return this;
   }

   @Generated
   public CaseData animation(String animation) {
      this.animation = animation;
      return this;
   }

   @Generated
   public CaseData items(Map<String, CaseDataItem> items) {
      this.items = items;
      return this;
   }

   @Generated
   public CaseData hologram(Hologram hologram) {
      this.hologram = hologram;
      return this;
   }

   @Generated
   public CaseData levelGroups(Map<String, Integer> levelGroups) {
      this.levelGroups = levelGroups;
      return this;
   }

   @Generated
   public CaseData caseGui(CaseGui caseGui) {
      this.caseGui = caseGui;
      return this;
   }

   @Generated
   public CaseData noKeyActions(List<String> noKeyActions) {
      this.noKeyActions = noKeyActions;
      return this;
   }

   @Generated
   public CaseData openType(OpenType openType) {
      this.openType = openType;
      return this;
   }

   @Generated
   public CaseData animationSettings(ConfigurationNode animationSettings) {
      this.animationSettings = animationSettings;
      return this;
   }

   @Generated
   public CaseData cooldownBeforeStart(int cooldownBeforeStart) {
      this.cooldownBeforeStart = cooldownBeforeStart;
      return this;
   }

   @Generated
   public CaseData historyDataSize(int historyDataSize) {
      this.historyDataSize = historyDataSize;
      return this;
   }

   @DatabaseTable(
      tableName = "history_data"
   )
   public static class History {
      /** @deprecated */
      @DatabaseField(
         columnName = "id"
      )
      @Deprecated
      private int id;
      @DatabaseField(
         columnName = "item"
      )
      private String item;
      @DatabaseField(
         columnName = "player_name"
      )
      private String playerName;
      @DatabaseField(
         columnName = "time"
      )
      private long time;
      @DatabaseField(
         columnName = "group"
      )
      private String group;
      @DatabaseField(
         columnName = "case_type"
      )
      private String caseType;
      @DatabaseField(
         columnName = "action"
      )
      private String action;

      public History(String item, String caseType, String playerName, long time, String group, String action) {
         this.item = item;
         this.playerName = playerName;
         this.time = time;
         this.group = group;
         this.caseType = caseType;
         this.action = action;
      }

      public History() {
      }

      public String toString() {
         return "History{item='" + this.item + '\'' + ", playerName='" + this.playerName + '\'' + ", time=" + this.time + ", group='" + this.group + '\'' + ", caseType='" + this.caseType + '\'' + ", action='" + this.action + '\'' + '}';
      }

      public History clone() {
         try {
            return (History)super.clone();
         } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
         }
      }

      /** @deprecated */
      @Deprecated
      @Generated
      public int id() {
         return this.id;
      }

      @Generated
      public String item() {
         return this.item;
      }

      @Generated
      public String playerName() {
         return this.playerName;
      }

      @Generated
      public long time() {
         return this.time;
      }

      @Generated
      public String group() {
         return this.group;
      }

      @Generated
      public String caseType() {
         return this.caseType;
      }

      @Generated
      public String action() {
         return this.action;
      }

      /** @deprecated */
      @Deprecated
      @Generated
      public void id(int id) {
         this.id = id;
      }

      @Generated
      public void item(String item) {
         this.item = item;
      }

      @Generated
      public void playerName(String playerName) {
         this.playerName = playerName;
      }

      @Generated
      public void time(long time) {
         this.time = time;
      }

      @Generated
      public void group(String group) {
         this.group = group;
      }

      @Generated
      public void caseType(String caseType) {
         this.caseType = caseType;
      }

      @Generated
      public void action(String action) {
         this.action = action;
      }
   }

   @ConfigSerializable
   public static class Hologram {
      @Setting(
         nodeFromParent = true
      )
      private ConfigurationNode node;
      @Setting("Toggle")
      private boolean enabled;
      @Setting("Height")
      private double height;
      @Setting("Range")
      private int range;
      @Setting("Message")
      private List<String> messages;

      @Generated
      public ConfigurationNode node() {
         return this.node;
      }

      @Generated
      public boolean enabled() {
         return this.enabled;
      }

      @Generated
      public double height() {
         return this.height;
      }

      @Generated
      public int range() {
         return this.range;
      }

      @Generated
      public List<String> messages() {
         return this.messages;
      }
   }
}
