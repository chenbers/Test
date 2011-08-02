package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import com.inthinc.pro.automation.selenium.RallyTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class WebRallyTest extends RallyTest{
	
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
        super.after();
        didTestFail();
    }
    
    @AfterClass
    public static void afterClass(){
        RallyTest.afterClass();
    }
}
