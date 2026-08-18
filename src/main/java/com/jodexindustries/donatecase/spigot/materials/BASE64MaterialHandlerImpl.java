package com.jodexindustries.donatecase.spigot.materials;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.MaterialHandler;
import com.jodexindustries.donatecase.spigot.tools.SkullCreator;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BASE64MaterialHandlerImpl implements MaterialHandler {
   public @NotNull ItemStack handle(@NotNull String context) {
      try {
         return SkullCreator.itemFromBase64(context);
      } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
         DCAPI.getInstance().getPlatform().getLogger().log(Level.WARNING, "Error with handling item: " + context, e);
         return new ItemStack(Material.AIR);
      }
   }
}
