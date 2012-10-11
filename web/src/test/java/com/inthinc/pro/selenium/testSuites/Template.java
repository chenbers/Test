package com.inthinc.pro.selenium.testSuites;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageLogin;

@Ignore
public class Template extends WebRallyTest {

	@Test
	@Ignore
	public void UI() {
		// Set up the (main)page you intend to test...
		PageLogin loginPage = new PageLogin();

		// if this test corresponds to a TestCase in Rally then uncomment the
		// following and insert appropriate TC####
		set_test_case("TC####");

		// navigate the browser to the intended test page
		loginPage.openLogout();

		// perform page actions as necessary to recreate testcase

		// additional checks will typically be necessary to ensure your expected
		// result was met
	}

}
