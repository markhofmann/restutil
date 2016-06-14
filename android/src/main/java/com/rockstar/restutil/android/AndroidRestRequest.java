package com.rockstar.restutil.android;

import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestUtil;
import lombok.val;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class AndroidRestRequest extends BaseRestRequest {
    private static final String TAG = AndroidRestRequest.class.getName();

    public AndroidRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        super(requestMethod, url, restUtil);
    }

    private int getVolleyRequestMethod() {
        val requestMethod = getRequestMethod();
        int volleyRequestMethod;
        switch (requestMethod) {
            case GET:
                volleyRequestMethod = Request.Method.GET;
                break;
            case POST:
                volleyRequestMethod = Request.Method.POST;
                break;
            case PUT:
                volleyRequestMethod = Request.Method.PUT;
                break;
            case DELETE:
                volleyRequestMethod = Request.Method.DELETE;
                break;
            default:
                throw new RuntimeException("unhandled method:" + requestMethod);
        }
        return volleyRequestMethod;
    }


    @Override
    public <T, E> void execute(final RestCallback<T, E> callback) {
        Map<String, String> stringParams = new HashMap<>();

        Map<String, Object> params = getParameters();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    if (Log.isLoggable(TAG, Log.DEBUG)) {
                        Log.d(TAG, "value for " + entry.getKey() + " is null, ignoring");
                    }
                    continue;
                }
                if (value instanceof String) {
                    stringParams.put(entry.getKey(), (String) value);
                } else {
                    stringParams.put(entry.getKey(), value.toString());
                }
            }
        }
        Request request;
        Map<String, String> headers = getHeaders();
        String fileUri = getFileUri();
        val restUtil = getRestUtil();
        VolleySuccessListener<T, E> successListener = new VolleySuccessListener<>(callback);
        VolleyErrorListener<T, E> errorListener = new VolleyErrorListener<>(callback);
        if (fileUri != null) {
            val file = new File(fileUri);
            request = new MultipartRequest(getUrl(), headers, successListener, errorListener);
            MultipartRequest multipartRequest = ((MultipartRequest) request);
            multipartRequest.addFilePart("fileUpload", file, file.getName());
            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                multipartRequest.addTextPart(entry.getKey(), entry.getValue());
            }
            multipartRequest.endParts();
        } else {
            request =
                new VolleyRequest(getVolleyRequestMethod(), getUrl(), getBody(), stringParams, headers,
                    successListener, errorListener);
        }
        // see http://stackoverflow.com/questions/17094718/change-volley-timeout-duration
        request.setRetryPolicy(new DefaultRetryPolicy(
            5000,
            1, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ((AndroidRestUtil) restUtil).getRequestQueue().add(request);
    }

    private class VolleyErrorListener<T, E> implements Response.ErrorListener {
        private final RestCallback<T, E> callback;

        public VolleyErrorListener(RestCallback<T, E> callback) {
            this.callback = callback;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            val networkResponse = error.networkResponse;
            val message = error.getMessage();
            Log.e(TAG, "got error message: " + (networkResponse == null ?
                message :
                message + "\n" + networkResponse.toString()));
            if (networkResponse == null || error instanceof NetworkError) {
                executeCallback(callback, -1, error.getMessage());
            } else {
                executeCallback(callback, networkResponse.statusCode, error.getMessage(), networkResponse.headers);
            }
        }
    }


    private class VolleySuccessListener<T, E> implements Response.Listener<JsonResponse> {
        private final RestCallback<T, E> callback;

        public VolleySuccessListener(RestCallback<T, E> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JsonResponse response) {
            executeCallback(callback, 200, response.getContent(), response.getHeaders());
        }
    }
}
