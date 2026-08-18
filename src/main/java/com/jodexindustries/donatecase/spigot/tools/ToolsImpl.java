package com.jodexindustries.donatecase.spigot.tools;

import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseInventory;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import com.jodexindustries.donatecase.spigot.api.armorstand.EntityArmorStandCreator;
import com.jodexindustries.donatecase.spigot.api.armorstand.PacketArmorStandCreator;
import com.jodexindustries.donatecase.spigot.api.platform.BukkitInventory;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ToolsImpl extends DCToolsBukkit {
   private final BukkitBackend backend;

   public ToolsImpl(BukkitBackend backend) {
      this.backend = backend;
   }

   public CaseInventory createInventory(int size, @Nullable String title) {
      return new BukkitInventory(size, title);
   }

   public ArmorStandCreator createArmorStand(UUID animationId, CaseLocation location) {
      return (ArmorStandCreator)(this.backend.getPacketEventsSupport() != null && this.backend.getPacketEventsSupport().isUsePackets() ? new PacketArmorStandCreator(animationId, location) : new EntityArmorStandCreator(animationId, BukkitUtils.toBukkit(location)));
   }

   public Object loadCaseItem(String id) {
      if (id == null) {
         return null;
      } else {
         Material material = Material.getMaterial(id);
         return material == null ? DCTools.getItemFromManager(id) : new ItemStack(material);
      }
   }
}
