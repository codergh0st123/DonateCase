package com.jodexindustries.donatecase.api.data.action;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaseAction implements ActionExecutor {
   private final @NotNull Addon addon;
   private final @NotNull String name;
   private final @NotNull ActionExecutor executor;
   private final @Nullable String description;

   public void execute(@Nullable DCPlayer player, @NotNull String context) throws ActionException {
      this.executor.execute(player, context);
   }

   @Generated
   CaseAction(@NotNull Addon addon, @NotNull String name, @NotNull ActionExecutor executor, @Nullable String description) {
      this.addon = addon;
      this.name = name;
      this.executor = executor;
      this.description = description;
   }

   @Generated
   public static CaseActionBuilder builder() {
      return new CaseActionBuilder();
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
   public @NotNull ActionExecutor executor() {
      return this.executor;
   }

   @Generated
   public @Nullable String description() {
      return this.description;
   }

   @Generated
   public static class CaseActionBuilder {
      @Generated
      private Addon addon;
      @Generated
      private String name;
      @Generated
      private ActionExecutor executor;
      @Generated
      private String description;

      @Generated
      CaseActionBuilder() {
      }

      @Generated
      public CaseActionBuilder addon(@NotNull Addon addon) {
         this.addon = addon;
         return this;
      }

      @Generated
      public CaseActionBuilder name(@NotNull String name) {
         this.name = name;
         return this;
      }

      @Generated
      public CaseActionBuilder executor(@NotNull ActionExecutor executor) {
         this.executor = executor;
         return this;
      }

      @Generated
      public CaseActionBuilder description(@Nullable String description) {
         this.description = description;
         return this;
      }

      @Generated
      public CaseAction build() {
         return new CaseAction(this.addon, this.name, this.executor, this.description);
      }

      @Generated
      public String toString() {
         return "CaseAction.CaseActionBuilder(addon=" + this.addon + ", name=" + this.name + ", executor=" + this.executor + ", description=" + this.description + ")";
      }
   }
}
