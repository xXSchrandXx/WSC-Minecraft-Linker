package de.xxschrandxx.wsc.wsclinker.bungee.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import de.xxschrandxx.wsc.wscbridge.bungee.api.MinecraftBridgeBungeeAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.MinecraftBridgeCoreAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;
import de.xxschrandxx.wsc.wsclinker.core.api.IMinecraftLinkerCoreAPI;
import de.xxschrandxx.wsc.wsclinker.core.api.MinecraftLinkerCoreAPI;

public class MinecraftLinkerBungeeAPI extends MinecraftBridgeBungeeAPI implements IMinecraftLinkerCoreAPI {

    protected final URL urlSendCode;
    protected final URL urlUpdateNames;
    protected final URL urlGetLinked;
    protected final URL urlGetUnlinked;

    public MinecraftLinkerBungeeAPI(URL urlSendCode, URL urlUpdateNames, URL urlGetLinked, URL urlGetUnlinked, Logger logger, MinecraftBridgeCoreAPI api) {
        super(api.getID(), api.getAuth(), logger, api.isDebugModeEnabled());
        this.urlSendCode = urlSendCode;
        this.urlUpdateNames = urlUpdateNames;
        this.urlGetLinked = urlGetLinked;
        this.urlGetUnlinked = urlGetUnlinked;
    }

    public Response<String, Object> sendCode(UUID uuid, String name) throws SocketTimeoutException, MalformedURLException, UnknownServiceException, IOException {
        return MinecraftLinkerCoreAPI.sendCode(this, urlSendCode, uuid, name);
    }

    public Response<String, Object> sendNames(HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return MinecraftLinkerCoreAPI.sendNames(this, urlUpdateNames, uuids);
    }

    public ArrayList<UUID> getUnlinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return null;
    }

    public ArrayList<UUID> getLinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return MinecraftLinkerCoreAPI.getLinkedUUIDs(this, urlGetLinked);
    }
}
