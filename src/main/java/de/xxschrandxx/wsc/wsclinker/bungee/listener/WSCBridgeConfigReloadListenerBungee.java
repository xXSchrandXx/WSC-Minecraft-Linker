package de.xxschrandxx.wsc.wsclinker.bungee.listener;

import java.util.logging.Level;

import de.xxschrandxx.wsc.wscbridge.bungee.api.event.WSCBridgeConfigReloadEventBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.MinecraftLinkerBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.api.event.WSCLinkerConfigReloadEventBungee;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class WSCBridgeConfigReloadListenerBungee implements Listener {
    @EventHandler
    public void onConfigReload(WSCBridgeConfigReloadEventBungee event) {
        MinecraftLinkerBungee instance = MinecraftLinkerBungee.getInstance();
        String configStart = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdReloadConfigStart);
        event.getSender().sendMessage(configStart);
        if (!instance.reloadConfiguration(event.getSender())) {
            String configError = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdReloadConfigError);
            event.getSender().sendMessage(configError);
            instance.getLogger().log(Level.WARNING, "Could not load config.yml!");
            return;
        }
        String configSuccess = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdReloadConfigSuccess);
        event.getSender().sendMessage(configSuccess);
        instance.getProxy().getPluginManager().callEvent(new WSCLinkerConfigReloadEventBungee(event.getSender()));
    }
}
