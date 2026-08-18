package com.jodexindustries.donatecase.spigot.animations.select;

import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.animation.Facing;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SelectSettings {
   @Setting("Facing")
   public Facing facing;
   @Setting("Period")
   public int period;
   @Setting("Timeout")
   public int timeout;
   @Setting("Radius")
   public double radius;
   @Setting("ItemSlot")
   public EquipmentSlot itemSlot;
   @Setting("Item")
   public String item;

   public SelectSettings() {
      this.facing = Facing.SOUTH;
      this.timeout = 600;
      this.radius = (double)1.5F;
      this.itemSlot = EquipmentSlot.HEAD;
      this.item = "CHEST";
   }
}
