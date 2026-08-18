package com.jodexindustries.donatecase.spigot.holograms;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.hologram.HologramDriver;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class GDisplayHologramsImpl implements HologramDriver {

    private final Map<CaseLocation, UUID> holograms = new HashMap<>();

    @Override
    public void create(CaseLocation block, CaseData.Hologram caseHologram) {
        if (!caseHologram.enabled()) {
            return;
        }

        List<String> messages = caseHologram.messages();

        if (messages == null || messages.isEmpty()) {
            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "Голограмма кейса не создана: Hologram.Message не содержит строк."
            );
            return;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("GDisplayHologram");

        if (plugin == null || !plugin.isEnabled()) {
            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "Голограмма кейса не создана: GDisplayHologram не загружен."
            );
            return;
        }

        UUID hologramId = UUID.randomUUID();
        Location location = BukkitUtils.toBukkit(block).add(0.5D, caseHologram.height(), 0.5D);

        try {
            Method method = plugin.getClass().getMethod(
                    "createDonateCaseHologram",
                    UUID.class,
                    List.class,
                    int.class,
                    Location.class
            );
            Object result = method.invoke(plugin, hologramId, messages, caseHologram.range(), location);

            if (Boolean.TRUE.equals(result)) {
                holograms.put(block, hologramId);
                return;
            }

            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "GDisplayHologram отказался создавать голограмму для кейса " + block + "."
            );
        } catch (NoSuchMethodException exception) {
            DCAPI.getInstance().getPlatform().getLogger().warning(
                    "Установлена старая версия GDisplayHologram. Обновите оба плагина одной сборкой."
            );
        } catch (IllegalAccessException | InvocationTargetException exception) {
            Throwable cause = exception instanceof InvocationTargetException invocation
                    ? invocation.getCause()
                    : exception;
            DCAPI.getInstance().getPlatform().getLogger().log(
                    Level.WARNING,
                    "Не удалось создать GDisplayHologram для кейса " + block + ".",
                    cause
            );
        }
    }

    @Override
    public void remove(CaseLocation block) {
        UUID hologramId = holograms.remove(block);

        if (hologramId == null) {
            return;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("GDisplayHologram");

        if (plugin == null || !plugin.isEnabled()) {
            return;
        }

        try {
            Method method = plugin.getClass().getMethod("removeHologram", UUID.class);
            method.invoke(plugin, hologramId);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            DCAPI.getInstance().getPlatform().getLogger().log(
                    Level.WARNING,
                    "Не удалось удалить GDisplayHologram для кейса " + block + ".",
                    exception
            );
        }
    }

    @Override
    public void remove() {
        for (CaseLocation block : List.copyOf(holograms.keySet())) {
            remove(block);
        }
    }
}
