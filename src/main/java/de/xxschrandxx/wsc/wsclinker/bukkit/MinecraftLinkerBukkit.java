package de.xxschrandxx.wsc.wsclinker.bukkit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.wsc.wscbridge.bukkit.MinecraftBridgeBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.ConfigurationBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.command.SenderBukkit;
import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.MinecraftBridgeVars;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.MinecraftLinkerBukkitAPI;
import de.xxschrandxx.wsc.wsclinker.bukkit.commands.*;
import de.xxschrandxx.wsc.wsclinker.bukkit.listener.*;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UpdateNamesRunnable;

public class MinecraftLinkerBukkit extends JavaPlugin implements IMinecraftBridgePlugin<MinecraftLinkerBukkitAPI> {

    // start of api part
    private static MinecraftLinkerBukkit instance;

    public static MinecraftLinkerBukkit getInstance() {
        return instance;
    }

    private MinecraftLinkerBukkitAPI api;

    public void loadAPI(ISender<?> sender) {
        String urlSendCodeString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlSendCode);
        URL urlSendCode;
        try {
            urlSendCode = new URL(urlSendCodeString);
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlUpdateNamesString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlUpdateNames);
        URL urlUpdateNames;
        try {
            urlUpdateNames = new URL(urlUpdateNamesString);
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        MinecraftBridgeBukkit wsc = MinecraftBridgeBukkit.getInstance();
        this.api = new MinecraftLinkerBukkitAPI(
            urlSendCode,
            urlUpdateNames,
            wsc.getConfiguration().getInt(MinecraftBridgeVars.Configuration.ID),
            wsc.getConfiguration().getString(MinecraftBridgeVars.Configuration.User),
            wsc.getConfiguration().getString(MinecraftBridgeVars.Configuration.Password),
            getLogger(),
            wsc.getConfiguration().getBoolean(MinecraftBridgeVars.Configuration.Debug)
        );
    }
    public MinecraftLinkerBukkitAPI getAPI() {
        return this.api;
    }
    // end of api part

    // start of plugin part
    @Override
    public void onEnable() {
        instance = this;

        // Load configuration
        getLogger().log(Level.INFO, "Loading Configuration.");
        SenderBukkit sender = new SenderBukkit(getServer().getConsoleSender(), getInstance());
        if (!reloadConfiguration(sender)) {
            getLogger().log(Level.WARNING, "Could not load config.yml, disabeling plugin!");
            onDisable();
            return;
        }

        // Load api
        getLogger().log(Level.INFO, "Loading API.");
        loadAPI(sender);

        // Load listener
        getLogger().log(Level.INFO, "Loading Listener.");
        getServer().getPluginManager().registerEvents(new WSCBridgeConfigReloadListenerBukkit(), getInstance());
        getServer().getPluginManager().registerEvents(new WSCBridgePluginReloadListenerBukkit(), getInstance());

        // Load commands
        getLogger().log(Level.INFO, "Loading Commands.");
        getCommand("wsclinker").setExecutor(new WSCLinkerBukkit());

        // load runnable
        getLogger().log(Level.INFO, "Loading Runnables.");
        int minutes = 5 * 60 * 1000;
        getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), new UpdateNamesRunnable(instance), minutes, minutes);
    }

    @Override
    public void onDisable() {
    }
    // end of plugin part

    // start config part
    public ConfigurationBukkit getConfiguration() {
        return new ConfigurationBukkit(getInstance().getConfig());
    }

    public boolean reloadConfiguration(ISender<?> sender) {
        reloadConfig();

        if (MinecraftLinkerVars.startConfig(getConfiguration(), getLogger())) {
            if (!saveConfiguration()) {
                return false;
            }
            return reloadConfiguration(sender);
        }
        return true;
    }

    public boolean saveConfiguration() {
        saveConfig();
        return true;
    }
    // end config part
}
