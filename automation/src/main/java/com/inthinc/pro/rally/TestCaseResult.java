package com.inthinc.pro.rally;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.RallyStrings;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.HTTPCommands.RallyFields;

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
        PASS("Pass"), 
        ;

        private String string;

        private Verdicts(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
    
    public static enum Fields implements RallyFields{
        ATTACHMENTS("Attachments"),
        BUILD("Build"),
        DATE("Date"),
        DURATION("Duration"),
        NOTES("Notes"),
        TEST_CASE("TestCase"),
        TEST_SET("TestSet"),
        TESTER("Tester"),
        VERDICT("Verdict"),
        
        ;

        private String string;

        private Fields(String string) {
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
        newResults();
    }

    
    public String getField(Fields field){
        try {
            return testCaseResults.getString(field.toString());
        } catch (JSONException e) {
            logger.info(StackToString.toString(e));
        }
        return null;
    }


    public void newResults() {
        testCaseResults = new JSONObject();
        setField(HTTPCommands.Fields.WORKSPACE, http.getWorkspace());
        setDate();
    }

    /**
     * Method Create Test Case Results<br />
     * Send the created testCaseResults to Rally
     */
    public void send_test_case_results() {
        http.postObjects(RallyWebServices.TEST_CASE_RESULTS, testCaseResults, true);
    }

    public void setBuildNumber(String build) {
        setField(Fields.BUILD, build);
    }
    
    public <T> void setField(RallyFields field, T value){
        try {
            testCaseResults.put(field.toString(), value);
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    /**
     * Method setDate add the time we executed the test. <br />
     * We will grab and format the current time.
     * 
     * @param date
     */
    public void setDate() {
        setDate(new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT));
    }

    public void setDate(AutomationCalendar automationCalendar) {
        setField(Fields.DATE, automationCalendar.toString());
    }

    public void setDuration(Long time) {
        setField(Fields.DURATION, time.toString());
    }

    public void setNotes(ErrorCatcher notes) {
        setNotes(RallyStrings.toString(notes.toString()));
    }
    
    public void setNotes(String box, ErrorCatcher notes) {
        setNotes(RallyStrings.toString(box + "\n"+notes.toString()));
    }

    public void setNotes(String notes) {
        setField(Fields.NOTES, notes);
    }

    public void setTestCase(NameValuePair searchParams) {
        TestCase testcase = new TestCase(http);
        setField(Fields.TEST_CASE, testcase.getTestCase(searchParams));
    }
    
    public void setTestSet(NameValuePair searchParams){
    	TestSet testSet = new TestSet(http);
        setField(Fields.TEST_SET, testSet.getTestSet(searchParams));
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
        setField(Fields.VERDICT, verdict.toString());
    }

    public String toString() {
        return PrettyJSON.toString(testCaseResults);
    }
}
