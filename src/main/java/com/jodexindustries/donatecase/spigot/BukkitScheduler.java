package com.jodexindustries.donatecase.spigot;

import com.jodexindustries.donatecase.api.addon.Addon;
import com.jodexindustries.donatecase.api.scheduler.SchedulerTask;
import com.jodexindustries.donatecase.common.scheduler.BackendScheduler;
import com.jodexindustries.donatecase.common.scheduler.WrappedTask;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitScheduler extends BackendScheduler {
   private final Plugin plugin;
   private final org.bukkit.scheduler.BukkitScheduler scheduler;

   public BukkitScheduler(BukkitBackend backend) {
      this.plugin = backend.getPlugin();
      this.scheduler = Bukkit.getScheduler();
   }

   private WrappedTask add(Addon addon, BukkitTask task) {
      WrappedTask wrappedTask = new WrappedTask(addon, task.getTaskId(), task.isSync(), task);
      this.add(wrappedTask);
      return wrappedTask;
   }

   public SchedulerTask run(Addon addon, Runnable task, long delay) {
      BukkitTask bukkitTask = this.scheduler.runTaskLater(this.plugin, task, delay);
      return this.add(addon, bukkitTask);
   }

   public SchedulerTask run(Addon addon, Runnable task, long delay, long period) {
      BukkitTask bukkitTask = this.scheduler.runTaskTimer(this.plugin, task, delay, period);
      return this.add(addon, bukkitTask);
   }

   public void run(Addon addon, Consumer<SchedulerTask> task, long delay) {
      this.scheduler.runTaskLater(this.plugin, (bukkitTask) -> {
         WrappedTask wrappedTask = this.add(addon, bukkitTask);
         task.accept(wrappedTask);
      }, delay);
   }

   public void run(Addon addon, Consumer<SchedulerTask> task, long delay, long period) {
      this.scheduler.runTaskTimer(this.plugin, (bukkitTask) -> {
         WrappedTask wrappedTask = this.add(addon, bukkitTask);
         task.accept(wrappedTask);
      }, delay, period);
   }

   public SchedulerTask async(Addon addon, Runnable task, long delay) {
      BukkitTask bukkitTask = this.scheduler.runTaskLaterAsynchronously(this.plugin, task, delay);
      return this.add(addon, bukkitTask);
   }

   public SchedulerTask async(Addon addon, Runnable task, long delay, long period) {
      BukkitTask bukkitTask = this.scheduler.runTaskTimerAsynchronously(this.plugin, task, delay, period);
      return this.add(addon, bukkitTask);
   }

   public void async(Addon addon, Consumer<SchedulerTask> task, long delay) {
      this.scheduler.runTaskLaterAsynchronously(this.plugin, (bukkitTask) -> {
         WrappedTask wrappedTask = this.add(addon, bukkitTask);
         task.accept(wrappedTask);
      }, delay);
   }

   public void async(Addon addon, Consumer<SchedulerTask> task, long delay, long period) {
      this.scheduler.runTaskTimerAsynchronously(this.plugin, (bukkitTask) -> {
         WrappedTask wrappedTask = this.add(addon, bukkitTask);
         task.accept(wrappedTask);
      }, delay, period);
   }

   public void cancel(int taskId) {
      this.remove(taskId);
      Bukkit.getScheduler().cancelTask(taskId);
   }

   public void shutdown() {
      this.tasks.values().forEach((task) -> {
         task.cancel();
         Bukkit.getScheduler().cancelTask(task.getTaskId());
      });
      this.tasks.clear();
   }
}
