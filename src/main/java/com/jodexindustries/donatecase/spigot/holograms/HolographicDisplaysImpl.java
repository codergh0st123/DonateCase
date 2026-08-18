package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.HashMap;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.PlaceholderSetting;
import org.jetbrains.annotations.NotNull;

public class HolographicDisplaysImpl implements HologramDriver {
   private final @NotNull HolographicDisplaysAPI api = HolographicDisplaysAPI.get(((BukkitBackend)DCAPI.getInstance().getPlatform()).getPlugin());
   private final HashMap<CaseLocation, Hologram> holograms = new HashMap();

   public void create(CaseLocation block, CaseData.Hologram caseHologram) {
      if (caseHologram.enabled()) {
         double height = caseHologram.height();
         Hologram hologram = this.api.createHologram(BukkitUtils.toBukkit(block).add((double)0.5F, height, (double)0.5F));
         hologram.setPlaceholderSetting(PlaceholderSetting.DEFAULT);
         caseHologram.messages().forEach((line) -> hologram.getLines().appendText(DCTools.rc(line)));
         this.holograms.put(block, hologram);
      }
   }

   public void remove(CaseLocation block) {
      if (this.holograms.containsKey(block)) {
         Hologram hologram = (Hologram)this.holograms.get(block);
         this.holograms.remove(block);
         hologram.delete();
      }
   }

   public void remove() {
      this.holograms.values().forEach(Hologram::delete);
      this.holograms.clear();
   }
}
