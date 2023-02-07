package de.xxschrandxx.wsc.wsclinker.bukkit.listener;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.event.WSCBridgeConfigReloadEventBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.MinecraftLinkerBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.event.WSCLinkerConfigReloadEventBukkit;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;

public class WSCLinkerConfigReloadListenerBukkit implements Listener {
    @EventHandler
    public void onConfigReload(WSCBridgeConfigReloadEventBukkit event) {
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
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
        instance.getServer().getPluginManager().callEvent(new WSCLinkerConfigReloadEventBukkit(event.getSender()));
    }
}
