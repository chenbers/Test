package com.inthinc.pro.rally;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class TestCase extends RallyObject {

    public static enum Fields {

        ATTACHMENTS("Attachments", false),
        AUTHOR("Author", true), /* Required */
        CREATION_DATE("CreationDate", false),
        DESCRIPTION("Description", false),
        DEFECT_STATUS("DefectStatus", false),
        FORMATTED_ID("FormattedID", true), /* Required */
        LAST_BUILD("LastBuild", false),
        LAST_RUN("LastRun", false),
        LAST_VERDICT("LastVerdict", false),
        METHOD("Method", true), /* Required */
        NAME("Name", true), /* Required */
        NOTES("Notes", false),
        OBJECTIVE("Objective", false),
        OWNER("Owner", false),
        POST_CONDITIONS("PostConditions", false),
        PRE_CONDITIONS("PreConditions", false),
        PRIORITY("Priority", false),
        PROJECT("Project", true), /* Required */
        RESULTS("Results", false),
        RISK("Risk", false),
        TAGS("Tags", false),
        TEST_FOLDER("TestFolder", false),
        TYPE("Type", true), /* Required */
        VALIDATION_EXPECTED_RESULTS("ValidationExpectedResults", false),
        VALIDATION_INPUT("ValidationInput", false),
        WORK_PRODUCT("WorkProduct", false),
        WORKSPACE("Workspace", true),

        CUSTOM(),

        ;

        private String string;
        private boolean required;

        private Fields() {}

        private Fields(String string, boolean required) {
            this.string = string;
            this.required = required;
        }

        public boolean required() {
            return required;
        }

        public Fields setCustom(String fieldName) {
            if (!this.equals(Fields.CUSTOM)) {
                return this;
            }

            string = fieldName;
            return this;
        }

        public String toString() {
            return string;
        }

    }

    private Map<Fields, Object> fields;

    private AutomationCalendar lastRun;
    private Verdicts lastVerdict;

    private final static Logger logger = Logger.getLogger(TestCase.class);

    private Map<String, Boolean> customFields = new HashMap<String, Boolean>();

    private JSONObject testCase;

    public TestCase(RallyHTTP http) {
        this.http = http;
        fields = new HashMap<Fields, Object>();
    }
    
    public TestCase(RallyObject tcr){
        if (tcr == null){
            throw new IllegalStateException("The Test Case Result has not been created yet!");
        }
        fields = new HashMap<Fields, Object>();
        this.http = tcr.getHttp();
    }
    public TestCase(String username, String password, RallyWebServices space) {
        http = new RallyHTTP(username, password);
        http.setWorkspace(space);
        fields = new HashMap<Fields, Object>();
    }

    public JSONArray[] deleteCasesByProject(String project)
            throws URIException, JSONException {
        List<JSONArray> list = getTestCases(new NameValuePair("Project.Name",
                project), false);
        for (JSONArray array : list) {
            for (int i = 0; i < array.length(); i++) {
                http.deleteObject(array.getJSONObject(i));
            }
        }
        return null;
    }
    

    /**
     * Method getTestCase<br />
     * <br />
     * Create a new Get Method, then use the testCase_base as our URL base<br />
     * testCase_base = hostname+"testcase.js"<br />
     * <br />
     * Define our workspace as a filter, we need the _ref for our workspace<br />
     * then we add the parameters to our query<br />
     * <br />
     * ?workspace=WorkspaceAddress&query=( FormattedID = TCXXX )&start=1&pagesize=20<br />
     * <br />
     * Then we set the query string with setQueryString(query) This adds our<br />
     * query to the end of the URI<br />
     * We then send the request using httpRequest<br />
     * Turn the response body into a string for JSON to consume, <br />
     * Turn the string into a JSONObject. To get the results we have to do the<br />
     * following<br />
     * <br />
     * JSONObject.getJSONObject("QueryResult").getJSONArray("Results").<br />
     * getJSONObject(0)<br />
     * We should only get one result because of our query filter, then we add<br />
     * the <br />
     * test case object we got to the testCaseResults JSONObject<br />
     * 
     * @param testCaseFormattedID
     * @return
     * @throws HttpException
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject get_test_case_by_ID(String testCaseFormattedID,
            Boolean fetch) {
        return getTestCase(
                new NameValuePair("FormattedID", testCaseFormattedID), fetch);
    }

    public Object getField(Fields field) {
        if (fields.isEmpty()) {
            throw new IllegalStateException("The Test case hasn't been parsed");
        }
        return fields.get(field);
    }

    public Map<Fields, Object> getFields() {
        return fields;
    }

    private void getFieldValues() {
        NameValuePair search = new NameValuePair("Name", "Test Case");
        String filter;
        try {
            filter = http.constructFilter(search);
            http.constructQuery(filter, 1, 2, true);
            http.getObjects(RallyWebServices.TYPE_DEFINITION);
            JSONObject rallyTestCase = http.getResults().getJSONObject(0);
            JSONArray attrs = rallyTestCase.getJSONArray("Attributes");
            for (int i = 0; i < attrs.length(); i++) {
                JSONObject field = attrs.getJSONObject(i);
                customFields.put(field.getString("ElementName"),
                        field.getBoolean("Custom"));
            }
        } catch (URIException e) {
            logger.fatal(StackToString.toString(e));
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
    }

    public AutomationCalendar getLastRun() {
        return lastRun;
    }

    public Verdicts getLastVerdict() {
        return lastVerdict;
    }

    public JSONObject getTestCase(NameValuePair searchParams) {
        return getTestCase(searchParams, false);
    }

    public JSONObject getTestCase(NameValuePair searchParams, Boolean fetch) {
        try {
            String filter = http.constructFilter(searchParams);
            if (fetch)
                http.constructQuery(filter, 1, 200, fetch);
            else
                http.constructQuery(filter, 1, 200);

            http.getObjects(RallyWebServices.TEST_CASE);
            return http.getResults().getJSONObject(0);
        } catch (HttpException e) {
            logger.fatal(StackToString.toString(e));
        } catch (JSONException e) {
            try {
                logger.fatal(PrettyJSON.toString((http.getErrors().getJSONObject(0))));
            } catch (JSONException e1) {
                logger.fatal(StackToString.toString(e1));
            }
            logger.fatal(StackToString.toString(e));
        }
        return null;
    }

    public JSONObject getTestCase(String testCaseFormattedID) {
        return getTestCase(testCaseFormattedID, false);
    }

    public JSONObject getTestCase(String testCaseFormattedID, boolean fullItem) {
        return getTestCase(
                new NameValuePair("FormattedID", testCaseFormattedID), fullItem);
    }

    public List<JSONArray> getTestCases(NameValuePair pair) {
        NameValuePair[] set = { pair };
        return getTestCases(set, false);
    }

    public List<JSONArray> getTestCases(NameValuePair pair, Boolean fetch) {
        NameValuePair[] set = { pair };
        return getTestCases(set, fetch);
    }

    public List<JSONArray> getTestCases(NameValuePair[] filterBy) {
        return getTestCases(filterBy, false);
    }

    public List<JSONArray> getTestCases(NameValuePair[] filterBy, Boolean fetch) {
        List<JSONArray> getAll = new ArrayList<JSONArray>();
        try {
            String filter = http.constructFilter(filterBy);
            boolean more = true;
            Integer start = 1, received = 0;
            do {
                if (fetch)
                    http.constructQuery(filter, start, 200, fetch);
                else
                    http.constructQuery(filter, start, 200);
                http.getObjects(RallyWebServices.TEST_CASE);
                JSONArray reply = http.getResults();
                received += reply.length();
                getAll.add(reply);
                Integer total = http.getQueryResult()
                        .getInt("TotalResultCount");
                if (total < received || start > total) {
                    more = false;
                    break;
                }
                start = received + 1;

            } while (more);

        } catch (HttpException e) {
            logger.fatal(StackToString.toString(e));
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
        return getAll;
    }

    private boolean parseTestCase() {
        for (Fields field : EnumSet.allOf(Fields.class)) {
            if (field.equals(Fields.CUSTOM)) {
                continue;
            }
            try {
                if (field == Fields.LAST_RUN) {
                    String lastRun = testCase.getString(field.toString());
                    if (lastRun == null){
                        this.lastRun = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
                        this.lastRun.addToDay(-2);
                    } else {
                        this.lastRun = new AutomationCalendar(lastRun, WebDateFormat.RALLY_DATE_FORMAT);
                    }
                    fields.put(field, this.lastRun);
                } else if (field == Fields.LAST_VERDICT) {
                    lastVerdict = Verdicts.getEnum(testCase.getString(field
                            .toString()));
                    fields.put(field, lastVerdict);
                } else {
                    fields.put(field, testCase.get(field.toString()));
                }
            } catch (Exception e) {
                fields.put(field, new JSONObject());
            }
        }

        return true;
    }

    public boolean parseTestCase(JSONObject testCase) {
        this.testCase = testCase;
        return parseTestCase();
    }

    private JSONObject processForCustomFields(JSONObject testCase) {
        if (customFields.isEmpty()) {
            getFieldValues();
        }
        JSONArray keys = testCase.names();
        for (int i = 0; i < keys.length(); i++) {
            try {
                if (keys.getString(i) == null)
                    continue;
                String key = keys.getString(i);
                if (customFields.containsKey(key)) {
                    if (customFields.get(key)) {
                        Object values = testCase.get(key);
                        testCase.remove(key);
                        key = "Custom:" + key.toLowerCase().replace(" ", "_");
                        testCase.put(key, values);
                    }
                }
            } catch (JSONException e) {
                logger.fatal(StackToString.toString(e));
            }
        }
        return testCase;
    }

    public boolean setTestCase(String testCaseID) {
        testCase = getTestCase(testCaseID, true);
        return parseTestCase();
    }

    public JSONArray[] update(JSONObject testCase) {
        http.postObjects(RallyWebServices.TEST_CASE,
                processForCustomFields(testCase));
        System.out.println(http.getQueryResult());
        JSONArray[] sendme = new JSONArray[2];
        try {
            sendme[0] = http.getErrors();
            sendme[0] = http.getWarnings();
        } catch (JSONException e) {
            logger.fatal(StackToString.toString(e));
        }
        return sendme;
    }
    
    public boolean wasRunToday(){
        if (lastRun == null){
            return false;
        }
        return lastRun.compareDays(AutomationCalendar.now(WebDateFormat.RALLY_DATE_FORMAT));
    }
}
