package com.jodexindustries.donatecase.spigot.hook.packetevents;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.armorstand.EquipmentSlot;
import com.jodexindustries.donatecase.api.event.player.ArmorStandCreatorInteractEvent;
import com.jodexindustries.donatecase.spigot.tools.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketEventsPacketListener implements PacketListener {
   public void onPacketReceive(PacketReceiveEvent event) {
      User user = event.getUser();
      if (event.getPacketType() == Client.INTERACT_ENTITY) {
         WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
         int entityId = packet.getEntityId();
         ArmorStandCreator creator = (ArmorStandCreator)ArmorStandCreator.armorStands.get(entityId);
         if (creator != null) {
            Player player = Bukkit.getPlayer(user.getUUID());
            if (player != null) {
               EquipmentSlot hand = packet.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
               DCAPI.getInstance().getEventBus().post(new ArmorStandCreatorInteractEvent(BukkitUtils.fromBukkit(player), creator, hand));
            }
         }
      }
   }
}
