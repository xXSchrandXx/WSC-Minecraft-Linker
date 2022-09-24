package de.xxschrandxx.wsc.wsclinker.core.runnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.core.api.IMinecraftLinkerCoreAPI;

public class UpdateNamesRunnable implements Runnable {
    private IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance;

    public UpdateNamesRunnable(IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance) {
        this.instance = instance;
    }
    public void run() {
        ArrayList<ISender<?>> players = instance.getAPI().getOnlineSender(instance);
        if (players.isEmpty()) {
            if (instance.getAPI().isDebugModeEnabled()) {
                instance.getAPI().log("No players online. Skipping UpdateNames.");
            }
            return;
        }
        HashMap<UUID, HashMap<String, String>> uuids = new HashMap<UUID, HashMap<String, String>>();
        for (ISender<?> sender : players) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("name", sender.getName());
            uuids.put(sender.getUniqueId(), options);
        }
        if (uuids.isEmpty()) {
            return;
        }
        try {
            instance.getAPI().sendNames(uuids);
        } catch (IOException e) {
            if (instance.getAPI().isDebugModeEnabled()) {
                instance.getAPI().log("Could no update names.", e);
            }
        }
    }
}
