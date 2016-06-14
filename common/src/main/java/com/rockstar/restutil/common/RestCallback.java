package com.rockstar.restutil.common;

import com.google.j2objc.annotations.ObjectiveCName;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("RestCallback")
public interface RestCallback<T, E> {

    void onSuccess(RestResponse<T> response);

    void onError(RestResponse<E> errorResponse);

    void onNetworkError(String reason);

    Class<T> getResponseType();

    Class<E> getErrorResponseType();

}
