package com.jodexindustries.donatecase.api.tools;

public class Placeholder {
   private final String name;
   private final String value;

   public Placeholder(String name, String value) {
      this.name = name;
      this.value = value;
   }

   public String name() {
      return this.name;
   }

   public String value() {
      return this.value;
   }

   public static Placeholder of(String name, Object value) {
      return new Placeholder(name, String.valueOf(value));
   }
}
