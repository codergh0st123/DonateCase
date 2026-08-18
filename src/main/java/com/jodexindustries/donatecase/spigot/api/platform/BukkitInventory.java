package com.jodexindustries.donatecase.spigot.api.platform;

import com.jodexindustries.donatecase.api.data.casedata.gui.CaseInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BukkitInventory implements CaseInventory {
   private final Inventory inventory;

   public BukkitInventory(int size, String title) {
      String safeTitle = title != null ? title : "";
      this.inventory = Bukkit.createInventory((InventoryHolder)null, size, safeTitle);
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   public void setItem(int index, @Nullable Object item) {
      this.inventory.setItem(index, (ItemStack)item);
   }
}
