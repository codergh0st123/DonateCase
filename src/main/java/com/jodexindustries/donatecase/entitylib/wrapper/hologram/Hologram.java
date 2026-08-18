package com.jodexindustries.donatecase.entitylib.wrapper.hologram;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.jodexindustries.donatecase.entitylib.meta.display.TextDisplayMeta;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hologram {
   static @NotNull Legacy legacy(@NotNull Location location) {
      return new LegacyHologram(location);
   }

   static @NotNull Legacy legacy(@NotNull Location location, List<Component> lines) {
      return new LegacyHologram(location, lines);
   }

   static @NotNull Modern modern(@NotNull Location location) {
      return new ModernHologram(location);
   }

   static @NotNull Modern modern(@NotNull Location location, List<Component> lines) {
      return new ModernHologram(location, lines);
   }

   @NotNull Location getLocation();

   void show();

   void hide();

   void teleport(Location var1);

   @Nullable Component getLine(int var1);

   int length();

   void setLine(int var1, @Nullable Component var2);

   void addLine(@Nullable Component var1);

   void addViewer(@NotNull UUID var1);

   default void addViewer(@NotNull User user) {
      this.addViewer(user.getUUID());
   }

   public interface Legacy extends Hologram {
      float getLineOffset(boolean var1);

      void setLineOffset(boolean var1, float var2);

      default float getLineOffset() {
         return this.getLineOffset(false);
      }

      default void setLineOffset(float value) {
         this.setLineOffset(false, value);
      }

      boolean isMarker();

      void setMarker(boolean var1);
   }

   public interface Modern extends Hologram {
      void setModifier(@NotNull Consumer<TextDisplayMeta> var1);
   }
}
