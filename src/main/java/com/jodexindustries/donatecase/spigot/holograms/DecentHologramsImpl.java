package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DecentHologramsImpl implements HologramDriver {

    private final Map<CaseLocation, Object> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        Object hologram = OptionalPluginApi.invokeStatic(
                "eu.decentsoftware.holograms.api.DHAPI",
                "createHologram",
                "DonateCase-" + UUID.randomUUID(),
                BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D)
        );

        if (hologram == null) {
            return;
        }

        OptionalPluginApi.invoke(hologram, "setDisplayRange", caseHologram.range());

        for (String message : caseHologram.messages()) {
            OptionalPluginApi.invokeStatic(
                    "eu.decentsoftware.holograms.api.DHAPI",
                    "addHologramLine",
                    hologram,
                    DCTools.rc(message)
            );
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
