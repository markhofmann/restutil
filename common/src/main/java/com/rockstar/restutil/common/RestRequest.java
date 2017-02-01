package com.rockstar.restutil.common;

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

    /**
     * Sets the request body to be sent (if any).
     *
     * @param body The body.
     * @param <D>  The object type of the body.
     */
    <D> void setBody(D body);

    String getBody();

    void setFileUri(String fileUri);

    Map<String, Object> getParameters();

    /**
     * Set the request parameters. May be empty.
     */
    void setParameters(Map<String, Object> parameters);

    /**
     * Add a single parameter to the existing ones.
     */
    void addParameter(String parameter, String data);

    Map<String, String> getHeaders();

    /**
     * Set the request headers. May be empty.
     */
    void setHeaders(Map<String, String> headers);

    /**
     * Add a single header to the existing ones.
     */
    void addHeader(String name, String value);

    String getUrl();

    /**
     * Executes the current request and calls the given callback depending on the result.
     *
     * @param callback The callback to be notified.
     * @param <T>      The expected response type.
     * @param <E>      The expected error response type.
     */
    <T, E> void execute(RestCallback<T, E> callback);

}
