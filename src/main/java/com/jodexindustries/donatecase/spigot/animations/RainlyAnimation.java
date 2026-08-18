package com.jodexindustries.donatecase.spigot.animations;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandEulerAngle;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;

public class RainlyAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private EquipmentSlot itemSlot;

   public void start() {
      try {
         Particle particle = Particle.valueOf(this.getSettings().node(new Object[]{"FallingParticle"}).getString());
         ArmorStandCreator as = DCAPI.getInstance().getPlatform().getTools().createArmorStand(this.getUuid(), this.getLocation().clone().add((double)0.5F, (double)1.0F, (double)0.5F));
         ArmorStandEulerAngle armorStandEulerAngle = (ArmorStandEulerAngle)this.getSettings().node(new Object[]{"Pose"}).get(ArmorStandEulerAngle.class);
         if (armorStandEulerAngle != null) {
            as.setAngle(armorStandEulerAngle);
         }

         as.setCustomNameVisible(true);
         as.setVisible(false);
         as.setGravity(false);
         this.itemSlot = EquipmentSlot.valueOf(this.getSettings().node(new Object[]{"ItemSlot"}).getString("HEAD").toUpperCase());
         boolean small = this.getSettings().node(new Object[]{"SmallArmorStand"}).getBoolean(true);
         as.setSmall(small);
         as.spawn();
         api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)(new Task(as, particle)), 0L, 2L);
      } catch (Exception exception) {
         throw new IllegalStateException("Не удалось запустить анимацию RAINLY.", exception);
      }
   }

   private class Task implements Consumer<SchedulerTask> {
      private int i = 0;
      private final List<Location> clouds = new ArrayList<>();
      private final Location bukkitLocation;
      private final CaseLocation location;
      private final Particle particle;
      private final ArmorStandCreator as;
      private final World world;
      private final Sound endSound = Sound.valueOf(RainlyAnimation.this.getSettings().node(new Object[]{"End", "Sound"}).getString("ENTITY_GENERIC_EXPLODE"));
      private final float endVolume = RainlyAnimation.this.getSettings().node(new Object[]{"End", "Volume"}).getFloat();
      private final float endPitch = RainlyAnimation.this.getSettings().node(new Object[]{"End", "Pitch"}).getFloat();
      private final Sound scrollSound = Sound.valueOf(RainlyAnimation.this.getSettings().node(new Object[]{"Scroll", "Sound"}).getString("ENTITY_EXPERIENCE_ORB_PICKUP"));
      private final float scrollVolume = RainlyAnimation.this.getSettings().node(new Object[]{"Scroll", "Volume"}).getFloat();
      private final float scrollPitch = RainlyAnimation.this.getSettings().node(new Object[]{"Scroll", "Pitch"}).getFloat();

      public Task(ArmorStandCreator as, Particle particle) {
         this.as = as;
         this.location = as.getLocation();
         this.bukkitLocation = BukkitUtils.toBukkit(this.location);
         this.particle = particle;
         this.clouds.add(this.bukkitLocation.clone().add((double)-2.0F, (double)3.0F, (double)2.0F));
         this.clouds.add(this.bukkitLocation.clone().add((double)-2.0F, (double)3.0F, (double)-2.0F));
         this.clouds.add(this.bukkitLocation.clone().add((double)2.0F, (double)3.0F, (double)2.0F));
         this.clouds.add(this.bukkitLocation.clone().add((double)2.0F, (double)3.0F, (double)-2.0F));
         this.world = this.bukkitLocation.getWorld() != null ? this.bukkitLocation.getWorld() : RainlyAnimation.this.getPlayer().getWorld();
      }

      public void accept(SchedulerTask task) {
         for(Location cloud : this.clouds) {
            this.world.spawnParticle(this.particle, cloud, 1);
            this.world.spawnParticle(Particle.CLOUD, cloud.clone().add((double)0.0F, (double)0.5F, (double)0.0F), 0);
         }

         if (this.i == 32) {
            this.handleWinningItem();
         }

         if (this.i < 32) {
            this.location.yaw(this.location.yaw() + 20.0F);
            this.as.teleport(this.location);
            if (this.i % 2 == 0) {
               this.updateRandomItem();
               this.world.spawnParticle(this.getParticle("fireworks"), this.bukkitLocation.clone().add((double)0.0F, 0.4, (double)0.0F), 9, 0.1, 0.1, 0.1, (double)0.0F);
            }
         }

         if (this.i >= 70) {
            this.as.remove();
            task.cancel();
            RainlyAnimation.this.end();
         }

         ++this.i;
      }

      private void handleWinningItem() {
         this.as.setEquipment(RainlyAnimation.this.itemSlot, RainlyAnimation.this.getWinItem().material().itemStack());
         String winGroupDisplayName = DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders((Object)RainlyAnimation.this.getPlayer(), RainlyAnimation.this.getWinItem().material().displayName());
         RainlyAnimation.this.getWinItem().material().displayName(winGroupDisplayName);
         this.as.setCustomName(winGroupDisplayName);
         this.as.updateMeta();
         RainlyAnimation.this.preEnd();
         this.world.spawnParticle(this.getParticle("explosion"), this.bukkitLocation, 0);
         this.world.playSound(this.bukkitLocation, this.endSound, this.endVolume, this.endPitch);
      }

      private void updateRandomItem() {
         CaseDataItem item = RainlyAnimation.this.getCaseData().getRandomItem();
         String itemDisplayName = DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders((Object)RainlyAnimation.this.getPlayer(), item.material().displayName());
         item.material().displayName(itemDisplayName);
         this.as.setEquipment(RainlyAnimation.this.itemSlot, item.material().itemStack());
         if (item.material().displayName() != null && !item.material().displayName().isEmpty()) {
            this.as.setCustomName(item.material().displayName());
         }

         this.as.updateMeta();
         this.world.playSound(this.bukkitLocation, this.scrollSound, this.scrollVolume, this.scrollPitch);
      }

      private Particle getParticle(String name) {
         if (name.equalsIgnoreCase("explosion")) {
            try {
               return Particle.valueOf("EXPLOSION_HUGE");
            } catch (IllegalArgumentException var3) {
               return Particle.valueOf("EXPLOSION");
            }
         } else {
            try {
               return Particle.valueOf("FIREWORKS_SPARK");
            } catch (IllegalArgumentException var4) {
               return Particle.valueOf("FIREWORK");
            }
         }
      }
   }
}
