package com.jodexindustries.donatecase.entitylib.wrapper.hologram;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.jodexindustries.donatecase.entitylib.meta.display.TextDisplayMeta;
import com.jodexindustries.donatecase.entitylib.utils.Check;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ModernHologram implements Hologram.Modern {
   private Location location;
   private final List<WrapperEntity> lines;
   private Consumer<TextDisplayMeta> modifier;
   private boolean spawned;

   ModernHologram(@NotNull Location location) {
      this.lines = new ArrayList(3);
      this.spawned = false;
      this.location = location;
   }

   ModernHologram(@NotNull Location location, List<Component> lines) {
      this(location);

      for(Component line : lines) {
         this.addLine(line);
      }

   }

   public void show() {
      for(WrapperEntity line : this.lines) {
         line.spawn(this.location);
      }

      this.teleport(this.location);
      this.spawned = true;
   }

   public void hide() {
      for(WrapperEntity line : this.lines) {
         line.despawn();
      }

      this.spawned = false;
   }

   public void teleport(Location location) {
      this.location = location;
      if (!this.lines.isEmpty()) {
         WrapperEntity first = (WrapperEntity)this.lines.get(0);
         first.teleport(location);

         for(WrapperEntity e : this.lines) {
            if (!e.getUuid().equals(first.getUuid())) {
               first.addPassenger(e);
            }
         }

      }
   }

   public @Nullable Component getLine(int index) {
      return index >= 0 && index < this.lines.size() ? ((WrapperEntity)this.lines.get(index)).getEntityMeta().getCustomName() : null;
   }

   public void setLine(int index, @Nullable Component line) {
      WrapperEntity e = new WrapperEntity(EntityTypes.TEXT_DISPLAY);
      e.spawn(this.location);
      TextDisplayMeta meta = (TextDisplayMeta)e.getEntityMeta();
      meta.setInvisible(true);
      meta.setHasNoGravity(true);
      meta.setText(line);
      if (this.modifier != null) {
         this.modifier.accept(meta);
      }

      Check.arrayLength(this.lines, index, e);
      if (this.spawned) {
         e.spawn(this.location);
         this.teleport(this.location);
      }

   }

   public void addLine(@Nullable Component line) {
      this.setLine(this.lines.size(), line);
   }

   public void addViewer(@NotNull UUID viewer) {
      for(WrapperEntity line : this.lines) {
         line.addViewer(viewer);
      }

   }

   public int length() {
      return this.lines.size();
   }

   public @NotNull Location getLocation() {
      return this.location;
   }

   public void setModifier(@NotNull Consumer<TextDisplayMeta> consumer) {
      this.modifier = consumer;
   }
}
