package com.jodexindustries.donatecase.entitylib.ve;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Server;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUnloadChunk;
import com.jodexindustries.donatecase.entitylib.utils.Check;
import java.util.concurrent.atomic.AtomicBoolean;

final class ViewerEngineListener extends PacketListenerAbstract {
   private final ViewerEngine engine;

   ViewerEngineListener(ViewerEngine engine) {
      this.engine = engine;
   }

   public void onPacketSend(PacketSendEvent event) {
      PacketTypeCommon type = event.getPacketType();
      if (type == Server.UNLOAD_CHUNK) {
         PacketSendEvent copy = event.clone();
         this.engine.getExecutor().execute(() -> {
            WrapperPlayServerUnloadChunk packet = new WrapperPlayServerUnloadChunk(event);
            int chunkX = packet.getChunkX();
            int chunkZ = packet.getChunkZ();
            this.engine.getTracked0().forEach((entity) -> {
               if (Check.inChunk(entity.getLocation(), chunkX, chunkZ)) {
                  entity.removeViewer(event.getUser());
               }
            });
            copy.cleanUp();
         });
      }

      if (type == Server.CHUNK_DATA) {
         PacketSendEvent copy = event.clone();
         this.engine.getExecutor().execute(() -> {
            WrapperPlayServerUnloadChunk packet = new WrapperPlayServerUnloadChunk(event);
            int chunkX = packet.getChunkX();
            int chunkZ = packet.getChunkZ();
            this.engine.getTracked0().forEach((entity) -> {
               if (Check.inChunk(entity.getLocation(), chunkX, chunkZ)) {
                  if (!entity.hasViewer(event.getUser())) {
                     AtomicBoolean pass = new AtomicBoolean(false);
                     entity.getViewerRules().forEach((rule) -> pass.set(rule.shouldSee(event.getUser())));
                     this.engine.getViewerRules().forEach((rule) -> pass.set(rule.shouldSee(event.getUser())));
                     if (pass.get()) {
                        entity.addViewer(event.getUser());
                     }
                  }
               }
            });
            copy.cleanUp();
         });
      }

   }
}
