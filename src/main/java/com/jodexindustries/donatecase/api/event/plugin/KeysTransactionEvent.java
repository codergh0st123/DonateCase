package com.jodexindustries.donatecase.api.event.plugin;

import com.jodexindustries.donatecase.api.event.DCEvent;
import lombok.Generated;
import net.kyori.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class KeysTransactionEvent extends DCEvent implements Cancellable {
   private final @NotNull String caseType;
   private final @NotNull String source;
   private int amount;
   private final int before;
   private int after;
   private @NotNull TransactionType transactionType;
   private boolean cancelled = false;

   public KeysTransactionEvent(@NotNull String caseType, @NotNull String source, int after, int before) {
      this.caseType = caseType;
      this.source = source;
      this.before = before;
      this.after(after);
   }

   public void after(int after) {
      this.after = after;
      if (this.before == after) {
         this.amount = 0;
         this.transactionType = KeysTransactionEvent.TransactionType.NOTHING;
      } else if (this.before > after) {
         this.amount = this.before - after;
         this.transactionType = KeysTransactionEvent.TransactionType.REMOVE;
      } else {
         this.amount = after - this.before;
         this.transactionType = KeysTransactionEvent.TransactionType.ADD;
      }

   }

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
      } else if (!(o instanceof KeysTransactionEvent)) {
         return false;
      } else {
         KeysTransactionEvent other = (KeysTransactionEvent)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (!super.equals(o)) {
            return false;
         } else if (this.amount() != other.amount()) {
            return false;
         } else if (this.before() != other.before()) {
            return false;
         } else if (this.after() != other.after()) {
            return false;
         } else if (this.cancelled() != other.cancelled()) {
            return false;
         } else {
            Object this$caseType = this.caseType();
            Object other$caseType = other.caseType();
            if (this$caseType == null) {
               if (other$caseType != null) {
                  return false;
               }
            } else if (!this$caseType.equals(other$caseType)) {
               return false;
            }

            Object this$source = this.source();
            Object other$source = other.source();
            if (this$source == null) {
               if (other$source != null) {
                  return false;
               }
            } else if (!this$source.equals(other$source)) {
               return false;
            }

            Object this$transactionType = this.transactionType();
            Object other$transactionType = other.transactionType();
            if (this$transactionType == null) {
               if (other$transactionType != null) {
                  return false;
               }
            } else if (!this$transactionType.equals(other$transactionType)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof KeysTransactionEvent;
   }

   @Generated
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      result = result * 59 + this.amount();
      result = result * 59 + this.before();
      result = result * 59 + this.after();
      result = result * 59 + (this.cancelled() ? 79 : 97);
      Object $caseType = this.caseType();
      result = result * 59 + ($caseType == null ? 43 : $caseType.hashCode());
      Object $source = this.source();
      result = result * 59 + ($source == null ? 43 : $source.hashCode());
      Object $transactionType = this.transactionType();
      result = result * 59 + ($transactionType == null ? 43 : $transactionType.hashCode());
      return result;
   }

   @Generated
   public @NotNull String caseType() {
      return this.caseType;
   }

   @Generated
   public @NotNull String source() {
      return this.source;
   }

   @Generated
   public int amount() {
      return this.amount;
   }

   @Generated
   public int before() {
      return this.before;
   }

   @Generated
   public int after() {
      return this.after;
   }

   @Generated
   public @NotNull TransactionType transactionType() {
      return this.transactionType;
   }

   @Generated
   public KeysTransactionEvent amount(int amount) {
      this.amount = amount;
      return this;
   }

   @Generated
   public KeysTransactionEvent transactionType(@NotNull TransactionType transactionType) {
      this.transactionType = transactionType;
      return this;
   }

   @Generated
   public String toString() {
      return "KeysTransactionEvent(caseType=" + this.caseType() + ", source=" + this.source() + ", amount=" + this.amount() + ", before=" + this.before() + ", after=" + this.after() + ", transactionType=" + this.transactionType() + ", cancelled=" + this.cancelled() + ")";
   }

   public static enum TransactionType {
      ADD,
      REMOVE,
      NOTHING;

      // $FF: synthetic method
      private static TransactionType[] $values() {
         return new TransactionType[]{ADD, REMOVE, NOTHING};
      }
   }
}
