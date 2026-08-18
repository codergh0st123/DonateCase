package com.jodexindustries.donatecase.api.data.material;

import com.jodexindustries.donatecase.api.addon.Addon;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaseMaterial implements MaterialHandler {
   private final @NotNull MaterialHandler handler;
   private final @NotNull Addon addon;
   private final @NotNull String id;
   private final @Nullable String description;

   public @NotNull Object handle(@NotNull String context) throws CaseMaterialException {
      return this.handler.handle(context);
   }

   @Generated
   CaseMaterial(@NotNull MaterialHandler handler, @NotNull Addon addon, @NotNull String id, @Nullable String description) {
      this.handler = handler;
      this.addon = addon;
      this.id = id;
      this.description = description;
   }

   @Generated
   public static CaseMaterialBuilder builder() {
      return new CaseMaterialBuilder();
   }

   @Generated
   public @NotNull MaterialHandler handler() {
      return this.handler;
   }

   @Generated
   public @NotNull Addon addon() {
      return this.addon;
   }

   @Generated
   public @NotNull String id() {
      return this.id;
   }

   @Generated
   public @Nullable String description() {
      return this.description;
   }

   @Generated
   public static class CaseMaterialBuilder {
      @Generated
      private MaterialHandler handler;
      @Generated
      private Addon addon;
      @Generated
      private String id;
      @Generated
      private String description;

      @Generated
      CaseMaterialBuilder() {
      }

      @Generated
      public CaseMaterialBuilder handler(@NotNull MaterialHandler handler) {
         this.handler = handler;
         return this;
      }

      @Generated
      public CaseMaterialBuilder addon(@NotNull Addon addon) {
         this.addon = addon;
         return this;
      }

      @Generated
      public CaseMaterialBuilder id(@NotNull String id) {
         this.id = id;
         return this;
      }

      @Generated
      public CaseMaterialBuilder description(@Nullable String description) {
         this.description = description;
         return this;
      }

      @Generated
      public CaseMaterial build() {
         return new CaseMaterial(this.handler, this.addon, this.id, this.description);
      }

      @Generated
      public String toString() {
         return "CaseMaterial.CaseMaterialBuilder(handler=" + this.handler + ", addon=" + this.addon + ", id=" + this.id + ", description=" + this.description + ")";
      }
   }
}
