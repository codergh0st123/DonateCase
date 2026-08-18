package com.jodexindustries.donatecase.spigot.hook.packetevents;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.jodexindustries.donatecase.entitylib.APIConfig;
import com.jodexindustries.donatecase.entitylib.EntityLib;
import com.jodexindustries.donatecase.entitylib.spigot.SpigotEntityLibPlatform;
import com.jodexindustries.donatecase.spigot.BukkitBackend;
import lombok.Generated;
import org.bukkit.Bukkit;

public class PacketEventsSupport {

    private final PacketEventsAPI<?> api = PacketEvents.getAPI();
    private final PacketEventsPacketListener listener = new PacketEventsPacketListener();
    private final BukkitBackend backend;
    private boolean usePackets;
    private PacketListenerCommon registeredListener;

    public PacketEventsSupport(BukkitBackend backend) {
        this.backend = backend;
        this.usePackets = backend.getAPI().getConfigManager().getConfig().usePackets();
        load();
    }

    public void load() {
        if (!usePackets) {
            return;
        }

        ServerVersion version = getServerVersion();
        backend.getLogger().info("Loading PacketEvents support...");
        backend.getLogger().info("Server version: " + version.getReleaseName());
        backend.getLogger().info("Server protocol version: " + version.getProtocolVersion());

        if (version.isOlderThan(ServerVersion.V_1_18)) {
            backend.getLogger().warning("Server version older than V_1_18. PacketEvents support disabled!");
            usePackets = false;
            return;
        }

        if (api == null || !api.isLoaded()) {
            backend.getLogger().warning("PacketEvents is not fully loaded. Packet mode disabled for DonateCase.");
            usePackets = false;
            return;
        }

        try {
            SpigotEntityLibPlatform platform = new SpigotEntityLibPlatform(backend.getPlugin());
            APIConfig settings = new APIConfig(api).tickTickables().trackPlatformEntities();
            EntityLib.init(platform, settings);
            registeredListener = api.getEventManager().registerListener(listener, PacketListenerPriority.NORMAL);
            backend.getLogger().info("DonateCase is using the existing PacketEvents instance.");
        } catch (Throwable exception) {
            backend.getLogger().warning("Packet mode disabled: " + exception.getMessage());
            usePackets = false;
        }
    }

    public void unload() {
        if (registeredListener != null) {
            api.getEventManager().unregisterListener(registeredListener);
            registeredListener = null;
        }

        usePackets = false;
    }

    private ServerVersion getServerVersion() {
        String bukkitVersion = Bukkit.getBukkitVersion();

        for (ServerVersion value : ServerVersion.reversedValues()) {
            if (bukkitVersion.contains(value.getReleaseName())) {
                return value;
            }
        }

        return ServerVersion.ERROR;
    }

    @Generated
    public boolean isUsePackets() {
        return usePackets;
    }
}
