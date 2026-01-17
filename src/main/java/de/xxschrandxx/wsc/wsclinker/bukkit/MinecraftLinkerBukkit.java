package de.xxschrandxx.wsc.wsclinker.bukkit;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.wsc.wscbridge.bukkit.MinecraftBridgeBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.ConfigurationBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.command.SenderBukkit;
import de.xxschrandxx.wsc.wscbridge.core.IBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.MinecraftBridgeLogger;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.MinecraftLinkerBukkitAPI;
import de.xxschrandxx.wsc.wsclinker.bukkit.commands.*;
import de.xxschrandxx.wsc.wsclinker.bukkit.listener.*;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UnlinkedMessageRunnable;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UpdateNamesRunnable;

public class MinecraftLinkerBukkit extends JavaPlugin implements IBridgePlugin<MinecraftLinkerBukkitAPI> {

    // start of api part
    public String getInfo() {
        return null;
    }

    private static MinecraftLinkerBukkit instance;

    public static MinecraftLinkerBukkit getInstance() {
        return instance;
    }

    private MinecraftLinkerBukkitAPI api;

    private MinecraftBridgeLogger bridgeLogger;

    @Override
    public MinecraftBridgeLogger getBridgeLogger() {
        return bridgeLogger;
    }

    public void loadAPI(ISender<?> sender) {
        String urlSendCodeString = getConfiguration().getString(LinkerVars.Configuration.urlSendCode);
        URL urlSendCode;
        try {
            urlSendCode = URI.create(urlSendCodeString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlUpdateNamesString = getConfiguration().getString(LinkerVars.Configuration.urlUpdateNames);
        URL urlUpdateNames;
        try {
            urlUpdateNames = URI.create(urlUpdateNamesString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetLinkedString = getConfiguration().getString(LinkerVars.Configuration.urlGetLinked);
        URL urlGetLinked;
        try {
            urlGetLinked = URI.create(urlGetLinkedString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetUnlinkedString = getConfiguration().getString(LinkerVars.Configuration.urlGetUnlinked);
        URL urlGetUnlinked;
        try {
            urlGetUnlinked = URI.create(urlGetUnlinkedString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        MinecraftBridgeBukkit wsc = MinecraftBridgeBukkit.getInstance();
        this.api = new MinecraftLinkerBukkitAPI(
            urlSendCode,
            urlUpdateNames,
            urlGetLinked,
            urlGetUnlinked,
            getBridgeLogger(),
            wsc.getAPI()
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
        bridgeLogger = new MinecraftBridgeLogger(getLogger());

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
        getServer().getPluginManager().registerEvents(new WSCLinkerCommandAliasBukkit(), getInstance());
        getServer().getPluginManager().registerEvents(new WSCLinkerConfigReloadListenerBukkit(), getInstance());
        getServer().getPluginManager().registerEvents(new WSCLinkerPluginReloadListenerBukkit(), getInstance());
        getServer().getPluginManager().registerEvents(new AddModuleListenerBukkit(), getInstance());

        // Load commands
        getLogger().log(Level.INFO, "Loading Commands.");
        getCommand("wsclinker").setExecutor(new WSCLinkerBukkit());

        // load runnable
        getLogger().log(Level.INFO, "Loading Runnables.");
        if (getConfiguration().getBoolean(LinkerVars.Configuration.updateNamesEnabled)) {
            Integer updateNamesInterval = getConfiguration().getInt(LinkerVars.Configuration.updateNamesInterval) * 60 * 20;
            getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), new UpdateNamesRunnable(instance), updateNamesInterval, updateNamesInterval);
        }
        if (getConfiguration().getBoolean(LinkerVars.Configuration.unlinkedMessageEnabled)) {
            Integer unlinkedMessageInterval = getConfiguration().getInt(LinkerVars.Configuration.unlinkedMessageInterval) * 60 * 20;
            getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), new UnlinkedMessageRunnable(instance), unlinkedMessageInterval, unlinkedMessageInterval);
        }
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

        if (LinkerVars.startConfig(getConfiguration(), getBridgeLogger())) {
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
