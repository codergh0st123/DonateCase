package com.jodexindustries.donatecase.spigot.animations.shape;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import com.jodexindustries.donatecase.spigot.api.animation.BukkitJavaAnimation;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import com.jodexindustries.donatecase.spigot.tools.DCToolsBukkit;
import java.util.Random;
import java.util.function.Consumer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;

public class ShapeAnimation extends BukkitJavaAnimation {
   private static final DCAPI api = DCAPI.getInstance();
   private ShapeSettings settings;

   public void start() {
      try {
         this.settings = (ShapeSettings)this.getSettings().get(ShapeSettings.class);
      } catch (SerializationException e) {
         throw new RuntimeException("Error with parsing animation settings", e);
      }

      this.getLocation().add(this.settings.startPosition);
      ArmorStandCreator as = api.getPlatform().getTools().createArmorStand(this.getUuid(), this.getLocation());
      if (this.settings.pose != null) {
         as.setAngle(this.settings.pose);
      }

      as.setSmall(this.settings.small);
      as.setVisible(false);
      as.setGravity(false);
      as.spawn();
      Color orangeColor = DCToolsBukkit.fromRGBString(this.settings.particle.orange.rgb, Color.ORANGE);
      Color whiteColor = DCToolsBukkit.fromRGBString(this.settings.particle.white.rgb, Color.WHITE);
      api.getPlatform().getScheduler().run(api.getPlatform(), (Consumer)(new Task(as, orangeColor, whiteColor)), 0L, (long)this.settings.scroll.period);
   }

   private class Task implements Consumer<SchedulerTask> {
      private int tick;
      private final @NotNull World world;
      private final ArmorStandCreator as;
      private final CaseLocation location;
      private final Location bukkitLocation;
      private final Color orangeColor;
      private final Color whiteColor;
      private double currentTail;
      private final int endTime;
      private final double blockPerTick;
      private final Particle particle = this.getParticle();

      public Task(final ArmorStandCreator as, final Color orangeColor, final Color whiteColor) {
         this.as = as;
         this.location = as.getLocation();
         this.bukkitLocation = BukkitUtils.toBukkit(this.location);
         this.orangeColor = orangeColor;
         this.whiteColor = whiteColor;
         this.currentTail = ShapeAnimation.this.settings.scroll.tail.radius;
         this.endTime = ShapeAnimation.this.getSettings().node(new Object[]{"End", "Time"}).getInt(40);
         this.blockPerTick = ShapeAnimation.this.settings.scroll.height / (double)ShapeAnimation.this.settings.scroll.time;
         this.world = ShapeAnimation.this.getPlayer().getWorld();
      }

      public void accept(SchedulerTask task) {
         if (this.tick <= ShapeAnimation.this.settings.scroll.time) {
            this.location.yaw(this.location.yaw() + ShapeAnimation.this.settings.scroll.yaw);
            this.location.add((double)0.0F, this.blockPerTick, (double)0.0F);
            this.bukkitLocation.add((double)0.0F, this.blockPerTick, (double)0.0F);
            Particle.DustOptions dustOptions = new Particle.DustOptions(this.orangeColor, ShapeAnimation.this.settings.particle.orange.size);
            this.world.spawnParticle(this.particle, this.bukkitLocation.clone().add((double)0.0F, 0.4, (double)0.0F), 5, 0.3, 0.3, 0.3, (double)0.0F, dustOptions);
            this.as.teleport(this.location);
         }

         if (this.tick <= ShapeAnimation.this.settings.scroll.time) {
            this.handleTail();
            if (this.tick % ShapeAnimation.this.settings.scroll.interval == 0) {
               CaseDataItem item = ShapeAnimation.this.getCaseData().getRandomItem();
               this.as.setEquipment(ShapeAnimation.this.settings.itemSlot, item.material().itemStack());
               String winGroupDisplayName = ShapeAnimation.api.getPlatform().getPAPI().setPlaceholders((Object)ShapeAnimation.this.getPlayer(), item.material().displayName());
               if (item.material().displayName() != null && !item.material().displayName().isEmpty()) {
                  this.as.setCustomNameVisible(true);
               }

               this.as.setCustomName(winGroupDisplayName);
               this.as.updateMeta();
            }

            this.world.playSound(this.bukkitLocation, ShapeAnimation.this.settings.scroll.sound(), ShapeAnimation.this.settings.scroll.volume, ShapeAnimation.this.settings.scroll.pitch);
         }

         if (this.tick == ShapeAnimation.this.settings.scroll.time + 1) {
            this.as.setEquipment(ShapeAnimation.this.settings.itemSlot, ShapeAnimation.this.getWinItem().material().itemStack());
            this.as.setCustomName(ShapeAnimation.this.getWinItem().material().displayName());
            this.as.updateMeta();
            if (ShapeAnimation.this.settings.firework) {
               this.launchFirework(this.bukkitLocation.add((double)0.0F, (double)0.5F, (double)0.0F));
            }

            ShapeAnimation.this.preEnd();
         }

         if (this.tick >= ShapeAnimation.this.settings.scroll.time + this.endTime) {
            this.as.remove();
            task.cancel();
            ShapeAnimation.this.end();
         }

         ++this.tick;
      }

      private void handleTail() {
         this.currentTail -= ShapeAnimation.this.settings.scroll.tail.radius / (double)ShapeAnimation.this.settings.scroll.time;
         Location loc = this.bukkitLocation.clone().add((double)0.0F, this.blockPerTick, (double)0.0F);
         double c = Math.PI;
         double angle = c / (double)10.0F;

         for(double t = (double)0.0F; t <= c; t += angle) {
            double x = this.currentTail * Math.cos(t);
            double z = this.currentTail * Math.sin(t);
            loc.add(x, 0.4, z);
            Particle.DustOptions dustOptions = new Particle.DustOptions(this.whiteColor, ShapeAnimation.this.settings.particle.white.size);
            this.world.spawnParticle(this.particle, loc, 1, 0.1, 0.1, 0.1, (double)0.0F, dustOptions);
            loc.subtract(x, 0.4, z);
         }

      }

      private Particle getParticle() {
         try {
            return Particle.valueOf("REDSTONE");
         } catch (IllegalArgumentException var2) {
            return Particle.valueOf("DUST");
         }
      }

      public void launchFirework(Location location) {
         Random r = new Random();
         World world = location.getWorld();
         if (world != null) {
            Firework firework = (Firework)world.spawn(location.subtract(new Vector((double)0.0F, (double)0.5F, (double)0.0F)), Firework.class);
            FireworkMeta meta = firework.getFireworkMeta();
            Color[] color = new Color[]{Color.RED, Color.AQUA, Color.GREEN, Color.ORANGE, Color.LIME, Color.BLUE, Color.MAROON, Color.WHITE};
            meta.addEffect(FireworkEffect.builder().flicker(false).with(Type.BALL).trail(false).withColor(new Color[]{color[r.nextInt(color.length)], color[r.nextInt(color.length)], color[r.nextInt(color.length)]}).build());
            firework.setFireworkMeta(meta);
            firework.setMetadata("case", new FixedMetadataValue(((BukkitBackend)ShapeAnimation.api.getPlatform()).getPlugin(), "case"));
            firework.detonate();
         }
      }
   }
}
