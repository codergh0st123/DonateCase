package com.jodexindustries.donatecase.api.data.subcommand;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SubCommand implements SubCommandExecutor, SubCommandTabCompleter {
   private @NotNull Addon addon;
   private @NotNull String name;
   private @Nullable SubCommandExecutor executor;
   private @Nullable SubCommandTabCompleter tabCompleter;
   private @Nullable String description;
   private @Nullable String permission;
   private @Nullable String[] args;

   public boolean execute(@NotNull DCCommandSender sender, @NotNull String label, @NotNull String[] args) throws SubCommandException {
      return this.executor == null ? false : this.executor.execute(sender, label, args);
   }

   public List<String> getTabCompletions(@NotNull DCCommandSender sender, @NotNull String label, @NotNull String[] args) throws SubCommandException {
      return (List<String>)(this.tabCompleter == null ? new ArrayList<>() : this.tabCompleter.getTabCompletions(sender, label, args));
   }

   @Generated
   SubCommand(@NotNull Addon addon, @NotNull String name, @Nullable SubCommandExecutor executor, @Nullable SubCommandTabCompleter tabCompleter, @Nullable String description, @Nullable String permission, @Nullable String[] args) {
      this.addon = addon;
      this.name = name;
      this.executor = executor;
      this.tabCompleter = tabCompleter;
      this.description = description;
      this.permission = permission;
      this.args = args;
   }

   @Generated
   public static SubCommandBuilder builder() {
      return new SubCommandBuilder();
   }

   @Generated
   public @NotNull Addon addon() {
      return this.addon;
   }

   @Generated
   public @NotNull String name() {
      return this.name;
   }

   @Generated
   public @Nullable SubCommandExecutor executor() {
      return this.executor;
   }

   @Generated
   public @Nullable SubCommandTabCompleter tabCompleter() {
      return this.tabCompleter;
   }

   @Generated
   public @Nullable String description() {
      return this.description;
   }

   @Generated
   public @Nullable String permission() {
      return this.permission;
   }

   @Generated
   public @Nullable String[] args() {
      return this.args;
   }

   @Generated
   public static class SubCommandBuilder {
      @Generated
      private Addon addon;
      @Generated
      private String name;
      @Generated
      private SubCommandExecutor executor;
      @Generated
      private SubCommandTabCompleter tabCompleter;
      @Generated
      private String description;
      @Generated
      private String permission;
      @Generated
      private String[] args;

      @Generated
      SubCommandBuilder() {
      }

      @Generated
      public SubCommandBuilder addon(@NotNull Addon addon) {
         this.addon = addon;
         return this;
      }

      @Generated
      public SubCommandBuilder name(@NotNull String name) {
         this.name = name;
         return this;
      }

      @Generated
      public SubCommandBuilder executor(@Nullable SubCommandExecutor executor) {
         this.executor = executor;
         return this;
      }

      @Generated
      public SubCommandBuilder tabCompleter(@Nullable SubCommandTabCompleter tabCompleter) {
         this.tabCompleter = tabCompleter;
         return this;
      }

      @Generated
      public SubCommandBuilder description(@Nullable String description) {
         this.description = description;
         return this;
      }

      @Generated
      public SubCommandBuilder permission(@Nullable String permission) {
         this.permission = permission;
         return this;
      }

      @Generated
      public SubCommandBuilder args(@Nullable String[] args) {
         this.args = args;
         return this;
      }

      @Generated
      public SubCommand build() {
         return new SubCommand(this.addon, this.name, this.executor, this.tabCompleter, this.description, this.permission, this.args);
      }

      @Generated
      public String toString() {
         return "SubCommand.SubCommandBuilder(addon=" + this.addon + ", name=" + this.name + ", executor=" + this.executor + ", tabCompleter=" + this.tabCompleter + ", description=" + this.description + ", permission=" + this.permission + ", args=" + Arrays.deepToString(this.args) + ")";
      }
   }
}
