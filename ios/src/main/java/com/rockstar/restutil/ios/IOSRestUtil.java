package com.rockstar.restutil.ios;

import com.google.gson.Gson;
import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;
import com.rockstar.restutil.common.RestUtil;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestUtil")
public class IOSRestUtil extends BaseRestUtil implements RestUtil {

    @Override
    protected RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr) {
        return new IOSRestRequest(method, urlStr, this);
    }

    @Override
    public <T> T getObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }

    @Override
    public <D> String getJson(D data) {
        return new Gson().toJson(data);
    }


    @Override
    public void cancelAllRequests() {
        // TODO: implement me
    }
}
