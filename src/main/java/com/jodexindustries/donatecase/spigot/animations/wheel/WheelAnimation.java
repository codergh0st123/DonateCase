package com.jodexindustries.donatecase.spigot.animations.wheel;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataMaterial;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.storage.CaseVector;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.spongepowered.configurate.serialize.SerializationException;

public class WheelAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private final List<ArmorStandCreator> armorStands = new ArrayList();
   private WheelSettings settings;

   public void start() {
      try {
         this.settings = (WheelSettings)this.getSettings().get(WheelSettings.class);
      } catch (SerializationException e) {
         throw new RuntimeException("Error with parsing animation settings", e);
      }

      api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)(new Task()), 0L, 0L);
   }

   private ArmorStandCreator spawnArmorStand(CaseLocation location, CaseDataItem item, boolean small) {
      CaseDataMaterial material = item.material();
      ArmorStandCreator as = api.getPlatform().getTools().createArmorStand(this.getUuid(), location);
      as.setSmall(small);
      as.setVisible(false);
      as.setGravity(false);
      if (this.settings.armorStandEulerAngle != null) {
         as.setAngle(this.settings.armorStandEulerAngle);
      }

      as.setCustomName(api.getPlatform().getPAPI().setPlaceholders((Object)this.getPlayer(), item.material().displayName()));
      as.setCustomNameVisible(item.material().displayName() != null && !item.material().displayName().isEmpty());
      as.spawn();
      as.setEquipment(this.settings.itemSlot, material.itemStack());
      return as;
   }

   private class Task implements Consumer<SchedulerTask> {
      private final CaseLocation location = WheelAnimation.this.getLocation().clone();
      private final Location bukkitLocation;
      private final World world;
      private final double baseAngle;
      private double lastCompletedRotation = (double)0.0F;
      private int ticks;
      private double targetAngle;
      private final double rotationThreshold;
      private final double offset;

      public Task() {
         float pitch = WheelAnimation.this.settings.facing == null ? (float)Math.round(this.location.pitch() / 45.0F) * 45.0F : WheelAnimation.this.settings.facing.pitch;
         float yaw = WheelAnimation.this.settings.facing == null ? (float)Math.round(this.location.yaw() / 45.0F) * 45.0F : WheelAnimation.this.settings.facing.yaw;
         if (WheelAnimation.this.settings.startPosition != null) {
            this.location.add(WheelAnimation.this.settings.startPosition);
         }

         this.location.pitch(pitch);
         this.location.yaw(yaw);
         this.baseAngle = (double)this.location.clone().getDirection().angle(new CaseVector(0, 0, 1));
         this.initializeItems();
         this.rotationThreshold = Math.PI / (double)WheelAnimation.this.armorStands.size();
         this.offset = (double)2.0F * this.rotationThreshold;
         this.world = WheelAnimation.this.getPlayer().getWorld();
         this.bukkitLocation = BukkitUtils.toBukkit(this.location);
      }

      public void accept(SchedulerTask task) {
         ++this.ticks;
         double progress = Math.min((double)this.ticks / (double)WheelAnimation.this.settings.scroll.time, (double)1.0F);
         double easedProgress = (double)1.0F - Math.pow((double)1.0F - progress, WheelAnimation.this.settings.scroll.easeAmount);
         double currentAngle = easedProgress * this.targetAngle;
         if (this.ticks <= WheelAnimation.this.settings.scroll.time) {
            this.handleFlameEffects();
            this.moveArmorStands(currentAngle);
         }

         if (this.ticks == WheelAnimation.this.settings.scroll.time) {
            WheelAnimation.this.preEnd();
         }

         if (this.ticks >= WheelAnimation.this.settings.scroll.time + 20) {
            this.endAnimation(task);
         }

      }

      private void initializeItems() {
         if (WheelAnimation.this.settings.wheelType == WheelSettings.WheelType.FULL) {
            List<CaseDataItem> uniqueItems = new ArrayList(WheelAnimation.this.getCaseData().items().values());
            if (WheelAnimation.this.settings.shuffle) {
               Collections.shuffle(uniqueItems);
            }

            int additionalSteps = 0;

            for(CaseDataItem uniqueItem : uniqueItems) {
               if (uniqueItem.getName().equals(WheelAnimation.this.getWinItem().getName())) {
                  additionalSteps = uniqueItems.size() - WheelAnimation.this.armorStands.size();
                  WheelAnimation.this.armorStands.add(WheelAnimation.this.spawnArmorStand(this.location, WheelAnimation.this.getWinItem(), WheelAnimation.this.settings.smallArmorStand));
               } else {
                  WheelAnimation.this.armorStands.add(WheelAnimation.this.spawnArmorStand(this.location, uniqueItem, WheelAnimation.this.settings.smallArmorStand));
               }
            }

            double additionalAngle = (double)additionalSteps * ((Math.PI * 2D) / (double)WheelAnimation.this.armorStands.size());
            this.targetAngle = (Math.PI * 2D) * (double)WheelAnimation.this.settings.scroll.count + additionalAngle;
         } else {
            WheelAnimation.this.armorStands.add(WheelAnimation.this.spawnArmorStand(this.location, WheelAnimation.this.getWinItem(), WheelAnimation.this.settings.smallArmorStand));

            for(int i = 1; i < WheelAnimation.this.settings.itemsCount; ++i) {
               CaseDataItem randomItem = WheelAnimation.this.getCaseData().getRandomItem();
               WheelAnimation.this.armorStands.add(WheelAnimation.this.spawnArmorStand(this.location, randomItem, WheelAnimation.this.settings.smallArmorStand));
            }

            int rand = (new Random()).nextInt(WheelAnimation.this.armorStands.size());
            int additionalSteps = WheelAnimation.this.armorStands.size() - rand;
            double additionalAngle = (double)additionalSteps * ((Math.PI * 2D) / (double)WheelAnimation.this.armorStands.size());
            this.targetAngle = (Math.PI * 2D) * (double)WheelAnimation.this.settings.scroll.count + additionalAngle;
            Collections.swap(WheelAnimation.this.armorStands, 0, rand);
         }

      }

      private void handleFlameEffects() {
         if (WheelAnimation.this.settings.flame.enabled) {
            double progress = Math.min((double)this.ticks / (double)WheelAnimation.this.settings.scroll.time * 0.9, (double)1.0F);
            double easedProgress = (double)1.0F - Math.pow((double)1.0F - progress, WheelAnimation.this.settings.scroll.easeAmount);
            double deltaX = Math.max(((double)1.0F - easedProgress) * WheelAnimation.this.settings.radius, 0.4);
            double deltaY = easedProgress * WheelAnimation.this.settings.radius + 0.7;
            double theta = (double)this.ticks / 6.666666666666667;
            this.spawnFlameEffect(deltaX, deltaY, theta);
            this.spawnFlameEffect(deltaX, deltaY, theta + Math.PI);
         }

      }

      private void spawnFlameEffect(double deltaX, double deltaY, double theta) {
         double dx = deltaX * Math.sin(theta);
         double dz = deltaX * Math.cos(theta);
         this.world.spawnParticle(WheelAnimation.this.settings.flame.particle, this.bukkitLocation.clone().add(dx, deltaY, dz), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (Object)null);
      }

      private void moveArmorStands(double angle) {
         for(ArmorStandCreator entity : WheelAnimation.this.armorStands) {
            double x = WheelAnimation.this.settings.radius * Math.sin(angle);
            double y = WheelAnimation.this.settings.radius * Math.cos(angle);
            CaseVector rotationAxis = this.location.getDirection().crossProduct(new CaseVector(0, 1, 0)).normalize();
            CaseLocation newLoc = this.location.clone().add(rotationAxis.multiply(x).add(this.location.getDirection().multiply(y)));
            entity.teleport(newLoc);
            angle += this.offset;
            double currentAngle = angle - this.baseAngle;
            if (currentAngle - this.lastCompletedRotation >= this.rotationThreshold) {
               Sound sound = WheelAnimation.this.settings.scroll.sound();
               if (sound != null) {
                  this.world.playSound(this.bukkitLocation, sound, WheelAnimation.this.settings.scroll.volume, WheelAnimation.this.settings.scroll.pitch);
                  this.lastCompletedRotation = currentAngle;
               }
            }
         }

      }

      private void endAnimation(SchedulerTask task) {
         task.cancel();

         for(ArmorStandCreator stand : WheelAnimation.this.armorStands) {
            stand.remove();
         }

         WheelAnimation.this.end();
         WheelAnimation.this.armorStands.clear();
      }
   }
}
