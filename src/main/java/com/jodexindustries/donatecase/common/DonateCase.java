package com.jodexindustries.donatecase.common;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.addon.PowerReason;
import com.jodexindustries.donatecase.common.config.CaseLoader;
import com.jodexindustries.donatecase.common.config.ConfigManagerImpl;
import com.jodexindustries.donatecase.common.database.CaseDatabaseImpl;
import com.jodexindustries.donatecase.common.event.EventBusImpl;
import com.jodexindustries.donatecase.common.event.EventListener;
import com.jodexindustries.donatecase.common.managers.ActionManagerImpl;
import com.jodexindustries.donatecase.common.managers.AddonManagerImpl;
import com.jodexindustries.donatecase.common.managers.AnimationManagerImpl;
import com.jodexindustries.donatecase.common.managers.CaseKeyManagerImpl;
import com.jodexindustries.donatecase.common.managers.CaseManagerImpl;
import com.jodexindustries.donatecase.common.managers.CaseOpenManagerImpl;
import com.jodexindustries.donatecase.common.managers.GUIManagerImpl;
import com.jodexindustries.donatecase.common.managers.GUITypedItemManagerImpl;
import com.jodexindustries.donatecase.common.managers.HologramManagerImpl;
import com.jodexindustries.donatecase.common.managers.MaterialManagerImpl;
import com.jodexindustries.donatecase.common.managers.SubCommandManagerImpl;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import com.jodexindustries.donatecase.common.tools.updater.UpdateChecker;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;

public class DonateCase extends DCAPI {
   private final ActionManagerImpl actionManager;
   private final AddonManagerImpl addonManager;
   private final AnimationManagerImpl animationManager;
   private final GUIManagerImpl guiManager;
   private final GUITypedItemManagerImpl guiTypedItemManager;
   private final MaterialManagerImpl materialManager;
   private final SubCommandManagerImpl subCommandManager;
   private final CaseKeyManagerImpl caseKeyManager;
   private final CaseOpenManagerImpl caseOpenManager;
   private final CaseManagerImpl caseManager;
   private final HologramManagerImpl hologramManager;
   private final CaseDatabaseImpl database;
   private final ConfigManagerImpl configManager;
   private final CaseLoader caseLoader;
   private final UpdateChecker updateChecker;
   private final EventBusImpl eventBus;
   private final EventListener eventListener;
   private final BackendPlatform platform;

   public DonateCase(BackendPlatform platform) {
      this.platform = platform;
      this.actionManager = new ActionManagerImpl(this);
      this.addonManager = new AddonManagerImpl(this);
      this.animationManager = new AnimationManagerImpl(this);
      this.guiManager = new GUIManagerImpl(this);
      this.guiTypedItemManager = new GUITypedItemManagerImpl(this);
      this.materialManager = new MaterialManagerImpl(this);
      this.subCommandManager = new SubCommandManagerImpl(this);
      this.caseKeyManager = new CaseKeyManagerImpl(this);
      this.caseOpenManager = new CaseOpenManagerImpl(this);
      this.caseManager = new CaseManagerImpl();
      this.hologramManager = new HologramManagerImpl(this);
      this.database = new CaseDatabaseImpl(this);
      this.configManager = new ConfigManagerImpl(platform);
      this.caseLoader = new CaseLoader(this);
      this.updateChecker = new UpdateChecker(this);
      this.eventBus = new EventBusImpl(platform.getLogger());
      this.eventListener = new EventListener(this);
   }

   public void load() {
      long time = System.currentTimeMillis();
      this.addonManager.load();
      this.configManager.load();
      this.caseLoader.load();
      this.hologramManager.load();
      this.updateChecker.check();
      this.database.connect();
      this.eventBus.register(this.eventListener);
      this.addonManager.enable(PowerReason.DONATE_CASE);
      this.platform.getLogger().info("Enabled in " + (System.currentTimeMillis() - time) + "ms");
   }

   public void unload() {
      this.eventBus.unregisterAll();
      this.addonManager.unload(PowerReason.DONATE_CASE);
      this.animationManager.unregister();
      this.subCommandManager.unregister();
      this.actionManager.unregister();
      this.materialManager.unregister();
      this.guiTypedItemManager.unregister();
      this.hologramManager.remove();
      this.database.close();
      this.guiManager.getMap().values().parallelStream().forEach((gui) -> gui.getPlayer().closeInventory());
      this.clear();
   }

   public @NotNull ActionManagerImpl getActionManager() {
      return this.actionManager;
   }

   public @NotNull AddonManagerImpl getAddonManager() {
      return this.addonManager;
   }

   public @NotNull AnimationManagerImpl getAnimationManager() {
      return this.animationManager;
   }

   public @NotNull CaseKeyManagerImpl getCaseKeyManager() {
      return this.caseKeyManager;
   }

   public @NotNull CaseManagerImpl getCaseManager() {
      return this.caseManager;
   }

   public @NotNull CaseOpenManagerImpl getCaseOpenManager() {
      return this.caseOpenManager;
   }

   public @NotNull GUIManagerImpl getGUIManager() {
      return this.guiManager;
   }

   public @NotNull GUITypedItemManagerImpl getGuiTypedItemManager() {
      return this.guiTypedItemManager;
   }

   public @NotNull MaterialManagerImpl getMaterialManager() {
      return this.materialManager;
   }

   public @NotNull SubCommandManagerImpl getSubCommandManager() {
      return this.subCommandManager;
   }

   public @NotNull HologramManagerImpl getHologramManager() {
      return this.hologramManager;
   }

   public @NotNull CaseDatabaseImpl getDatabase() {
      return this.database;
   }

   public @NotNull ConfigManagerImpl getConfigManager() {
      return this.configManager;
   }

   public @NotNull CaseLoader getCaseLoader() {
      return this.caseLoader;
   }

   public @NotNull EventBusImpl getEventBus() {
      return this.eventBus;
   }

   public @NotNull BackendPlatform getPlatform() {
      return this.platform;
   }

   @Generated
   public UpdateChecker getUpdateChecker() {
      return this.updateChecker;
   }
}
