package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityHeadLook;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRotation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityVelocity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPassengers;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSystemChatMessage;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.TrackedEntity;
import com.jodexindustries.donatecase.entitylib.container.EntityContainer;
import com.jodexindustries.donatecase.entitylib.meta.EntityMeta;
import com.jodexindustries.donatecase.entitylib.meta.types.ObjectData;
import com.jodexindustries.donatecase.entitylib.tick.Tickable;
import com.jodexindustries.donatecase.entitylib.ve.ViewerRule;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class WrapperEntity implements Tickable, TrackedEntity {
   private final UUID uuid;
   private final int entityId;
   private EntityType entityType;
   private EntityMeta entityMeta;
   private boolean ticking;
   protected Location location;
   private Location preRidingLocation;
   protected final Set<UUID> viewers;
   private boolean onGround;
   private boolean spawned;
   private Vector3d velocity;
   private int riding;
   private final Set<Integer> passengers;
   private EntityContainer parent;
   private final List<ViewerRule> viewerRules;

   public WrapperEntity(int entityId, UUID uuid, EntityType entityType, EntityMeta entityMeta) {
      this.riding = -1;
      this.entityId = entityId;
      this.uuid = uuid;
      this.entityType = entityType;
      this.entityMeta = entityMeta;
      this.ticking = true;
      this.viewers = ConcurrentHashMap.newKeySet();
      this.passengers = ConcurrentHashMap.newKeySet();
      this.location = new Location((double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 0.0F);
      this.viewerRules = new CopyOnWriteArrayList();
   }

   public WrapperEntity(int entityId, EntityType entityType) {
      this(entityId, EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public WrapperEntity(UUID uuid, EntityType entityType) {
      this(EntityLib.getPlatform().getEntityIdProvider().provide(uuid, entityType), uuid, entityType);
   }

   public WrapperEntity(EntityType entityType) {
      this(EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
   }

   public WrapperEntity(int entityId, UUID uuid, EntityType entityType) {
      this(entityId, uuid, entityType, EntityMeta.createMeta(entityId, entityType));
   }

   public boolean spawn(Location location, EntityContainer parent) {
      if (this.spawned) {
         return false;
      } else {
         this.location = location;
         this.spawned = true;
         this.sendPacketToViewers(new WrapperPlayServerSpawnEntity(this.entityId, Optional.of(this.uuid), this.entityType, location.getPosition(), location.getPitch(), location.getYaw(), location.getYaw(), this.getObjectData(), this.createVeloPacket()));
         this.sendPacketToViewers(this.entityMeta.createPacket());
         this.parent = parent;
         parent.addEntity(this);
         return true;
      }
   }

   public boolean spawn(Location location) {
      return this.spawn(location, EntityLib.getApi().getDefaultContainer());
   }

   protected int getObjectData() {
      return this.entityMeta instanceof ObjectData ? ((ObjectData)this.entityMeta).getObjectData() : 0;
   }

   public Optional<Vector3d> createVeloPacket() {
      double veloX = (double)0.0F;
      double veloY = (double)0.0F;
      double veloZ = (double)0.0F;
      if (this.entityMeta instanceof ObjectData) {
         ObjectData od = (ObjectData)this.entityMeta;
         if (od.requiresVelocityPacketAtSpawn()) {
            WrapperPlayServerEntityVelocity veloPacket = this.getVelocityPacket();
            veloX = veloPacket.getVelocity().getX();
            veloY = veloPacket.getVelocity().getY();
            veloZ = veloPacket.getVelocity().getZ();
         }
      }

      Optional<Vector3d> velocity;
      if (veloX == (double)0.0F && veloY == (double)0.0F && veloZ == (double)0.0F) {
         velocity = Optional.empty();
      } else {
         velocity = Optional.of(new Vector3d(veloX, veloY, veloZ));
      }

      return velocity;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public void remove() {
      if (this.parent != null) {
         this.parent.removeEntity(this, true);
      } else {
         this.despawn();
      }

   }

   public void despawn() {
      if (this.spawned) {
         this.spawned = false;
         if (this instanceof WrapperPlayer) {
            WrapperPlayer p = (WrapperPlayer)this;
            this.sendPacketsToViewers(p.tabListRemovePacket());
         }

         this.sendPacketToViewers(new WrapperPlayServerDestroyEntities(this.entityId));
      }
   }

   public void teleport(@NotNull Location location, boolean onGround) {
      if (this.spawned) {
         this.location = location;
         this.onGround = onGround;
         this.sendPacketToViewers(new WrapperPlayServerEntityTeleport(this.entityId, location.getPosition(), location.getYaw(), location.getPitch(), onGround));
      }
   }

   public void teleport(@NotNull Location location) {
      this.teleport(location, this.onGround);
   }

   public void addViewer(UUID uuid) {
      if (this.viewers.add(uuid)) {
         if (this.location == null) {
            if (EntityLib.getApi().getSettings().isDebugMode()) {
               EntityLib.getPlatform().getLogger().warning("Location is null for entity " + this.entityId + ". Cannot spawn.");
            }

         } else {
            if (this.spawned) {
               if (this instanceof WrapperPlayer) {
                  WrapperPlayer p = (WrapperPlayer)this;
                  sendPacket(uuid, p.tabListPacket());
               }

               sendPacket(uuid, this.createSpawnPacket());
               sendPacket(uuid, this.entityMeta.createPacket());
            }

            if (EntityLib.getApi().getSettings().isDebugMode()) {
               EntityLib.getPlatform().getLogger().info("Added viewer " + uuid + " to entity " + this.entityId);
            }

         }
      }
   }

   public EntityContainer getParentContainer() {
      return this.parent;
   }

   public void sendMessageToViewers(Component message) {
      this.sendPacketToViewers(new WrapperPlayServerSystemChatMessage(false, message));
   }

   public void sendActionbarToViewers(Component message) {
      this.sendPacketToViewers(new WrapperPlayServerSystemChatMessage(true, message));
   }

   protected WrapperPlayServerSpawnEntity createSpawnPacket() {
      return new WrapperPlayServerSpawnEntity(this.entityId, Optional.of(this.uuid), this.entityType, this.location.getPosition(), this.location.getPitch(), this.location.getYaw(), this.location.getYaw(), this.getObjectData(), this.createVeloPacket());
   }

   public void addViewer(User user) {
      this.addViewer(user.getUUID());
   }

   public void addViewerSilently(UUID uuid) {
      this.viewers.add(uuid);
   }

   public void addViewerSilently(User user) {
      this.addViewerSilently(user.getUUID());
   }

   public void removeViewer(UUID uuid) {
      if (this.viewers.remove(uuid)) {
         if (this instanceof WrapperPlayer) {
            WrapperPlayer p = (WrapperPlayer)this;
            sendPacket(uuid, p.tabListRemovePacket());
         }

         sendPacket(uuid, new WrapperPlayServerDestroyEntities(this.entityId));
      }
   }

   public void removeViewer(User user) {
      this.removeViewer(user.getUUID());
   }

   public void removeViewerSilently(UUID uuid) {
      this.viewers.remove(uuid);
   }

   public void removeViewerSilently(User user) {
      this.removeViewerSilently(user.getUUID());
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public Vector3d getVelocity() {
      return this.velocity;
   }

   public void setVelocity(Vector3d velocity) {
      this.velocity = velocity;
      this.sendPacketToViewers(this.getVelocityPacket());
   }

   public double getX() {
      return this.location.getX();
   }

   public double getY() {
      return this.location.getY();
   }

   public double getZ() {
      return this.location.getZ();
   }

   public float getYaw() {
      return this.location.getYaw();
   }

   public float getPitch() {
      return this.location.getPitch();
   }

   public int getEntityId() {
      return this.entityId;
   }

   public EntityMeta getEntityMeta() {
      return this.entityMeta;
   }

   public <T extends EntityMeta> T getEntityMeta(@NotNull Class<T> metaClass) {
      return (T)(metaClass.cast(this.entityMeta));
   }

   public <T extends EntityMeta> void consumeEntityMeta(@NotNull Class<T> metaClass, Consumer<T> consumer) {
      T meta = this.getEntityMeta(metaClass);
      consumer.accept(meta);
   }

   public void consumeMeta(Consumer<EntityMeta> consumer) {
      consumer.accept(this.entityMeta);
   }

   public @NotNull UUID getUuid() {
      return this.uuid;
   }

   public EntityType getEntityType() {
      return this.entityType;
   }

   public Set<Integer> getPassengers() {
      return Collections.unmodifiableSet(this.passengers);
   }

   public WrapperEntity getRiding() {
      return EntityLib.getApi().getEntity(this.riding);
   }

   protected WrapperPlayServerSetPassengers createPassengerPacket() {
      return this.passengers.isEmpty() ? null : new WrapperPlayServerSetPassengers(this.entityId, this.passengers.stream().mapToInt((i) -> i).toArray());
   }

   public @UnmodifiableView Collection<ViewerRule> getViewerRules() {
      return Collections.unmodifiableCollection(this.viewerRules);
   }

   public void addViewerRule(@NotNull ViewerRule rule) {
      this.viewerRules.add(rule);
   }

   public void removeViewerRule(@NotNull ViewerRule rule) {
      this.viewerRules.remove(rule);
   }

   public void removeViewerRule(int index) {
      this.viewerRules.remove(index);
   }

   public void clearViewerRules() {
      this.viewerRules.clear();
   }

   public @Nullable ViewerRule getViewerRule(int index) {
      if (this.viewerRules.size() >= index - 1) {
         return null;
      } else {
         return index < 0 ? null : (ViewerRule)this.viewerRules.get(index);
      }
   }

   private WrapperPlayServerEntityVelocity getVelocityPacket() {
      Vector3d velocity = this.velocity.multiply((double)400.0F);
      return new WrapperPlayServerEntityVelocity(this.entityId, velocity);
   }

   public boolean isSpawned() {
      return this.spawned;
   }

   public boolean isTicking() {
      return this.ticking;
   }

   public void setTicking(boolean ticking) {
      this.ticking = ticking;
   }

   public boolean hasVelocity() {
      if (!this.isOnGround()) {
         return !this.velocity.equals(Vector3d.zero());
      } else {
         return Double.compare(this.velocity.x, (double)0.0F) != 0 || Double.compare(this.velocity.z, (double)0.0F) != 0 || this.velocity.y > (double)0.0F;
      }
   }

   public void rotateHead(float yaw, float pitch) {
      this.sendPacketsToViewersIfSpawned(new WrapperPlayServerEntityRotation(this.entityId, yaw, pitch, this.onGround), new WrapperPlayServerEntityHeadLook(this.entityId, yaw));
      this.location.setYaw(yaw);
      this.location.setPitch(pitch);
   }

   public void rotateHead(Location location) {
      this.rotateHead(location.getYaw(), location.getPitch());
   }

   public void rotateHead(WrapperEntity entity) {
      this.rotateHead(entity.getLocation());
   }

   public void refresh() {
      if (this.spawned) {
         this.sendPacketToViewers(this.entityMeta.createPacket());
         this.sendPacketToViewers(this.createPassengerPacket());
      }
   }

   public void sendPacketToViewers(PacketWrapper<?> packet) {
      this.viewers.forEach((uuid) -> sendPacket(uuid, packet));
   }

   public void sendPacketsToViewers(PacketWrapper<?>... wrappers) {
      for(PacketWrapper<?> wrapper : wrappers) {
         this.sendPacketToViewers(wrapper);
      }

   }

   public void sendPacketToViewersIfSpawned(PacketWrapper<?> packet) {
      if (this.spawned) {
         this.sendPacketToViewers(packet);
      }

   }

   public void sendPacketsToViewersIfSpawned(PacketWrapper<?>... wrappers) {
      if (this.spawned) {
         this.sendPacketsToViewers(wrappers);
      }

   }

   private static void sendPacket(UUID user, PacketWrapper<?> wrapper) {
      if (wrapper != null) {
         Object channel = EntityLib.getApi().getPacketEvents().getProtocolManager().getChannel(user);
         if (channel == null) {
            if (EntityLib.getApi().getSettings().isDebugMode()) {
               EntityLib.getPlatform().getLogger().warning("Failed to send packet to " + user + " because the channel was null. They may be disconnected/not online.");
            }

         } else {
            EntityLib.getApi().getPacketEvents().getProtocolManager().sendPacket(channel, wrapper);
         }
      }
   }

   public boolean hasNoGravity() {
      return this.entityMeta.hasNoGravity();
   }

   public void setHasNoGravity(boolean hasNoGravity) {
      this.entityMeta.setHasNoGravity(hasNoGravity);
      this.refresh();
   }

   public void addPassenger(int passenger) {
      if (this.passengers.contains(passenger)) {
         throw new IllegalArgumentException("Passenger already exists");
      } else {
         this.passengers.add(passenger);
         this.sendPacketToViewers(this.createPassengerPacket());
         WrapperEntity e = EntityLib.getApi().getEntity(passenger);
         if (e != null) {
            e.riding = this.entityId;
            e.preRidingLocation = e.location;
         }

      }
   }

   public @Nullable Location getPreRidingLocation() {
      return this.preRidingLocation;
   }

   public int getRidingId() {
      return this.riding;
   }

   public void addPassengers(int... passengers) {
      for(int passenger : passengers) {
         this.addPassenger(passenger);
      }

   }

   public void addPassenger(WrapperEntity passenger) {
      this.addPassenger(passenger.getEntityId());
   }

   public void addPassengers(WrapperEntity... passengers) {
      for(WrapperEntity passenger : passengers) {
         this.addPassenger(passenger);
      }

   }

   public void removePassenger(int passenger) {
      if (!this.passengers.contains(passenger)) {
         throw new IllegalArgumentException("Passenger does not exist");
      } else {
         this.passengers.remove(passenger);
         this.sendPacketToViewers(this.createPassengerPacket());
         WrapperEntity e = EntityLib.getApi().getEntity(passenger);
         if (e != null) {
            e.riding = -1;
            e.teleport(e.preRidingLocation, e.onGround);
         }

      }
   }

   public boolean hasPassenger(int passenger) {
      return this.passengers.contains(passenger);
   }

   public boolean hasPassenger(WrapperEntity passenger) {
      return this.hasPassenger(passenger.getEntityId());
   }

   public void removePassengers(int... passengers) {
      for(int passenger : passengers) {
         this.removePassenger(passenger);
      }

   }

   public void removePassenger(WrapperEntity passenger) {
      this.removePassenger(passenger.getEntityId());
   }

   public void removePassengers(WrapperEntity... passengers) {
      for(WrapperEntity passenger : passengers) {
         this.removePassenger(passenger);
      }

   }

   public boolean isRiding() {
      return this.riding != -1;
   }

   public @NotNull Set<UUID> getViewers() {
      return Collections.unmodifiableSet(this.viewers);
   }

   public boolean hasViewer(UUID uuid) {
      return this.viewers.contains(uuid);
   }

   public boolean hasViewer(User user) {
      return this.hasViewer(user.getUUID());
   }

   public Location getLocation() {
      return this.location;
   }

   public void tick(long time) {
      if (this.isRiding()) {
         WrapperEntity riding = this.getRiding();
         if (riding != null) {
            Location l = riding.getLocation();
            this.location = new Location(l.getX(), l.getY() + (double)1.0F, l.getZ(), l.getYaw(), l.getPitch());
         }
      }

   }
}
