package com.jodexindustries.donatecase.spigot.api.armorstand;

import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandEulerAngle;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class EntityArmorStandCreator implements ArmorStandCreator {
   private final UUID animationId;
   private final ArmorStand entity;

   public EntityArmorStandCreator(UUID animationId, Location location) {
      this.animationId = animationId;
      World world = location.getWorld();
      if (world == null) {
         this.entity = null;
      } else {
         this.entity = (ArmorStand)world.spawn(location, ArmorStand.class);
         this.entity.setMetadata("case", new FixedMetadataValue(BukkitUtils.getDonateCase(), "case"));
         ArmorStandCreator.armorStands.put(this.entity.getEntityId(), this);
      }
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         EntityArmorStandCreator that = (EntityArmorStandCreator)object;
         return Objects.equals(this.entity, that.entity);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.entity);
   }

   public void setVisible(boolean isVisible) {
      this.entity.setVisible(isVisible);
   }

   public void setSmall(boolean small) {
      this.entity.setSmall(small);
   }

   public void setMarker(boolean marker) {
      this.entity.setMarker(marker);
   }

   public void setGlowing(boolean glowing) {
      this.entity.setGlowing(glowing);
   }

   public boolean isGlowing() {
      return this.entity.isGlowing();
   }

   public void setCollidable(boolean collidable) {
      this.entity.setCollidable(collidable);
   }

   public void setCustomNameVisible(boolean flag) {
      this.entity.setCustomNameVisible(flag);
   }

   public boolean isCustomNameVisible() {
      return this.entity.isCustomNameVisible();
   }

   public void setCustomName(String displayName) {
      this.entity.setCustomName(displayName);
   }

   public void teleport(CaseLocation location) {
      this.entity.teleport(BukkitUtils.toBukkit(location));
   }

   public void setEquipment(EquipmentSlot equipmentSlot, Object item) {
      EntityEquipment equipment = this.entity.getEquipment();
      if (equipment != null) {
         equipment.setItem(org.bukkit.inventory.EquipmentSlot.valueOf(equipmentSlot.name()), (ItemStack)item);
      }

   }

   public void setAngle(@NotNull ArmorStandEulerAngle angle) {
      this.entity.setHeadPose(BukkitUtils.toBukkit(angle.getHead()));
      this.entity.setBodyPose(BukkitUtils.toBukkit(angle.getBody()));
      this.entity.setLeftArmPose(BukkitUtils.toBukkit(angle.getLeftArm()));
      this.entity.setRightArmPose(BukkitUtils.toBukkit(angle.getRightArm()));
      this.entity.setLeftLegPose(BukkitUtils.toBukkit(angle.getLeftLeg()));
      this.entity.setRightLegPose(BukkitUtils.toBukkit(angle.getRightLeg()));
   }

   public void setRotation(float yaw, float pitch) {
      this.entity.setRotation(yaw, pitch);
   }

   public CaseLocation getLocation() {
      return BukkitUtils.fromBukkit(this.entity.getLocation());
   }

   public @NotNull UUID getUniqueId() {
      return this.entity.getUniqueId();
   }

   public UUID getAnimationId() {
      return this.animationId;
   }

   public int getEntityId() {
      return this.entity.getEntityId();
   }

   public void setGravity(boolean hasGravity) {
      this.entity.setGravity(hasGravity);
   }

   public void remove() {
      ArmorStandCreator.armorStands.remove(this.entity.getEntityId());
      this.entity.remove();
   }
}
