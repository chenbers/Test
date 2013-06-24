package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/19/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonEmailReportQueueTest {
    private static final Integer REPORT_SCHEDULE_LIST_SIZE = 10;



    private static AmazonQueueImpl emailReportQueue;

    @BeforeClass
    public static void beforeClass(){
        emailReportQueue = new AmazonQueueImpl();
        emailReportQueue.setMaxPop(10);
        emailReportQueue.setQueueName("AmazonEmailReportQueueTest");
        emailReportQueue.setPropertyfile("tiwipro.properties");

        emailReportQueue.init();
    }

    @AfterClass
    public static void afterClass() {
        emailReportQueue.deleteQueue();
    }

    @Before
    public void beforeTest(){

        if(!emailReportQueue.isQueueCreated()){
            emailReportQueue.createQueue();
        }
    }

    @Test
    public void amazonSQSClientCheck(){
        assertTrue(emailReportQueue.isClientCreated());
    }

    @Test
    public void createQueueTest(){
        if(emailReportQueue.isQueueCreated()){
            emailReportQueue.deleteQueue();
            AmazonAwsUtil.amazonWaitRule();
        }

        assertNotNull(emailReportQueue.createQueue());
    }

    @Test
    public void deleteQueueTest() {
        if(!emailReportQueue.isQueueCreated()){
            emailReportQueue.createQueue();
        }

        assertTrue(emailReportQueue.deleteQueue());

        AmazonAwsUtil.amazonWaitRule();
    }

    @Test
    public void pushToQueueTest() {
        if(!emailReportQueue.isQueueCreated()){
            emailReportQueue.createQueue();
        }
        assertTrue(emailReportQueue.pushToQueue("Test"));
        assertFalse(emailReportQueue.isQueueEmpty());
    }

    @Test
    public void popFromQueueTest() {
        if(!emailReportQueue.isQueueCreated()){
            emailReportQueue.createQueue();
        }

        emailReportQueue.pushToQueue("Test");

        List<Message> msgs = (List<Message>)emailReportQueue.popFromQueue();

        assertNotNull(msgs);
        assertTrue(msgs.size() > 0);

        boolean hasCorrectMsgBody = false;

        for(Message message : msgs){
            if(message.getBody().equals("Test")) {
                hasCorrectMsgBody = true;
                break;
            }
        }

        assertTrue(hasCorrectMsgBody);
    }

    @Test
    public void clearQueueTest() {
        assertTrue(emailReportQueue.clearQueue());
    }

    @Test
    public void deleteMessageFromQueueTest() {
        if(!emailReportQueue.isQueueCreated()){
            emailReportQueue.createQueue();
        }

        emailReportQueue.pushToQueue("Test");

        List<Message> messageList = (List<Message>)emailReportQueue.popFromQueue();

        for(Message message : messageList) {
            String receiptHandle = message.getReceiptHandle();

            emailReportQueue.deleteMessageFromQueue(receiptHandle);
        }

        assertTrue(emailReportQueue.isQueueEmpty());
    }

    private List<ReportSchedule> buildReportScheduleList() {

        Integer reportScheduleID = 0;
        Integer accountID = 0;
        Integer reportID = 0;
        Integer userID = 0;

        List<String> emailTo = new ArrayList<String>();
        emailTo.add("Test Email Address");

        String name = "Test ReportSchedule";
        Date startDate = new Date();
        Date endDate = new Date();

        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        dayOfWeek.add(true); // 0
        dayOfWeek.add(false); // 0
        dayOfWeek.add(false); // 0
        dayOfWeek.add(false); // 0
        dayOfWeek.add(false); // 0
        dayOfWeek.add(false); // 0
        dayOfWeek.add(false); // 0


        Integer driverID = 0;
        Integer vehicleID = 0;
        Integer groupID = 0;
        Status status = Status.ACTIVE;
        Date lastDate = new Date();
        Integer timeOfDay = 200;

        List<ReportSchedule> retVal = new ArrayList<ReportSchedule>();

        for(int i = 0; i < REPORT_SCHEDULE_LIST_SIZE; i++) {
            ReportSchedule reportSchedule = new ReportSchedule(reportScheduleID + i, accountID, reportID, userID, emailTo,
                    name, startDate, endDate, dayOfWeek, driverID, vehicleID, groupID, status, lastDate, timeOfDay);
            retVal.add(reportSchedule);
        }

        return retVal;
    }


}
