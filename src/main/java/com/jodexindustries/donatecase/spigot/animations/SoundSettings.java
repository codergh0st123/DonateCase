package com.jodexindustries.donatecase.spigot.animations;

import org.bukkit.Sound;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SoundSettings {
   @Setting("Sound")
   private String sound = "ENTITY_ITEM_PICKUP";
   @Setting("Volume")
   public float volume = 10.0F;
   @Setting("Pitch")
   public float pitch = 1.0F;

   public Sound sound() {
      if (this.sound != null && !this.sound.isEmpty()) {
         try {
            return Sound.valueOf(this.sound);
         } catch (IllegalArgumentException var2) {
            return null;
         }
      } else {
         return null;
      }
   }
}
