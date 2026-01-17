package de.xxschrandxx.wsc.wsclinker.core.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.xxschrandxx.wsc.wscbridge.core.api.IBridgeCoreAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;

public interface ILinkerCoreAPI extends IBridgeCoreAPI {
    public Response<String, Object> sendCode(UUID uuid, String name) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException;
    public Response<String, Object> sendNames(HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException;
    public ArrayList<UUID> getUnlinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException;
    public ArrayList<UUID> getLinkedUUIDs() throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException;
}
