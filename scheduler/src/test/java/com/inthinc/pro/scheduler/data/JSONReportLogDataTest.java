package com.inthinc.pro.scheduler.data;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for {@link JSONReportLogData}.
 */
public class JSONReportLogDataTest {

    @Test
    public void testJSON() {
        // get a mock instance
        ReportLogData mockData = createMockReportLogData();

        // test json
        String strJson = mockData.formatForStash();

        // basic
        assertNotNull(strJson);
        assertFalse(strJson.isEmpty());

        // contents
        try {
            JSONObject jsObj = new JSONObject(strJson);

            // must not have general error
            assertFalse(jsObj.has("reportLogError"));

            // must have "reportLog"
            assertTrue(jsObj.has("reportLog"));

            // now get "reportLog" and test that it has the correct info
            JSONObject reportLog = jsObj.getJSONObject("reportLog");
            assertNotNull(reportLog);

            // all fields are found
            assertTrue(reportLog.has("reportID"));
            assertTrue(reportLog.has("reportType"));
            assertTrue(reportLog.has("accountId"));
            assertTrue(reportLog.has("accountName"));
            assertTrue(reportLog.has("idUserRequestingReport"));
            assertTrue(reportLog.has("recipientUserIds"));
            assertTrue(reportLog.has("recipientEmailAddresses"));
            assertTrue(reportLog.has("scheduledTime"));
            assertTrue(reportLog.has("actualTimeSent"));
            assertTrue(reportLog.has("processMilis"));
            assertTrue(reportLog.has("errors"));
            assertTrue(reportLog.has("success"));

            // fileds have correct value
            assertEquals(reportLog.getString("reportID"), "234");
            assertEquals(reportLog.getString("reportType"), "super");
            assertEquals(reportLog.getString("accountId"), "123");
            assertEquals(reportLog.getString("accountName"), "hello");
            assertEquals(reportLog.getString("idUserRequestingReport"), "34525");
            assertEquals(reportLog.getString("processMilis"), "14124124");
            assertEquals(reportLog.getString("success"), "false");
            assertEquals(reportLog.getString("scheduledTime"), "08:20");

            //test recipientUserIds
            JSONArray recIdArr = reportLog.getJSONArray("recipientUserIds");
            assertEquals(recIdArr.getInt(0), 123);
            assertEquals(recIdArr.getInt(1), 234);
            assertEquals(recIdArr.getInt(2), 345);

            //test recipientEmailAddresses
            JSONArray recEmailList = reportLog.getJSONArray("recipientEmailAddresses");
            assertEquals(recEmailList.getString(0), "abc@inthinc.com");
            assertEquals(recEmailList.getString(1), "cde@inthinc.com");
            assertEquals(recEmailList.getString(2), "efg@inthinc.com");

            //test errors
            JSONArray errorsArr = reportLog.getJSONArray("errors");
            for (int i = 0; i <= 5; i++) {
                JSONObject err = errorsArr.getJSONObject(i);
                assertNotNull(err);

                assertTrue(err.has("message"));
                assertTrue(err.has("type"));
                assertTrue(err.has("stackTrace"));

                assertEquals(err.getString("message"), "exception: " + i);
                assertEquals(err.getString("type"), "java.lang.Exception");

                JSONArray stackTrace = err.getJSONArray("stackTrace");
                assertNotNull(stackTrace);
                assertFalse(stackTrace.length() == 0);
            }

        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Creates mock data for testing report log.
     *
     * @return mock report log object
     */
    private ReportLogData createMockReportLogData() {
        // Fake a report object
        ReportLogData data = new JSONReportLogData();

        // add simple data
        data.setAccountName("hello");
        data.setActualTimeSent(new DateTime());
        data.setAccountId(123);
        data.setProcessMilis(14124124l);
        data.setReportID(234);
        data.setReportType("super");
        data.setSuccess(false);
        data.setScheduledTime(500);
        data.setIdUserRequestingReport(34525);

        // add list data
        data.setRecipientUserIds(Arrays.asList(123, 234, 345));
        data.setRecipientEmailAddresses(Arrays.asList("abc@inthinc.com", "cde@inthinc.com", "efg@inthinc.com"));

        // create fake exceptions and add them
        List<Throwable> thList = new ArrayList<Throwable>();

        for (int i = 0; i <= 5; i++) {
            try {
                throw new Exception("exception: " + i);
            } catch (Exception e) {
                thList.add(e);
            }
        }
        data.setErrors(thList);

        return data;
    }
}
