package com.jodexindustries.donatecase.spigot;

import com.jodexindustries.donatecase.api.data.casedata.MetaUpdater;
import com.jodexindustries.donatecase.spigot.tools.DCToolsBukkit;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BukkitMetaUpdater implements MetaUpdater {
   public void updateMeta(Object itemStack, String displayName, List<String> lore, int modelData, boolean enchanted, String[] rgb) {
      if (itemStack instanceof ItemStack) {
         ItemStack item = (ItemStack)itemStack;
         ItemMeta meta = item.getItemMeta();
         if (meta != null) {
            if (displayName != null) {
               meta.setDisplayName(displayName);
            }

            if (lore != null) {
               meta.setLore(lore);
            }

            if (modelData != -1) {
               meta.setCustomModelData(modelData);
            }

            if (enchanted) {
               meta.addEnchant(Enchantment.LURE, 1, true);
            }

            if (rgb != null && rgb.length >= 3 && meta instanceof LeatherArmorMeta) {
               LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)meta;
               leatherArmorMeta.setColor(DCToolsBukkit.fromRGBString(rgb, Color.WHITE));
            }

            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_DYE});

            try {
               meta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf("HIDE_POTION_EFFECTS")});
            } catch (IllegalArgumentException var10) {
            }

            item.setItemMeta(meta);
         }
      }

   }
}
