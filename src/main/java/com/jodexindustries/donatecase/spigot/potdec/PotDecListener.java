package com.jodexindustries.donatecase.spigot.potdec;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PotDecListener implements Listener {
   private final JavaPlugin plugin;
   private final PotDecManager manager;

   public PotDecListener(JavaPlugin plugin, PotDecManager manager) {
      this.plugin = plugin;
      this.manager = manager;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
         return;
      }

      if (!this.manager.isPot(event.getClickedBlock())) {
         return;
      }

      event.setCancelled(true);
      if (this.manager.discover(event.getClickedBlock(), event.getPlayer().getUniqueId())) {
         this.showGenie(event.getPlayer(), event.getClickedBlock().getLocation());
      }
   }

   private void showGenie(Player player, Location potLocation) {
      Location start = potLocation.clone().add(0.5D, 0.35D, 0.5D);
      ArmorStand hologram = player.getWorld().spawn(start, ArmorStand.class, stand -> {
         stand.setVisible(false);
         stand.setMarker(true);
         stand.setGravity(false);
         stand.setInvulnerable(true);
         stand.setCollidable(false);
         stand.setCustomName("§d§lДжин");
         stand.setCustomNameVisible(true);
         stand.setVisibleByDefault(false);
      });

      for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
            onlinePlayer.hideEntity(this.plugin, hologram);
         }
      }

      player.showEntity(this.plugin, hologram);
      new BukkitRunnable() {
         private int tick;

         public void run() {
            if (!player.isOnline() || hologram.isDead() || this.tick >= 65) {
               hologram.remove();
               this.cancel();
               return;
            }

            double offset;
            if (this.tick < 20) {
               offset = 0.06D;
            } else if (this.tick < 45) {
               offset = 0.0D;
            } else {
               offset = -0.06D;
            }

            if (this.tick == 20) {
               hologram.setCustomName("§eТы нашёл подсказку джина");
            }

            if (offset != 0.0D) {
               hologram.teleport(hologram.getLocation().add(0.0D, offset, 0.0D));
            }

            player.spawnParticle(Particle.END_ROD, hologram.getLocation().add(0.0D, 0.25D, 0.0D), 3, 0.12D, 0.12D, 0.12D, 0.01D);
            player.spawnParticle(Particle.ENCHANT, hologram.getLocation(), 5, 0.15D, 0.15D, 0.15D, 0.15D);
            ++this.tick;
         }
      }.runTaskTimer(this.plugin, 0L, 1L);
   }
}
