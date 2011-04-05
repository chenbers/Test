package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.inthinc.pro.automation.selenium.InthincTest;

public class WebTest extends InthincTest{
    
    
    @BeforeClass
    public static void beforeClass(){
        InthincTest.beforeClass();
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
    }
    
    @AfterClass
    public static void afterClass(){
        InthincTest.afterClass();
    }
    
}
