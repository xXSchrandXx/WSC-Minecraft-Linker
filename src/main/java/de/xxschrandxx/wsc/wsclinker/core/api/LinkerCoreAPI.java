package de.xxschrandxx.wsc.wsclinker.core.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.UUID;

import de.xxschrandxx.wsc.wscbridge.core.api.BridgeCoreAPI;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;

public class LinkerCoreAPI {
    public static Response<String, Object> sendCode(BridgeCoreAPI api, URL url, UUID uuid, String name) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        HashMap<String, Object> postData = new HashMap<String, Object>();
        postData.put("uuid", uuid.toString());
        postData.put("name", name);
        Response<String, Object> request = api.requestObject(url, postData);
        return request;
    }
    public static Response<String, Object> sendNames(BridgeCoreAPI api, URL url, HashMap<UUID, HashMap<String, String>> uuids) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
        HashMap<String, Object> postData = new HashMap<String, Object>();
        postData.put("uuids", uuids);
        Response<String, Object> request = api.requestObject(url, postData);
        return request;
    }
    public static ArrayList<UUID> getUnlinkedUUIDs(BridgeCoreAPI api, URL url) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
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
        if (!(uuidsObject instanceof ArrayList)) {
            if (api.isDebugModeEnabled()) {
                api.log("'uuids' is no ArrayList.");
            }
            return uuids;
        }
        @SuppressWarnings("unchecked")
        ArrayList<String> uuidsArray = (ArrayList<String>) uuidsObject;
        for (String uuidString : uuidsArray) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                uuids.add(uuid);
            }
            catch (IllegalFormatException e) {
                if (api.isDebugModeEnabled()) {
                    api.log("UUID '" + uuidString + " is no UUID, skipping it.", e);
                }
            }
        }
        return uuids;
    }
    public static ArrayList<UUID> getLinkedUUIDs(BridgeCoreAPI api, URL url) throws MalformedURLException, UnknownServiceException, SocketTimeoutException, IOException {
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
        if (!(uuidsObject instanceof ArrayList)) {
            if (api.isDebugModeEnabled()) {
                api.log("'uuids' is no ArrayList.");
            }
            return uuids;
        }
        @SuppressWarnings("unchecked")
        ArrayList<String> uuidsArray = (ArrayList<String>) uuidsObject;
        for (String uuidString : uuidsArray) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                uuids.add(uuid);
            }
            catch (IllegalFormatException e) {
                if (api.isDebugModeEnabled()) {
                    api.log("UUID '" + uuidString + " is no UUID, skipping it.", e);
                }
            }
        }
        return uuids;
    }
}
