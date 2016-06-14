package com.sample.restutil.common;

import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("RestRequest")
public interface RestRequest {

    enum RequestMethod {
        GET("GET"), PUT("PUT"), POST("POST"), DELETE("DELETE"), HEAD("HEAD");
        String method;

        RequestMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }

    <D> void setBody(D body);

    String getBody();

    void setFileUri(String fileUri);

    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);

    void addParameter(String parameter, String data);

    Map<String, String> getHeaders();

    void setHeaders(Map<String, String> headers);

    void addHeader(String name, String value);

    <T, E> void execute(RestCallback<T, E> callback);

}
