package com.jodexindustries.donatecase.entitylib.spigot;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.common.AbstractEntityLibAPI;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.LivingEntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.PlayerMeta;
import com.jodexindustries.donatecase.entitylib.tick.TickContainer;
import com.jodexindustries.donatecase.entitylib.utils.Check;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperLivingEntity;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperPlayer;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import java.util.UUID;
import java.util.logging.Level;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SpigotEntityLibAPI extends AbstractEntityLibAPI<JavaPlugin, BukkitTask> {
   SpigotEntityLibAPI(SpigotEntityLibPlatform platform, APIConfig settings) {
      super(platform, settings);
   }

   public void onLoad() {
   }

   public void onEnable() {
   }

   public <T extends WrapperEntity> @NotNull T cloneEntity(@NotNull Object platformEntity) {
      Check.stateCondition(!(platformEntity instanceof Entity), "Entity must be a Bukkit entity");
      Entity e = (Entity)platformEntity;
      EntityType type = SpigotConversionUtil.fromBukkitEntityType(e.getType());
      EntityMeta meta = EntityMeta.createMeta(e.getEntityId(), type);
      meta.setHasNoGravity(!e.hasGravity());
      meta.setCustomNameVisible(e.isCustomNameVisible());
      String customName = e.getCustomName();
      if (customName != null) {
         meta.setCustomName(LegacyComponentSerializer.legacyAmpersand().deserialize(customName));
      }

      meta.setPose(ExtraConversionUtil.fromBukkitPose(e.getPose()));
      meta.setOnFire(e.getFireTicks() > 0);
      meta.setSilent(e.isSilent());
      meta.setHasGlowingEffect(e.isGlowing());
      if (e instanceof LivingEntity) {
         LivingEntity le = (LivingEntity)e;
         LivingEntityMeta lm = (LivingEntityMeta)meta;
         lm.setHealth((float)le.getHealth());
         lm.setFlyingWithElytra(le.isGliding());
      }

      if (e instanceof Player) {
         Player p = (Player)e;
         PlayerMeta pm = (PlayerMeta)meta;
         pm.setSneaking(p.isSneaking());
         pm.setSprinting(p.isSprinting());
         pm.setSwimming(p.isSwimming());
         pm.setActiveHand(ExtraConversionUtil.fromBukkitHand(p.getMainHand()));
      }

      int id = EntityLib.getPlatform().getEntityIdProvider().provide(e.getUniqueId(), type);
      UUID uuid = e.getUniqueId();
      WrapperEntity entity;
      if (meta instanceof PlayerMeta) {
         Player p = (Player)e;
         entity = new WrapperPlayer(ExtraConversionUtil.getProfileFromBukkitPlayer(p), id);
      } else if (meta instanceof LivingEntityMeta) {
         entity = new WrapperLivingEntity(id, uuid, type, meta);
      } else {
         entity = new WrapperEntity(id, uuid, type, meta);
      }

      return (T)entity;
   }

   public void addTickContainer(@NotNull TickContainer<?, BukkitTask> tickContainer) {
      if (!this.settings.shouldTickTickables()) {
         if (this.settings.isDebugMode()) {
            this.platform.getLogger().log(Level.WARNING, "Tried to add a TickContainer when ticking tickables is disabled!");
         }

      } else {
         this.tickContainers.add(tickContainer);
         if (this.settings.isDebugMode()) {
            this.platform.getLogger().log(Level.CONFIG, "Registering new tick container...");
         }

         this.getTickContainers().add(tickContainer);
         BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this.platform.getHandle(), () -> tickContainer.tick(System.currentTimeMillis()), 1L, 1L);
         tickContainer.setHandle(task);
      }
   }
}
