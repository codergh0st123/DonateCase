package com.jodexindustries.donatecase.entitylib.meta.mobs.villager;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.villager.VillagerData;
import com.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfession;
import com.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfessions;
import com.github.retrooper.packetevents.protocol.entity.villager.type.VillagerType;
import com.github.retrooper.packetevents.protocol.entity.villager.type.VillagerTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public class VillagerMeta extends BaseVillagerMeta {
   public static final byte OFFSET = 18;
   public static final byte MAX_OFFSET = 19;
   @Internal
   public static final VillagerType[] TYPES;
   @Internal
   public static final VillagerProfession[] PROFESSIONS;

   public VillagerMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull VillagerData getVillagerData() {
      int[] data = (int[])super.metadata.getIndex((byte)18, null);
      return data == null ? new VillagerData(VillagerTypes.PLAINS, VillagerProfessions.NONE, VillagerMeta.Level.NOVICE.ordinal()) : new VillagerData(TYPES[data[0]], PROFESSIONS[data[1]], VillagerMeta.Level.VALUES[data[2] - 1].ordinal());
   }

   public void setVillagerData(@NotNull VillagerData data) {
      super.metadata.setIndex((byte)18, EntityDataTypes.VILLAGER_DATA, new VillagerData(data.getType().getId(), data.getProfession().getId(), data.getLevel()));
   }

   static {
      TYPES = new VillagerType[]{VillagerTypes.DESERT, VillagerTypes.JUNGLE, VillagerTypes.PLAINS, VillagerTypes.SAVANNA, VillagerTypes.SNOW, VillagerTypes.SWAMP, VillagerTypes.TAIGA};
      PROFESSIONS = new VillagerProfession[]{VillagerProfessions.NONE, VillagerProfessions.ARMORER, VillagerProfessions.BUTCHER, VillagerProfessions.CARTOGRAPHER, VillagerProfessions.CLERIC, VillagerProfessions.FARMER, VillagerProfessions.FISHERMAN, VillagerProfessions.FLETCHER, VillagerProfessions.LEATHERWORKER, VillagerProfessions.LIBRARIAN, VillagerProfessions.MASON, VillagerProfessions.NITWIT, VillagerProfessions.SHEPHERD, VillagerProfessions.TOOLSMITH, VillagerProfessions.WEAPONSMITH};
   }

   public static enum Level {
      NOVICE,
      APPRENTICE,
      JOURNEYMAN,
      EXPERT,
      MASTER;

      public static final Level[] VALUES = values();

      // $FF: synthetic method
      private static Level[] $values() {
         return new Level[]{NOVICE, APPRENTICE, JOURNEYMAN, EXPERT, MASTER};
      }
   }
}
