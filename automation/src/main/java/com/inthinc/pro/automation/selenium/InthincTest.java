package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import org.apache.commons.httpclient.NameValuePair;

import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCaseResult;

/****************************************************************************************
 * Purpose: To standardize the setup and teardown for System Test Automation tests
 * <p>
 * Update:  11/18/Added comments and made changes to adhere to Java Coding Standards<br />
 * Update:  11/19/Changed name to InthincTest and removed previous functionality that<br />
 *              is no longer being used.  Also fixed start_selenium() so if we can't<br />
 *              start the selenium instance we will fail the test, move to <br />
 *              stop_selenium(), and skip the Rally stuff.<br />
 * 
 * @author larringt , dtanner
 */
public abstract class InthincTest {

	private Long startTime;
	private static HashMap<String, HashMap<String, String>> errors;
	private static TestCaseResult rally;

	private String testVerdict = "Error";

	private Boolean skip = false;

	private final static String username = "dtanner@inthinc.com";
	private final static String password = "aOURh7PL5v";
	private final static RallyWebServices workspace = RallyWebServices.INTHINC;

	private static CoreMethodLib selenium;

	public static void beforeClass() {
		try {
			rally = new TestCaseResult(username, password, workspace);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException();
		}

	}// end setup

	public void before() {
		try {
			selenium = GlobalSelenium.getYourOwn();
			startTime = System.currentTimeMillis() / 1000;
			rally.new_results();
		} catch (Exception e) {
			e.printStackTrace();
			skip = true;
			throw new NullPointerException();
		}

	}

	public void after() {
		if (!skip) {
			try {
				rally.setBuildNumber(selenium.getText("footerForm:version"));
				errors = selenium.getErrors().get_errors();
				selenium.stop();

				// check error var for entries
				if (errors.isEmpty())
					testVerdict = "Pass"; // no errors = pass
				else if (!errors.isEmpty())
					testVerdict = "Fail"; // errors = fail

				rally.setVerdict(testVerdict);
				rally.setNotes(errors);
				rally
						.setDuration(System.currentTimeMillis() / 1000
								- startTime);
				rally.send_test_case_results();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		startTime = null;
	}

	public static void afterClass() {
		GlobalSelenium.dieSeleniumDie();

	}// tear down

	public void set_test_case(String formattedID) {
		rally.setTestCase(new NameValuePair("FormattedID", formattedID));
	}
}
