package de.xxschrandxx.wsc.wsclinker.bungee.listener;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.WSCBridgePluginReloadEventBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.MinecraftLinkerBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.api.event.WSCLinkerPluginReloadEventBungee;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class WSCLinkerPluginReloadListenerBungee implements Listener {
    @EventHandler
    public void onPluginReload(WSCBridgePluginReloadEventBungee event) {
        MinecraftLinkerBungee instance = MinecraftLinkerBungee.getInstance();
        String apiStart = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPIStart);
        event.getSender().sendMessage(apiStart);
        instance.loadAPI(event.getSender());
        String apiSuccess = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPISuccess);
        event.getSender().sendMessage(apiSuccess);
        instance.getProxy().getPluginManager().callEvent(new WSCLinkerPluginReloadEventBungee(event.getSender()));
    }
}
