package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.util.Vector3d;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class WrapperExperienceOrbEntity extends WrapperEntity {
   private short experience;
   private Location slideTowards;

   public WrapperExperienceOrbEntity(int entityId, @NotNull UUID uuid, EntityType entityType, EntityMeta meta) {
      super(entityId, uuid, entityType, meta);
   }

   public WrapperExperienceOrbEntity(int entityId, @NotNull UUID uuid, EntityType entityType) {
      this(entityId, uuid, entityType, EntityMeta.createMeta(entityId, entityType));
   }

   public WrapperExperienceOrbEntity(int entityId, EntityType entityType) {
      this(entityId, EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public WrapperExperienceOrbEntity(UUID uuid, EntityType entityType) {
      this(EntityLib.getPlatform().getEntityIdProvider().provide(uuid, entityType), uuid, entityType);
   }

   public WrapperExperienceOrbEntity(EntityType entityType) {
      this(EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public void tick(long time) {
      super.tick(time);
      if (this.hasNoGravity()) {
         this.setVelocity(this.getVelocity().add((double)0.0F, (double)-0.3F, (double)0.0F));
      }

      double d = (double)8.0F;
      Vector3d distance = new Vector3d(this.slideTowards.getX() - this.getX(), this.slideTowards.getY() - this.getY(), this.slideTowards.getZ() - this.getZ());
      double length = distance.length();
      if (length < (double)8.0F) {
         double f = (double)1.0F - length / (double)8.0F;
         this.setVelocity(this.getVelocity().add(distance.normalize().multiply(f * f * 0.1)));
      }

      float g = 0.98F;
      if (this.isOnGround()) {
         g = 0.58800006F;
      }

      this.setVelocity(this.getVelocity().multiply((double)g, (double)0.98F, (double)g));
      if (this.isOnGround()) {
         this.setVelocity(this.getVelocity().multiply((double)1.0F, (double)-0.9F, (double)1.0F));
      }

   }

   public Location getSlideTowards() {
      return this.slideTowards;
   }

   public void setSlideTowards(Location slideTowards) {
      this.slideTowards = slideTowards;
   }

   public short getExperience() {
      return this.experience;
   }

   public void setExperience(short experience) {
      this.getViewers().forEach(this::removeViewer);
      this.experience = experience;
      this.getViewers().forEach(this::addViewer);
   }
}
