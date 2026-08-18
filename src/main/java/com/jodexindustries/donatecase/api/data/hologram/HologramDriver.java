package com.jodexindustries.donatecase.api.data.hologram;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;

public interface HologramDriver {
   void create(CaseLocation var1, CaseData.Hologram var2);

   void remove(CaseLocation var1);

   void remove();
}
