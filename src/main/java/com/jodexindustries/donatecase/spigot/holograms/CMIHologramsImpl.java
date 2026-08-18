package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CMIHologramsImpl implements HologramDriver {

    private final Map<CaseLocation, Object> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        Object location = OptionalPluginApi.create(
                "net.Zrips.CMILib.Container.CMILocation",
                BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D)
        );
        Object hologram = OptionalPluginApi.create(
                "com.Zrips.CMI.Modules.Holograms.CMIHologram",
                "DonateCase-" + UUID.randomUUID(),
                location
        );

        if (hologram == null) {
            return;
        }

        OptionalPluginApi.invoke(hologram, "setLines", caseHologram.messages());
        OptionalPluginApi.invoke(hologram, "setShowRange", caseHologram.range());
        Object cmi = OptionalPluginApi.invokeStatic("com.Zrips.CMI.CMI", "getInstance");
        Object manager = OptionalPluginApi.invoke(cmi, "getHologramManager");
        OptionalPluginApi.invoke(manager, "addHologram", hologram);
        OptionalPluginApi.invoke(hologram, "update");
        holograms.put(block, hologram);
    }

    @Override
    public void remove(CaseLocation block) {
        Object hologram = holograms.remove(block);
        OptionalPluginApi.invoke(hologram, "remove");
    }

    @Override
    public void remove() {
        holograms.values().forEach(hologram -> OptionalPluginApi.invoke(hologram, "remove"));
        holograms.clear();
    }
}
