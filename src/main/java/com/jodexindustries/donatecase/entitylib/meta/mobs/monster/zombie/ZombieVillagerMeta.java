package com.jodexindustries.donatecase.entitylib.meta.mobs.monster.zombie;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.villager.VillagerData;
import com.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfessions;
import com.github.retrooper.packetevents.protocol.entity.villager.type.VillagerTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import com.jodexindustries.donatecase.entitylib.meta.mobs.villager.VillagerMeta;

public class ZombieVillagerMeta extends ZombieMeta {
   public static final byte OFFSET = 19;
   public static final byte MAX_OFFSET = 21;

   public ZombieVillagerMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public boolean isConverting() {
      return (Boolean)super.metadata.getIndex((byte)19, false);
   }

   public void setConverting(boolean value) {
      super.metadata.setIndex((byte)19, EntityDataTypes.BOOLEAN, value);
   }

   public VillagerData getVillagerData() {
      int[] data = (int[])super.metadata.getIndex(offset((byte)19, 1), (Object)null);
      return data == null ? new VillagerData(VillagerTypes.PLAINS, VillagerProfessions.NONE, VillagerMeta.Level.NOVICE.ordinal()) : new VillagerData(VillagerMeta.TYPES[data[0]], VillagerMeta.PROFESSIONS[data[1]], VillagerMeta.Level.VALUES[data[2] - 1].ordinal());
   }

   public void setVillagerData(VillagerData data) {
      super.metadata.setIndex(offset((byte)19, 1), EntityDataTypes.VILLAGER_DATA, new VillagerData(data.getType().getId(), data.getProfession().getId(), data.getLevel() + 1));
   }
}
