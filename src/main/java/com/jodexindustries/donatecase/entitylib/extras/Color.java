package com.jodexindustries.donatecase.entitylib.extras;

import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class Color implements RGBLike {
   private static final int BIT_MASK = 255;
   private final int red;
   private final int green;
   private final int blue;

   public Color(@Range(
   from = 0L,
   to = 255L
) int red, @Range(
   from = 0L,
   to = 255L
) int green, @Range(
   from = 0L,
   to = 255L
) int blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public Color(int rgb) {
      this(rgb >> 16 & 255, rgb >> 8 & 255, rgb & 255);
   }

   public @NotNull Color withRed(@Range(
   from = 0L,
   to = 255L
) int red) {
      return new Color(red, this.green, this.blue);
   }

   public @NotNull Color withGreen(@Range(
   from = 0L,
   to = 255L
) int green) {
      return new Color(this.red, green, this.blue);
   }

   public @NotNull Color withBlue(@Range(
   from = 0L,
   to = 255L
) int blue) {
      return new Color(this.red, this.green, blue);
   }

   public int asRGB() {
      int rgb = this.red;
      rgb = (rgb << 8) + this.green;
      return (rgb << 8) + this.blue;
   }

   public @Range(
   from = 0L,
   to = 255L
) int red() {
      return this.red;
   }

   public @Range(
   from = 0L,
   to = 255L
) int green() {
      return this.green;
   }

   public @Range(
   from = 0L,
   to = 255L
) int blue() {
      return this.blue;
   }
}
