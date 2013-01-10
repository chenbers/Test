package com.inthinc.pro.scheduler.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.form.FormQuestion;
import com.inthinc.pro.model.form.FormSubmission;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class AlertMessageBuilderTest {
    private static final Integer alertID = 1;
    private static final Integer messageID = 1;
    private static final Integer vehicleID = 1;
    private static final String address = "person@address.com";
    private static final boolean acknowledge = true;

    @SuppressWarnings("unused")
    @Test
    public void inspectionFailTest() {
        List<String> parameterList = new ArrayList<String>();
        FormSubmission submission = createFormSubmission();
        parameterList.add("12343546456");
        parameterList.add("driver");
        parameterList.add("vehicle");
        parameterList.add("Address");
        parameterList.add("http://dev.tiwipro.com:8080/forms/submissions");

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder(alertID, messageID, Locale.getDefault(), address, AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_FAIL, acknowledge, parameterList);
        assertNotNull(alertMessageBuilder);
        String text = LocalizedMessage.getStringWithValues(alertMessageBuilder.getAlertMessageType().toString(),alertMessageBuilder.getLocale(),
                (String[])alertMessageBuilder.getParamterList().toArray(new String[alertMessageBuilder.getParamterList().size()]));
         assertEquals("12343546456 driver (vehicle) A pre-trip inspection failed near Address, and the vehicle is not safe to drive. See http://dev.tiwipro.com:8080/forms/submissions for details.",text);
         int i = 0;
    }
    @Test
    public void inspectionPassTest() {
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("12343546456");
        parameterList.add("driver");
        parameterList.add("vehicle");
        parameterList.add("Address");
        parameterList.add("http://dev.tiwipro.com:8080/forms/submissions");

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder(alertID, messageID, Locale.getDefault(), address, AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_PASS, acknowledge, parameterList);
        assertNotNull(alertMessageBuilder);
        String text = LocalizedMessage.getStringWithValues(alertMessageBuilder.getAlertMessageType().toString(),alertMessageBuilder.getLocale(),
                (String[])alertMessageBuilder.getParamterList().toArray(new String[alertMessageBuilder.getParamterList().size()]));
       assertEquals("12343546456 driver (vehicle) A pre-trip inspection passed near Address. See http://dev.tiwipro.com:8080/forms/submissions for details.",text);
    }

    @Test
    public void inspectionNotDoneTest() {
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("12343546456");
        parameterList.add("driver");
        parameterList.add("vehicle");
        parameterList.add("Address");

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder(alertID, messageID, Locale.getDefault(), address, AlertMessageType.ALERT_TYPE_DVIR_DRIVEN_WITHOUT_INSPECTION, acknowledge, parameterList);
        assertNotNull(alertMessageBuilder);
        String text = LocalizedMessage.getStringWithValues(alertMessageBuilder.getAlertMessageType().toString(),alertMessageBuilder.getLocale(),
                (String[])alertMessageBuilder.getParamterList().toArray(new String[alertMessageBuilder.getParamterList().size()]));
       assertEquals("12343546456 driver is driving vehicle vehicle near Address without an inspection.",text);
    }
    @Test
    public void inspectionUnsafeTest() {
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("12343546456");
        parameterList.add("driver");
        parameterList.add("vehicle");
        parameterList.add("Address");

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder(alertID, messageID, Locale.getDefault(), address, AlertMessageType.ALERT_TYPE_DVIR_DRIVEN_INSPECTED_UNSAFE, acknowledge, parameterList);
        assertNotNull(alertMessageBuilder);
        String text = LocalizedMessage.getStringWithValues(alertMessageBuilder.getAlertMessageType().toString(),alertMessageBuilder.getLocale(),
                (String[])alertMessageBuilder.getParamterList().toArray(new String[alertMessageBuilder.getParamterList().size()]));
       assertEquals("12343546456 driver is driving vehicle vehicle near Address after inspection and found to be unsafe.",text);
    }
    @Test
    public void didntDoInspectionTest() {
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("12343546456");
        parameterList.add("driver");
        parameterList.add("vehicle");
        parameterList.add("Address");

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder(alertID, messageID, Locale.getDefault(), address, AlertMessageType.ALERT_TYPE_DVIR_NO_POST_TRIP_INSPECTION, acknowledge, parameterList);
        assertNotNull(alertMessageBuilder);
        String text = LocalizedMessage.getStringWithValues(alertMessageBuilder.getAlertMessageType().toString(),alertMessageBuilder.getLocale(),
                (String[])alertMessageBuilder.getParamterList().toArray(new String[alertMessageBuilder.getParamterList().size()]));
       assertEquals("12343546456 driver left vehicle vehicle near Address without a post-trip inspection.",text);
    }
    private FormSubmission createFormSubmission() {
        FormSubmission submission = new FormSubmission();
        submission.setFormTitle("Pre-trip Inspection");
        submission.setTimestamp(12343546456L);
        submission.setVehicleID(vehicleID);

        submission.setDataList(getQuestionList());
        return submission;
    }

    private List<FormQuestion> getQuestionList() {
        List<FormQuestion> dataList = new ArrayList<FormQuestion>();
        for (int i = 0; i < 10; i++) {
            FormQuestion formQuestion = new FormQuestion("Q" + i, "Question" + i, "Answer" + i);
            dataList.add(formQuestion);
        }
        return dataList;
    }
}
