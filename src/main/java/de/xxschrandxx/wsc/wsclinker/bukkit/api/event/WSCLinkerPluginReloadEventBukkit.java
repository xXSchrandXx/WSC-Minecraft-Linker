package de.xxschrandxx.wsc.wsclinker.bukkit.api.event;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.AbstractWSCPluginReloadEventBukkit;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;

public final class WSCLinkerPluginReloadEventBukkit extends AbstractWSCPluginReloadEventBukkit {
    public WSCLinkerPluginReloadEventBukkit(ISender<?> sender) {
        super(sender);
    }    
}
