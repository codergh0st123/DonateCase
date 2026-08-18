package com.jodexindustries.donatecase.spigot.potdec;

import java.util.Collections;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PotDecCommand implements CommandExecutor, TabCompleter {
   private final PotDecManager manager;

   public PotDecCommand(PotDecManager manager) {
      this.manager = manager;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (!(sender instanceof Player player)) {
         sender.sendMessage("§cКоманду можно использовать только в игре.");
         return true;
      }

      if (!player.hasPermission("donatecase.potdec")) {
         player.sendMessage("§cНедостаточно прав.");
         return true;
      }

      if (args.length != 1 || !args[0].equalsIgnoreCase("create")) {
         player.sendMessage("§eИспользование: /potdec create");
         return true;
      }

      Block block = player.getTargetBlockExact(5);
      if (block == null || block.getType() != Material.DECORATED_POT) {
         player.sendMessage("§cПосмотрите на декоративную вазу в радиусе 5 блоков.");
         return true;
      }

      if (!this.manager.create(block)) {
         player.sendMessage("§cЭта ваза уже добавлена.");
         return true;
      }

      player.sendMessage("§aВаза с подсказкой джина создана.");
      return true;
   }

   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
      if (args.length == 1 && "create".startsWith(args[0].toLowerCase())) {
         return Collections.singletonList("create");
      }

      return Collections.emptyList();
   }
}
