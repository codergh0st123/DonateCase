package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItem;
import com.jodexindustries.donatecase.api.manager.GUITypedItemManager;
import com.jodexindustries.donatecase.api.platform.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GUITypedItemManagerImpl implements GUITypedItemManager {
   private static final Map<String, TypedItem> registeredItems = new ConcurrentHashMap<>();
   private final Platform platform;

   public GUITypedItemManagerImpl(DCAPI api) {
      this.platform = api.getPlatform();
   }

   public boolean register(TypedItem item) {
      String id = item.id().toLowerCase();
      if (registeredItems.get(id) == null) {
         registeredItems.put(id, item);
         return true;
      } else {
         this.platform.getLogger().warning("Typed item " + id + " already registered!");
         return false;
      }
   }

   public void unregister(String id) {
      if (registeredItems.get(id) != null) {
         registeredItems.remove(id);
      } else {
         this.platform.getLogger().warning("Typed item " + id + " not registered!");
      }

   }

   public void unregister() {
      List<String> items = new ArrayList<>(registeredItems.keySet());
      items.forEach(this::unregister);
   }

   public @Nullable TypedItem get(@NotNull String id) {
      return (TypedItem)registeredItems.get(id.toLowerCase());
   }

   public @NotNull Map<String, TypedItem> getMap() {
      return registeredItems;
   }

   public @Nullable Optional<TypedItem> getFromString(@NotNull String string) {
      Optional<String> temp = this.getByStart(string);
      return temp.map(this::get);
   }
}
