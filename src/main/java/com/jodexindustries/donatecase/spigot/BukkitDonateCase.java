package com.jodexindustries.donatecase.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitDonateCase extends JavaPlugin {
   private BukkitBackend backend;

   public void onLoad() {
      this.backend = new BukkitBackend(this);
   }

   public void onEnable() {
      this.backend.load();
   }

   public void onDisable() {
      this.backend.unload();
   }
}
