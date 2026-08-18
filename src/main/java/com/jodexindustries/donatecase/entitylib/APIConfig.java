package com.jodexindustries.donatecase.entitylib;

import com.github.retrooper.packetevents.PacketEventsAPI;
import org.jetbrains.annotations.NotNull;

public final class APIConfig {
   private final PacketEventsAPI<?> packetEvents;
   private boolean debugMode = false;
   private boolean checkForUpdates = false;
   private boolean tickTickables = false;
   private boolean platformLogger = false;
   private boolean defaultCommands = false;
   private boolean platformTracking = false;
   private boolean bstats = false;

   public APIConfig(PacketEventsAPI<?> packetEvents) {
      this.packetEvents = packetEvents;
   }

   public @NotNull APIConfig useBstats() {
      this.bstats = true;
      return this;
   }

   public @NotNull APIConfig usePlatformLogger() {
      this.platformLogger = true;
      return this;
   }

   public @NotNull APIConfig trackPlatformEntities() {
      this.platformTracking = true;
      return this;
   }

   public @NotNull APIConfig checkForUpdates() {
      this.checkForUpdates = true;
      return this;
   }

   public @NotNull APIConfig tickTickables() {
      this.tickTickables = true;
      return this;
   }

   public @NotNull APIConfig debugMode() {
      this.debugMode = true;
      return this;
   }

   public @NotNull APIConfig registerDefaultCommands() {
      this.defaultCommands = true;
      return this;
   }

   public boolean shouldRegisterDefaultCommands() {
      return this.defaultCommands;
   }

   public boolean isDebugMode() {
      return this.debugMode;
   }

   public boolean shouldCheckForUpdate() {
      return this.checkForUpdates;
   }

   public boolean shouldTickTickables() {
      return this.tickTickables;
   }

   public PacketEventsAPI<?> getPacketEvents() {
      return this.packetEvents;
   }

   public boolean shouldUsePlatformLogger() {
      return this.platformLogger;
   }

   public boolean shouldTrackPlatformEntities() {
      return this.platformTracking;
   }

   public boolean shouldUseBstats() {
      return this.bstats;
   }
}
