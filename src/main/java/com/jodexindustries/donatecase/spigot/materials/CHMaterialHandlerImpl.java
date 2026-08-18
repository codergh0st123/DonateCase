package com.jodexindustries.donatecase.spigot.materials;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.MaterialHandler;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CHMaterialHandlerImpl implements MaterialHandler {

    @Override
    public @NotNull ItemStack handle(@NotNull String context) {
        ItemStack fallback = new ItemStack(Material.STONE);
        String[] arguments = context.split(":", 2);

        if (arguments.length != 2) {
            return fallback;
        }

        try {
            int id = Integer.parseInt(arguments[1]);
            Object api = OptionalPluginApi.invokeStatic("de.likewhat.customheads.CustomHeads", "getApi");
            Object result = OptionalPluginApi.invoke(api, "getHead", arguments[0], id);

            if (result instanceof ItemStack itemStack) {
                return itemStack;
            }
        } catch (NumberFormatException ignored) {
            // Некорректный идентификатор обрабатывается тем же сообщением, что и отсутствующая голова.
        }

        DCAPI.getInstance().getPlatform().getLogger().warning(
                "Could not find the head you were looking for by CustomHeads support. Category: " + arguments[0]
        );
        return fallback;
    }
}
