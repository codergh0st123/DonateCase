package com.jodexindustries.donatecase.spigot.materials;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.MaterialHandler;
import com.jodexindustries.donatecase.spigot.tools.OptionalPluginApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class IAMaterialHandlerImpl implements MaterialHandler {

    @Override
    public @NotNull ItemStack handle(@NotNull String context) {
        ItemStack fallback = new ItemStack(Material.STONE);
        Object stack = OptionalPluginApi.invokeStatic(
                "dev.lone.itemsadder.api.CustomStack",
                "getInstance",
                context
        );
        Object result = OptionalPluginApi.invoke(stack, "getItemStack");

        if (result instanceof ItemStack itemStack) {
            return itemStack;
        }

        DCAPI.getInstance().getPlatform().getLogger().log(
                Level.WARNING,
                "Could not find the item you were looking for by ItemsAdder support. Namespace: " + context
        );
        return fallback;
    }
}
