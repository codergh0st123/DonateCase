package com.jodexindustries.donatecase.spigot.animations.pop;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.animation.Facing;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.spongepowered.configurate.serialize.SerializationException;

public class PopAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private PopSettings settings;

   public void start() {
      try {
         this.settings = (PopSettings)this.getSettings().get(PopSettings.class);
      } catch (SerializationException e) {
         throw new RuntimeException("Error with parsing animation settings", e);
      }

      List<Pair<ArmorStandCreator, CaseLocation>> asList = new ArrayList<>();
      double origX = this.getLocation().x() + (double)0.5F;
      double origY = this.getLocation().y() - (double)0.5F;
      double origZ = this.getLocation().z() + (double)0.5F;
      CaseLocation origCaseLocation = this.getLocation().clone().x(origX).y(origY).z(origZ);

      for(double y = (double)-1.0F; y < (double)2.0F; ++y) {
         for(double horOffset = (double)-1.0F; horOffset < (double)2.0F; ++horOffset) {
            if (y != (double)0.0F || horOffset != (double)0.0F) {
               this.getLocation().y(origY + (this.settings.rounded ? y / 1.4142 : y));
               double horizonOffset = this.settings.rounded ? horOffset * (y == (double)0.0F ? (double)1.0F : 0.707) : horOffset;
               if (this.settings.facing != Facing.EAST && this.settings.facing != Facing.WEST) {
                  this.getLocation().x(origX + horizonOffset);
               } else {
                  this.getLocation().z(origZ + horizonOffset);
               }

               ArmorStandCreator as = DCAPI.getInstance().getPlatform().getTools().createArmorStand(this.getUuid(), this.getLocation());
               as.setVisible(false);
               as.setGravity(false);
               as.setSmall(true);
               as.teleport(origCaseLocation);
               as.spawn();
               double yOffset = origY + (this.settings.rounded ? y / (horOffset == (double)0.0F ? (double)1.0F : 1.4142) : y) * this.settings.radius;
               if (this.settings.facing != Facing.EAST && this.settings.facing != Facing.WEST) {
                  asList.add(Pair.of(as, as.getLocation().clone().x(origX + horizonOffset * this.settings.radius).y(yOffset).z(origZ)));
               } else {
                  asList.add(Pair.of(as, as.getLocation().clone().x(origX).y(yOffset).z(origZ + horizonOffset * this.settings.radius)));
               }
            }
         }
      }

      api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)(new Task(asList, origCaseLocation)), 0L, (long)this.settings.period);
   }

   private class Task implements Consumer<SchedulerTask> {
      private int tick;
      private final CaseLocation location;
      private final List<Pair<ArmorStandCreator, CaseLocation>> asList;
      private final World world;
      private final List<Integer> indexes = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
      private int randomIndex;

      public Task(final List<Pair<ArmorStandCreator, CaseLocation>> asList, CaseLocation location) {
         this.asList = asList;
         this.location = location;
         this.world = PopAnimation.this.getPlayer().getWorld();
      }

      public void accept(SchedulerTask task) {
         if (this.tick == 0) {
            this.alignArmorStands();
         }

         if (this.tick == 10) {
            this.fillStandItem();
         }

         if (this.tick == 10) {
            for(Pair<ArmorStandCreator, CaseLocation> pair : this.asList) {
               ArmorStandCreator as = (ArmorStandCreator)pair.fst;
               CaseLocation target = (CaseLocation)pair.snd;
               as.teleport(target);
            }
         }

         if (this.tick >= 30 && this.tick % 15 == 0 && this.tick <= 129) {
            this.handleRemoveLogic();
         }

         if (this.tick == 129) {
            ArmorStandCreator as = (ArmorStandCreator)((Pair)this.asList.get(this.randomIndex)).fst;
            this.location.x(as.getLocation().x());
            this.location.y(as.getLocation().y());
            this.location.z(as.getLocation().z());
            this.location.yaw(as.getLocation().yaw());
            PopAnimation.this.preEnd();
         }

         if (this.tick >= 129) {
            ArmorStandCreator as = (ArmorStandCreator)((Pair)this.asList.get(this.randomIndex)).fst;
            this.location.yaw(this.location.yaw() + 15.0F);
            as.teleport(this.location);
         }

         if (this.tick >= 170) {
            this.cleanup(task);
         }

         ++this.tick;
      }

      public void handleRemoveLogic() {
         Random random = new Random();
         int randomIndex = random.nextInt(this.indexes.size());
         int initialIndex = (Integer)this.indexes.get(randomIndex);
         ArmorStandCreator as = (ArmorStandCreator)((Pair)this.asList.get(initialIndex)).fst;
         Location bukkitLocation = new Location(this.world, as.getLocation().x(), as.getLocation().y() + (double)1.0F, as.getLocation().z());
         this.world.spawnParticle(Particle.CLOUD, bukkitLocation, 0);
         as.remove();
         this.indexes.remove(randomIndex);
         Sound sound = PopAnimation.this.settings.scroll.sound();
         if (sound != null) {
            this.world.playSound(bukkitLocation, sound, PopAnimation.this.settings.scroll.volume, PopAnimation.this.settings.scroll.pitch);
         }

      }

      private void alignArmorStands() {
         for(Pair<ArmorStandCreator, CaseLocation> pair : this.asList) {
            ((ArmorStandCreator)pair.fst).teleport(this.location.yaw(PopAnimation.this.settings.facing.yaw));
         }

      }

      private void fillStandItem() {
         Random random = new Random();
         this.randomIndex = random.nextInt(8);
         Pair<ArmorStandCreator, CaseLocation> win = (Pair)this.asList.get(this.randomIndex);
         this.indexes.remove(this.randomIndex);
         ((ArmorStandCreator)win.fst).setEquipment(PopAnimation.this.settings.itemSlot, PopAnimation.this.getWinItem().material().itemStack());
         if (PopAnimation.this.getWinItem().material().displayName() != null && !PopAnimation.this.getWinItem().material().displayName().isEmpty()) {
            ((ArmorStandCreator)win.fst).setCustomNameVisible(true);
         }

         ((ArmorStandCreator)win.fst).setCustomName(PopAnimation.api.getPlatform().getPAPI().setPlaceholders((Object)PopAnimation.this.getPlayer(), PopAnimation.this.getWinItem().material().displayName()));
         ((ArmorStandCreator)win.fst).updateMeta();

         for(int i = 0; i < 8; ++i) {
            if (i != this.randomIndex) {
               Pair<ArmorStandCreator, CaseLocation> pair = (Pair)this.asList.get(i);
               ArmorStandCreator as = (ArmorStandCreator)pair.fst;
               CaseDataItem item = PopAnimation.this.getCaseData().getRandomItem();
               as.setEquipment(PopAnimation.this.settings.itemSlot, item.material().itemStack());
               String winGroupDisplayName = PopAnimation.api.getPlatform().getPAPI().setPlaceholders((Object)PopAnimation.this.getPlayer(), item.material().displayName());
               if (item.material().displayName() != null && !item.material().displayName().isEmpty()) {
                  as.setCustomNameVisible(true);
               }

               as.setCustomName(winGroupDisplayName);
               as.updateMeta();
            }
         }

      }

      private void cleanup(SchedulerTask task) {
         this.asList.forEach((pair) -> ((ArmorStandCreator)pair.fst).remove());
         task.cancel();
         PopAnimation.this.end();
      }
   }
}
