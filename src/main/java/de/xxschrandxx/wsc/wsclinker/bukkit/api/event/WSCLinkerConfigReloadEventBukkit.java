package de.xxschrandxx.wsc.wsclinker.bukkit.api.event;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.WSCBridgeConfigReloadEventBukkit;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public class WSCLinkerConfigReloadEventBukkit extends WSCBridgeConfigReloadEventBukkit {
    public WSCLinkerConfigReloadEventBukkit(ISender<?> sender) {
        super(sender);
    }   
}
