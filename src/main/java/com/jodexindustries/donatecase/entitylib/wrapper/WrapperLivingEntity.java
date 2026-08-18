package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.potion.PotionType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityAnimation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityAnimation.EntityAnimationType;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class WrapperLivingEntity extends WrapperEntity {
   private final WrapperEntityEquipment equipment;
   private final WrapperEntityAttributes attributes;

   public WrapperLivingEntity(int entityId, UUID uuid, EntityType entityType, EntityMeta entityMeta) {
      super(entityId, uuid, entityType, entityMeta);
      this.equipment = new WrapperEntityEquipment(this);
      this.attributes = new WrapperEntityAttributes(this);
   }

   public WrapperLivingEntity(int entityId, UUID uuid, EntityType entityType) {
      this(entityId, uuid, entityType, EntityMeta.createMeta(entityId, entityType));
   }

   public WrapperLivingEntity(int entityId, EntityType entityType) {
      this(entityId, EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public WrapperLivingEntity(UUID uuid, EntityType entityType) {
      this(EntityLib.getPlatform().getEntityIdProvider().provide(uuid, entityType), uuid, entityType);
   }

   public WrapperLivingEntity(EntityType entityType) {
      this(EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public void refresh() {
      super.refresh();
      this.equipment.refresh();
      this.attributes.refresh();
   }

   public WrapperEntityAttributes getAttributes() {
      return this.attributes;
   }

   public void addPotionEffect(PotionType type, int amplifier, int duration, byte flags, boolean hasFactorData, @Nullable NBTCompound factorData) {
      this.sendPacketToViewers(new WrapperPlayServerEntityEffect(this.getEntityId(), type, amplifier, duration, flags));
   }

   public void addPotionEffect(PotionType type, int amplifier, int duration, byte flags) {
      this.addPotionEffect(type, amplifier, duration, flags, false, (NBTCompound)null);
   }

   public void playCriticalHitAnimation() {
      this.sendAnimation(EntityAnimationType.CRITICAL_HIT);
   }

   public void playMagicCriticalHitAnimation() {
      this.sendAnimation(EntityAnimationType.MAGIC_CRITICAL_HIT);
   }

   public void playWakeupAnimation() {
      this.sendAnimation(EntityAnimationType.WAKE_UP);
   }

   public void playHurtAnimation() {
      this.sendAnimation(EntityAnimationType.HURT);
   }

   public void swingMainHand() {
      this.sendAnimation(EntityAnimationType.SWING_MAIN_ARM);
   }

   public void swingOffHand() {
      this.sendAnimation(EntityAnimationType.SWING_OFF_HAND);
   }

   public void sendAnimation(WrapperPlayServerEntityAnimation.EntityAnimationType type) {
      this.sendPacketToViewers(new WrapperPlayServerEntityAnimation(this.getEntityId(), type));
   }

   public WrapperEntityEquipment getEquipment() {
      return this.equipment;
   }
}
