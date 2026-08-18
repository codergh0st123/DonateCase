package com.jodexindustries.donatecase.spigot.animations.firework;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.DCToolsBukkit;
import java.util.function.Consumer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.spongepowered.configurate.serialize.SerializationException;

public class FireworkAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private FireworkSettings settings;

   public void start() {
      try {
         this.settings = (FireworkSettings)this.getSettings().get(FireworkSettings.class);
      } catch (SerializationException e) {
         throw new RuntimeException("Error with parsing animation settings", e);
      }

      this.getLocation().add(this.settings.startPosition);
      ArmorStandCreator as = DCAPI.getInstance().getPlatform().getTools().createArmorStand(this.getUuid(), this.getLocation());
      if (this.settings.pose != null) {
         as.setAngle(this.settings.pose);
      }

      as.setSmall(this.settings.small);
      as.setVisible(false);
      as.setGravity(false);
      as.spawn();
      api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)(new Task(as)), 0L, (long)this.settings.scroll.period);
   }

   private class Task implements Consumer<SchedulerTask> {
      private int tick;
      private final CaseLocation location;
      private final ArmorStandCreator as;
      private final World world;

      public Task(final ArmorStandCreator as) {
         this.as = as;
         this.location = as.getLocation();
         this.world = FireworkAnimation.this.getPlayer().getWorld();
      }

      public void accept(SchedulerTask task) {
         if (this.tick == 0) {
            Firework firework = (Firework)this.world.spawn(BukkitUtils.toBukkit(this.location), Firework.class);
            FireworkMeta data = firework.getFireworkMeta();
            data.addEffects(new FireworkEffect[]{FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.RED).with(Type.BALL).withFlicker().build()});

            for(String color : FireworkAnimation.this.settings.fireworkColors) {
               data.addEffect(FireworkEffect.builder().withColor(DCToolsBukkit.parseColor(color)).build());
            }

            data.setPower(FireworkAnimation.this.settings.power);
            firework.setFireworkMeta(data);
         }

         if (this.tick == 10) {
            this.as.setEquipment(FireworkAnimation.this.settings.itemSlot, FireworkAnimation.this.getWinItem().material().itemStack());
            if (FireworkAnimation.this.getWinItem().material().displayName() != null && !FireworkAnimation.this.getWinItem().material().displayName().isEmpty()) {
               this.as.setCustomNameVisible(true);
            }

            this.as.setCustomName(DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders((Object)FireworkAnimation.this.getPlayer(), FireworkAnimation.this.getWinItem().material().displayName()));
            this.as.updateMeta();
            FireworkAnimation.this.preEnd();
         }

         if (this.tick >= 10 && this.tick < 60) {
            this.location.yaw(this.location.yaw() + FireworkAnimation.this.settings.scroll.yaw);
            this.as.teleport(this.location);
         }

         if (this.tick >= 60) {
            this.as.remove();
            task.cancel();
            FireworkAnimation.this.end();
         }

         ++this.tick;
      }
   }
}
