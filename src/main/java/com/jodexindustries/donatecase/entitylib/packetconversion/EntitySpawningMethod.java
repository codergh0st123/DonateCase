package com.jodexindustries.donatecase.entitylib.packetconversion;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;

public interface EntitySpawningMethod {
   EntitySpawningMethod EXPERIENCE_ORB = new CommonSpawningMethods.ExperienceOrb();

   PacketWrapper<?> getSpawnPacket(WrapperEntity var1);
}
