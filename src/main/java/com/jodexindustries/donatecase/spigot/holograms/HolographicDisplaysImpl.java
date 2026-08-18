package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;

import java.util.HashMap;
import java.util.Map;

public class HolographicDisplaysImpl implements HologramDriver {

    private final Map<CaseLocation, Object> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        BukkitBackend backend = (BukkitBackend) DCAPI.getInstance().getPlatform();
        Object api = OptionalPluginApi.invokeStatic(
                "me.filoghost.holographicdisplays.api.HolographicDisplaysAPI",
                "get",
                backend.getPlugin()
        );
        Object hologram = OptionalPluginApi.invoke(
                api,
                "createHologram",
                BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D)
        );

        if (hologram == null) {
            return;
        }

        Object lines = OptionalPluginApi.invoke(hologram, "getLines");

        for (String message : caseHologram.messages()) {
            OptionalPluginApi.invoke(lines, "appendText", DCTools.rc(message));
        }

        holograms.put(block, hologram);
    }

    @Override
    public void remove(CaseLocation block) {
        Object hologram = holograms.remove(block);
        OptionalPluginApi.invoke(hologram, "delete");
    }

    @Override
    public void remove() {
        holograms.values().forEach(hologram -> OptionalPluginApi.invoke(hologram, "delete"));
        holograms.clear();
    }
}
