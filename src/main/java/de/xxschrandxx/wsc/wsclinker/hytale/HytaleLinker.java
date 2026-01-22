package de.xxschrandxx.wsc.wsclinker.hytale;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.xxschrandxx.wsc.wscbridge.hytale.HytaleBridge;
import de.xxschrandxx.wsc.wscbridge.hytale.api.ConfigurationHytale;
import de.xxschrandxx.wsc.wscbridge.hytale.api.HytaleBridgeLogger;
import de.xxschrandxx.wsc.wscbridge.hytale.api.command.SenderHytale;
import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgeConfigReloadEventHytale;
import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgeModuleEventHytale;
import de.xxschrandxx.wsc.wscbridge.hytale.api.event.WSCBridgePluginReloadEventHytale;
import de.xxschrandxx.wsc.wscbridge.core.IBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.hytale.api.HytaleLinkerAPI;
import de.xxschrandxx.wsc.wsclinker.hytale.api.event.WSCLinkerConfigReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.hytale.api.event.WSCLinkerPluginReloadEventHytale;
import de.xxschrandxx.wsc.wsclinker.hytale.commands.*;
import de.xxschrandxx.wsc.wsclinker.hytale.listener.*;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UnlinkedMessageRunnable;
import de.xxschrandxx.wsc.wsclinker.core.runnable.UpdateNamesRunnable;

public class HytaleLinker extends JavaPlugin implements IBridgePlugin<HytaleLinkerAPI> {
    // start of api part
    public String getInfo() {
        return null;
    }

    private static HytaleLinker instance;

    public static HytaleLinker getInstance() {
        return instance;
    }

    private HytaleLinkerAPI api;

    private HytaleBridgeLogger bridgeLogger;

    @Override
    public HytaleBridgeLogger getBridgeLogger() {
        return bridgeLogger;
    }

