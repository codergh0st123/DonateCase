package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.wrapper.ai.AIGroup;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class WrapperEntityCreature extends WrapperLivingEntity {
   private final Set<AIGroup> aiGroups;

   public WrapperEntityCreature(int entityId, @NotNull UUID uuid, EntityType entityType, EntityMeta meta) {
      super(entityId, uuid, entityType, meta);
      this.aiGroups = new HashSet();
   }

   public WrapperEntityCreature(int entityId, @NotNull UUID uuid, EntityType entityType) {
      this(entityId, uuid, entityType, EntityMeta.createMeta(entityId, entityType));
   }

   public WrapperEntityCreature(int entityId, EntityType entityType) {
      this(entityId, EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public WrapperEntityCreature(UUID uuid, EntityType entityType) {
      this(EntityLib.getPlatform().getEntityIdProvider().provide(uuid, entityType), uuid, entityType);
   }

   public WrapperEntityCreature(EntityType entityType) {
      this(EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public void tick(long time) {
      super.tick(time);
      this.aiGroups.forEach((aiGroup) -> aiGroup.tick(time));
   }

   public void addAIGroup(AIGroup aiGroup) {
      this.aiGroups.add(aiGroup);
   }

   public void removeAIGroup(AIGroup aiGroup) {
      this.aiGroups.remove(aiGroup);
   }

   public void clearAIGroups() {
      this.aiGroups.clear();
   }

   public Set<AIGroup> getAIGroups() {
      return Collections.unmodifiableSet(this.aiGroups);
   }
}
