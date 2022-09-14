package de.xxschrandxx.wsc.wsclinker.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.xxschrandxx.wsc.wsclinker.bukkit.MinecraftLinkerBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.event.WSCLinkerPluginReloadEventBukkit;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;

public class WSCBridgePluginReloadListenerBukkit implements Listener {
    @EventHandler
    public void onPluginReload(WSCLinkerPluginReloadEventBukkit event) {
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
        String apiStart = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdReloadAPIStart);
        event.getSender().sendMessage(apiStart);
        instance.loadAPI(event.getSender());
        String apiSuccess = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdReloadAPISuccess);
        event.getSender().sendMessage(apiSuccess);
        instance.getServer().getPluginManager().callEvent(new WSCLinkerPluginReloadEventBukkit(event.getSender()));
    }
}
