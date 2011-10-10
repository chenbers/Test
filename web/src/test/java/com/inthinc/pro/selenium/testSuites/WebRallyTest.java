package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.Before;

import com.inthinc.pro.automation.test.BrowserRallyTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class WebRallyTest extends BrowserRallyTest{
	
	public WebRallyTest(){
		super(MastheadEnum.VERSION);
	}
    
    @Before
    public void a_before(){
        super.before();
    }
    
    @After
    @Override
    public void after(){
        System.out.println("WebRallyTest.after");
        System.out.println("get_test_case(): '"+get_test_case()+"'");
        super.after();
        if(get_test_case() == null || get_test_case().equals(""))   
            logger.warn("This test did NOT have a testcase?!");
        didTestFail(); 
    }
    
}
