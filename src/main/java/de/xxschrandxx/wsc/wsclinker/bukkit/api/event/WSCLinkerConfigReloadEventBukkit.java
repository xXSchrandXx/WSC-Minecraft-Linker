package de.xxschrandxx.wsc.wsclinker.bukkit.api.event;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.AbstractWSCConfigReloadEventBukkit;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerConfigReloadEventBukkit extends AbstractWSCConfigReloadEventBukkit {
    public WSCLinkerConfigReloadEventBukkit(ISender<?> sender) {
        super(sender);
    }   
}
