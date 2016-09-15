package com.rockstar.restutil.ios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;
import com.rockstar.restutil.common.RestUtil;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestUtil")
public class IOSRestUtil extends BaseRestUtil implements RestUtil {
    private Gson gson;

    public IOSRestUtil() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.gson = gsonBuilder.create();
    }

    @Override
    protected RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr) {
        return new IOSRestRequest(method, urlStr, this);
    }

    @Override
    public <T> T getObject(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <D> String getJson(D data) {
        return gson.toJson(data);
    }


    @Override
    public void cancelAllRequests() {
        // TODO: implement me
    }
}
