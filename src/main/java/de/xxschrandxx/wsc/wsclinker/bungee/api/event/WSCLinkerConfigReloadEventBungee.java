package de.xxschrandxx.wsc.wsclinker.bungee.api.event;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.AbstractWSCConfigReloadEventBungee;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerConfigReloadEventBungee extends AbstractWSCConfigReloadEventBungee {
    public WSCLinkerConfigReloadEventBungee(ISender<?> sender) {
        super(sender);
    }   
}
