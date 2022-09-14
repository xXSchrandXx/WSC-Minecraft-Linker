package de.xxschrandxx.wsc.wsclinker.bukkit.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.MinecraftBridgeBukkitAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;
import de.xxschrandxx.wsc.wsclinker.core.api.IMinecraftLinkerCoreAPI;
import de.xxschrandxx.wsc.wsclinker.core.api.MinecraftLinkerCoreAPI;

public class MinecraftLinkerBukkitAPI extends MinecraftBridgeBukkitAPI implements IMinecraftLinkerCoreAPI {

    protected final URL urlSendCode;
    protected final URL urlUpdateNames;

    public MinecraftLinkerBukkitAPI(URL urlSendCode, URL urlUpdateNames, Integer id, String user, String password, Logger logger, Boolean debug) {
        super(id, user, password, logger, debug);
        this.urlSendCode = urlSendCode;
        this.urlUpdateNames = urlUpdateNames;
    }

    public Response<String, Object> sendCode(UUID uuid, String name) throws SocketTimeoutException, MalformedURLException, UnknownServiceException, IOException {
        return MinecraftLinkerCoreAPI.sendCode(this, urlSendCode, uuid, name);
    }

    public Response<String, Object> sendNames(HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return MinecraftLinkerCoreAPI.sendNames(this, urlUpdateNames, uuids);
    }
}
