package com.rockstar.restutil.test;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.ios.IOSRestUtil;

/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestUtilTest")
public class IOSRestUtilTest extends RestUtilTest {

    public void testGet() throws Exception {
        testGet(new IOSRestUtil());
    }
}
