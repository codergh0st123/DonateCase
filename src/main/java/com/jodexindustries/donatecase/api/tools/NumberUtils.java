package com.jodexindustries.donatecase.api.tools;

public class NumberUtils {
   public static double square(double num) {
      return num * num;
   }

   public static int floor(double num) {
      int floor = (int)num;
      return (double)floor == num ? floor : floor - (int)(Double.doubleToRawLongBits(num) >>> 63);
   }
}
