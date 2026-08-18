package com.jodexindustries.donatecase.spigot.api.armorstand;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.util.Vector3f;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandEulerAngle;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.entitylib.meta.other.ArmorStandMeta;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperLivingEntity;
import io.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PacketArmorStandCreator implements ArmorStandCreator {
   private CaseLocation location;
   private final UUID animationId;
   private final WrapperLivingEntity entity;
   private final ArmorStandMeta meta;

   public PacketArmorStandCreator(UUID animationId, CaseLocation location) {
      this.animationId = animationId;
      this.entity = new WrapperLivingEntity(EntityTypes.ARMOR_STAND);
      this.entity.getEquipment().setNotifyChanges(true);

      for(Player p : Bukkit.getOnlinePlayers()) {
         this.entity.addViewer(p.getUniqueId());
      }

      this.meta = (ArmorStandMeta)this.entity.getEntityMeta();
      this.location = location;
      ArmorStandCreator.armorStands.put(this.entity.getEntityId(), this);
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         PacketArmorStandCreator that = (PacketArmorStandCreator)object;
         return this.getEntityId() == that.getEntityId();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.getEntityId());
   }

   public void setEquipment(EquipmentSlot equipmentSlot, Object item) {
      ItemStack itemStack = SpigotReflectionUtil.decodeBukkitItemStack((org.bukkit.inventory.ItemStack)item);
      switch (equipmentSlot) {
         case LEGS:
            this.entity.getEquipment().setLeggings(itemStack);
            break;
         case FEET:
            this.entity.getEquipment().setBoots(itemStack);
            break;
         case OFF_HAND:
            this.entity.getEquipment().setOffhand(itemStack);
            break;
         case CHEST:
            this.entity.getEquipment().setChestplate(itemStack);
            break;
         case HAND:
            this.entity.getEquipment().setMainHand(itemStack);
            break;
         case HEAD:
            this.entity.getEquipment().setHelmet(itemStack);
      }

   }

   public void setAngle(@NotNull ArmorStandEulerAngle angle) {
      this.meta.setHeadRotation(new Vector3f((float)angle.getHead().getX(), (float)angle.getHead().getY(), (float)angle.getHead().getZ()));
      this.meta.setLeftArmRotation(new Vector3f((float)angle.getLeftArm().getX(), (float)angle.getLeftArm().getY(), (float)angle.getLeftArm().getZ()));
      this.meta.setRightArmRotation(new Vector3f((float)angle.getRightArm().getX(), (float)angle.getRightArm().getY(), (float)angle.getRightArm().getZ()));
      this.meta.setBodyRotation(new Vector3f((float)angle.getBody().getX(), (float)angle.getBody().getY(), (float)angle.getBody().getZ()));
      this.meta.setLeftLegRotation(new Vector3f((float)angle.getLeftLeg().getX(), (float)angle.getLeftLeg().getY(), (float)angle.getLeftLeg().getZ()));
      this.meta.setRightLegRotation(new Vector3f((float)angle.getRightLeg().getX(), (float)angle.getRightLeg().getY(), (float)angle.getRightLeg().getZ()));
   }

   public void setRotation(float yaw, float pitch) {
      this.entity.rotateHead(yaw, pitch);
   }

   public void setVisible(boolean isVisible) {
      this.meta.setInvisible(!isVisible);
   }

   public void setCustomName(String displayName) {
      if (displayName != null) {
         this.meta.setIndex((byte)2, EntityDataTypes.OPTIONAL_ADV_COMPONENT, Optional.of(LegacyComponentSerializer.legacySection().deserialize(DCTools.rc(displayName))));
      }

   }

   public void setGravity(boolean isGravity) {
      this.meta.setIndex((byte)5, EntityDataTypes.BOOLEAN, isGravity);
   }

   public void setSmall(boolean small) {
      this.meta.setSmall(small);
   }

   public void setMarker(boolean marker) {
      this.meta.setMarker(marker);
   }

   public void setGlowing(boolean glowing) {
      this.meta.setGlowing(glowing);
   }

   public boolean isGlowing() {
      return this.meta.isGlowing();
   }

   public void setCollidable(boolean collidable) {
   }

   public void setCustomNameVisible(boolean flag) {
      this.meta.setIndex((byte)3, EntityDataTypes.BOOLEAN, flag);
   }

   public boolean isCustomNameVisible() {
      return (Boolean)this.meta.getIndex((byte)3, false);
   }

   public CaseLocation getLocation() {
      return this.location;
   }

   public @NotNull UUID getUniqueId() {
      return this.entity.getUuid();
   }

   public UUID getAnimationId() {
      return this.animationId;
   }

   public int getEntityId() {
      return this.entity.getEntityId();
   }

   public void teleport(CaseLocation location) {
      this.entity.teleport(fromBukkitLocation(location));
      this.location = location;
   }

   public void remove() {
      ArmorStandCreator.armorStands.remove(this.entity.getEntityId());
      this.entity.remove();
   }

   public void spawn() {
      this.entity.spawn(fromBukkitLocation(this.location));
   }

   public void updateMeta() {
      this.entity.sendPacketToViewers(this.meta.createPacket());
   }

   public static Location fromBukkitLocation(CaseLocation location) {
      return new Location(location.x(), location.y(), location.z(), location.yaw(), location.pitch());
   }
}
