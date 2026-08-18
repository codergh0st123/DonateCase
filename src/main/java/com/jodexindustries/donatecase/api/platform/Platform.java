package com.jodexindustries.donatecase.api.platform;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.data.casedata.MetaUpdater;
import com.jodexindustries.donatecase.api.data.storage.CaseWorld;
import com.jodexindustries.donatecase.api.scheduler.Scheduler;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.PAPI;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Platform extends Addon {
   String getName();

   String getIdentifier();

   String getVersion();

   Logger getLogger();

   DCTools getTools();

   PAPI getPAPI();

   MetaUpdater getMetaUpdater();

   DCAPI getAPI();

   @NotNull Scheduler getScheduler();

   @Nullable DCPlayer getPlayer(String var1);

   DCPlayer[] getOnlinePlayers();

   DCOfflinePlayer[] getOfflinePlayers();

   @Nullable CaseWorld getWorld(String var1);

   boolean isWorldLoaded(String var1);

   int getSpawnRadius();
}
