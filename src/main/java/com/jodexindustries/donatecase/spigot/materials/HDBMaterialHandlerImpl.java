package com.jodexindustries.donatecase.spigot.materials;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.MaterialHandler;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HDBMaterialHandlerImpl implements MaterialHandler {

    @Override
    public @NotNull ItemStack handle(@NotNull String context) {
        ItemStack fallback = new ItemStack(Material.STONE);
        Object api = OptionalPluginApi.create("me.arcaniax.hdb.api.HeadDatabaseAPI");
        Object result = OptionalPluginApi.invoke(api, "getItemHead", context);

        if (result instanceof ItemStack itemStack) {
            return itemStack;
        }

        DCAPI.getInstance().getPlatform().getLogger().warning(
                "Could not find the head you were looking for: " + context
        );
        return fallback;
    }
}
