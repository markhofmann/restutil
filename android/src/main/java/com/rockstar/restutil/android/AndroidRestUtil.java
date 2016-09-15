package com.rockstar.restutil.android;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class AndroidRestUtil extends BaseRestUtil {
    private static final String TAG = AndroidRestUtil.class.getName();
    private Gson gson;

    private RequestQueue requestQueue;

    /**
     * Initializes the RestUtil with a Volley request queue.
     *
     * To ensure that all requests are stopped implement the following:
     *
     * <code>
     *
     * @Override protected void onStop () {
     * super.onStop();
     * if (restUtil != null) {
     * restUtil.cancelAllRequests();
     * }
     * }
     * </code>>
     */
    public AndroidRestUtil(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.gson = gsonBuilder.create();
    }

    //    @Override
    //    public <D, T, E> void post(String urlStr, String fileUri, D object, Map<String, Object> parameters,
    //        Map<String, String> headers, RestCallback<T, E> callback) {
    //        File file = getTestFile();
    //        super.post(urlStr, file.toURI().getPath(), object, parameters, headers, callback);
    //    }
    //
    //    private File getTestFile() {
    //        File file;
    //        try {
    //            file = File.createTempFile("test_", ".jpg");
    //            FileOutputStream out = new FileOutputStream(file);
    //            InputStream in = getClass().getResourceAsStream("/com/sample/images/4stooges.jpg");
    //            IOUtils.copy(in, out);
    //            IOUtils.closeQuietly(in);
    //            IOUtils.closeQuietly(out);
    //        } catch (IOException e) {
    //            throw new RuntimeException("error uploading file: " + e.getMessage(), e);
    //        }
    //        return file;
    //    }


    @Override
    public <T> T getObject(String json, Class<T> type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "unable to parse json to type " + type, e);
            return null;
            // throw new RuntimeException("unable to parse json to type " + type, e);
        }
    }

    @Override
    public <D> String getJson(D data) {
        if (data == null) {
            Log.w(TAG, "data to be posted is null, returning null String");
            return null;
        }
        return gson.toJson(data);
    }

    @Override
    public void cancelAllRequests() {
        if (requestQueue != null) {
            requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }


    @Override
    protected RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr) {
        return new AndroidRestRequest(method, urlStr, this);
    }


    protected RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
