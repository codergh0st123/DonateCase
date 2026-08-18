package com.jodexindustries.donatecase.spigot.api.platform;

import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.storage.CaseWorld;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class BukkitPlayer extends BukkitCommandSender implements DCPlayer {
   private final Player player;

   public BukkitPlayer(@NotNull Player player) {
      super(player);
      this.player = player;
   }

   public @NotNull Player getHandler() {
      return this.player;
   }

   public @NotNull UUID getUniqueId() {
      return this.player.getUniqueId();
   }

   public CaseWorld getWorld() {
      return BukkitUtils.fromBukkit(this.player.getWorld());
   }

   public CaseLocation getLocation() {
      return BukkitUtils.fromBukkit(this.player.getLocation());
   }

   public CaseLocation getTargetBlock(int maxDistance) {
      return BukkitUtils.fromBukkit(this.player.getTargetBlock((Set)null, maxDistance).getLocation());
   }

   public void openInventory(Object inventory) {
      this.player.openInventory((Inventory)inventory);
   }

   public void closeInventory() {
      this.player.closeInventory();
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         if (!super.equals(object)) {
            return false;
         } else {
            BukkitPlayer that = (BukkitPlayer)object;
            return Objects.equals(this.player, that.player);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{super.hashCode(), this.player});
   }
}
