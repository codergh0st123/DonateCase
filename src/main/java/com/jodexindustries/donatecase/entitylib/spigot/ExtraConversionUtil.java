package com.jodexindustries.donatecase.entitylib.spigot;

import com.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import com.github.retrooper.packetevents.protocol.player.HumanoidArm;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.google.common.collect.Multimap;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.MainHand;
import org.jetbrains.annotations.Nullable;

public final class ExtraConversionUtil {
   private static Method PLAYER_PROFILE_METHOD;
   private static Class<?> PROFILE_CLASS;
   private static Method GET_PROPERTIES_METHOD;

   private ExtraConversionUtil() {
   }

   public static @Nullable UserProfile getProfileFromBukkitPlayer(Player player) {
      try {
         Object profile = PLAYER_PROFILE_METHOD.invoke(player);
         if (PROFILE_CLASS == null) {
            PROFILE_CLASS = profile.getClass();
            GET_PROPERTIES_METHOD = PROFILE_CLASS.getMethod("getProperties");
            GET_PROPERTIES_METHOD.setAccessible(true);
         }

         Multimap properties = (Multimap)GET_PROPERTIES_METHOD.invoke(profile);
         Collection<?> textures = properties.get("textures");
         if (textures != null && !textures.isEmpty()) {
            Object texture = textures.iterator().next();
            String value = (String)texture.getClass().getDeclaredMethod("value").invoke(texture);
            String signature = (String)texture.getClass().getDeclaredMethod("signature").invoke(texture);
            ArrayList<TextureProperty> t = new ArrayList();
            t.add(new TextureProperty("textures", value, signature));
            return new UserProfile(player.getUniqueId(), player.getName(), t);
         } else {
            return new UserProfile(player.getUniqueId(), player.getName());
         }
      } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
         throw new RuntimeException(e);
      }
   }

   public static EntityPose fromBukkitPose(Pose pose) {
      return EntityPose.values()[pose.ordinal()];
   }

   public static HumanoidArm fromBukkitHand(MainHand hand) {
      return hand == MainHand.RIGHT ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
   }

   static {
      try {
         PLAYER_PROFILE_METHOD = SpigotReflectionUtil.CRAFT_PLAYER_CLASS.getMethod("getProfile");
         PLAYER_PROFILE_METHOD.setAccessible(true);
      } catch (NoSuchMethodException e) {
         throw new RuntimeException(e);
      }
   }
}
