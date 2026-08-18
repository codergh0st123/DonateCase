package com.jodexindustries.donatecase.spigot.tools;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.EulerAngle;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.storage.CaseWorld;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.platform.Platform;
import com.jodexindustries.donatecase.spigot.api.platform.BukkitCommandSender;
import com.jodexindustries.donatecase.spigot.api.platform.BukkitPlayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitUtils {
   public static CaseWorld fromBukkit(World world) {
      if (world == null) {
         return null;
      } else {
         CaseLocation spawnLocation = fromBukkit(world.getSpawnLocation());
         CaseWorld caseWorld = new CaseWorld(world.getName());
         caseWorld.spawnLocation(spawnLocation);
         return caseWorld;
      }
   }

   public static @NotNull CaseLocation fromBukkit(@NotNull Location location) {
      return new CaseLocation(location.getWorld() != null ? location.getWorld().getName() : null, location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
   }

   public static @NotNull EulerAngle fromBukkit(org.bukkit.util.@NotNull EulerAngle eulerAngle) {
      return new EulerAngle(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ());
   }

   public static @NotNull DCPlayer fromBukkit(@NotNull Player player) {
      return new BukkitPlayer(player);
   }

   public static @NotNull DCCommandSender fromBukkit(@NotNull CommandSender sender) {
      return new BukkitCommandSender(sender);
   }

   public static @NotNull Location toBukkit(@NotNull CaseLocation location) {
      CaseWorld world = location.getWorld();
      World bukkitWorld = null;
      if (world != null) {
         bukkitWorld = Bukkit.getWorld(world.name());
      }

      return new Location(bukkitWorld, location.x(), location.y(), location.z(), location.yaw(), location.pitch());
   }

   public static org.bukkit.util.@NotNull EulerAngle toBukkit(@NotNull EulerAngle eulerAngle) {
      return new org.bukkit.util.EulerAngle(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ());
   }

   public static @NotNull Player toBukkit(@NotNull DCPlayer player) {
      return (Player)player.getHandler();
   }

   public static @NotNull CommandSender toBukkit(@NotNull DCCommandSender sender) {
      return (CommandSender)sender.getHandler();
   }

   public static Plugin getDonateCase() {
      try {
         Platform platform = DCAPI.getInstance().getPlatform();
         Method method = platform.getClass().getDeclaredMethod("getPlugin");
         return (Plugin)method.invoke(platform);
      } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }
}
