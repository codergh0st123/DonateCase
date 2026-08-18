package com.jodexindustries.donatecase.spigot.tools;

import com.jodexindustries.donatecase.api.tools.DCTools;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

public abstract class DCToolsBukkit extends DCTools {
   public static Color parseColor(String s) {
      Color color = fromRGBString((String)s, (Color)null);
      if (color == null) {
         color = getColor(s);
      }

      return color;
   }

   public static Color getColor(String color) {
      Field[] fields = Color.class.getFields();

      for(Field field : fields) {
         if (Modifier.isStatic(field.getModifiers()) && field.getType() == Color.class && field.getName().equalsIgnoreCase(color)) {
            try {
               return (Color)field.get(null);
            } catch (IllegalAccessException | IllegalArgumentException e1) {
               throw new RuntimeException(e1);
            }
         }
      }

      return null;
   }

   public static String[] parseRGB(@NotNull String string) {
      return string.replaceAll(" ", "").split(",");
   }

   public static Color fromRGBString(String[] rgb, Color def) {
      if (rgb.length >= 3) {
         try {
            int red = Integer.parseInt(rgb[0]);
            int green = Integer.parseInt(rgb[1]);
            int blue = Integer.parseInt(rgb[2]);
            def = Color.fromRGB(red, green, blue);
         } catch (NumberFormatException var5) {
         }
      }

      return def;
   }

   public static Color fromRGBString(String string, Color def) {
      if (string != null) {
         def = fromRGBString(parseRGB(string), def);
      }

      return def;
   }
}
