package com.jodexindustries.donatecase.spigot.listener;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.event.player.ArmorStandCreatorInteractEvent;
import com.jodexindustries.donatecase.api.event.player.CaseInteractEvent;
import com.jodexindustries.donatecase.api.event.player.GuiClickEvent;
import com.jodexindustries.donatecase.api.event.player.JoinEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
   private final BukkitBackend backend;

   public EventListener(BukkitBackend backend) {
      this.backend = backend;
   }

   @EventHandler
   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
      if (event.getDamager() instanceof Firework && event.getEntity() instanceof Player && event.getDamager().hasMetadata("case")) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void onAdminJoined(PlayerJoinEvent event) {
      this.backend.getAPI().getEventBus().post((DCEvent)(new JoinEvent(BukkitUtils.fromBukkit(event.getPlayer()))));
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void InventoryClick(InventoryClickEvent e) {
      Player player = (Player)e.getWhoClicked();
      CaseGuiWrapper gui = (CaseGuiWrapper)DCAPI.getInstance().getGUIManager().getMap().get(player.getUniqueId());
      if (gui != null) {
         e.setCancelled(true);
         String itemType = gui.getCaseData().caseGui().getItemTypeBySlot(e.getRawSlot());
         if (itemType == null) {
            return;
         }

         this.backend.getAPI().getEventBus().post((DCEvent)(new GuiClickEvent(e.getRawSlot(), BukkitUtils.fromBukkit(player), gui, itemType)));
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void PlayerInteractEntity(PlayerInteractAtEntityEvent e) {
      ArmorStandCreator creator = (ArmorStandCreator)ArmorStandCreator.armorStands.get(e.getRightClicked().getEntityId());
      if (creator != null) {
         e.setCancelled(true);
         DCPlayer player = BukkitUtils.fromBukkit(e.getPlayer());
         EquipmentSlot hand = e.getHand() == org.bukkit.inventory.EquipmentSlot.HAND ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
         this.backend.getAPI().getEventBus().post((DCEvent)(new ArmorStandCreatorInteractEvent(player, creator, hand)));
      }
   }

   @EventHandler
   public void PlayerInteract(PlayerInteractEvent e) {
      if (e.getHand() != org.bukkit.inventory.EquipmentSlot.OFF_HAND) {
         Block block = e.getClickedBlock();
         if (block != null) {
            CaseInfo caseInfo = DCAPI.getInstance().getConfigManager().getCaseStorage().get(BukkitUtils.fromBukkit(block.getLocation()));
            if (caseInfo != null) {
               CaseInteractEvent.Action action = e.getAction() == Action.RIGHT_CLICK_BLOCK ? CaseInteractEvent.Action.RIGHT : CaseInteractEvent.Action.LEFT;
               e.setCancelled(true);
               DCPlayer player = BukkitUtils.fromBukkit(e.getPlayer());
               this.backend.getAPI().getEventBus().post((DCEvent)(new CaseInteractEvent(player, caseInfo, action)));
            }
         }
      }
   }

   @EventHandler
   public void InventoryClose(InventoryCloseEvent e) {
      DCAPI.getInstance().getGUIManager().getMap().remove(e.getPlayer().getUniqueId());
   }

   @EventHandler
   public void BlockBreak(BlockBreakEvent e) {
      CaseLocation location = BukkitUtils.fromBukkit(e.getBlock().getLocation());
      if (DCAPI.getInstance().getConfigManager().getCaseStorage().has(location)) {
         e.setCancelled(true);
         e.getPlayer().sendMessage(DCTools.prefix(DCAPI.getInstance().getConfigManager().getMessages().getString("case-destroy-disallow")));
      }

   }
}
