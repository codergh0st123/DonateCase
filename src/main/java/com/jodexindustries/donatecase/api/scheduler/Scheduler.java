package com.jodexindustries.donatecase.api.scheduler;

import com.jodexindustries.donatecase.api.addon.Addon;
import java.util.function.Consumer;

public interface Scheduler {
   SchedulerTask run(Addon var1, Runnable var2, long var3);

   SchedulerTask run(Addon var1, Runnable var2, long var3, long var5);

   void run(Addon var1, Consumer<SchedulerTask> var2, long var3);

   void run(Addon var1, Consumer<SchedulerTask> var2, long var3, long var5);

   SchedulerTask async(Addon var1, Runnable var2, long var3);

   SchedulerTask async(Addon var1, Runnable var2, long var3, long var5);

   void async(Addon var1, Consumer<SchedulerTask> var2, long var3);

   void async(Addon var1, Consumer<SchedulerTask> var2, long var3, long var5);

   void cancel(int var1);

   void shutdown();
}
