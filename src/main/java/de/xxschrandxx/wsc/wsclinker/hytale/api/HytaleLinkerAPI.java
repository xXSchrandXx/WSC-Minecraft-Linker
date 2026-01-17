package de.xxschrandxx.wsc.wsclinker.hytale.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.xxschrandxx.wsc.wscbridge.core.api.Response;
import de.xxschrandxx.wsc.wscbridge.hytale.api.HytaleBridgeAPI;
import de.xxschrandxx.wsc.wscbridge.hytale.api.HytaleBridgeLogger;
import de.xxschrandxx.wsc.wsclinker.core.api.ILinkerCoreAPI;
import de.xxschrandxx.wsc.wsclinker.core.api.LinkerCoreAPI;

public class HytaleLinkerAPI extends HytaleBridgeAPI implements ILinkerCoreAPI {

    protected final URL urlSendCode;
    protected final URL urlUpdateNames;
    protected final URL urlGetLinked;
    protected final URL urlGetUnlinked;

    public HytaleLinkerAPI(URL urlSendCode, URL urlUpdateNames, URL urlGetLinked, URL urlGetUnlinked, HytaleBridgeLogger logger, HytaleBridgeAPI api) {
        super(api, logger);
        this.urlSendCode = urlSendCode;
        this.urlUpdateNames = urlUpdateNames;
        this.urlGetLinked = urlGetLinked;
        this.urlGetUnlinked = urlGetUnlinked;
    }

    public Response<String, Object> sendCode(UUID uuid, String name) throws SocketTimeoutException, MalformedURLException, UnknownServiceException, IOException {
        return LinkerCoreAPI.sendCode(this, urlSendCode, uuid, name);
    }

    public Response<String, Object> sendNames(HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return LinkerCoreAPI.sendNames(this, urlUpdateNames, uuids);
    }

    public ArrayList<UUID> getUnlinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return LinkerCoreAPI.getUnlinkedUUIDs(this, urlGetUnlinked);
    }

    public ArrayList<UUID> getLinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        return LinkerCoreAPI.getLinkedUUIDs(this, urlGetLinked);
    }
}
