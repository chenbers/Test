package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.inthinc.pro.automation.selenium.RallyTest;

public class WebRallyTest extends RallyTest{
    
    @BeforeClass
    public static void beforeClass(){
        RallyTest.beforeClass();
    }
    
    @Before
    @Override
    public void before(){
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
