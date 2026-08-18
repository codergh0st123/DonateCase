package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FancyHologramsImpl implements HologramDriver {

    private final Map<CaseLocation, Object> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        Object plugin = OptionalPluginApi.invokeStatic(
                "de.oliver.fancyholograms.api.FancyHologramsPlugin",
                "get"
        );
        Object manager = OptionalPluginApi.invoke(plugin, "getHologramManager");
        Object data = OptionalPluginApi.create(
                "de.oliver.fancyholograms.api.data.TextHologramData",
                "DonateCase-" + UUID.randomUUID(),
                BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D)
        );

        if (manager == null || data == null) {
            return;
        }

        OptionalPluginApi.invoke(data, "setText", caseHologram.messages());
        Object hologram = OptionalPluginApi.invoke(manager, "create", data);

        if (hologram == null) {
            return;
        }

        OptionalPluginApi.invoke(manager, "addHologram", hologram);
        holograms.put(block, hologram);
    }

    @Override
    public void remove(CaseLocation block) {
        Object hologram = holograms.remove(block);
        Object plugin = OptionalPluginApi.invokeStatic(
                "de.oliver.fancyholograms.api.FancyHologramsPlugin",
                "get"
        );
        Object manager = OptionalPluginApi.invoke(plugin, "getHologramManager");
        OptionalPluginApi.invoke(manager, "removeHologram", hologram);
    }

    @Override
    public void remove() {
        for (CaseLocation block : new java.util.ArrayList<>(holograms.keySet())) {
            remove(block);
        }
    }
}
