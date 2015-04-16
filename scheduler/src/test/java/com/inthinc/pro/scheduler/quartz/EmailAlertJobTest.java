package com.inthinc.pro.scheduler.quartz;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.dispatch.MailDispatcher;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Email alert job test.
 */
public abstract class EmailAlertJobTest {
    private EmailAlertJob emailAlertJob;

    private MailDispatcher mailDispatcher;

    private AlertMessageBuilder alertMessageBuilder;

    private List<AlertMessageBuilder> alertMessageBuilderList;

    @Mocked
    private AlertMessageDAO alertMessageDAO;

    @Mocked
    private JobExecutionContext jobExecutionContext;

    public abstract String getExpectedMessage();

    public abstract String getExpectedTitle();

    public abstract void setAlertMessageType(AlertMessageBuilder alertMessageBuilder);

    public abstract void setAlertName(AlertMessageBuilder alertMessageBuilder);


    /*
     * #RedFlag ezCRM Parmeter List
     * #0 {Red Flag Alert Name} - for subject line
     * #1 {GROUP} - fmt: division-division-...-team
     * #2 {CATEGORY} - 0|1|2 for now...One of: Critical|Normal|Information
     * #3 {DATE} - fmt: yyyy-mm-dd hh:mm:ss
     * #4 {EMP ID} - fmt: external driver id | omitted if no driver
     * #5 {DRIVER ID} - fmt: internal driver id | UNKNOWN if no driver - empty here
     * #6 {DRIVER NAME} - fmt: First Middle Last | UNKNOWN - empty here
     * #7 {VEHICLE NAME}
     * #8 {VEHICLE ID} - fmt: internal vehicle ID
     * #9 {YEAR} - fmt: yyyy | ommited if blank
     * #10 {MAKE} - ommited if blank
     * #11 {MODEL} - ommited if blank
     * #12 {LAT} - latitude | NO GPS LOCK
     * #13 {LON} - longitude | NO GPS LOCK
     * #14 {ADDRESS} - address | UNKNOWN
     * #15 {ODOMETER} - fmt: NNNNN (KM | Mi)
     * #16 {SPEED} - fmt: NN (KPH | MPH)
     * #17 {MeasurementType}: 0 or 1
     * #18 {Event Detail Param1}
     * #19 {Event Detail Param2}
     */
    public void setEzParamList(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setParamterList(getFakeParamList(30));
    }

    public void setParamList(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setEzParameterList(getFakeParamList(30));
    }

    @Before
    public void init() {
        // create test mail dispatcher
        mailDispatcher = new TestMailDispatcher(getExpectedTitle(), getExpectedMessage());

        // create job
        emailAlertJob = new EmailAlertJob();
        emailAlertJob.setAlertMessageDAO(alertMessageDAO);
        emailAlertJob.setMailDispatcher(mailDispatcher);

        // create fake alert message builder
        alertMessageBuilder = new AlertMessageBuilder();
        alertMessageBuilder.setAcknowledge(true);
        alertMessageBuilder.setAddress("test address");
        alertMessageBuilder.setAlertID(1);
        alertMessageBuilder.setLocale(Locale.getDefault());
        alertMessageBuilder.setMessageID(1);

        setAlertMessageType(alertMessageBuilder);
        setParamList(alertMessageBuilder);
        setEzParamList(alertMessageBuilder);
        setAlertName(alertMessageBuilder);

        alertMessageBuilderList = Arrays.asList(alertMessageBuilder);
    }


    @Test
    public void testAlertJobMessage() throws JobExecutionException {

        // alert message dao expectations
        new NonStrictExpectations() {{
            alertMessageDAO.getMessageBuilders(AlertMessageDeliveryType.EMAIL);
            result = alertMessageBuilderList;
        }};

        // run test
        emailAlertJob.executeInternal(jobExecutionContext);
    }

    protected List<String> getFakeParamList(int num) {
        assertTrue(num > 0);

        List<String> paramList = new ArrayList<String>();

        for (int i = 0; i <= num; i++) {
            paramList.add("param" + i);
        }
        return paramList;
    }

    public EmailAlertJob getEmailAlertJob() {
        return emailAlertJob;
    }

    public void setEmailAlertJob(EmailAlertJob emailAlertJob) {
        this.emailAlertJob = emailAlertJob;
    }

    public MailDispatcher getMailDispatcher() {
        return mailDispatcher;
    }

    public void setMailDispatcher(MailDispatcher mailDispatcher) {
        this.mailDispatcher = mailDispatcher;
    }

    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }

    public AlertMessageBuilder getAlertMessageBuilder() {
        return alertMessageBuilder;
    }

    public void setAlertMessageBuilder(AlertMessageBuilder alertMessageBuilder) {
        this.alertMessageBuilder = alertMessageBuilder;
    }

    public List<AlertMessageBuilder> getAlertMessageBuilderList() {
        return alertMessageBuilderList;
    }

    public void setAlertMessageBuilderList(List<AlertMessageBuilder> alertMessageBuilderList) {
        this.alertMessageBuilderList = alertMessageBuilderList;
    }
}

/**
 * Fake mail dispatcher that only tests message compared to expectedBody.
 */
class TestMailDispatcher extends MailDispatcher {
    private String expectedBody;
    private String expectedTitle;

    public TestMailDispatcher(String expectedTitle, String expectedBody) {
        this.expectedTitle = expectedTitle;
        this.expectedBody = expectedBody;
    }

    @Override
    public boolean send(String emailAddress, String subjectText, String messageText) {
        assertEquals(expectedTitle, subjectText);
        assertEquals(expectedBody, messageText);
        return true;
    }

    public String getExpectedTitle() {
        return expectedTitle;
    }

    public void setExpectedTitle(String expectedTitle) {
        this.expectedTitle = expectedTitle;
    }

    public String getExpectedBody() {
        return expectedBody;
    }

    public void setExpectedBody(String expectedBody) {
        this.expectedBody = expectedBody;
    }
}
