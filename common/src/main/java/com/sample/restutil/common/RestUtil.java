package com.sample.restutil.common;

import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("RestUtil")
public interface RestUtil {

    /**
     * Uploads the given file as multipart/form-data along with the given parameters.
     *
     * @param urlStr     The url to post to.
     * @param fileUri    The uri of the file to be sent.
     * @param parameters The parameters to send along with the file.
     * @param callback   The interface of the callback handler.
     * @param <T>        The type of the return value
     * @param <E>        The error type.
     */
    <T, E> void post(String urlStr, String fileUri, Map<String, String> parameters, RestCallback<T, E> callback);

    /**
     * Uploads the given file as multipart/form-data along with the given object.
     * The object will be serialized into a json String and added to the request as a "json" parameter
     *
     * @param urlStr   The url to send the POST request to.
     * @param fileUri  The uri of the file to be sent.
     * @param object   The object to send along with the file.
     * @param callback The callback handler.
     * @param <D>      The type of the json object.
     * @param <T>      The type of the return value
     * @param <E>      The error type.
     */
    <D, T, E> void post(String urlStr, String fileUri, D object, RestCallback<T, E> callback);

    /**
     * Uploads the given file as multipart/form-data along with the given object.
     * The object will be serialized into a json String and added to the request as a "json" parameter
     *
     * @param urlStr     The url to send the POST request to.
     * @param fileUri    The uri of the file to be sent.
     * @param object     The object to send along with the file.
     * @param parameters The parameters to send along with the file.
     * @param headers    The headers to include in the POST request.
     * @param callback   The callback handler.
     * @param <D>        The type of the json object.
     * @param <T>        The type of the return value
     * @param <E>        The error type.
     */
    <D, T, E> void post(String urlStr, String fileUri, D object, Map<String, Object> parameters,
        Map<String, String> headers, RestCallback<T, E> callback);

    /**
     * Uploads the given file as multipart/form-data.
     *
     * @param urlStr   The url to send the POST request to.
     * @param fileUri  The uri of the file to be sent.
     * @param callback The callback handler.
     * @param <T>      The type of the return value
     * @param <E>      The error type.
     */
    <T, E> void post(String urlStr, String fileUri, RestCallback<T, E> callback);

    /**
     * Sends a POST request with the body containing the given object as json to the specified url.
     *
     * @param urlStr   The url to send the POST request to.
     * @param object   The object to be sent.
     * @param callback The callback handler.
     * @param <D>      The type of the json object.
     * @param <T>      The type of the return value
     * @param <E>      The error type.
     */
    <D, T, E> void post(String urlStr, D object, RestCallback<T, E> callback);

    /**
     * Sends the given object as json along with given headers to the specified url.
     *
     * @param urlStr     The url to send the POST request to.
     * @param object     The object to be sent.
     * @param parameters The parameters to send along with the object.
     * @param headers    The headers to include in the POST request.
     * @param callback   The callback handler.
     * @param <D>        The type of the json object.
     * @param <T>        The type of the return value
     * @param <E>        The error type.
     */
    <D, T, E> void post(String urlStr, D object, Map<String, Object> parameters, Map<String, String> headers,
        RestCallback<T, E> callback);

    /**
     * Sends a GET request to the given url.
     *
     * @param urlStr   The url to send the GET request to.
     * @param callback The callback handler.
     * @param <T>      The type of the return value
     * @param <E>      The error type.
     */
    <T, E> void get(String urlStr, RestCallback<T, E> callback);

    /**
     * Sends a GET request to the given url.
     *
     * @param urlStr     The url to send the GET request to.
     * @param callback   The callback handler.
     * @param parameters The parameters to include in the request.
     *                   NOTE that all parameters must already be url encoded
     * @param <T>        The type of the return value
     * @param <E>        The error type.
     */
    <T, E> void get(String urlStr, Map<String, Object> parameters, RestCallback<T, E> callback);

    /**
     * Sends a GET request to the given url.
     *
     * @param urlStr     The url to send the GET request to.
     * @param callback   The callback handler.
     * @param parameters The parameters to include in the request.
     *                   NOTE that all parameters must already be url encoded
     * @param headers    The headers to include in the request.
     * @param <T>        The type of the return value
     * @param <E>        The error type.
     */
    <T, E> void get(String urlStr, Map<String, Object> parameters, Map<String, String> headers,
        RestCallback<T, E> callback);

    /**
     * Deserializes the given json String into an object of a given type.
     *
     * @param json The json String to deserialize.
     * @param type The target type.
     * @param <T>  The target type.
     * @return The deserialized object.
     */
    <T> T getObject(String json, Class<T> type);

    /**
     * Serializes the given object into a json String.
     *
     * @param data The object to be serialized.
     * @param <D>  The source type.
     * @return The serialized object as a json String.
     */
    <D> String getJson(D data);

    /**
     * Cancels all running requests.
     */
    void cancelAllRequests();
}
