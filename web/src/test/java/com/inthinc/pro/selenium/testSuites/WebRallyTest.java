package com.inthinc.pro.selenium.testSuites;

import org.apache.log4j.Level;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.junit.After;
import org.junit.Before;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.test.BrowserRallyTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

/**
 * WebRallyTest is an abstract class that can be extended for automated in-browser tests of the "web" project, that DO get reported to Rally. In other words, typical web project
 * automated browser tests will extend this class, which provides before and after methods that setup and tear down: the browser, related selenium and Rally resources
 * 
 */
public class WebRallyTest extends BrowserRallyTest {

    public WebRallyTest() {
        super(MastheadEnum.VERSION);
    }

    @Before
    @BeforeScenario
    public void a_before() {
        super.before();
    }

    
    @After
    @AfterScenario
    @Override
    public void after() {
        Log.debug("get_test_case(): '" + get_test_case() + "'");
        if (get_test_case() == null || get_test_case().equals(""))
            Log.info("This test did NOT have a testcase?!", Level.WARN);
        super.after();
        didTestFail();
    }

}
