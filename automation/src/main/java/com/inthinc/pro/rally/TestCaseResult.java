package com.inthinc.pro.rally;

import java.io.IOException;
import java.io.StringWriter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.RallyStrings;
import com.inthinc.pro.automation.utils.StackToString;

/**
 * 
 * This class provides a way to send test case results to Rally.
 * <p>
 * URI = https://rally1.rallydev.com/slm/webservice/1.21/testcaseresult/create.js ?workspace =https://rally1.rallydev.com/slm/webservice/1.21/workspace/558474157<br />
 * <br />
 * {<br />
 * TestCaseResult:<br />
 * {<br />
 * Build: 3.0<br />
 * Date: 2010-11-16T19:53:29.000Z<br />
 * Notes: This was done in Java<br />
 * We successfully sent the results<br />
 * TestCase: {<br />
 * _ref: https://rally1.rallydev.com/slm/webservice/1.21/testcase/2433294998.js<br />
 * _refObjectName: test Result Test<br />
 * _type: TestCase<br />
 * }<br />
 * Verdict: Pass<br />
 * }<br />
 * }
 * 
 * @author dtanner
 */
public class TestCaseResult {

    public static enum Verdicts {
        BLOCKED("Blocked"),
        ERROR("Error"),
        FAIL("Fail"),
        INCONCLUSIVE("Inconclusive"),
        PASS("Pass"), ;

        private String string;

        private Verdicts(String string) {
            this.string = string;
        }

        public String toString() {
            return string;
        }
    }

    private final static Logger logger = Logger.getLogger(TestCaseResult.class);

    private HTTPCommands http;
    private JSONObject testCaseResults;

    public TestCaseResult(String username, String password, RallyWebServices space) {
        http = new HTTPCommands(username, password);
        http.setWorkspace(space);
    }

    public String getBuildNumber() {
        try {
            return testCaseResults.getString("Build");
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
            return null;
        }
    }

    public String getDate() {
        try {
            return testCaseResults.getString("Date");
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
            return null;
        }
    }

    public String getDuration() {
        try {
            return testCaseResults.getString("Duration");
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
            return null;
        }
    }

    public JSONObject getTestCase() {
        try {
            return testCaseResults.getJSONObject("TestCase");
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
            return null;
        }
    }

    public String getVerdict() {
        try {
            return testCaseResults.getString("Verdict");
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
            return null;
        }
    }

    public void new_results() {
        testCaseResults = new JSONObject();
        try {
            testCaseResults.put("WorkSpace", http.getWorkspace());
            setDate();
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    /**
     * Method Create Test Case Results<br />
     * Send the created testCaseResults to Rally
     */
    public void send_test_case_results() {
        http.postObjects(RallyWebServices.TEST_CASE_RESULTS, testCaseResults, true);
    }

    public void setBuildNumber(String build) {
        try {
            testCaseResults.put("Build", build);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    /**
     * Method setDate<br />
     * <br />
     * add the time we executed the test. This has to be an xml string, so we will convert it from a <br />
     * date object<br />
     * 
     * @param date
     */
    public void setDate() {
        GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
        XsdDatetimeFormat xdf = new XsdDatetimeFormat();
        xdf.setTimeZone("MST");
        setDate(xdf.format(date.getTime()));
    }

    public void setDate(String date) {
        try {
            testCaseResults.put("Date", date);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    public void setDuration(Long time) {
        try {
            testCaseResults.put("Duration", time);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    public void setNotes(ErrorCatcher notes) {
        setNotes(RallyStrings.toString(notes.toString()));
    }

    public void setNotes(String notes) {
        try {
            testCaseResults.put("Notes", notes);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    public void setTestCase(NameValuePair searchParams) {
        TestCase testcase = new TestCase(http);
        try {
            testCaseResults.put("TestCase", testcase.getTestCase(searchParams));
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }
    
    public void setTestSet(NameValuePair searchParams){
    	TestSet testSet = new TestSet(http);
    	try{
    		testCaseResults.put("TestSet", testSet.getTestSet(searchParams));
    	} catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    	
    	
    	
    }

    /**
     * Method setVerdict<br />
     * <br />
     * add the verdict to our TestCaseResult JSON Object<br />
     * also validate it is one of the valid options<br />
     * 
     * @param verdict
     */
    public void setVerdict(Verdicts verdict) {
        try {
            testCaseResults.put("Verdict", verdict.toString());
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    public String toString() {
        return PrettyJSON.toString(testCaseResults);
    }
}
