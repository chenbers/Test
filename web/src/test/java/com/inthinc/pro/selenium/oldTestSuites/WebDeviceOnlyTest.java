package com.inthinc.pro.selenium.oldTestSuites;

import org.junit.After;
import org.junit.Before;

import com.inthinc.pro.automation.test.DeviceOnlyTest;

public class WebDeviceOnlyTest extends DeviceOnlyTest {

    public WebDeviceOnlyTest() {
    }
    
    @Before
    public void a_before(){
        super.before();
    }
    
    @After
    @Override
    public void after(){
        super.after();
        didTestFail();
    }

}
