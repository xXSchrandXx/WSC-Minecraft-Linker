package de.xxschrandxx.wsc.wsclinker.bukkit.api.event;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.WSCBridgePluginReloadEventBukkit;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public class WSCLinkerPluginReloadEventBukkit extends WSCBridgePluginReloadEventBukkit {
    public WSCLinkerPluginReloadEventBukkit(ISender<?> sender) {
        super(sender);
    }    
}
