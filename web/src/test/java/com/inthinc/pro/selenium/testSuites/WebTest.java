package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import com.inthinc.pro.automation.selenium.AutomatedTest;

public class WebTest extends AutomatedTest {

    @Before
    @Override
    public void before() {
        super.before();
    }

    @After
    @Override
    public void after() {
        super.after();
        didTestFail();
    }

    @AfterClass
    public static void afterClass() {
        AutomatedTest.afterClass();
    }
}
