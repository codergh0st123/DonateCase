package com.jodexindustries.donatecase.spigot.animations.select;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.animation.Facing;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import lombok.Generated;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;

public class SelectAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private Task task;
   public @NotNull SelectSettings settings = new SelectSettings();

   public void start() {
      try {
         this.settings = (SelectSettings)this.getSettings().get(SelectSettings.class, new SelectSettings());
      } catch (SerializationException e) {
         throw new RuntimeException("Error with parsing animation settings", e);
      }

      List<Pair<ArmorStandCreator, CaseLocation>> asList = new ArrayList<>();
      double origX = this.getLocation().x() + (double)0.5F;
      double origY = this.getLocation().y() - (double)0.5F;
      double origZ = this.getLocation().z() + (double)0.5F;
      CaseLocation origCaseLocation = this.getLocation().clone().x(origX).y(origY).z(origZ);
      boolean boxSelect = "BOX_SELECT".equalsIgnoreCase(this.getCaseData().animation());
      Location playerLocation = this.getPlayer().getLocation();
      double yawRadians = Math.toRadians((double)playerLocation.getYaw());
      double lookX = -Math.sin(yawRadians);
      double lookZ = Math.cos(yawRadians);
      double sideX = lookZ;
      double sideZ = -lookX;
      CaseLocation spawnLocation = boxSelect ? origCaseLocation.clone().x(origX - lookX * this.settings.radius).z(origZ - lookZ * this.settings.radius) : origCaseLocation;

      for(double y = (double)-1.0F; y < (double)2.0F; ++y) {
         for(double horizonOffset = (double)-1.0F; horizonOffset < (double)2.0F; ++horizonOffset) {
            if (y != (double)0.0F || horizonOffset != (double)0.0F) {
               this.getLocation().y(origY + y);
               if (this.settings.facing != Facing.EAST && this.settings.facing != Facing.WEST) {
                  this.getLocation().x(origX + horizonOffset);
               } else {
                  this.getLocation().z(origZ + horizonOffset);
               }

               ArmorStandCreator as = api.getPlatform().getTools().createArmorStand(this.getUuid(), this.getLocation());
               as.setVisible(false);
               as.setGravity(false);
               as.setSmall(true);
               as.teleport(spawnLocation);
               as.spawn();
               if (boxSelect) {
                  asList.add(Pair.of(as, as.getLocation().clone().x(origX - lookX * this.settings.radius + sideX * horizonOffset * this.settings.radius).y(origY + y * this.settings.radius).z(origZ - lookZ * this.settings.radius + sideZ * horizonOffset * this.settings.radius)));
               } else if (this.settings.facing != Facing.EAST && this.settings.facing != Facing.WEST) {
                  asList.add(Pair.of(as, as.getLocation().clone().x(origX + horizonOffset * this.settings.radius).y(origY + y * this.settings.radius).z(origZ)));
               } else {
                  asList.add(Pair.of(as, as.getLocation().clone().x(origX).y(origY + y * this.settings.radius).z(origZ + horizonOffset * this.settings.radius)));
               }
            }
         }
      }

      this.task = new Task(asList, spawnLocation, boxSelect ? (playerLocation.getYaw() + 180.0F) % 360.0F : (this.settings.facing.yaw + 180.0F) % 360.0F);
      api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)this.task, 0L, (long)this.settings.period);
   }

   @Generated
   public Task getTask() {
      return this.task;
   }

   public class Task implements Consumer<SchedulerTask> {
      private int tick;
      private final CaseLocation location;
      private final List<Pair<ArmorStandCreator, CaseLocation>> asList;
      private final World world;
      private final float yaw;
      public boolean canSelect = false;
      public volatile boolean selected = false;
      private Pair<ArmorStandCreator, CaseLocation> randomAS;
      private final List<ArmorStandCreator> toDelete = new ArrayList<>();

      public Task(final List<Pair<ArmorStandCreator, CaseLocation>> asList, CaseLocation location, float yaw) {
         this.asList = asList;
         this.location = location;
         this.world = SelectAnimation.this.getPlayer().getWorld();
         this.yaw = yaw;
      }

      public void accept(SchedulerTask task) {
         if (this.tick == 0) {
            for(Pair<ArmorStandCreator, CaseLocation> pair : this.asList) {
               ArmorStandCreator as = (ArmorStandCreator)pair.fst;
               this.location.yaw(this.yaw);
               as.teleport(this.location);
            }
         }

         if (this.tick >= 10 && this.tick < 90) {
            if (this.tick % 10 == 0 && !this.asList.isEmpty()) {
               Random random = new Random();
               int index = random.nextInt(this.asList.size());
               this.randomAS = (Pair)this.asList.get(index);
               this.asList.remove(index);
               this.toDelete.add(this.randomAS.fst);
            }

            if (this.randomAS != null) {
               ArmorStandCreator as = (ArmorStandCreator)this.randomAS.fst;
               CaseLocation needLocation = (CaseLocation)this.randomAS.snd;
               as.setEquipment(SelectAnimation.this.settings.itemSlot, SelectAnimation.api.getPlatform().getTools().loadCaseItem(SelectAnimation.this.settings.item));
               as.updateMeta();
               CaseLocation currentLocation = as.getLocation().clone();
               double deltaX = needLocation.x() - currentLocation.x();
               double deltaY = needLocation.y() - currentLocation.y();
               double deltaZ = needLocation.z() - currentLocation.z();
               double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
               double step = distance / (double)10.0F;
               double moveX = deltaX * (step / distance);
               double moveY = deltaY * (step / distance);
               double moveZ = deltaZ * (step / distance);
               as.teleport(currentLocation.add(moveX, moveY, moveZ));
               Location bukkitLocation = new Location(this.world, as.getLocation().x(), as.getLocation().y() + (double)1.0F, as.getLocation().z());
               this.world.spawnParticle(Particle.CLOUD, bukkitLocation, 0);
            }
         }

         if (this.tick == 91) {
            this.canSelect = true;
         }

         if (this.tick > 91 && this.selected) {
            task.cancel();
            SelectAnimation.api.getPlatform().getScheduler().run(SelectAnimation.api.getPlatform(), this::end, 40L);
         }

         if (this.tick >= SelectAnimation.this.settings.timeout) {
            task.cancel();
            this.end();
         }

         ++this.tick;
      }

      private void end() {
         SelectAnimation.super.preEnd();

         for(ArmorStandCreator as : this.toDelete) {
            as.remove();
         }

         SelectAnimation.super.end();
      }
   }
}
