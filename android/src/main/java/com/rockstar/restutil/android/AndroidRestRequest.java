package com.rockstar.restutil.android;

import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestRequest;
import com.rockstar.restutil.common.RestUtil;
import lombok.val;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        val stringParams = new HashMap<String, String>();

        val params = getParameters();
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
        val headers = getHeaders();
        val fileUri = getFileUri();
        val restUtil = getRestUtil();
        val successListener = new VolleySuccessListener<T, E>(callback);
        val errorListener = new VolleyErrorListener<T, E>(this, callback);
        if (fileUri != null) {
            // TODO refactor to use URI instead of String
            val file = new File(fileUri);
            request = new MultipartRequest(getUrl(), headers, successListener, errorListener);
            val multipartRequest = ((MultipartRequest) request);
            multipartRequest.addFilePart("fileUpload", file, file.getName());
            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                multipartRequest.addTextPart(entry.getKey(), entry.getValue());
            }
            multipartRequest.endParts();
        } else {
            String url = getUrl();
            if (getRequestMethod() == RequestMethod.GET && !stringParams.isEmpty()) {
                url = url + "?" + getParams(stringParams);
            }
            request =
                new VolleyRequest(getVolleyRequestMethod(), url, getBody(), stringParams, headers,
                    successListener, errorListener);
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
            (int) TimeUnit.SECONDS.toMillis(10), // 10 second timeout
            0, // don't retry
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ((AndroidRestUtil) restUtil).getRequestQueue().add(request);
    }

    private String getParams(Map<String, String> paramMap) {
        //        List<NameValuePair> list = new ArrayList<>();
        //        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
        //            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        //        }
        //        return URLEncodedUtils.format(list, "UTF-8");

        // TODO: add URL encoding!
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (b.length() > 0) {
                b.append('&');
            }
            b.append(entry.getKey()).append('=').append(entry.getValue());
        }
        return b.toString();
    }

    private class VolleyErrorListener<T, E> implements Response.ErrorListener {
        private final RestCallback<T, E> callback;
        private final RestRequest request;

        VolleyErrorListener(RestRequest request, RestCallback<T, E> callback) {
            this.callback = callback;
            this.request = request;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            val networkResponse = error.networkResponse;
            val message = error.getMessage();

            Log.e(TAG, "request " + getRequestMethod().getMethod() + " " + request.getUrl() + " failed with error: " + (
                networkResponse == null ?
                    message :
                    message + " , network response: " + networkResponse.statusCode));
            if (networkResponse == null || error instanceof NetworkError) {
                executeCallback(callback, -1, error.getMessage());
            } else {
                String responseText;
                try {
                    responseText = new String(networkResponse.data, "UTF-8");
                    Log.e(TAG, "error response: " + responseText);
                } catch (Exception e) {
                    Log.e(TAG, "could not read response data");
                    responseText = "ERROR";
                }
                executeCallback(callback, networkResponse.statusCode, responseText, networkResponse.headers);
            }
        }
    }


    private class VolleySuccessListener<T, E> implements Response.Listener<JsonResponse> {
        private final RestCallback<T, E> callback;

        VolleySuccessListener(RestCallback<T, E> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JsonResponse response) {
            executeCallback(callback, 200, response.getContent(), response.getHeaders());
        }
    }
}
