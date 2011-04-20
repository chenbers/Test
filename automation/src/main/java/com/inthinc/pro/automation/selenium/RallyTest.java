package com.inthinc.pro.automation.selenium;

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
public abstract class RallyTest extends AutomatedTest {
	private static TestCaseResult rally;

	private final static String username = "dtanner@inthinc.com";
	private final static String password = "aOURh7PL5v";
	private final static RallyWebServices workspace = RallyWebServices.INTHINC;

	public static void beforeClass() {
		try {
			rally = new TestCaseResult(username, password, workspace);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException();
		}

	}// end setup

	@Override
	public void before() {
	    if (startTime == null){
	        startTime = currentTime();
	    }
		super.before();
		try {
			rally.new_results();
		} catch (Exception e) {
			e.printStackTrace();
			skip = true;
			throw new NullPointerException();
		}

	}

	@Override
	public void after() {
	    super.after();
		if (!skip) {
			try {
				rally.setBuildNumber(getBuildNumber());
				rally.setVerdict(getTestVerdict());
                rally.setNotes(errors);
                rally.setDuration(stopTime - startTime);
				rally.send_test_case_results();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void set_test_case(String formattedID) {
		rally.setTestCase(new NameValuePair("FormattedID", formattedID));
	}
}
