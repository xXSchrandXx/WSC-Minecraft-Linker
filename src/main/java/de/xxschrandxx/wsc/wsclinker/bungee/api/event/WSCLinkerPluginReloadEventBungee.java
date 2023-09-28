package de.xxschrandxx.wsc.wsclinker.bungee.api.event;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.AbstractWSCPluginReloadEventBungee;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerPluginReloadEventBungee extends AbstractWSCPluginReloadEventBungee {
    public WSCLinkerPluginReloadEventBungee(ISender<?> sender) {
        super(sender);
    }    
}
