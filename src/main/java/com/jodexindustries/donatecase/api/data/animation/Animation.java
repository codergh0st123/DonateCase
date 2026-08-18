package com.jodexindustries.donatecase.api.data.animation;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.util.UUID;
import lombok.Generated;
import org.spongepowered.configurate.ConfigurationNode;

public abstract class Animation {
   private DCPlayer player;
   private CaseLocation location;
   private UUID uuid;
   private CaseData caseData;
   private CaseDataItem winItem;
   private ConfigurationNode settings;

   public void init(DCPlayer player, CaseLocation location, UUID uuid, CaseData caseData, CaseDataItem winItem, ConfigurationNode settings) {
      this.player = player;
      this.location = location;
      this.uuid = uuid;
      this.caseData = caseData;
      this.winItem = winItem;
      this.settings = settings;
   }

   public abstract void start();

   public Object getPlayer() {
      return this.player;
   }

   public final void preEnd() {
      DCAPI.getInstance().getAnimationManager().preEnd(this.getUuid());
   }

   public final void end() {
      DCAPI.getInstance().getAnimationManager().end(this.getUuid());
   }

   @Generated
   public CaseLocation getLocation() {
      return this.location;
   }

   @Generated
   public UUID getUuid() {
      return this.uuid;
   }

   @Generated
   public CaseData getCaseData() {
      return this.caseData;
   }

   @Generated
   public CaseDataItem getWinItem() {
      return this.winItem;
   }

   @Generated
   public ConfigurationNode getSettings() {
      return this.settings;
   }
}
