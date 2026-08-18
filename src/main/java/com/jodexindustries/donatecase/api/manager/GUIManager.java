package com.jodexindustries.donatecase.api.manager;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface GUIManager {
   void open(@NotNull DCPlayer var1, @NotNull CaseData var2, @NotNull CaseLocation var3);

   Map<UUID, CaseGuiWrapper> getMap();
}
