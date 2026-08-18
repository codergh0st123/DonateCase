package com.jodexindustries.donatecase.spigot.animations.wheel;

import com.jodexindustries.donatecase.api.armorstand.ArmorStandEulerAngle;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.animation.Facing;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.animations.SoundSettings;
import org.bukkit.Particle;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class WheelSettings {
   @Setting("StartPosition")
   public CaseLocation startPosition;
   @Setting("CircleRadius")
   public double radius;
   @Setting("Scroll")
   public Scroll scroll;
   @Setting("Flame")
   public Flame flame;
   @Setting("ItemsCount")
   public int itemsCount;
   @Setting("ItemSlot")
   public EquipmentSlot itemSlot;
   @Setting("Pose")
   public ArmorStandEulerAngle armorStandEulerAngle;
   @Setting("Type")
   public WheelType wheelType;
   @Setting("Shuffle")
   public boolean shuffle;
   @Setting("SmallArmorStand")
   public boolean smallArmorStand;
   @Setting("Facing")
   public @Nullable Facing facing;

   public WheelSettings() {
      this.itemSlot = EquipmentSlot.HEAD;
      this.wheelType = WheelSettings.WheelType.RANDOM;
      this.shuffle = true;
      this.smallArmorStand = true;
   }

   public static enum WheelType {
      FULL,
      RANDOM;

      // $FF: synthetic method
      private static WheelType[] $values() {
         return new WheelType[]{FULL, RANDOM};
      }
   }

   @ConfigSerializable
   public static class Scroll extends SoundSettings {
      @Setting("Time")
      public int time = 100;
      @Setting("Count")
      public int count = 1;
      @Setting("EaseAmount")
      public double easeAmount = (double)2.5F;
   }

   @ConfigSerializable
   public static class Flame {
      @Setting("Enabled")
      public boolean enabled;
      @Setting("Particle")
      public Particle particle;

      public Flame() {
         this.particle = Particle.FLAME;
      }
   }
}
