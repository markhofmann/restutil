package com.rockstar.restutil.common;

import com.google.j2objc.annotations.ObjectiveCName;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("RestResponse")
public class RestResponse<T> {
    @Getter
    private T content;

    @Getter
    private String rawResponse;

    @Getter
    private int statusCode;

    @Getter
    private Map<String, String> headers;

    public RestResponse(int responseCode, String rawResponse, Map<String, String> headers) {
        this.statusCode = responseCode;
        this.rawResponse = rawResponse;
        this.headers = (headers == null ? Collections.<String, String>emptyMap() : headers);
    }

    public RestResponse(int responseCode, String rawResponse, Map<String, String> headers, Class<T> responseType,
        RestUtil restUtil) {
        this(responseCode, rawResponse, headers);
        if (rawResponse != null && !rawResponse.isEmpty()) {
            this.content = restUtil.getObject(rawResponse, responseType);
        }
    }
}
