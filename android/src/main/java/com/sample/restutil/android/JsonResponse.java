package com.sample.restutil.android;

import lombok.Value;

import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@Value
public class JsonResponse {
    private String content;
    private Map<String, String> headers;
}
