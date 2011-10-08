package com.inthinc.pro.automation.test;

import com.inthinc.pro.automation.selenium.ErrorCatcher;


public class DeviceOnlyTest extends Test {
    

    @Override
    public void before(){
        super.before();
        setErrorCatcher(new ErrorCatcher());
        setBuildNumber("Not Applicable");
    }
    
    @Override
    public void after(){
        super.after();
    }

}
