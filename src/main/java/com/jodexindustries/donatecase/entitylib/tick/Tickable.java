package com.jodexindustries.donatecase.entitylib.tick;

public interface Tickable {
   boolean isTicking();

   void setTicking(boolean var1);

   void tick(long var1);
}
