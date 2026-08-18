package com.jodexindustries.donatecase.api;

import com.jodexindustries.donatecase.api.config.Loadable;
import com.jodexindustries.donatecase.api.database.CaseDatabase;
import com.jodexindustries.donatecase.api.event.EventBus;
import com.jodexindustries.donatecase.api.manager.ActionManager;
import com.jodexindustries.donatecase.api.manager.AddonManager;
import com.jodexindustries.donatecase.api.manager.AnimationManager;
import com.jodexindustries.donatecase.api.manager.CaseKeyManager;
import com.jodexindustries.donatecase.api.manager.CaseManager;
import com.jodexindustries.donatecase.api.manager.CaseOpenManager;
import com.jodexindustries.donatecase.api.manager.ConfigManager;
import com.jodexindustries.donatecase.api.manager.GUIManager;
import com.jodexindustries.donatecase.api.manager.GUITypedItemManager;
import com.jodexindustries.donatecase.api.manager.HologramManager;
import com.jodexindustries.donatecase.api.manager.MaterialManager;
import com.jodexindustries.donatecase.api.manager.SubCommandManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Experimental;

public abstract class DCAPI {
   private static DCAPI instance;

   public static void setInstance(@NotNull DCAPI instance) {
      if (DCAPI.instance == null) {
         DCAPI.instance = instance;
      }

   }

   public abstract @NotNull ActionManager getActionManager();

   public abstract @NotNull AddonManager getAddonManager();

   public abstract @NotNull AnimationManager getAnimationManager();

   public abstract @NotNull CaseKeyManager getCaseKeyManager();

   public abstract @NotNull CaseManager getCaseManager();

   public abstract @NotNull CaseOpenManager getCaseOpenManager();

   public abstract @NotNull GUIManager getGUIManager();

   public abstract @NotNull GUITypedItemManager getGuiTypedItemManager();

   public abstract @NotNull MaterialManager getMaterialManager();

   public abstract @NotNull SubCommandManager getSubCommandManager();

   public abstract @NotNull HologramManager getHologramManager();

   public abstract @NotNull CaseDatabase getDatabase();

   public abstract @NotNull ConfigManager getConfigManager();

   public abstract @NotNull Loadable getCaseLoader();

   public abstract @NotNull EventBus getEventBus();

   public abstract @NotNull Platform getPlatform();

   @Experimental
   public void clear() {
      this.getCaseManager().getMap().clear();
      this.getAnimationManager().getActiveCases().clear();
      this.getAnimationManager().getActiveCasesByBlock().clear();
      CaseOpenManager.cache.clear();
      CaseKeyManager.cache.clear();
      CaseDatabase.cache.clear();
   }

   @Generated
   public static DCAPI getInstance() {
      return instance;
   }
}
