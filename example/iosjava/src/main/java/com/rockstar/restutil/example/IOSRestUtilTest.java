package com.rockstar.restutil.example;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.ios.IOSRestUtil;
import com.rockstar.restutil.common.RestCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestUtilTest")
public class IOSRestUtilTest extends RestUtilTest {

//    public void testGet() throws Exception {
//        testGet(new IOSRestUtil());
//    }

    public void getGoogleMapsDirection(float ox,float oy,float dx,float dy,RestCallback callback) {
        String path = "https://maps.googleapis.com/maps/api/directions/json";

        Map<String, Object> params = new HashMap<>();

        params.put("origin",ox + "," + oy);
        params.put("destination",dx + "," + dy);

    	new IOSRestUtil().get(path,params,callback);
    	
    }
}
