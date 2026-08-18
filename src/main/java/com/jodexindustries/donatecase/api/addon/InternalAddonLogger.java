package com.jodexindustries.donatecase.api.addon;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class InternalAddonLogger extends Logger {
   private final String addonName;

   public InternalAddonLogger(@NotNull InternalAddon context) {
      super(context.getPlatform().getName(), (String)null);
      this.addonName = "[" + context.getName() + "] ";
      this.setParent(context.getPlatform().getLogger());
      this.setLevel(Level.ALL);
   }

   public void log(@NotNull LogRecord logRecord) {
      logRecord.setMessage(this.addonName + logRecord.getMessage());
      super.log(logRecord);
   }
}
