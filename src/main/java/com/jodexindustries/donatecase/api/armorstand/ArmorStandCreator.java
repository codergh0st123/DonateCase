package com.jodexindustries.donatecase.api.armorstand;

import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ArmorStandCreator {
   Map<Integer, ArmorStandCreator> armorStands = new HashMap();

   void setVisible(boolean var1);

   void setCustomName(@Nullable String var1);

   void teleport(CaseLocation var1);

   void setEquipment(EquipmentSlot var1, Object var2);

   void setAngle(@NotNull ArmorStandEulerAngle var1);

   void setRotation(float var1, float var2);

   void setGravity(boolean var1);

   void setSmall(boolean var1);

   void setMarker(boolean var1);

   void setGlowing(boolean var1);

   boolean isGlowing();

   void setCollidable(boolean var1);

   void setCustomNameVisible(boolean var1);

   boolean isCustomNameVisible();

   CaseLocation getLocation();

   @NotNull UUID getUniqueId();

   UUID getAnimationId();

   int getEntityId();

   default void spawn() {
   }

   default void updateMeta() {
   }

   void remove();
}
