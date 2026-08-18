package com.jodexindustries.donatecase.spigot.holograms;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.HashMap;
import java.util.UUID;
import net.Zrips.CMILib.Container.CMILocation;

public class CMIHologramsImpl implements HologramDriver {
   private final HashMap<CaseLocation, CMIHologram> holograms = new HashMap();

   public void create(CaseLocation block, CaseData.Hologram caseHologram) {
      if (caseHologram.enabled()) {
         double height = caseHologram.height();
         CMILocation location = new CMILocation(BukkitUtils.toBukkit(block).add((double)0.5F, height, (double)0.5F));
         CMIHologram hologram = new CMIHologram("DonateCase-" + UUID.randomUUID(), location);
         hologram.setLines(caseHologram.messages());
         hologram.setShowRange(caseHologram.range());
         CMI.getInstance().getHologramManager().addHologram(hologram);
         hologram.update();
         this.holograms.put(block, hologram);
      }
   }

   public void remove(CaseLocation block) {
      if (this.holograms.containsKey(block)) {
         CMIHologram hologram = (CMIHologram)this.holograms.get(block);
         this.holograms.remove(block);
         hologram.remove();
      }
   }

   public void remove() {
      this.holograms.values().forEach(CMIHologram::remove);
      this.holograms.clear();
   }
}
