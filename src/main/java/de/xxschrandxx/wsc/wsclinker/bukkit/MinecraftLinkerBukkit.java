package de.xxschrandxx.wsc.wsclinker.bukkit;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.wsc.wscbridge.bukkit.MinecraftBridgeBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.ConfigurationBukkit;
import de.xxschrandxx.wsc.wscbridge.bukkit.api.command.SenderBukkit;
import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.bukkit.api.MinecraftLinkerBukkitAPI;
import de.xxschrandxx.wsc.wsclinker.bukkit.commands.*;
import de.xxschrandxx.wsc.wsclinker.bukkit.listener.*;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UnlinkedMessageRunnable;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UpdateNamesRunnable;

public class MinecraftLinkerBukkit extends JavaPlugin implements IMinecraftBridgePlugin<MinecraftLinkerBukkitAPI> {

    // start of api part
    public String getInfo() {
        return null;
    }

    private static MinecraftLinkerBukkit instance;

    public static MinecraftLinkerBukkit getInstance() {
        return instance;
    }

    private MinecraftLinkerBukkitAPI api;

    public void loadAPI(ISender<?> sender) {
        String urlSendCodeString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlSendCode);
        URL urlSendCode;
        try {
            urlSendCode = URI.create(urlSendCodeString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlUpdateNamesString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlUpdateNames);
        URL urlUpdateNames;
        try {
            urlUpdateNames = URI.create(urlUpdateNamesString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetLinkedString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlGetLinked);
        URL urlGetLinked;
        try {
            urlGetLinked = URI.create(urlGetLinkedString).toURL();
        } catch (MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetUnlinkedString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlGetUnlinked);
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
            getLogger(),
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
        if (getConfiguration().getBoolean(MinecraftLinkerVars.Configuration.updateNamesEnabled)) {
            Integer updateNamesInterval = getConfiguration().getInt(MinecraftLinkerVars.Configuration.updateNamesInterval) * 60 * 20;
            getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), new UpdateNamesRunnable(instance), updateNamesInterval, updateNamesInterval);
        }
        if (getConfiguration().getBoolean(MinecraftLinkerVars.Configuration.unlinkedMessageEnabled)) {
            Integer unlinkedMessageInterval = getConfiguration().getInt(MinecraftLinkerVars.Configuration.unlinkedMessageInterval) * 60 * 20;
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
