package com.jodexindustries.donatecase.entitylib.extras;

import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.util.Vector3d;

public final class CoordinateUtil {
   private CoordinateUtil() {
   }

   public static Location withDirection(Location location, Vector3d direction) {
      double x = direction.getX();
      double z = direction.getZ();
      if (x == (double)0.0F && z == (double)0.0F) {
         float pitch = direction.getY() > (double)0.0F ? -90.0F : 90.0F;
         return new Location(location.getX(), location.getY(), location.getZ(), location.getYaw(), pitch);
      } else {
         double theta = Math.atan2(-x, z);
         double xz = Math.sqrt(square(x) + square(z));
         double _2PI = (Math.PI * 2D);
         return new Location(location.getX(), location.getY(), location.getZ(), (float)Math.toDegrees((theta + (Math.PI * 2D)) % (Math.PI * 2D)), (float)Math.toDegrees(Math.atan(-direction.getY() / xz)));
      }
   }

   public static double square(double in) {
      return in * in;
   }
}
