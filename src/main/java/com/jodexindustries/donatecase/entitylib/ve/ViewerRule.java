package com.jodexindustries.donatecase.entitylib.ve;

import com.github.retrooper.packetevents.protocol.player.User;

@FunctionalInterface
public interface ViewerRule {
   boolean shouldSee(User var1);
}
