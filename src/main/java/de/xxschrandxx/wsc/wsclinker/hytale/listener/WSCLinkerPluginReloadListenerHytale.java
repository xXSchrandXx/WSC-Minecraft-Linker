package de.xxschrandxx.wsc.wsclinker.hytale.listener;

import com.hypixel.hytale.server.core.HytaleServer;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgePluginReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import de.xxschrandxx.wsc.wsclinker.hytale.HytaleLinker;
import de.xxschrandxx.wsc.wsclinker.hytale.api.event.WSCLinkerPluginReloadEventHytale;

public class WSCLinkerPluginReloadListenerHytale {
    public void execute(WSCBridgePluginReloadEventHytale event) {
        HytaleLinker instance = HytaleLinker.getInstance();
        String apiStart = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPIStart);
        event.getSender().sendMessage(apiStart);
        instance.loadAPI(event.getSender());
        String apiSuccess = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPISuccess);
        event.getSender().sendMessage(apiSuccess);
        HytaleServer.get().getEventBus().dispatchFor(WSCLinkerPluginReloadEventHytale.class).dispatch(new WSCLinkerPluginReloadEventHytale(event.getSender()));
    }
}
