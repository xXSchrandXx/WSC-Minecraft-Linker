package de.xxschrandxx.wsc.wsclinker.bungee.api.event;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.WSCBridgePluginReloadEventBungee;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public class WSCLinkerPluginReloadEventBungee extends WSCBridgePluginReloadEventBungee {
    public WSCLinkerPluginReloadEventBungee(ISender<?> sender) {
        super(sender);
    }    
}
