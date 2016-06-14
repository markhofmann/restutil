package com.rockstar.restutil.gwt;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.rockstar.restutil.common.BaseRestUtil;
import com.rockstar.restutil.common.RestRequest;
import com.rockstar.restutil.common.RestUtil;

import java.util.logging.Logger;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class GwtRestUtil extends BaseRestUtil implements RestUtil {
    private static final Logger LOG = Logger.getLogger(RestUtil.class.getName());

    @Override
    protected RestRequest getRestRequestImpl(RestRequest.RequestMethod method, String urlStr) {
        return new GwtRestRequest(method, urlStr, this);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(String json, Class<T> type) {
        LOG.info("getting object, type: " + type.getSimpleName() + ", json:" + json);
        return ((T) (type == String.class ? json : JsonUtils.safeEval(json)));
    }

    @Override
    public <D> String getJson(D data) {
        return JsonUtils.stringify((JavaScriptObject) data);
    }

    @Override
    public void cancelAllRequests() {
        // TODO: implement me
    }
}
