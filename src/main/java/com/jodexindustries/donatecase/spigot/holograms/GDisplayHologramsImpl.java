package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GDisplayHologramsImpl implements HologramDriver {

    private final Map<CaseLocation, UUID> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("GDisplayHologram");

        if (plugin == null || !plugin.isEnabled()) {
            return;
        }

        String type = caseHologram.node().node("GDISPLAY_TYPE").getString();

        if (type == null || type.isBlank()) {
            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "Для голограммы кейса не указан GDISPLAY_TYPE."
            );
            return;
        }

        Object configuration = OptionalPluginApi.invoke(plugin, "getHologramConfiguration");
        Object result = OptionalPluginApi.invoke(configuration, "find", type);

        if (!(result instanceof Optional<?> definition) || definition.isEmpty()) {
            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "Тип GDisplayHologram " + type + " для кейса не найден в config.yml."
            );
            return;
        }

        UUID hologramId = UUID.randomUUID();
        Object created = OptionalPluginApi.invoke(
                plugin,
                "createHologram",
                hologramId,
                definition.get(),
                BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D)
        );

        if (Boolean.TRUE.equals(created)) {
            holograms.put(block, hologramId);
        }
    }

    @Override
    public void remove(CaseLocation block) {
        UUID hologramId = holograms.remove(block);

        if (hologramId == null) {
            return;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("GDisplayHologram");

        if (plugin != null && plugin.isEnabled()) {
            OptionalPluginApi.invoke(plugin, "removeHologram", hologramId);
        }
    }

    @Override
    public void remove() {
        for (CaseLocation block : new java.util.ArrayList<>(holograms.keySet())) {
            remove(block);
        }
    }
}
