package de.xxschrandxx.wsc.wsclinker.core.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.internal.LinkedTreeMap;

import de.xxschrandxx.wsc.wscbridge.core.api.MinecraftBridgeCoreAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;

public class MinecraftLinkerCoreAPI {
    public static Response<String, Object> sendCode(MinecraftBridgeCoreAPI api, URL url, UUID uuid, String name) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        HashMap<String, Object> postData = new HashMap<String, Object>();
        postData.put("uuid", uuid.toString());
        postData.put("name", name);
        Response<String, Object> request = api.requestObject(url, postData);
        return request;
    }
    public static Response<String, Object> sendNames(MinecraftBridgeCoreAPI api, URL url, HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        HashMap<String, Object> postData = new HashMap<String, Object>();
        postData.put("uuids", uuids);
        Response<String, Object> request = api.requestObject(url, postData);
        return request;
    }
    public static ArrayList<UUID> getLinkedUUIDs(MinecraftBridgeCoreAPI api, URL url) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        Response<String, Object> response = api.getObject(url);
        ArrayList<UUID> uuids = new ArrayList<UUID>();
        if (response.getResponse() == null) {
            if (api.isDebugModeEnabled()) {
                api.log("Response is null");
            }
            return uuids;
        }
        if (!response.getResponse().containsKey("uuids")) {
            if (api.isDebugModeEnabled()) {
                api.log("Response does not contain key 'uuids'.");
            }
            return uuids;
        }
        Object uuidsObject = response.get("uuids");
        api.log(uuidsObject.getClass().getCanonicalName());
        if (!(uuidsObject instanceof LinkedTreeMap)) {
            if (api.isDebugModeEnabled()) {
                api.log("'uuids' is no LinkedTreeMap.");
            }
            return uuids;
        }
        LinkedTreeMap uuidsTreeMap = (LinkedTreeMap) uuidsObject;
        return uuids;
    }
}
