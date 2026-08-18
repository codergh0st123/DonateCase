package com.jodexindustries.donatecase.api.data.subcommand;

public enum SubCommandType {
   PLAYER("donatecase.player"),
   MODER("donatecase.mod"),
   ADMIN("donatecase.admin");

   public final String permission;

   private SubCommandType(String permission) {
      this.permission = permission;
   }

   // $FF: synthetic method
   private static SubCommandType[] $values() {
      return new SubCommandType[]{PLAYER, MODER, ADMIN};
   }
}
