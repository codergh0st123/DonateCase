package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate.Action;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.text.Component;

public class WrapperPlayer extends WrapperLivingEntity {
   private UserProfile profile;
   private GameMode gameMode;
   private Component displayName;
   private boolean tablist;
   private int latency;

   public WrapperPlayer(UserProfile profile, int entityId) {
      super(entityId, profile.getUUID(), EntityTypes.PLAYER, EntityMeta.createMeta(entityId, EntityTypes.PLAYER));
      this.gameMode = GameMode.CREATIVE;
      this.tablist = true;
      this.latency = -1;
      this.profile = profile;
   }

   public WrapperPlayServerPlayerInfoUpdate tabListPacket() {
      EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions = EnumSet.of(Action.ADD_PLAYER, Action.UPDATE_LISTED);
      return new WrapperPlayServerPlayerInfoUpdate(actions, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo[]{this.createInfo()});
   }

   public List<TextureProperty> getTextures() {
      return this.profile.getTextureProperties();
   }

   public WrapperPlayServerPlayerInfoUpdate tabListRemovePacket() {
      return new WrapperPlayServerPlayerInfoUpdate(EnumSet.of(Action.UPDATE_LISTED), new WrapperPlayServerPlayerInfoUpdate.PlayerInfo[]{this.createInfo()});
   }

   public void setGameMode(GameMode gameMode) {
      this.gameMode = gameMode;
      this.sendPacketsToViewersIfSpawned(new PacketWrapper[]{new WrapperPlayServerPlayerInfoUpdate(Action.UPDATE_GAME_MODE, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo[]{this.createInfo()})});
   }

   public void setDisplayName(Component displayName) {
      this.displayName = displayName;
      this.sendPacketsToViewersIfSpawned(new PacketWrapper[]{new WrapperPlayServerPlayerInfoUpdate(Action.UPDATE_DISPLAY_NAME, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo[]{this.createInfo()})});
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   public String getUsername() {
      return this.profile.getName();
   }

   public List<TextureProperty> getTextureProperties() {
      return this.profile.getTextureProperties();
   }

   public void setTextureProperties(List<TextureProperty> textureProperties) {
      this.profile.setTextureProperties(textureProperties);
      if (this.isSpawned()) {
         WrapperPlayServerDestroyEntities destroyEntities = new WrapperPlayServerDestroyEntities(this.getEntityId());
         WrapperPlayServerPlayerInfoRemove removePacket = new WrapperPlayServerPlayerInfoRemove(new UUID[]{this.getUuid()});
         WrapperPlayServerPlayerInfoUpdate updatePacket = this.tabListPacket();
         this.sendPacketToViewers(removePacket);
         this.sendPacketToViewers(destroyEntities);
         this.sendPacketToViewers(updatePacket);

         for(UUID viewer : this.viewers) {
            this.removeViewer(viewer);
            this.addViewer(viewer);
         }

      }
   }

   public GameMode getGameMode() {
      return this.gameMode;
   }

   public boolean isInTablist() {
      return this.tablist;
   }

   public void setInTablist(boolean tablist) {
      this.tablist = tablist;
      this.sendPacketsToViewersIfSpawned(new PacketWrapper[]{this.tabListPacket()});
   }

   public int getLatency() {
      return this.latency;
   }

   public void setLatency(int latency) {
      this.latency = latency;
      this.sendPacketsToViewersIfSpawned(new PacketWrapper[]{new WrapperPlayServerPlayerInfoUpdate(Action.UPDATE_LATENCY, new WrapperPlayServerPlayerInfoUpdate.PlayerInfo[]{this.createInfo()})});
   }

   protected WrapperPlayServerPlayerInfoUpdate.PlayerInfo createInfo() {
      return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(this.profile, this.tablist, this.latency, this.gameMode, this.displayName, (RemoteChatSession)null);
   }
}
