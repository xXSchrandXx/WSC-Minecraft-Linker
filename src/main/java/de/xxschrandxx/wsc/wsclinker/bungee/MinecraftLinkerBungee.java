package de.xxschrandxx.wsc.wsclinker.bungee;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import de.xxschrandxx.wsc.wscbridge.bungee.MinecraftBridgeBungee;
import de.xxschrandxx.wsc.wscbridge.bungee.api.ConfigurationBungee;
import de.xxschrandxx.wsc.wscbridge.bungee.api.command.SenderBungee;
import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.bungee.api.MinecraftLinkerBungeeAPI;
import de.xxschrandxx.wsc.wsclinker.bungee.commands.*;
import de.xxschrandxx.wsc.wsclinker.bungee.listener.*;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UnlinkedMessageRunnable;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UpdateNamesRunnable;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MinecraftLinkerBungee extends Plugin implements IMinecraftBridgePlugin<MinecraftLinkerBungeeAPI> {

    // start of api part
    public String getInfo() {
        return null;
    }

    private static MinecraftLinkerBungee instance;

    public static MinecraftLinkerBungee getInstance() {
        return instance;
    }

    private MinecraftLinkerBungeeAPI api;

    public void loadAPI(ISender<?> sender) {
        String urlSendCodeString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlSendCode);
        URL urlSendCode;
        try {
            urlSendCode = new URI(urlSendCodeString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlUpdateNamesString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlUpdateNames);
        URL urlUpdateNames;
        try {
            urlUpdateNames = new URI(urlUpdateNamesString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetLinkedString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlGetLinked);
        URL urlGetLinked;
        try {
            urlGetLinked = new URI(urlGetLinkedString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetUnlinkedString = getConfiguration().getString(MinecraftLinkerVars.Configuration.urlGetUnlinked);
        URL urlGetUnlinked;
        try {
            urlGetUnlinked = new URI(urlGetUnlinkedString).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            getLogger().log(Level.INFO, "Could not load api, disabeling plugin!.", e);
            return;
        }
        MinecraftBridgeBungee wsc = MinecraftBridgeBungee.getInstance();
        this.api = new MinecraftLinkerBungeeAPI(
            urlSendCode,
            urlUpdateNames,
            urlGetLinked,
            urlGetUnlinked,
            getLogger(),
            wsc.getAPI()
        );
    }

    public MinecraftLinkerBungeeAPI getAPI() {
        return this.api;
    }
    // end of api part

    // start of plugin part
    @Override
    public void onEnable() {
        instance = this;

        // Load configuration
        getLogger().log(Level.INFO, "Loading Configuration.");
        SenderBungee sender = new SenderBungee(getProxy().getConsole(), getInstance());
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
        getProxy().getPluginManager().registerListener(getInstance(), new WSCLinkerCommandAliasBungee());
        getProxy().getPluginManager().registerListener(getInstance(), new WSCLinkerConfigReloadListenerBungee());
        getProxy().getPluginManager().registerListener(getInstance(), new WSCLinkerPluginReloadListenerBungee());
        getProxy().getPluginManager().registerListener(getInstance(), new AddModuleListenerBungee());

        // load commands
        getLogger().log(Level.INFO, "Loading Commands.");
        getProxy().getPluginManager().registerCommand(getInstance(), new WSCLinkerBungee("wsclinker"));

        // load runnable
        getLogger().log(Level.INFO, "Loading Runnables.");
        if (getConfiguration().getBoolean(MinecraftLinkerVars.Configuration.updateNamesEnabled)) {
            Integer updateNamesInterval = getConfiguration().getInt(MinecraftLinkerVars.Configuration.updateNamesInterval);
            getProxy().getScheduler().schedule(getInstance(), new UpdateNamesRunnable(instance), updateNamesInterval, updateNamesInterval, TimeUnit.MINUTES);
        }
        if (getConfiguration().getBoolean(MinecraftLinkerVars.Configuration.unlinkedMessageEnabled)) {
            Integer unlinkedMessageInterval = getConfiguration().getInt(MinecraftLinkerVars.Configuration.unlinkedMessageInterval);
            getProxy().getScheduler().schedule(getInstance(), new UnlinkedMessageRunnable(instance), unlinkedMessageInterval, unlinkedMessageInterval, TimeUnit.MINUTES);
        }
    }

    @Override
    public void onDisable() {
    }
    // end of plugin part

    // start config part
    private File configFile = new File(getDataFolder(), "config.yml");
    private ConfigurationBungee config;

    public ConfigurationBungee getConfiguration() {
        return getInstance().config;
    }

    public boolean reloadConfiguration(ISender<?> sender) {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        if (configFile.exists()) {
            try {
                config = new ConfigurationBungee(ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile));
            }
            catch (IOException e) {
                getLogger().log(Level.WARNING, "Could not load config.yml.", e);
                return false;
            }
        }
        else {
            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                getLogger().log(Level.WARNING, "Could not create config.yml.", e);
                return false;
            }
            config = new ConfigurationBungee();
        }

        if (MinecraftLinkerVars.startConfig(getConfiguration(), getLogger())) {
            if (!saveConfiguration()) {
                return false;
            }
            return reloadConfiguration(sender);
        }
        return true;
    }

    public boolean saveConfiguration() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config.getConfiguration(), configFile);
        }
        catch (IOException e) {
            getLogger().log(Level.WARNING, "Could not save config.yml.", e);
            return false;
        }
        return true;
    }
    // end config part
}
