package de.xxschrandxx.wsc.wsclinker.hytale.api.event;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.AbstractWSCPluginReloadEventHytale;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerPluginReloadEventHytale extends AbstractWSCPluginReloadEventHytale {
    public WSCLinkerPluginReloadEventHytale(ISender<?> sender) {
        super(sender);
    }    
}
