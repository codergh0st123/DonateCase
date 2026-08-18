package com.jodexindustries.donatecase.api.data.casedata.gui.typeditem;

import com.jodexindustries.donatecase.api.addon.Addon;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypedItem {
   private final @NotNull String id;
   private final @NotNull Addon addon;
   private final @Nullable String description;
   private final @Nullable TypedItemHandler handler;
   private final @Nullable TypedItemClickHandler click;
   private final boolean updateMeta;
   private final boolean loadOnCase;

   @Generated
   TypedItem(@NotNull String id, @NotNull Addon addon, @Nullable String description, @Nullable TypedItemHandler handler, @Nullable TypedItemClickHandler click, boolean updateMeta, boolean loadOnCase) {
      this.id = id;
      this.addon = addon;
      this.description = description;
      this.handler = handler;
      this.click = click;
      this.updateMeta = updateMeta;
      this.loadOnCase = loadOnCase;
   }

   @Generated
   public static TypedItemBuilder builder() {
      return new TypedItemBuilder();
   }

   @Generated
   public @NotNull String id() {
      return this.id;
   }

   @Generated
   public @NotNull Addon addon() {
      return this.addon;
   }

   @Generated
   public @Nullable String description() {
      return this.description;
   }

   @Generated
   public @Nullable TypedItemHandler handler() {
      return this.handler;
   }

   @Generated
   public @Nullable TypedItemClickHandler click() {
      return this.click;
   }

   @Generated
   public boolean updateMeta() {
      return this.updateMeta;
   }

   @Generated
   public boolean loadOnCase() {
      return this.loadOnCase;
   }

   @Generated
   public static class TypedItemBuilder {
      @Generated
      private String id;
      @Generated
      private Addon addon;
      @Generated
      private String description;
      @Generated
      private TypedItemHandler handler;
      @Generated
      private TypedItemClickHandler click;
      @Generated
      private boolean updateMeta;
      @Generated
      private boolean loadOnCase;

      @Generated
      TypedItemBuilder() {
      }

      @Generated
      public TypedItemBuilder id(@NotNull String id) {
         this.id = id;
         return this;
      }

      @Generated
      public TypedItemBuilder addon(@NotNull Addon addon) {
         this.addon = addon;
         return this;
      }

      @Generated
      public TypedItemBuilder description(@Nullable String description) {
         this.description = description;
         return this;
      }

      @Generated
      public TypedItemBuilder handler(@Nullable TypedItemHandler handler) {
         this.handler = handler;
         return this;
      }

      @Generated
      public TypedItemBuilder click(@Nullable TypedItemClickHandler click) {
         this.click = click;
         return this;
      }

      @Generated
      public TypedItemBuilder updateMeta(boolean updateMeta) {
         this.updateMeta = updateMeta;
         return this;
      }

      @Generated
      public TypedItemBuilder loadOnCase(boolean loadOnCase) {
         this.loadOnCase = loadOnCase;
         return this;
      }

      @Generated
      public TypedItem build() {
         return new TypedItem(this.id, this.addon, this.description, this.handler, this.click, this.updateMeta, this.loadOnCase);
      }

      @Generated
      public String toString() {
         return "TypedItem.TypedItemBuilder(id=" + this.id + ", addon=" + this.addon + ", description=" + this.description + ", handler=" + this.handler + ", click=" + this.click + ", updateMeta=" + this.updateMeta + ", loadOnCase=" + this.loadOnCase + ")";
      }
   }
}
