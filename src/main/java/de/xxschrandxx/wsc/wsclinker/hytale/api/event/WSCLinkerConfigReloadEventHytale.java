package de.xxschrandxx.wsc.wsclinker.hytale.api.event;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.AbstractWSCConfigReloadEventHytale;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerConfigReloadEventHytale extends AbstractWSCConfigReloadEventHytale {
    public WSCLinkerConfigReloadEventHytale(ISender<?> sender) {
        super(sender);
    }   
}
