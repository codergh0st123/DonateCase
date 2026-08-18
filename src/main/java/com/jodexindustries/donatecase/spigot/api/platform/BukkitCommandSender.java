package com.jodexindustries.donatecase.spigot.api.platform;

import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import java.util.Objects;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandSender implements DCCommandSender {
   private final CommandSender sender;

   public BukkitCommandSender(CommandSender sender) {
      this.sender = sender;
   }

   public @NotNull String getName() {
      return this.sender.getName();
   }

   public @NotNull CommandSender getHandler() {
      return this.sender;
   }

   public boolean hasPermission(String permission) {
      return this.sender.hasPermission(permission);
   }

   public void sendMessage(@NotNull String text) {
      this.sender.sendMessage(text);
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         BukkitCommandSender that = (BukkitCommandSender)object;
         return Objects.equals(this.sender, that.sender);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.sender);
   }
}
