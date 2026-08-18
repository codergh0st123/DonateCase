package com.jodexindustries.donatecase.spigot.animations.select;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.ActiveCase;
import com.jodexindustries.donatecase.api.data.animation.Animation;
import com.jodexindustries.donatecase.api.event.player.ArmorStandCreatorInteractEvent;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import java.util.UUID;
import net.kyori.event.EventSubscriber;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

public class SelectAnimationListener implements EventSubscriber<ArmorStandCreatorInteractEvent> {
   private static final DCAPI api = DCAPI.getInstance();

   public void invoke(@NonNull ArmorStandCreatorInteractEvent event) {
      ArmorStandCreator creator = event.armorStandCreator();
      SelectAnimation animation = this.getAnimation(creator.getAnimationId());
      if (animation != null) {
         SelectAnimation.Task task = animation.getTask();
         if (!task.selected && task.canSelect) {
            if (animation.getPlayer().getUniqueId().equals(event.player().getUniqueId())) {
               task.selected = true;
               if ("BOX_SELECT".equalsIgnoreCase(animation.getCaseData().animation())) {
                  Player player = BukkitUtils.toBukkit(event.player());
                  Location location = BukkitUtils.toBukkit(creator.getLocation()).add(0.0D, 0.5D, 0.0D);
                  player.playSound(location, Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
                  player.getWorld().spawnParticle(Particle.BLOCK, location, 18, 0.25D, 0.2D, 0.25D, Material.CHEST.createBlockData());
                  player.getWorld().spawnParticle(Particle.END_ROD, location, 8, 0.2D, 0.2D, 0.2D, 0.02D);
                  api.getPlatform().getScheduler().run(api.getPlatform(), () -> this.revealReward(animation, creator, event), 3L);
               } else {
                  creator.setEquipment(animation.settings.itemSlot, animation.getWinItem().material().itemStack());
                  if (animation.getWinItem().material().displayName() != null && !animation.getWinItem().material().displayName().isEmpty()) {
                     creator.setCustomNameVisible(true);
                  }

                  creator.setCustomName(api.getPlatform().getPAPI().setPlaceholders(event.player(), animation.getWinItem().material().displayName()));
                  creator.updateMeta();
               }
            }
         }
      }
   }

   private void revealReward(SelectAnimation animation, ArmorStandCreator creator, ArmorStandCreatorInteractEvent event) {
      creator.setEquipment(animation.settings.itemSlot, animation.getWinItem().material().itemStack());
      if (animation.getWinItem().material().displayName() != null && !animation.getWinItem().material().displayName().isEmpty()) {
         creator.setCustomNameVisible(true);
      }

      creator.setCustomName(api.getPlatform().getPAPI().setPlaceholders(event.player(), animation.getWinItem().material().displayName()));
      creator.updateMeta();
      int[] ticks = new int[]{0};
      api.getPlatform().getScheduler().run(api.getPlatform(), (revealTask) -> {
         if (ticks[0]++ >= 10) {
            revealTask.cancel();
         } else {
            creator.teleport(creator.getLocation().clone().y(creator.getLocation().y() + 0.12D));
            creator.updateMeta();
            Player player = BukkitUtils.toBukkit(event.player());
            player.getWorld().spawnParticle(Particle.END_ROD, BukkitUtils.toBukkit(creator.getLocation()).add(0.0D, 0.4D, 0.0D), 3, 0.08D, 0.08D, 0.08D, 0.01D);
         }
      }, 0L, 1L);
   }

   private @Nullable SelectAnimation getAnimation(UUID uuid) {
      ActiveCase activeCase = (ActiveCase)api.getAnimationManager().getActiveCases().get(uuid);
      if (activeCase == null) {
         return null;
      } else {
         Animation animation = activeCase.animation();
         return !(animation instanceof SelectAnimation) ? null : (SelectAnimation)animation;
      }
   }
}
