package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import java.util.HashMap;
import java.util.UUID;

public class DecentHologramsImpl implements HologramDriver {
   private final HashMap<CaseLocation, Hologram> holograms = new HashMap();

   public void create(CaseLocation block, CaseData.Hologram caseHologram) {
      if (caseHologram.enabled()) {
         double height = caseHologram.height();
         Hologram hologram = DHAPI.createHologram("DonateCase-" + UUID.randomUUID(), BukkitUtils.toBukkit(block).add((double)0.5F, height, (double)0.5F));
         hologram.setDisplayRange(caseHologram.range());
         caseHologram.messages().forEach((line) -> DHAPI.addHologramLine(hologram, DCTools.rc(line)));
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
