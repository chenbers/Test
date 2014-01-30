package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.junit.After;
import org.junit.Before;

import com.inthinc.pro.automation.test.BrowserTest;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

/**
 * WebTest is an abstract class that can be extended for automated in-browser tests of the "web" project, that do NOT get reported to Rally.
 *
 */

public class WebTest extends BrowserTest {


	public WebTest(){
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
        super.after();
        didTestFail();
    }
}
