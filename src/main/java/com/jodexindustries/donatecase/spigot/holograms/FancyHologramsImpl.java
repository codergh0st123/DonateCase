package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.serializer.ConfigurationSectionImpl;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancyholograms.api.hologram.HologramType;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

public class FancyHologramsImpl implements HologramDriver {
   private final HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
   private final HashMap<CaseLocation, Hologram> holograms = new HashMap();

   public void create(CaseLocation block, CaseData.Hologram caseHologram) {
      ConfigurationNode node = caseHologram.node();
      HologramType type = HologramType.getByName(node.node(new Object[]{"type"}).getString());
      if (type != null) {
         Location location = BukkitUtils.toBukkit(block).add((double)0.5F, caseHologram.height(), (double)0.5F);
         String name = "DonateCase-" + UUID.randomUUID();
         DisplayHologramData hologramData = getData(type, name, location);
         hologramData.read(new ConfigurationSectionImpl(node), name);
         Location tempLocation = hologramData.getLocation();
         if (tempLocation.getYaw() != 0.0F) {
            location.setYaw(tempLocation.getYaw());
         }

         if (tempLocation.getPitch() != 0.0F) {
            location.setPitch(tempLocation.getPitch());
         }

         hologramData.setLocation(location);
         Hologram hologram = this.manager.create(hologramData);
         this.holograms.put(block, hologram);
         this.manager.addHologram(hologram);
      }
   }

   public void remove(CaseLocation block) {
      Hologram hologram = (Hologram)this.holograms.get(block);
      if (hologram != null) {
         this.holograms.remove(block);
         this.manager.removeHologram(hologram);
      }
   }

   public void remove() {
      for(Hologram hologram : this.holograms.values()) {
         this.manager.removeHologram(hologram);
      }

      this.holograms.clear();
   }

   private static @NotNull DisplayHologramData getData(HologramType type, String name, Location location) {
      DisplayHologramData hologramData;
      switch (type) {
         case BLOCK:
            hologramData = new BlockHologramData(name, location);
            break;
         case ITEM:
            hologramData = new ItemHologramData(name, location);
            break;
         case TEXT:
            hologramData = new TextHologramData(name, location);
            break;
         default:
            hologramData = new DisplayHologramData(name, type, location);
      }

      return hologramData;
   }
}
