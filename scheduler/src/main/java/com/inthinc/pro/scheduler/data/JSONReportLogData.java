package com.inthinc.pro.scheduler.data;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON version of ReportLogData.
 */
public class JSONReportLogData extends ReportLogData {

    /**
     * Transforms a list to a json array.
     *
     * @param list list
     * @return json array version
     * @tparam T type of list element
     */
    private <T> JSONArray listToJsonArray(List<T> list) {
        JSONArray jsonArray = new JSONArray();
        for (T elem : list) {
            jsonArray.put(elem);
        }
        return jsonArray;
    }

    /**
     * Transform an exception in a json object with details for message and stack trace elements.
     *
     * @param t exception
     * @return json object with details
     */
    private JSONObject exceptionToJsonObject(Throwable t) {
        JSONObject objExc = new JSONObject();
        try {
            objExc.put("message", t.getMessage() != null ? t.getMessage() : "");

            List<String> listStTr = new ArrayList<String>();
            for (StackTraceElement ste : t.getStackTrace())
                listStTr.add(ste.toString());

            objExc.put("stackTrace", listStTr);
        } catch (Exception e) {
        } finally {
            return objExc;
        }
    }

    @Override
    public String formatForStash() {

        String dateFormat = "yyyy/MM/dd kk:mm:ss";

        // base object
        JSONObject obj = new JSONObject();
        String prettyJson = "";
        try {
            try {
                // report log
                JSONObject reportLog = new JSONObject();
                reportLog.put("reportID", getReportID());
                reportLog.put("reportType", getReportType());
                reportLog.put("accountId", getAccountId());
                reportLog.put("accountName", getAccountName());
                reportLog.put("idUserRequestingReport", getIdUserRequestingReport());
                reportLog.put("scheduledTime", getScheduledTime());
                reportLog.put("actualTimeSent", formatDate(getActualTimeSent(), dateFormat));
                reportLog.put("processMilis", getProcessMilis());
                reportLog.put("success", getSuccess());
                reportLog.put("recipientUserIds", listToJsonArray(getRecipientUserIds()));
                reportLog.put("recipientEmailAddresses", listToJsonArray(getRecipientEmailAddresses()));

                // special case for exceptions
                List<JSONObject> jsonObjErrorList = new ArrayList<JSONObject>();
                for (Throwable t : getErrors()) {
                    jsonObjErrorList.add(exceptionToJsonObject(t));
                }

                reportLog.put("errors", jsonObjErrorList);

                obj.put("reportLog", reportLog);

            } catch (Exception e) {
                obj.put("reportLogError", exceptionToJsonObject(e));
            }
        } finally {
            return obj.toString();
        }
    }
}
