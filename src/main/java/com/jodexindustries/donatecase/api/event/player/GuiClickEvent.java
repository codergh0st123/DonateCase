package com.jodexindustries.donatecase.api.event.player;

import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import lombok.Generated;
import net.kyori.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class GuiClickEvent extends DCEvent implements Cancellable {
   private final int slot;
   private final @NotNull DCPlayer player;
   private final @NotNull CaseGuiWrapper guiWrapper;
   private final @NotNull String itemType;
   private boolean cancelled;

   public boolean cancelled() {
      return this.cancelled;
   }

   public void cancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   @Generated
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GuiClickEvent)) {
         return false;
      } else {
         GuiClickEvent other = (GuiClickEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else if (this.slot() != other.slot()) {
            return false;
         } else if (this.cancelled() != other.cancelled()) {
            return false;
         } else {
            Object this$player = this.player();
            Object other$player = other.player();
            if (this$player == null) {
               if (other$player != null) {
                  return false;
               }
            } else if (!this$player.equals(other$player)) {
               return false;
            }

            Object this$guiWrapper = this.guiWrapper();
            Object other$guiWrapper = other.guiWrapper();
            if (this$guiWrapper == null) {
               if (other$guiWrapper != null) {
                  return false;
               }
            } else if (!this$guiWrapper.equals(other$guiWrapper)) {
               return false;
            }

            Object this$itemType = this.itemType();
            Object other$itemType = other.itemType();
            if (this$itemType == null) {
               if (other$itemType != null) {
                  return false;
               }
            } else if (!this$itemType.equals(other$itemType)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof GuiClickEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      result = result * 59 + this.slot();
      result = result * 59 + (this.cancelled() ? 79 : 97);
      Object $player = this.player();
      result = result * 59 + ($player == null ? 43 : $player.hashCode());
      Object $guiWrapper = this.guiWrapper();
      result = result * 59 + ($guiWrapper == null ? 43 : $guiWrapper.hashCode());
      Object $itemType = this.itemType();
      result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
      return result;
   }

   @Generated
   public GuiClickEvent(int slot, @NotNull DCPlayer player, @NotNull CaseGuiWrapper guiWrapper, @NotNull String itemType) {
      this.slot = slot;
      this.player = player;
      this.guiWrapper = guiWrapper;
      this.itemType = itemType;
   }

   @Generated
   public int slot() {
      return this.slot;
   }

   @Generated
   public @NotNull DCPlayer player() {
      return this.player;
   }

   @Generated
   public @NotNull CaseGuiWrapper guiWrapper() {
      return this.guiWrapper;
   }

   @Generated
   public @NotNull String itemType() {
      return this.itemType;
   }

   @Generated
   public String toString() {
      return "GuiClickEvent(slot=" + this.slot() + ", player=" + this.player() + ", guiWrapper=" + this.guiWrapper() + ", itemType=" + this.itemType() + ", cancelled=" + this.cancelled() + ")";
   }
}
