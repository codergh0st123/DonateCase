package com.jodexindustries.donatecase.entitylib.container;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface EntityContainer extends Iterable<WrapperEntity> {
   static EntityContainer basic() {
      return new ContainerImpl();
   }

   void addEntity(WrapperEntity var1);

   void removeEntity(WrapperEntity var1, boolean var2);

   default void removeEntity(WrapperEntity entity) {
      this.removeEntity(entity, false);
   }

   void removeEntity(int var1, boolean var2);

   default void removeEntity(int entityId) {
      this.removeEntity(entityId, false);
   }

   void removeEntity(UUID var1, boolean var2);

   default void removeEntity(UUID entity) {
      this.removeEntity(entity, false);
   }

   void clearEntities(boolean var1);

   default void clearEntities() {
      this.clearEntities(false);
   }

   void tick();

   Collection<WrapperEntity> getEntities();

   @Nullable WrapperEntity getEntity(UUID var1);

   @Nullable WrapperEntity getEntity(int var1);

   boolean containsEntity(UUID var1);

   boolean containsEntity(int var1);

   boolean containsEntity(WrapperEntity var1);
}
