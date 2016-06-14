package com.rockstar.restutil.android;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class MultipartRequest extends Request<JsonResponse> {
    private static final String TAG = MultipartRequest.class.getName();

    private final static String TWO_HYPHENS = "--";
    private final static String LINE_END = "\n";
    private final String boundary;


    private final Response.Listener<JsonResponse> listener;
    private final Map<String, String> headers;
    private final ByteArrayOutputStream out;
    private final DataOutputStream dataOutputStream;

    public MultipartRequest(String url, Map<String, String> headers, Response.Listener<JsonResponse> listener,
        Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        boundary = "androidrestclient" + System.currentTimeMillis();
        headers.put("Content-Type", getBodyContentType());
        this.headers = headers;
        out = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(out);
    }


    public void addFilePart(String partName, File file, String fileName) {
        try {
            Log.d(TAG, "adding file part: " + partName);
            dataOutputStream.writeBytes(TWO_HYPHENS + boundary + LINE_END);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + partName + "\"; filename=\""
                + fileName + "\"" + LINE_END);
            dataOutputStream.writeBytes("Content-Type: image/jpeg" + LINE_END);
            dataOutputStream.writeBytes(LINE_END);
            FileUtils.copyFile(file, dataOutputStream);
            dataOutputStream.writeBytes(LINE_END);
        } catch (Exception e) {
            Log.e(TAG, "error constructing file part", e);
        }
    }

    public void endParts() {
        try {
            dataOutputStream.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + LINE_END);
        } catch (Exception e) {
            Log.e(TAG, "error adding end boundary", e);
        }
    }

    public void addTextPart(String parameterName, String parameterValue) {
        try {
            Log.d(TAG, "adding text part: " + parameterName);
            dataOutputStream.writeBytes(TWO_HYPHENS + boundary + LINE_END);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + LINE_END);
            dataOutputStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_END);
            dataOutputStream.writeBytes(LINE_END);
            dataOutputStream.writeBytes(parameterValue + LINE_END);
        } catch (Exception e) {
            Log.e(TAG, "error constructing text part", e);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        IOUtils.closeQuietly(dataOutputStream);
        return out.toByteArray();
    }


    @Override
    protected void deliverResponse(JsonResponse response) {
        Log.d(TAG, "received response:" + response);
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<JsonResponse> parseNetworkResponse(NetworkResponse response) {
        Log.d(TAG, "recieved response:" + response);
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response
            .success(new JsonResponse(parsed, response.headers), HttpHeaderParser.parseCacheHeaders(response));
    }
}
