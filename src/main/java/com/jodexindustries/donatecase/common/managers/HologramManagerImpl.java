package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.storage.CaseWorld;
import com.jodexindustries.donatecase.api.manager.HologramManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;

public class HologramManagerImpl implements HologramManager {
   private HologramDriver driver;
   private final Map<String, HologramDriver> drivers = new ConcurrentHashMap();
   private final DCAPI api;

   public HologramManagerImpl(DCAPI api) {
      this.api = api;
   }

   public void register(@NotNull String name, @NotNull HologramDriver driver) {
      if (!this.drivers.containsKey(name)) {
         this.drivers.put(name, driver);
      }
   }

   public void unregister(@NotNull String name) {
      this.drivers.remove(name);
   }

   public Map<String, HologramDriver> get() {
      return this.drivers;
   }

   public void set(@NotNull String name) {
      this.driver = (HologramDriver)this.drivers.get(name);
   }

   public void load() {
      String name = this.api.getConfigManager().getConfig().hologramDriver().toLowerCase();
      this.set(name);
      if (this.driver != null) {
         this.api.getPlatform().getLogger().info("Using " + name + " as hologram driver");
         this.remove();

         for(Map.Entry<String, CaseInfo> entry : this.api.getConfigManager().getCaseStorage().get().entrySet()) {
            CaseInfo info = (CaseInfo)entry.getValue();
            String caseType = info.type();
            CaseData caseData = this.api.getCaseManager().get(caseType);
            if (caseData != null && caseData.hologram().enabled()) {
               CaseLocation location = info.location();
               CaseWorld world = location.getWorld();
               if (!this.api.getAnimationManager().isLocked(location)) {
                  if (world != null && this.api.getPlatform().isWorldLoaded(world.name())) {
                     this.create(location, caseData.hologram());
                  } else {
                     this.api.getPlatform().getLogger().warning("Hologram creation error. World is null for case name: " + (String)entry.getKey());
                  }
               }
            }
         }

      }
   }

   public void create(CaseLocation block, CaseData.Hologram caseHologram) {
      try {
         if (this.driver != null) {
            this.driver.create(block, caseHologram);
         }
      } catch (Exception e) {
         this.api.getPlatform().getLogger().log(Level.WARNING, "Error with creating hologram: ", e);
      }

   }

   public void remove(CaseLocation block) {
      if (this.driver != null) {
         this.driver.remove(block);
      }

   }

   public void remove() {
      if (this.driver != null) {
         this.driver.remove();
      }

   }
}
