package de.xxschrandxx.wsc.wsclinker.core.runnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.api.IMinecraftLinkerCoreAPI;

public class UnlinkedMessageRunnable  implements Runnable {
    private IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance;

    public UnlinkedMessageRunnable(IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance) {
        this.instance = instance;
    }
    public void run() {
        ArrayList<ISender<?>> players = instance.getAPI().getOnlineSender(instance);
        if (players.isEmpty()) {
            if (instance.getAPI().isDebugModeEnabled()) {
                instance.getAPI().log("No players online. Skipping UnlinkedMessage.");
            }
            return;
        }
        ArrayList<UUID> linked = new ArrayList<UUID>();
        try {
            linked = instance.getAPI().getLinkedUUIDs();
        } catch (IOException e) {
            if (instance.getAPI().isDebugModeEnabled()) {
                instance.getAPI().log("Could not get linked UUIDs.", e);
            }
        }
        if (linked.isEmpty()) {
            if (instance.getAPI().isDebugModeEnabled()) {
                instance.getAPI().log("Linked UUIDs empty.");
            }
            return;
        }
        for (ISender<?> sender : players) {
            if (linked.contains(sender.getUniqueId())) {
                continue;
            }
            sender.send(MinecraftLinkerVars.Configuration.LangUnlinkedMessage);
        }
    }
}
