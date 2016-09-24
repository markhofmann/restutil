package com.rockstar.restutil.android;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;
import lombok.val;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class AndroidRestUtil extends BaseRestUtil {
    private static final String TAG = AndroidRestUtil.class.getName();
    private Gson gson;

    private RequestQueue requestQueue;

    /**
     * Initializes the RestUtil with a Volley request queue.
     */
    public AndroidRestUtil(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        val gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GmtDateTypeAdapter());
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
        }
    }

    @Override
    public <D> String getJson(D data) {
        if (data == null) {
            Log.w(TAG, "data object to be serialized is null, returning null String");
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


    RequestQueue getRequestQueue() {
        return requestQueue;
    }


    private static class GmtDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat dateFormat;

        private GmtDateTypeAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type,
            JsonSerializationContext jsonSerializationContext) {
            synchronized (dateFormat) {
                String dateFormatAsString = dateFormat.format(date);
                return new JsonPrimitive(dateFormatAsString);
            }
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) {
            try {
                synchronized (dateFormat) {
                    return dateFormat.parse(jsonElement.getAsString());
                }
            } catch (ParseException e) {
                throw new JsonSyntaxException(jsonElement.getAsString(), e);
            }
        }
    }
}
