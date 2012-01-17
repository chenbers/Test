package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.junit.After;
import org.junit.Before;

import com.inthinc.pro.automation.test.BrowserRallyTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

/**
 * WebRallyTest is an abstract class that can be extended for automated in-browser tests of the "web" project, that DO get reported to Rally. In other words, typical web project
 * automated browser tests will extend this class, which provides before and after methods that setup and tear down: the browser, related selenium and Rally resources
 * 
 */
public abstract class WebRallyTest extends BrowserRallyTest {

    public WebRallyTest() {
        super(MastheadEnum.VERSION);
    }

    @Before
    @BeforeStory
    //@BeforeScenario//TODO: jwimmer: there is some issue where we are loosing seleniums ability to control the browser on all but the FIRST test scenario.  BUT the scenarios are not necessarily written to shut themselves down?  eventually I need to work out the "Timer has already been stopped" error and fix this.
    public void a_before() {
        super.before();
    }

    
    @After
    @AfterStory
    //@AfterScenario
    @Override
    public void after() {
        System.out.println("get_test_case(): '" + get_test_case() + "'");
        super.after();
        if (get_test_case() == null || get_test_case().equals(""))
            logger.warn("This test did NOT have a testcase?!");
        didTestFail();
    }

}
