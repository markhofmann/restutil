package com.sample.restutil.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public abstract class BaseRestRequest implements RestRequest {
    @Getter
    protected RequestMethod requestMethod;

    @Getter
    protected String url;

    @Getter
    protected RestUtil restUtil;

    @Getter
    @Setter
    protected String fileUri;

    @Getter
    @Setter
    protected Map<String, Object> parameters = new HashMap<>();

    @Getter
    @Setter
    protected Map<String, String> headers = new HashMap<>();

    @Getter
    protected String body;


    public BaseRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.restUtil = restUtil;
    }


    @Override
    public <D> void setBody(D body) {
        this.body = restUtil.getJson(body);
    }


    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void addParameter(String parameter, String data) {
        parameters.put(parameter, data);
    }


    protected <T, E> void executeCallback(RestCallback<T, E> callback, int statusCode, String responseText) {
        executeCallback(callback, statusCode, responseText, null);
    }

    protected <T, E> void executeCallback(RestCallback<T, E> callback, int statusCode, String responseText,
        Map<String, String> headers) {
        switch (statusCode) {
            case -1:
                callback.onNetworkError(responseText);
                break;
            case 200:
                callback.onSuccess(
                    new RestResponse<>(statusCode, responseText, headers, callback.getResponseType(), restUtil));
                break;
            default:
                callback.onError(
                    new RestResponse<>(statusCode, responseText, headers, callback.getErrorResponseType(), restUtil));
        }
    }

}
