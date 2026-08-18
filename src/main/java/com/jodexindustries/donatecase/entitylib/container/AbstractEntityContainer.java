package com.jodexindustries.donatecase.entitylib.container;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEntityContainer implements EntityContainer {
   private final Map<UUID, WrapperEntity> entities = new ConcurrentHashMap();
   private final Map<Integer, WrapperEntity> entitiesById = new ConcurrentHashMap();

   public void addEntity(WrapperEntity entity) {
      this.entities.put(entity.getUuid(), entity);
      this.entitiesById.put(entity.getEntityId(), entity);
   }

   public void removeEntity(WrapperEntity entity, boolean despawn) {
      this.entities.remove(entity.getUuid());
      this.entitiesById.remove(entity.getEntityId());
      if (despawn) {
         entity.despawn();
      }

   }

   public void removeEntity(int entityId, boolean despawn) {
      WrapperEntity entity = (WrapperEntity)this.entitiesById.get(entityId);
      if (entity != null) {
         this.removeEntity(entity, despawn);
      }

   }

   public void removeEntity(UUID uuid, boolean despawn) {
      WrapperEntity entity = (WrapperEntity)this.entities.get(uuid);
      if (entity != null) {
         this.removeEntity(entity, despawn);
      }

   }

   public void clearEntities(boolean despawn) {
      this.entities.values().forEach((entity) -> this.removeEntity(entity, despawn));
   }

   public void tick() {
   }

   public @NotNull Iterator<WrapperEntity> iterator() {
      return this.entities.values().iterator();
   }

   public Collection<WrapperEntity> getEntities() {
      return Collections.unmodifiableCollection(this.entities.values());
   }

   public WrapperEntity getEntity(UUID uuid) {
      return (WrapperEntity)this.entities.get(uuid);
   }

   public WrapperEntity getEntity(int entityId) {
      return (WrapperEntity)this.entitiesById.get(entityId);
   }

   public boolean containsEntity(UUID uuid) {
      return this.entities.containsKey(uuid);
   }

   public boolean containsEntity(int entityId) {
      return this.entitiesById.containsKey(entityId);
   }

   public boolean containsEntity(WrapperEntity entity) {
      return this.entities.containsValue(entity);
   }
}
