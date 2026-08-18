package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.material.CaseMaterial;
import com.jodexindustries.donatecase.api.data.material.CaseMaterialException;
import com.jodexindustries.donatecase.api.manager.MaterialManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaterialManagerImpl implements MaterialManager {
   private static final Map<String, CaseMaterial> registeredMaterials = new ConcurrentHashMap();
   private final Platform platform;

   public MaterialManagerImpl(DCAPI api) {
      this.platform = api.getPlatform();
   }

   public void register(CaseMaterial material) throws CaseMaterialException {
      if (this.isRegistered(material.id())) {
         throw new CaseMaterialException("Material with id " + material.id() + " already registered!");
      } else {
         registeredMaterials.put(material.id(), material);
      }
   }

   public void unregister(String id) {
      if (this.isRegistered(id)) {
         registeredMaterials.remove(id);
      } else {
         this.platform.getLogger().warning("CaseMaterial with id " + id + " already unregistered!");
      }

   }

   public void unregister() {
      List<String> list = new ArrayList(registeredMaterials.keySet());
      list.forEach(this::unregister);
   }

   public boolean isRegistered(String id) {
      return registeredMaterials.containsKey(id);
   }

   public @Nullable CaseMaterial get(@NotNull String id) {
      return (CaseMaterial)registeredMaterials.get(id);
   }

   public @NotNull Map<String, CaseMaterial> getMap() {
      return registeredMaterials;
   }
}
