package com.rockstar.restutil.ios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;
import com.rockstar.restutil.common.RestUtil;
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
@ObjectiveCName("IOSRestUtil")
public class IOSRestUtil extends BaseRestUtil implements RestUtil {
    private Gson gson;

    public IOSRestUtil() {
        val gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GmtDateTypeAdapter());
        this.gson = gsonBuilder.create();
    }

    @Override
    protected RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr) {
        return new IOSRestRequest(method, urlStr, this);
    }

    @Override
    public <T> T getObject(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <D> String getJson(D data) {
        return gson.toJson(data);
    }


    @Override
    public void cancelAllRequests() {
        // TODO: implement me
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
