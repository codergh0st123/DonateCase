package com.jodexindustries.donatecase.entitylib.meta;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.data.EntityMetadataProvider;
import com.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.extras.InvalidVersionException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityMeta implements EntityMetadataProvider {
   private static final MetaConverterRegistry registry = new MetaConverterRegistry();
   private static final Map<Integer, EntityMeta> metaMap = new ConcurrentHashMap<>();
   public static final byte OFFSET = 0;
   public static final byte MAX_OFFSET = 8;
   private static final byte ON_FIRE_BIT = 1;
   private static final byte CROUCHING_BIT = 2;
   private static final byte SPRINTING_BIT = 8;
   private static final byte SWIMMING_BIT = 16;
   private static final byte INVISIBLE_BIT = 32;
   private static final byte HAS_GLOWING_EFFECT_BIT = 64;
   private static final byte FLYING_WITH_ELYTRA_BIT = -128;
   protected final int entityId;
   protected final Metadata metadata;

   public static @NotNull BiFunction<Integer, Metadata, EntityMeta> getConverter(EntityType entityType) {
      return registry.get(entityType);
   }

   public static @NotNull Class<? extends EntityMeta> getMetaClass(EntityType entityType) {
      return registry.getMetaClass(entityType);
   }

   public static @NotNull EntityMeta createMeta(int entityId, EntityType entityType) {
      Metadata metadata = new Metadata(entityId);
      BiFunction<Integer, Metadata, EntityMeta> converter = getConverter(entityType);
      EntityMeta entityMeta = (EntityMeta)converter.apply(entityId, metadata);
      metaMap.put(entityId, entityMeta);
      return entityMeta;
   }

   public static @Nullable EntityMeta getMeta(int entityId) {
      return (EntityMeta)metaMap.get(entityId);
   }

   public EntityMeta(int entityId, Metadata metadata) {
      this.entityId = entityId;
      this.metadata = metadata;
   }

   public void setNotifyAboutChanges(boolean notifyAboutChanges) {
      this.metadata.setNotifyAboutChanges(notifyAboutChanges);
   }

   public boolean isNotifyingChanges() {
      return this.metadata.isNotifyingChanges();
   }

   public boolean isOnFire() {
      return this.getMaskBit((byte)0, (byte)1);
   }

   public void setOnFire(boolean value) {
      this.setMaskBit(0, (byte)1, value);
   }

   public boolean isSneaking() {
      return this.getMaskBit((byte)0, (byte)2);
   }

   public void setSneaking(boolean value) {
      this.setMaskBit(0, (byte)2, value);
   }

   public boolean isSprinting() {
      return this.getMaskBit((byte)0, (byte)8);
   }

   public void setSprinting(boolean value) {
      this.setMaskBit(0, (byte)8, value);
   }

   public boolean isInvisible() {
      return this.getMaskBit((byte)0, (byte)32);
   }

   public void setInvisible(boolean value) {
      this.setMaskBit(0, (byte)32, value);
   }

   public boolean hasGlowingEffect() {
      return this.getMaskBit((byte)0, (byte)64);
   }

   public boolean isGlowing() {
      return this.hasGlowingEffect();
   }

   public void setHasGlowingEffect(boolean value) {
      this.setMaskBit(0, (byte)64, value);
   }

   public void setGlowing(boolean value) {
      this.setHasGlowingEffect(value);
   }

   public boolean isSwimming() {
      return this.getMaskBit((byte)0, (byte)16);
   }

   public void setSwimming(boolean value) {
      this.setMaskBit(0, (byte)16, value);
   }

   public boolean isFlyingWithElytra() {
      return this.getMaskBit((byte)0, (byte)-128);
   }

   public void setFlyingWithElytra(boolean value) {
      this.setMaskBit(0, (byte)-128, value);
   }

   public short getAirTicks() {
      return (Short)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.airTicksOffset(), (short)300);
   }

   public void setAirTicks(short value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.airTicksOffset(), EntityDataTypes.SHORT, value);
   }

   public Component getCustomName() {
      Optional<Component> component = (Optional)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.customNameOffset(), Optional.empty());
      return (Component)component.orElse(null);
   }

   public void setCustomName(Component value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.customNameOffset(), EntityDataTypes.OPTIONAL_ADV_COMPONENT, Optional.ofNullable(value));
   }

   public boolean isCustomNameVisible() {
      return (Boolean)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.customNameVisibleOffset(), false);
   }

   public void setCustomNameVisible(boolean value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.customNameVisibleOffset(), EntityDataTypes.BOOLEAN, value);
   }

   public boolean isSilent() {
      return (Boolean)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.silentOffset(), false);
   }

   public void setSilent(boolean value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.silentOffset(), EntityDataTypes.BOOLEAN, value);
   }

   public boolean hasNoGravity() {
      return (Boolean)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.hasNoGravityOffset(), true);
   }

   public void setHasNoGravity(boolean value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.hasNoGravityOffset(), EntityDataTypes.BOOLEAN, value);
   }

   public EntityPose getPose() {
      return (EntityPose)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.poseOffset(), EntityPose.STANDING);
   }

   public void setPose(EntityPose value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.poseOffset(), EntityDataTypes.ENTITY_POSE, value);
   }

   public int getTicksFrozenInPowderedSnow() {
      return (Integer)this.metadata.getIndex(MetaOffsetConverter.EntityMetaOffsets.ticksFrozenInPowderedSnowOffset(), 0);
   }

   public void setTicksFrozenInPowderedSnow(int value) {
      this.metadata.setIndex(MetaOffsetConverter.EntityMetaOffsets.ticksFrozenInPowderedSnowOffset(), EntityDataTypes.INT, value);
   }

   public WrapperPlayServerEntityMetadata createPacket() {
      return this.metadata.createPacket();
   }

   protected static void isVersionNewer(ServerVersion version) {
      if (EntityLib.getOptionalApi().isPresent() && !EntityLib.getApi().getPacketEvents().getServerManager().getVersion().is(VersionComparison.NEWER_THAN, version)) {
         throw new InvalidVersionException("This method is only available for versions newer than " + version.name() + ".");
      } else if (!PacketEvents.getAPI().getServerManager().getVersion().is(VersionComparison.NEWER_THAN, version)) {
         throw new InvalidVersionException("This method is only available for versions newer than " + version.name() + ".");
      }
   }

   protected static boolean isVersion(ServerVersion version, VersionComparison comparison) {
      return EntityLib.getOptionalApi().isPresent() ? EntityLib.getApi().getPacketEvents().getServerManager().getVersion().is(comparison, version) : PacketEvents.getAPI().getServerManager().getVersion().is(comparison, version);
   }

   protected static boolean isVersion(ServerVersion version) {
      return EntityLib.getOptionalApi().isPresent() ? EntityLib.getApi().getPacketEvents().getServerManager().getVersion().is(VersionComparison.EQUALS, version) : PacketEvents.getAPI().getServerManager().getVersion().is(VersionComparison.EQUALS, version);
   }

   protected static byte offset(byte value, int amount) {
      return (byte)(value + amount);
   }

   public <T> void setIndex(byte index, @NotNull EntityDataType<T> dataType, T value) {
      this.metadata.setIndex(index, dataType, value);
   }

   public <T> T getIndex(byte index, @Nullable T defaultValue) {
      return (T)this.metadata.getIndex(index, defaultValue);
   }

   public byte getMask(byte index) {
      return (Byte)this.metadata.getIndex(index, (byte)0);
   }

   public void setMask(byte index, byte mask) {
      this.metadata.setIndex(index, EntityDataTypes.BYTE, mask);
   }

   public boolean getMaskBit(byte index, byte bit) {
      return (this.getMask(index) & bit) == bit;
   }

   public void setMaskBit(int index, byte bit, boolean value) {
      byte mask = this.getMask((byte)index);
      boolean currentValue = (mask & bit) == bit;
      if (currentValue != value) {
         if (value) {
            mask = (byte)(mask | bit);
         } else {
            mask = (byte)(mask & (byte)(~bit));
         }

         this.setMask((byte)index, mask);
      }
   }

   public List<EntityData> entityData(ClientVersion clientVersion) {
      return this.metadata.getEntries();
   }

   public List<EntityData> entityData() {
      return this.metadata.getEntries();
   }
}
