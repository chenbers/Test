package com.inthinc.pro.automation.selenium;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONObject;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestCaseResult;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

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
	private TestCaseResult tcr;
	private AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
	private String testCase;
	private JSONObject deletelastResults;
	
	public RallyTest(SeleniumEnums version){
		super(version);
	}

    @Override
    public void before() {
        super.before();
        try {
            tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
            tcr.newResults();
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
			    if (deletelastResults != null){
			        tcr.deleteTestCaseResult(deletelastResults);
			    }
			    if(apb.getAddTestSet()){
			        setTestSet(determineTestSet());
			    }
				tcr.setBuildNumber(getBuildNumber());
				tcr.setVerdict(getTestVerdict());
                tcr.setNotes(determineTestSet(), errors);
                tcr.setDuration(stopTime - startTime);
				tcr.send_test_case_results();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean runToday(){
	    TestCase tc = new TestCase(tcr);
	    tc.setTestCase(testCase);
	    skip = tc.wasRunToday();
	    if (!skip){
	        tcr.setBuildNumber(getBuildNumber());
	        tcr.setVerdict(Verdicts.PASS);
	        deletelastResults = tcr.send_test_case_results();
	    }
	    return !skip;
	}
	
	private void setTestSet(String name){
	    logger.info("setTestSet("+name+")");
		tcr.setTestSet(new NameValuePair("Name", name));
	}

	public void set_test_case(String formattedID) {
	    logger.info("set_test_case("+formattedID+")");
	    testCase = formattedID;
		tcr.setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), formattedID));
	}
	public String get_test_case(){
	    return testCase;
	}
	
	public String determineTestSet() {
        return "automation_"+apb.getOperatingSystem()+"_"+apb.getDefaultWebDriverName();
	}
}
