package de.xxschrandxx.wsc.wsclinker.bungee.listener;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.WSCBridgeModuleEventBungee;
import de.xxschrandxx.wsc.wsclinker.core.listener.AddModuleListenerCore;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class AddModuleListenerBungee extends AddModuleListenerCore implements Listener {
    @EventHandler
    public void addModuleListener(WSCBridgeModuleEventBungee event) {
        event.addModule(name);
    }
}
