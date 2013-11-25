package com.inthinc.pro.scheduler.data;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

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
     * @param <T>  type of list element
     * @return json array version
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
     * @throws JSONException the json API may throw an exception
     */
    private JSONObject exceptionToJsonObject(Throwable t) throws JSONException {
        JSONObject objExc = new JSONObject();
        objExc.put("message", t.getMessage() != null ? t.getMessage() : "");
        objExc.put("type", t.getClass().getName());

        List<String> listStTr = new ArrayList<String>();
        for (StackTraceElement ste : t.getStackTrace())
            listStTr.add(ste.toString());

        objExc.put("stackTrace", listStTr);

        return objExc;
    }

    @Override
    public String formatForStash() {
        String ret;
        String dateFormat = "yyyy/MM/dd kk:mm:ss";

        try {
            // report log
            JSONObject obj = new JSONObject();
            JSONObject reportLog = new JSONObject();
            reportLog.put("reportID", getReportID());
            reportLog.put("reportType", getReportType());
            reportLog.put("accountId", getAccountId());
            reportLog.put("accountName", getAccountName());
            reportLog.put("idUserRequestingReport", getIdUserRequestingReport());
            reportLog.put("scheduledTime", minutesToHourMinute(getScheduledTime()));
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
            ret = obj.toString();
        } catch (JSONException e) {
            // json API exception, save a manual String json with the exception
            ret = "{\"reportLog\": {\"jsonException\":\"" + e.getMessage() + "\"}}";
        }
        return ret;
    }

    /**
     * Transforms number of minutes to a hh:mm format.
     *
     * @param minutes number of minutes
     * @return hh:mm
     */
    private String minutesToHourMinute(Integer minutes) {
        if (minutes == null)
            return "";

        String hh = String.valueOf(String.format("%02d", minutes / 60));
        String mm = String.valueOf(String.format("%02d", minutes % 60));

        return hh + ":" + mm;
    }
}
