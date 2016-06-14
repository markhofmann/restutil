package com.sample.restutil.common;

import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("BaseRestUtil")
public abstract class BaseRestUtil implements RestUtil {
    public <D, T, E> void post(String urlStr, D body, RestCallback<T, E> callback) {
        post(urlStr, body, null, null, callback);
    }

    @Override
    public <D, T, E> void post(String urlStr, String fileUri, D object, Map<String, Object> parameters,
        Map<String, String> headers, RestCallback<T, E> callback) {
        exec(RestRequest.RequestMethod.POST, urlStr, object, parameters, headers, fileUri, callback);
    }

    @Override
    public <D, T, E> void post(String urlStr, String fileUri, D object, RestCallback<T, E> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("json", getJson(object));
        post(urlStr, fileUri, params, callback);
    }

    @Override
    public <T, E> void post(String urlStr, String fileUri, RestCallback<T, E> callback) {
        post(urlStr, fileUri, null, callback);
    }

    @Override
    public <T, E> void post(String urlStr, String fileUri, Map<String, String> parameters,
        RestCallback<T, E> callback) {
        exec(RestRequest.RequestMethod.POST, urlStr, null, null, null, fileUri, callback);
    }


    @Override
    public <T, E> void get(String urlStr, Map<String, Object> parameters,
        Map<String, String> headers,
        RestCallback<T, E> callback) {
        exec(RestRequest.RequestMethod.GET, urlStr, null, parameters, headers, callback);
    }

    @Override
    public <D, T, E> void post(String urlStr, D body, Map<String, Object> parameters, Map<String, String> headers,
        RestCallback<T, E> callback) {
        exec(RestRequest.RequestMethod.POST, urlStr, body, parameters, headers, callback);
    }

    public <T, E> void get(String urlStr, RestCallback<T, E> callback) {
        get(urlStr, null, null, callback);
    }

    /**
     * Performs a GET request.
     * NOTE that all parameters must already be url encoded
     */
    public <T, E> void get(String urlStr, Map<String, Object> parameters, RestCallback<T, E> callback) {
        get(urlStr, parameters, null, callback);
    }

    protected <D, T, E> void exec(RestRequest.RequestMethod method, String urlStr,
        D body,
        Map<String, Object> parameters,
        Map<String, String> headers,
        RestCallback<T, E> callback) {

        exec(method, urlStr, body, parameters, headers, null, callback);
    }

    private <D, T, E> void exec(
        RestRequest.RequestMethod method,
        String urlStr,
        D body,
        Map<String, Object> parameters,
        Map<String, String> headers,
        String fileUri,
        RestCallback<T, E> callback) {

        if (parameters == null) {
            parameters = Collections.emptyMap();
        }
        if (headers == null) {
            headers = Collections.emptyMap();
        }
        executeRequest(method, urlStr, body, parameters, headers, fileUri, callback);
    }

    protected <D, T, E> void executeRequest(
        RestRequest.RequestMethod method,
        String urlStr,
        D body,
        Map<String, Object> parameters,
        Map<String, String> headers,
        String fileUri,
        RestCallback<T, E> callback) {
        final RestRequest request = getRestRequestImpl(method, urlStr);
        request.setHeaders(headers);
        request.setParameters(parameters);
        request.setBody(body);
        if (fileUri != null) {
            request.setFileUri(fileUri);
        }
        request.execute(callback);
    }

    protected abstract RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr);
}
