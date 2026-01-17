package de.xxschrandxx.wsc.wsclinker.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.WSCBridgePluginReloadEventBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.MinecraftLinkerBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.event.WSCLinkerPluginReloadEventBukkit;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;

public class WSCLinkerPluginReloadListenerBukkit implements Listener {
    @EventHandler
    public void onPluginReload(WSCBridgePluginReloadEventBukkit event) {
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
        String apiStart = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPIStart);
        event.getSender().sendMessage(apiStart);
        instance.loadAPI(event.getSender());
        String apiSuccess = instance.getConfiguration().getString(LinkerVars.Configuration.LangCmdReloadAPISuccess);
        event.getSender().sendMessage(apiSuccess);
        instance.getServer().getPluginManager().callEvent(new WSCLinkerPluginReloadEventBukkit(event.getSender()));
    }
}
