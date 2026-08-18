package com.jodexindustries.donatecase.common.scheduler;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import java.util.function.Consumer;
import java.util.logging.Level;

public class WrappedTask implements SchedulerTask {
   private final Addon owner;
   private final int taskId;
   private final boolean sync;
   private final Runnable r;
   private final Consumer<SchedulerTask> c;
   private volatile boolean cancelled = false;

   public WrappedTask(Addon owner, int taskId, boolean sync, Object task) {
      this.owner = owner;
      this.taskId = taskId;
      this.sync = sync;
      if (task instanceof Runnable) {
         this.c = null;
         this.r = (Runnable)task;
      } else {
         if (!(task instanceof Consumer)) {
            throw new AssertionError("Illegal task class " + task);
         }

         this.r = null;
         this.c = (Consumer)task;
      }

   }

   public void run() {
      if (!this.isCancelled()) {
         try {
            if (this.r != null) {
               this.r.run();
            }

            if (this.c != null) {
               this.c.accept(this);
            }
         } catch (Throwable e) {
            DCAPI.getInstance().getPlatform().getLogger().log(Level.WARNING, "Error with executing task: " + this.taskId, e);
         }
      }

   }

   public int getTaskId() {
      return this.taskId;
   }

   public boolean isSync() {
      return this.sync;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public Addon getOwner() {
      return this.owner;
   }

   public void cancel() {
      this.cancelled = true;
      DCAPI.getInstance().getPlatform().getScheduler().cancel(this.taskId);
   }
}
