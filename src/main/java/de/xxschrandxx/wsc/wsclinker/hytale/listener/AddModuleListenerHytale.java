package de.xxschrandxx.wsc.wsclinker.hytale.listener;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgeModuleEventHytale;
import de.xxschrandxx.wsc.wsclinker.core.listener.AddModuleListenerCore;

public class AddModuleListenerHytale extends AddModuleListenerCore {
    public void execute(WSCBridgeModuleEventHytale event) {
        event.addModule(name);
    }
}
