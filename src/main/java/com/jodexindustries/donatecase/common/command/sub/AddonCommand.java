package com.jodexindustries.donatecase.common.command.sub;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.addon.InternalAddonClassLoader;
import com.jodexindustries.donatecase.api.addon.InternalJavaAddon;
import com.jodexindustries.donatecase.api.addon.PowerReason;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandType;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.DefaultCommand;
import com.jodexindustries.donatecase.common.managers.AddonManagerImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class AddonCommand extends DefaultCommand {
   private final DCAPI api;

   public AddonCommand(DCAPI api) {
      super(api, "addon", SubCommandType.ADMIN);
      this.api = api;
   }

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      if (args.length < 2) {
         return false;
      } else {
         String action = args[0];
         String addonName = args[1];
         switch (action) {
            case "enable":
               this.handleEnableCommand(sender, addonName);
               break;
            case "disable":
               this.handleDisableCommand(sender, addonName);
               break;
            case "load":
               this.handleLoadCommand(sender, addonName);
               break;
            case "unload":
               this.handleUnloadCommand(sender, addonName);
               break;
            default:
               return false;
         }

         return true;
      }
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, String[] args) {
      List<String> list = new ArrayList<>();
      if (args.length == 1) {
         list.add("enable");
         list.add("disable");
         list.add("load");
         list.add("unload");
      }

      if (args.length == 2) {
         if (args[0].equalsIgnoreCase("enable")) {
            return this.getDisabledAddons();
         }

         if (args[0].equalsIgnoreCase("disable")) {
            return this.getEnabledAddons();
         }

         if (args[0].equalsIgnoreCase("unload")) {
            return this.getAddons();
         }

         if (args[0].equalsIgnoreCase("load")) {
            return this.getAddonsFiles();
         }
      }

      return list;
   }

   private void handleEnableCommand(DCCommandSender sender, String addonName) {
      InternalJavaAddon addon = this.api.getAddonManager().get(addonName);
      if (addon == null) {
         sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &cnot loaded!"));
      } else if (addon.isEnabled()) {
         sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &calready enabled!"));
      } else {
         if (this.api.getAddonManager().enable(addon, PowerReason.DONATE_CASE)) {
            this.handleAddonSuccess(sender, addonName, "enabled");
         } else {
            this.handleAddonError(sender, addonName, "enabling");
         }

      }
   }

   private void handleDisableCommand(DCCommandSender sender, String addonName) {
      InternalJavaAddon addon = this.api.getAddonManager().get(addonName);
      if (addon == null) {
         sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &cnot loaded!"));
      } else if (!addon.isEnabled()) {
         sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &calready disabled!"));
      } else {
         this.api.getAddonManager().disable(addon, PowerReason.DONATE_CASE);
         this.handleAddonSuccess(sender, addonName, "disabled");
      }
   }

   private void handleLoadCommand(DCCommandSender sender, String addonName) {
      File addonFile = new File(this.api.getAddonManager().getFolder(), addonName);
      if (!addonFile.exists()) {
         sender.sendMessage(DCTools.prefix("&cFile &6" + addonName + " &cnot found!"));
      } else {
         InternalAddonClassLoader loader = AddonManagerImpl.getAddonClassLoader(addonFile);
         if (loader != null) {
            sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &calready loaded!"));
         } else {
            if (this.api.getAddonManager().load(addonFile)) {
               loader = AddonManagerImpl.getAddonClassLoader(addonFile);
               if (loader == null) {
                  this.handleAddonError(sender, addonName, "loading");
                  return;
               }

               InternalJavaAddon addon = loader.getAddon();
               if (this.api.getAddonManager().enable(addon, PowerReason.DONATE_CASE)) {
                  this.handleAddonSuccess(sender, addonName, "loaded");
               } else {
                  this.handleAddonError(sender, addonName, "enabling");
               }
            } else {
               this.handleAddonError(sender, addonName, "loading");
            }

         }
      }
   }

   private void handleUnloadCommand(DCCommandSender sender, String addonName) {
      InternalJavaAddon addon = this.api.getAddonManager().get(addonName);
      if (addon == null) {
         sender.sendMessage(DCTools.prefix("&cAddon &6" + addonName + " &calready unloaded!"));
      } else {
         if (this.api.getAddonManager().unload(addon, PowerReason.DONATE_CASE)) {
            this.handleAddonSuccess(sender, addonName, "unloaded");
         } else {
            this.handleAddonError(sender, addonName, "unloading");
         }

      }
   }

   private void handleAddonError(DCCommandSender sender, String addonName, String action) {
      sender.sendMessage(DCTools.prefix("&cThere was an error " + action + " the addon &6" + addonName + "&c."));
   }

   private void handleAddonSuccess(DCCommandSender sender, String addonName, String action) {
      sender.sendMessage(DCTools.prefix("&aAddon &6" + addonName + " &a" + action + " successfully!"));
   }

   private List<String> getAddons() {
      return (List)this.api.getAddonManager().getMap().values().stream().map(InternalJavaAddon::getName).collect(Collectors.toList());
   }

   private List<String> getDisabledAddons() {
      return (List)this.api.getAddonManager().getMap().values().stream().filter((internalJavaAddon) -> !internalJavaAddon.isEnabled()).map(InternalJavaAddon::getName).collect(Collectors.toList());
   }

   private List<String> getEnabledAddons() {
      return (List)this.api.getAddonManager().getMap().values().stream().filter(InternalJavaAddon::isEnabled).map(InternalJavaAddon::getName).collect(Collectors.toList());
   }

   private List<String> getAddonsFiles() {
      List<String> addons = new ArrayList<>();
      File addonsDir = this.api.getAddonManager().getFolder();
      File[] files = addonsDir.listFiles();
      return files == null ? addons : (List)Arrays.stream(files).map(File::getName).filter((name) -> name.endsWith(".jar")).collect(Collectors.toList());
   }
}
