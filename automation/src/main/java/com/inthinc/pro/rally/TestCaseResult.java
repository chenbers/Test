package com.inthinc.pro.rally;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.RallyHTTP.RallyFields;

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
public class TestCaseResult extends RallyObject {

    public static enum Verdicts {
        BLOCKED("Blocked"),
        ERROR("Error"),
        FAIL("Fail"),
        INCONCLUSIVE("Inconclusive"),
        PASS("Pass"), ;

        private String verdict;

        private Verdicts(String verdict) {
            this.verdict = verdict;
        }

        @Override
        public String toString() {
            return verdict;
        }

        private static HashMap<String, Verdicts> lookupByCode = new HashMap<String, Verdicts>();

        static {
            for (Verdicts p : EnumSet.allOf(Verdicts.class)) {
                lookupByCode.put(p.toString(), p);
            }
        }

        public static Verdicts getEnum(String verdict) {
            return lookupByCode.get(verdict);
        }
    }

    public static enum Fields implements RallyFields {
        ATTACHMENTS("Attachments", false),
        BUILD("Build", true), /* Required */
        DATE("Date", true), /* Required */
        DURATION("Duration", false),
        NOTES("Notes", false),
        TEST_CASE("TestCase", true), /* Required */
        TEST_SET("TestSet", false),
        TESTER("Tester", false),
        VERDICT("Verdict", true), /* Required */
        WORKSPACE("Workspace", true), /* Required */

        ;

        private String string;
        private boolean required;

        private Fields(String string, boolean required) {
            this.string = string;
            this.required = required;
        }

        public boolean required() {
            return required;
        }

        public String toString() {
            return string;
        }
    }


    private JSONObject testCaseResults;
    private String notes;
    

    public TestCaseResult(String username, String password,
            RallyWebServices space) {
        http = new RallyHTTP(username, password);
        http.setWorkspace(space);
        newResults();
    }

    public Object getField(Fields field) {
        try {
            return testCaseResults.get(field.toString());
        } catch (JSONException e) {
            Log.debug(e);
        }
        return null;
    }

    public void newResults() {
        testCaseResults = new JSONObject();
        setField(Fields.WORKSPACE, http.getWorkspace());
        setDate();
    }

    /**
     * Method Create Test Case Results<br />
     * Send the created testCaseResults to Rally
     */
    public JSONObject send_test_case_results() {
        Fields fieldFailed = null;
        try {
            for (Fields field : EnumSet.allOf(Fields.class)) {
                fieldFailed = field;
                if (field.required()) {
                    testCaseResults.get(field.toString());
                }
            }
            http.postObjects(RallyWebServices.TEST_CASE_RESULTS, testCaseResults, true);
            return http.getResponse().getResults().getJSONObject(0);
        } catch (JSONException e) {
            Log.info("JSONException: "+e);
            Log.info("The " + fieldFailed
                    + " is missing from the test case results.");
            Log.info(PrettyJSON.toString(testCaseResults));
            Log.info(StackToString.toString(e));
        } catch(Exception e){
            Log.info("something bad happened with send_test_case_results()\n%s", e);
        }
        return testCaseResults;
    }
    
    public boolean deleteTestCaseResult(JSONObject tcr){
        http.deleteObject(tcr);
        return true;
    }

    public void setBuildNumber(String build) {
        setField(Fields.BUILD, build);
    }

    public <T> void setField(RallyFields field, T value) {
        try {
            testCaseResults.put(field.toString(), value);
        } catch (JSONException e) {
            Log.error(e);
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
        setNotes(RallyStrings.toString(box + "\n" + notes.toString()));
    }

    public void setNotes(String notes) {
    	if ((this.notes == null)||(this.notes.isEmpty())){
    		this.notes=notes;
    	}
    	else{
    		this.notes += "; "+notes;
    	}
        setField(Fields.NOTES, this.notes);
    }

    public void setTestCase(NameValuePair searchParams) {
        TestCase testcase = new TestCase(http);
        setField(Fields.TEST_CASE, testcase.getTestCase(searchParams));
    }

    public void setTestSet(NameValuePair searchParams) {
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

    public void updateTestCase(String testCase, String byID) {
        TestCase tc = new TestCase(http);
        tc.setTestCase(testCase);
        AutomationCalendar today = AutomationCalendar.now(WebDateFormat.RALLY_DATE_FORMAT);

        Verdicts lastVerdict = tc.getLastVerdict();

        if (!tc.wasRunToday() || lastVerdict != Verdicts.PASS) {
            Log.info("Last Test Case result: " + tc.getLastRun());
            Log.info("Today is: " + today);
            setTestCase(new NameValuePair(byID, testCase));
            send_test_case_results();
        }
    }

	public boolean hasVitalFields() {
		return missingRequiredFields().isEmpty();
	}
	
	private List<Fields> missingRequiredFields(){
	    List<Fields> missing = new ArrayList<Fields>();
	    for (Fields field : Fields.values()){
            if (field.required){
                if (getField(field)==null){
                    missing.add(field);
                }
            }
        }
	    return missing;
	}

    public String missingFields() {
        return missingRequiredFields().toString();
    }
}
