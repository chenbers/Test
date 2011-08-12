package com.inthinc.pro.automation.selenium;

import org.apache.commons.httpclient.NameValuePair;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
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
	private TestCaseResult rally;
	private AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
	private String testCase;
	
	public RallyTest(SeleniumEnums version){
		super(version);
	}

	@Override
	public void before() {
		super.before();
		if(apb.getSendToRally())
		{
    		try {
                rally = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
    			rally.newResults();
    		} catch (Exception e) {
    			e.printStackTrace();
    			skip = true;
    			throw new NullPointerException();
    		}
		}
	}

	@Override
	public void after() {
	    super.after();
		if (!skip && apb.getSendToRally()) {
			try {
				setTestSet(determineTestSet());
				rally.setBuildNumber(getBuildNumber());
				rally.setVerdict(getTestVerdict());
                rally.setNotes(determineTestSet(), errors);
                rally.setDuration(stopTime - startTime);
				rally.send_test_case_results();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setTestSet(String name){
	    logger.info("setTestSet("+name+")");
		rally.setTestSet(new NameValuePair("Name", name));
	}

	public void set_test_case(String formattedID) {
	    logger.info("set_test_case("+formattedID+")");
	    testCase = formattedID;
		rally.setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), formattedID));
	}
	public String get_test_case(){
	    return testCase;
	}
	
	public String determineTestSet() {
        return "automation_"+apb.getOperatingSystem()+"_"+apb.getDefaultWebDriverName()+apb.getDefaultWebDriverVersion();
	}
}
