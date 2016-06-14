package com.sample.restutil.gwt;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.sample.restutil.common.BaseRestRequest;
import com.sample.restutil.common.RestCallback;
import com.sample.restutil.common.RestUtil;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class GwtRestRequest extends BaseRestRequest {
    public GwtRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        super(requestMethod, url, restUtil);
    }
    //        if (requestMethod == RequestMethod.POST) {
    //            builder.setHeader("Content-type", "application/x-www-form-urlencoded");
    //        }

    @Override
    public <T, E> void execute(final RestCallback<T, E> callback) {
        val params = getParamsAsString();
        val body = getBody();
        val requestData = getRequestMethod() == RequestMethod.POST && body != null ? body : params;

        val builder = getRequestBuilder();
        builder.setRequestData(requestData);
        addHeaders(builder);

        try {
            builder.sendRequest(requestData, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    val statusCode = response.getStatusCode();
                    String responseText = response.getText();
                    Map<String, String> headers = getResponseHeaders(response);
                    executeCallback(callback, statusCode, responseText, headers);
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    executeCallback(callback, 500, exception.getMessage());
                }
            });
        } catch (RequestException exception) {
            Window.alert("error in request:" + exception.getMessage());
        }
    }


    private Map<String, String> getResponseHeaders(Response response) {
        Map<String, String> headers = new HashMap<>();
        Header[] responseHeaders = response.getHeaders();
        if (responseHeaders != null) {
            for (Header header : responseHeaders) {
                headers.put(header.getName(), header.getValue());
            }
        }
        return headers;
    }


    private RequestBuilder getRequestBuilder() {
        return new RequestBuilder(getMethod(), getUrl());
    }

    private RequestBuilder.Method getMethod() {
        final RequestBuilder.Method method;
        RequestMethod requestMethod = getRequestMethod();
        switch (requestMethod) {
            case GET:
                method = RequestBuilder.GET;
                break;
            case POST:
                method = RequestBuilder.POST;
                break;
            case PUT:
                method = RequestBuilder.PUT;
                break;
            case DELETE:
                method = RequestBuilder.DELETE;
                break;
            default:
                throw new RuntimeException("unhandled method:" + requestMethod);
        }
        return method;
    }

    private void addHeaders(RequestBuilder builder) {
        for (Map.Entry<String, String> header : getHeaders().entrySet()) {
            val value = header.getValue();
            if (value != null && value.length() > 0) {
                builder.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    private String getParamsAsString() {
        val params = new StringBuilder();
        for (Map.Entry<String, Object> param : getParameters().entrySet()) {
            Object value = param.getValue();
            if (value != null) {
                val stringValue = value.toString();
                if (stringValue.length() > 0) {
                    if (params.length() != 0) {
                        params.append('&');
                    }
                    params.append(param.getKey());
                    params.append('=');
                    params.append(URL.encodeQueryString(value.toString()));
                }
            }
        }
        return params.toString();
    }



}
