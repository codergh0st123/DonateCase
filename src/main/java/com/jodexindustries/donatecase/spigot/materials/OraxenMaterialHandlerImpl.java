package com.jodexindustries.donatecase.spigot.materials;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.MaterialHandler;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OraxenMaterialHandlerImpl implements MaterialHandler {

    @Override
    public @NotNull ItemStack handle(@NotNull String context) {
        ItemStack fallback = new ItemStack(Material.STONE);
        Object itemBuilder = OptionalPluginApi.invokeStatic(
                "io.th0rgal.oraxen.api.OraxenItems",
                "getItemById",
                context
        );
        Object result = OptionalPluginApi.invoke(itemBuilder, "getReferenceClone");

        if (result instanceof ItemStack itemStack) {
            return itemStack;
        }

        DCAPI.getInstance().getPlatform().getLogger().warning(
                "Could not find the item you were looking for by Oraxen support. ID: " + context
        );
        return fallback;
    }
}
