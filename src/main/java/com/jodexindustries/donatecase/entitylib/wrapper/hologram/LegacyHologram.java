package com.jodexindustries.donatecase.entitylib.wrapper.hologram;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.jodexindustries.donatecase.entitylib.meta.other.ArmorStandMeta;
import com.jodexindustries.donatecase.entitylib.utils.Check;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class LegacyHologram implements Hologram.Legacy {
   private Location location;
   private final List<WrapperEntity> lines;
   private float lineOffset;
   private float markerOffset;
   private boolean marker;

   LegacyHologram(@NotNull Location location) {
      this.lines = new ArrayList(3);
      this.lineOffset = -0.9875F;
      this.markerOffset = -0.40625F;
      this.location = location;
   }

   LegacyHologram(@NotNull Location location, List<Component> lines) {
      this(location);

      for(Component line : lines) {
         this.addLine(line);
      }

   }

   public void addViewer(@NotNull UUID viewer) {
      for(WrapperEntity line : this.lines) {
         line.addViewer(viewer);
      }

   }

   public boolean isMarker() {
      return this.marker;
   }

   public void setMarker(boolean marker) {
      this.marker = true;
      if (!this.lines.isEmpty()) {
         this.teleport(this.location);
      }
   }

   public void show() {
      for(WrapperEntity line : this.lines) {
         line.spawn(this.location);
      }

      this.teleport(this.location);
   }

   public void hide() {
      for(WrapperEntity line : this.lines) {
         line.despawn();
      }

   }

   public void teleport(Location location) {
      this.location = location;

      for(int i = this.lines.size() - 1; i >= 0; --i) {
         WrapperEntity line = (WrapperEntity)this.lines.get(i);
         double y;
         if (this.marker) {
            y = location.getY() + (double)this.markerOffset;
         } else {
            y = location.getY() + (double)((float)i * this.lineOffset);
         }

         ArmorStandMeta meta = (ArmorStandMeta)line.getEntityMeta();
         meta.setMarker(this.marker);
         Location l = new Location(location.getX(), y, location.getZ(), location.getYaw(), location.getPitch());
         line.teleport(l, false);
      }

   }

   public @Nullable Component getLine(int index) {
      return index >= 0 && index < this.lines.size() ? ((WrapperEntity)this.lines.get(index)).getEntityMeta().getCustomName() : null;
   }

   public void setLine(int index, @Nullable Component line) {
      WrapperEntity e = new WrapperEntity(EntityTypes.ARMOR_STAND);
      e.spawn(this.location);
      ArmorStandMeta meta = (ArmorStandMeta)e.getEntityMeta();
      meta.setCustomName(line);
      meta.setCustomNameVisible(true);
      meta.setInvisible(true);
      meta.setHasNoGravity(true);
      meta.setSmall(true);
      meta.setMarker(this.marker);
      Check.arrayLength(this.lines, index, e);
      e.spawn(this.location);
      this.teleport(this.location);
   }

   public void addLine(@Nullable Component line) {
      this.setLine(this.lines.size(), line);
   }

   public float getLineOffset(boolean marker) {
      return marker ? this.markerOffset : this.lineOffset;
   }

   public int length() {
      return this.lines.size();
   }

   public void setLineOffset(boolean marker, float value) {
      if (marker) {
         this.markerOffset = value;
      } else {
         this.lineOffset = value;
      }

   }

   public @NotNull Location getLocation() {
      return this.location;
   }
}