    public void loadAPI(ISender<?> sender) {
        String urlSendCodeString = getConfiguration().getString(LinkerVars.Configuration.urlSendCode);
        URL urlSendCode;
        try {
            urlSendCode = URI.create(urlSendCodeString).toURL();
        } catch (MalformedURLException e) {
            getLogger().atWarning().log("Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlUpdateNamesString = getConfiguration().getString(LinkerVars.Configuration.urlUpdateNames);
        URL urlUpdateNames;
        try {
            urlUpdateNames = URI.create(urlUpdateNamesString).toURL();
        } catch (MalformedURLException e) {
            getLogger().atWarning().log("Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetLinkedString = getConfiguration().getString(LinkerVars.Configuration.urlGetLinked);
        URL urlGetLinked;
        try {
            urlGetLinked = URI.create(urlGetLinkedString).toURL();
        } catch (MalformedURLException e) {
            getLogger().atWarning().log("Could not load api, disabeling plugin!.", e);
            return;
        }
        String urlGetUnlinkedString = getConfiguration().getString(LinkerVars.Configuration.urlGetUnlinked);
        URL urlGetUnlinked;
        try {
            urlGetUnlinked = URI.create(urlGetUnlinkedString).toURL();
        } catch (MalformedURLException e) {
            getLogger().atWarning().log("Could not load api, disabeling plugin!.", e);
            return;
        }
        HytaleBridge wsc = HytaleBridge.getInstance();
        this.api = new HytaleLinkerAPI(
            urlSendCode,
            urlUpdateNames,
            urlGetLinked,
            urlGetUnlinked,
            getBridgeLogger(),
            wsc.getAPI()
        );
        HytaleServer.get().getEventBus().dispatchFor(WSCLinkerPluginReloadEventHytale.class).dispatch(new WSCLinkerPluginReloadEventHytale(sender));
    }

    public HytaleLinkerAPI getAPI() {
        return this.api;
    }
    // end of api part

    // start of plugin part
    public HytaleLinker(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        instance = this;
        bridgeLogger = new HytaleBridgeLogger(getLogger());

        // Load configuration
        getLogger().atInfo().log("Loading Configuration.");
        SenderHytale sender = new SenderHytale(ConsoleSender.INSTANCE, getInstance());
        if (!reloadConfiguration(sender)) {
            getLogger().atInfo().log("Could not load config.yml, disabeling plugin!");
            shutdown();
            return;
        }
    }

    @Override
    protected void start() {
        // Load api
        getLogger().atInfo().log("Loading API.");
        SenderHytale sender = new SenderHytale(ConsoleSender.INSTANCE, getInstance());
        loadAPI(sender);

        // Load listener
        getLogger().atInfo().log("Loading Listener.");
        getEventRegistry().register(WSCBridgeModuleEventHytale.class, event -> {
            (new AddModuleListenerHytale()).execute(event);
        });
        getEventRegistry().register(WSCBridgeConfigReloadEventHytale.class, event -> {
            (new WSCLinkerConfigReloadListenerHytale()).execute(event);
        });
        getEventRegistry().register(WSCBridgePluginReloadEventHytale.class, event -> {
            (new WSCLinkerPluginReloadListenerHytale()).execute(event);
        });

        // load commands
        getLogger().atInfo().log("Loading Commands.");
        List<String> aliases = null;
        if (getConfiguration().getBoolean(LinkerVars.Configuration.cmdAliasEnabled)) {
            aliases = getConfiguration().getStringList(LinkerVars.Configuration.cmdAliases);
        }
        getCommandRegistry().registerCommand(new WSCLinkerHytale(aliases));

        // load runnable
        getLogger().atInfo().log("Loading Runnables.");
        if (getConfiguration().getBoolean(LinkerVars.Configuration.updateNamesEnabled)) {
            int updateNamesInterval = getConfiguration().getInt(LinkerVars.Configuration.updateNamesInterval);
            HytaleServer.SCHEDULED_EXECUTOR.schedule(new UpdateNamesRunnable(instance), updateNamesInterval, TimeUnit.MINUTES);
        }
        if (getConfiguration().getBoolean(LinkerVars.Configuration.unlinkedMessageEnabled)) {
            int unlinkedMessageInterval = getConfiguration().getInt(LinkerVars.Configuration.unlinkedMessageInterval);
            HytaleServer.SCHEDULED_EXECUTOR.schedule(new UnlinkedMessageRunnable(instance), unlinkedMessageInterval, TimeUnit.MINUTES);
        }
    }

    @Override
    protected void shutdown() {
    }
    // end of plugin part

    // start config part
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File configFile = new File(getDataDirectory().toFile(), "config.yml");
    private ConfigurationHytale config;

    public ConfigurationHytale getConfiguration() {
        return config;
    }

    public boolean reloadConfiguration(ISender sender) {
        if (!getDataDirectory().toFile().exists()) {
            getDataDirectory().toFile().mkdir();
        }
        if (configFile.exists()) {
            try {
                String json = Files.readString(configFile.toPath());
                config = new ConfigurationHytale(gson.fromJson(json, HashMap.class));
            }
            catch (IOException e) {
                getLogger().atWarning().log("Could not load config.yml.", e);
                return false;
            }
        }
        else {
            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                getLogger().atWarning().log("Could not create config.yml.", e);
                return false;
            }
            config = new ConfigurationHytale();
        }

        if (LinkerVars.startConfig(getConfiguration(), getBridgeLogger())) {
            if (!saveConfiguration()) {
                return false;
            }
            return reloadConfiguration(sender);
        }
        HytaleServer.get().getEventBus().dispatchFor(WSCLinkerConfigReloadEventHytale.class).dispatch(new WSCLinkerConfigReloadEventHytale(sender));
        return true;
    }

    public boolean saveConfiguration() {
        if (!getDataDirectory().toFile().exists()) {
            getDataDirectory().toFile().mkdir();
        }
        try {
            Path tmp = getDataDirectory().resolve("config.json.tmp");
            String json = gson.toJson(this.config.getConfiguration());
            Files.writeString(tmp, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            try {
                Files.move(tmp, configFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                // Falls ATOMIC_MOVE nicht unterstützt wird
                Files.move(tmp, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            getLogger().atWarning().log("Could not save config.yml.", e);
            return false;
        }
        return true;
    }
    // end config part
}
