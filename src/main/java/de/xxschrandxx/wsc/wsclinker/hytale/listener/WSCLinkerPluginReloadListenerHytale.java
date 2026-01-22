package de.xxschrandxx.wsc.wsclinker.hytale.listener;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgePluginReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import de.xxschrandxx.wsc.wsclinker.hytale.HytaleLinker;

public class WSCLinkerPluginReloadListenerHytale {
    public void execute(WSCBridgePluginReloadEventHytale event) {
        HytaleLinker instance = HytaleLinker.getInstance();
        String apiStart = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPIStart);
        event.getSender().sendMessage(apiStart);
        instance.loadAPI(event.getSender());
        String apiSuccess = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPISuccess);
        event.getSender().sendMessage(apiSuccess);
    }
}
