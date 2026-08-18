package com.jodexindustries.donatecase.entitylib.utils;

import com.github.retrooper.packetevents.util.PEVersion;

public final class ELVersions {
   public static final String RAW = "2.4.10+8a62a42-SNAPSHOT";
   public static final PEVersion CURRENT = new PEVersion(2, 4, 10, true);
   public static final PEVersion UNKNOWN = new PEVersion(0, 0, 0);

   private ELVersions() {
      throw new IllegalStateException();
   }
}
