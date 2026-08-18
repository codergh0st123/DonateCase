package com.jodexindustries.donatecase.api.data;

import com.jodexindustries.donatecase.api.data.animation.Animation;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.util.UUID;
import lombok.Generated;

public class ActiveCase {
   private final UUID uuid;
   private final CaseLocation block;
   private final DCPlayer player;
   private final CaseDataItem winItem;
   private final String caseType;
   private final Animation animation;
   private boolean locked;
   private boolean keyRemoved;

   public ActiveCase(UUID uuid, CaseLocation block, DCPlayer player, CaseDataItem winItem, String caseType, Animation animation) {
      this.uuid = uuid;
      this.block = block;
      this.player = player;
      this.winItem = winItem;
      this.caseType = caseType;
      this.animation = animation;
   }

   @Generated
   public UUID uuid() {
      return this.uuid;
   }

   @Generated
   public CaseLocation block() {
      return this.block;
   }

   @Generated
   public DCPlayer player() {
      return this.player;
   }

   @Generated
   public CaseDataItem winItem() {
      return this.winItem;
   }

   @Generated
   public String caseType() {
      return this.caseType;
   }

   @Generated
   public Animation animation() {
      return this.animation;
   }

   @Generated
   public boolean locked() {
      return this.locked;
   }

   @Generated
   public boolean keyRemoved() {
      return this.keyRemoved;
   }

   @Generated
   public ActiveCase locked(boolean locked) {
      this.locked = locked;
      return this;
   }

   @Generated
   public ActiveCase keyRemoved(boolean keyRemoved) {
      this.keyRemoved = keyRemoved;
      return this;
   }
}
