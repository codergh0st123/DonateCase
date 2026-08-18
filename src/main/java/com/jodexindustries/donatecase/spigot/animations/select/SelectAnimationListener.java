package com.jodexindustries.donatecase.spigot.animations.select;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.data.ActiveCase;
import com.jodexindustries.donatecase.api.data.animation.Animation;
import com.jodexindustries.donatecase.api.event.player.ArmorStandCreatorInteractEvent;
import java.util.UUID;
import net.kyori.event.EventSubscriber;
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
