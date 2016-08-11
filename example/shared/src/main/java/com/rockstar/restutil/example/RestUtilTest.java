package com.rockstar.restutil.example;

import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestResponse;
import com.rockstar.restutil.common.RestUtil;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
public class RestUtilTest {
    public void testGet(RestUtil restUtil) throws Exception {
        restUtil.get("https://jsonplaceholder.typicode.com/posts/1",
            new RestCallback<String, String>() {
                @Override
                public void onSuccess(RestResponse<String> response) {
                    System.out.println("received response:" + response.getRawResponse());
                }

                @Override
                public void onError(RestResponse<String> errorResponse) {
                    System.out.println("received error response:" + errorResponse.getRawResponse());
                }

                @Override
                public void onNetworkError(String reason) {
                    System.out.println("received network response:" + reason);
                }

                @Override
                public Class<String> getResponseType() {
                    return String.class;
                }

                @Override
                public Class<String> getErrorResponseType() {
                    return String.class;
                }
            });
    }
}
