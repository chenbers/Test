package com.inthinc.pro.selenium.testSuites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.selenium.AutomatedTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class WebTest extends AutomatedTest {

	@BeforeClass
	public static void beforeClass(){
        webVersionID = new SeleniumEnumWrapper(MastheadEnum.VERSION);
	}
	
    @Before
    public void a_before() {
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
