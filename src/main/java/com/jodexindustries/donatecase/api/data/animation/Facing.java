package com.jodexindustries.donatecase.api.data.animation;

public enum Facing {
   NORTH(180.0F, 0.0F),
   NORTH_EAST(225.0F, 0.0F),
   EAST(270.0F, 0.0F),
   SOUTH_EAST(315.0F, 0.0F),
   SOUTH(0.0F, 0.0F),
   SOUTH_WEST(45.0F, 0.0F),
   WEST(90.0F, 0.0F),
   NORTH_WEST(135.0F, 0.0F),
   UP(0.0F, -90.0F),
   DOWN(0.0F, 90.0F),
   UP_NORTH(180.0F, -45.0F),
   UP_EAST(270.0F, -45.0F),
   UP_SOUTH(0.0F, -45.0F),
   UP_WEST(90.0F, -45.0F),
   DOWN_NORTH(180.0F, 45.0F),
   DOWN_EAST(270.0F, 45.0F),
   DOWN_SOUTH(0.0F, 45.0F),
   DOWN_WEST(90.0F, 45.0F);

   public final float yaw;
   public final float pitch;

   private Facing(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }

   // $FF: synthetic method
   private static Facing[] $values() {
      return new Facing[]{NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, UP, DOWN, UP_NORTH, UP_EAST, UP_SOUTH, UP_WEST, DOWN_NORTH, DOWN_EAST, DOWN_SOUTH, DOWN_WEST};
   }
}
