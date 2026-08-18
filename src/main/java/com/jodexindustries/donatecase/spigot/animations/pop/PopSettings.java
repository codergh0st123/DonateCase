package com.jodexindustries.donatecase.spigot.animations.pop;

import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.animation.Facing;
import com.jodexindustries.donatecase.spigot.animations.SoundSettings;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class PopSettings {
   @Setting("Scroll")
   public Scroll scroll = new Scroll();
   @Setting("Facing")
   public Facing facing;
   @Setting("Rounded")
   public boolean rounded;
   @Setting("Radius")
   public double radius;
   @Setting("Period")
   public int period;
   @Setting("ItemSlot")
   public EquipmentSlot itemSlot;

   public PopSettings() {
      this.facing = Facing.SOUTH;
      this.rounded = true;
      this.radius = (double)1.5F;
      this.itemSlot = EquipmentSlot.HEAD;
   }

   @ConfigSerializable
   public static class Scroll extends SoundSettings {
   }
}
