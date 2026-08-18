package com.jodexindustries.donatecase.spigot.potdec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PotDecManager {
   private final File file;
   private final Map<String, Set<UUID>> pots = new HashMap<>();

   public PotDecManager(JavaPlugin plugin) {
      this.file = new File(plugin.getDataFolder(), "Pots.yml");
   }

   public void load() {
      this.pots.clear();
      if (!this.file.exists()) {
         this.save();
         return;
      }

      YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
      for(Map<?, ?> data : config.getMapList("POTS")) {
         Object world = data.get("WORLD");
         Object x = data.get("X");
         Object y = data.get("Y");
         Object z = data.get("Z");
         if (!(world instanceof String) || !(x instanceof Number) || !(y instanceof Number) || !(z instanceof Number)) {
            continue;
         }

         Set<UUID> found = new HashSet<>();
         Object foundValue = data.get("FOUND");
         if (foundValue instanceof List<?> list) {
            for(Object value : list) {
               try {
                  found.add(UUID.fromString(String.valueOf(value)));
               } catch (IllegalArgumentException ignored) {
               }
            }
         }

         this.pots.put(this.key((String)world, ((Number)x).intValue(), ((Number)y).intValue(), ((Number)z).intValue()), found);
      }
   }

   public boolean create(Block block) {
      String key = this.key(block);
      if (this.pots.containsKey(key)) {
         return false;
      }

      this.pots.put(key, new HashSet<>());
      this.save();
      return true;
   }

   public boolean isPot(Block block) {
      return this.pots.containsKey(this.key(block));
   }

   public boolean discover(Block block, UUID playerId) {
      Set<UUID> found = this.pots.get(this.key(block));
      if (found == null || !found.add(playerId)) {
         return false;
      }

      this.save();
      return true;
   }

   private void save() {
      YamlConfiguration config = new YamlConfiguration();
      List<Map<String, Object>> records = new ArrayList<>();

      for(Map.Entry<String, Set<UUID>> entry : this.pots.entrySet()) {
         String[] parts = entry.getKey().split("\\|", 4);
         if (parts.length != 4) {
            continue;
         }

         Map<String, Object> record = new HashMap<>();
         record.put("WORLD", parts[0]);
         record.put("X", Integer.parseInt(parts[1]));
         record.put("Y", Integer.parseInt(parts[2]));
         record.put("Z", Integer.parseInt(parts[3]));
         record.put("FOUND", entry.getValue().stream().map(UUID::toString).toList());
         records.add(record);
      }

      config.set("POTS", records);
      try {
         config.save(this.file);
      } catch (IOException e) {
         throw new IllegalStateException("Could not save Pots.yml", e);
      }
   }

   private String key(Block block) {
      return this.key(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
   }

   private String key(String world, int x, int y, int z) {
      return world + "|" + x + "|" + y + "|" + z;
   }
}
