package de.xxschrandxx.wsc.wsclinker.hytale.listener;

import com.hypixel.hytale.server.core.HytaleServer;

import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgeConfigReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.hytale.HytaleLinker;
import de.xxschrandxx.wsc.wsclinker.hytale.api.event.WSCLinkerConfigReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;

public class WSCLinkerConfigReloadListenerHytale {
    public void execute(WSCBridgeConfigReloadEventHytale event) {
        HytaleLinker instance = HytaleLinker.getInstance();
        String configStart = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadConfigStart);
        event.getSender().sendMessage(configStart);
        if (!instance.reloadConfiguration(event.getSender())) {
            String configError = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadConfigError);
            event.getSender().sendMessage(configError);
            instance.getBridgeLogger().warn("Could not load config.yml!");
            return;
        }
        String configSuccess = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadConfigSuccess);
        event.getSender().sendMessage(configSuccess);
        HytaleServer.get().getEventBus().dispatchFor(WSCLinkerConfigReloadEventHytale.class).dispatch(new WSCLinkerConfigReloadEventHytale(event.getSender()));
    }
}
