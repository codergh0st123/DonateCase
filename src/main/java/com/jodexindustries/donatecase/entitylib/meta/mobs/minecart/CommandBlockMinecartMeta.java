package com.jodexindustries.donatecase.entitylib.meta.mobs.minecart;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class CommandBlockMinecartMeta extends BaseMinecartMeta {
   public static final byte OFFSET = 14;
   public static final byte MAX_OFFSET = 16;

   public CommandBlockMinecartMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public @NotNull String getCommand() {
      return (String)super.metadata.getIndex((byte)14, "");
   }

   public void setCommand(@NotNull String value) {
      super.metadata.setIndex((byte)14, EntityDataTypes.STRING, value);
   }

   public @NotNull Component getLastOutput() {
      return (Component)super.metadata.getIndex(offset((byte)14, 1), Component.empty());
   }

   public void setLastOutput(@NotNull Component value) {
      super.metadata.setIndex(offset((byte)14, 1), EntityDataTypes.COMPONENT, (String)GsonComponentSerializer.gson().serialize(value));
   }

   public int getObjectData() {
      return 6;
   }
}
