package com.jodexindustries.donatecase.entitylib.meta;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.EntityLibAPI;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Metadata {
   private final int entityId;
   private volatile boolean notifyAboutChanges = true;
   private final Map<Byte, EntityData> notNotifiedChanges = new HashMap<>();
   private final Map<Byte, EntityData> metadataMap = new ConcurrentHashMap<>();

   public Metadata(int entityId) {
      this.entityId = entityId;
   }

   public <T> T getIndex(byte index, @Nullable T defaultValue) {
      EntityData value = (EntityData)this.metadataMap.get(index);
      return (T)(value != null ? value.getValue() : defaultValue);
   }

   public <T> void setIndex(byte index, @NotNull EntityDataType<T> dataType, T value) {
      EntityData entry = new EntityData(index, dataType, value);
      this.metadataMap.put(index, entry);
      Optional<EntityLibAPI<?>> optionalApi = EntityLib.getOptionalApi();
      if (optionalApi.isPresent()) {
         WrapperEntity entity = ((EntityLibAPI)optionalApi.get()).getEntity(this.entityId);
         if (entity != null && entity.isSpawned()) {
            if (!this.notifyAboutChanges) {
               synchronized(this.notNotifiedChanges) {
                  this.notNotifiedChanges.put(index, entry);
               }
            } else {
               entity.sendPacketToViewers(this.createPacket());
            }

         }
      }
   }

   public void setNotifyAboutChanges(boolean notifyAboutChanges) {
      if (this.notifyAboutChanges != notifyAboutChanges) {
         List<EntityData> entries = null;
         synchronized(this.notNotifiedChanges) {
            this.notifyAboutChanges = notifyAboutChanges;
            if (notifyAboutChanges) {
               entries = new ArrayList<>(this.notNotifiedChanges.values());
               if (entries.isEmpty()) {
                  return;
               }

               this.notNotifiedChanges.clear();
            }
         }

         WrapperEntity entity = EntityLib.getApi().getEntity(this.entityId);
         if (entries != null && entity != null && entity.isSpawned()) {
            WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(this.entityId, entries);
            entity.sendPacketsToViewers(packet);
         }
      }
   }

   public void setMetaFromPacket(WrapperPlayServerEntityMetadata wrapper) {
      for(EntityData data : wrapper.getEntityMetadata()) {
         this.metadataMap.put((byte)data.getIndex(), data);
      }

   }

   public boolean isNotifyingChanges() {
      return this.notifyAboutChanges;
   }

   @NotNull List<EntityData> getEntries() {
      return Collections.unmodifiableList(new ArrayList<>(this.metadataMap.values()));
   }

   public WrapperPlayServerEntityMetadata createPacket() {
      return new WrapperPlayServerEntityMetadata(this.entityId, this.getEntries());
   }
}
