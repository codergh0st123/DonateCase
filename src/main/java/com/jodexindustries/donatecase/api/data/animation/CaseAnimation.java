package com.jodexindustries.donatecase.api.data.animation;

import com.jodexindustries.donatecase.api.addon.Addon;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaseAnimation {
   private final @NotNull Addon addon;
   private final @NotNull String name;
   private final @NotNull Class<? extends Animation> animation;
   private final @Nullable String description;
   private final boolean requireBlock;
   private final boolean requireSettings;
   private final boolean removeKeyAtStart;

   @Generated
   CaseAnimation(@NotNull Addon addon, @NotNull String name, @NotNull Class<? extends Animation> animation, @Nullable String description, boolean requireBlock, boolean requireSettings, boolean removeKeyAtStart) {
      this.addon = addon;
      this.name = name;
      this.animation = animation;
      this.description = description;
      this.requireBlock = requireBlock;
      this.requireSettings = requireSettings;
      this.removeKeyAtStart = removeKeyAtStart;
   }

   @Generated
   public static CaseAnimationBuilder builder() {
      return new CaseAnimationBuilder();
   }

   @Generated
   public @NotNull Addon getAddon() {
      return this.addon;
   }

   @Generated
   public @NotNull String getName() {
      return this.name;
   }

   @Generated
   public @NotNull Class<? extends Animation> getAnimation() {
      return this.animation;
   }

   @Generated
   public @Nullable String getDescription() {
      return this.description;
   }

   @Generated
   public boolean isRequireBlock() {
      return this.requireBlock;
   }

   @Generated
   public boolean isRequireSettings() {
      return this.requireSettings;
   }

   @Generated
   public boolean isRemoveKeyAtStart() {
      return this.removeKeyAtStart;
   }

   @Generated
   public static class CaseAnimationBuilder {
      @Generated
      private Addon addon;
      @Generated
      private String name;
      @Generated
      private Class<? extends Animation> animation;
      @Generated
      private String description;
      @Generated
      private boolean requireBlock;
      @Generated
      private boolean requireSettings;
      @Generated
      private boolean removeKeyAtStart;

      @Generated
      CaseAnimationBuilder() {
      }

      @Generated
      public CaseAnimationBuilder addon(@NotNull Addon addon) {
         this.addon = addon;
         return this;
      }

      @Generated
      public CaseAnimationBuilder name(@NotNull String name) {
         this.name = name;
         return this;
      }

      @Generated
      public CaseAnimationBuilder animation(@NotNull Class<? extends Animation> animation) {
         this.animation = animation;
         return this;
      }

      @Generated
      public CaseAnimationBuilder description(@Nullable String description) {
         this.description = description;
         return this;
      }

      @Generated
      public CaseAnimationBuilder requireBlock(boolean requireBlock) {
         this.requireBlock = requireBlock;
         return this;
      }

      @Generated
      public CaseAnimationBuilder requireSettings(boolean requireSettings) {
         this.requireSettings = requireSettings;
         return this;
      }

      @Generated
      public CaseAnimationBuilder removeKeyAtStart(boolean removeKeyAtStart) {
         this.removeKeyAtStart = removeKeyAtStart;
         return this;
      }

      @Generated
      public CaseAnimation build() {
         return new CaseAnimation(this.addon, this.name, this.animation, this.description, this.requireBlock, this.requireSettings, this.removeKeyAtStart);
      }

      @Generated
      public String toString() {
         return "CaseAnimation.CaseAnimationBuilder(addon=" + this.addon + ", name=" + this.name + ", animation=" + this.animation + ", description=" + this.description + ", requireBlock=" + this.requireBlock + ", requireSettings=" + this.requireSettings + ", removeKeyAtStart=" + this.removeKeyAtStart + ")";
      }
   }
}
