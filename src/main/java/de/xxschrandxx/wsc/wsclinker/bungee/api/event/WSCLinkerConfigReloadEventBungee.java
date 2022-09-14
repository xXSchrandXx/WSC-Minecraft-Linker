package de.xxschrandxx.wsc.wsclinker.bungee.api.event;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.WSCBridgeConfigReloadEventBungee;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public class WSCLinkerConfigReloadEventBungee extends WSCBridgeConfigReloadEventBungee {
    public WSCLinkerConfigReloadEventBungee(ISender<?> sender) {
        super(sender);
    }   
}
