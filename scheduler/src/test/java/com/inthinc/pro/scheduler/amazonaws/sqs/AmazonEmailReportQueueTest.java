package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/19/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonEmailReportQueueTest {
    private static AmazonQueueImpl amazonQueue;

    private static final String AWS_ACCESS_KEY_ID_VALUE = "AKIAJNK2BGYZV32YDROQ";
    private static final String AWS_SECRET_KEY_VALUE = "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";

    // Make sure this queue does exist on cloud
    private static final String QUEUE_NAME = "amazon-queue-email-reports-test";

    @BeforeClass
    public static void beforeClass() {
        amazonQueue = new AmazonQueueImpl();
        amazonQueue.setQueueName(QUEUE_NAME);
        amazonQueue.setAmazonSQSClient(new AmazonSQSClient(new BasicAWSCredentials(AWS_ACCESS_KEY_ID_VALUE, AWS_SECRET_KEY_VALUE)));
    }

    @Test
    public void getAmazonQueueURLTest() {
        assertNotNull(amazonQueue.getAmazonQueueURL());
        assertTrue(amazonQueue.getAmazonQueueURL().length() > 0);
    }

    @Test
    public void pushToQueueTest() {
        assertTrue(amazonQueue.pushToQueue("Test"));
        assertTrue(!amazonQueue.isQueueEmpty());
    }

    @Test
    public void popFromQueueTest() {

        amazonQueue.pushToQueue("Test");

        List<Message> msgs = (List<Message>)amazonQueue.popFromQueue();

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
    public void deleteMessageFromQueueTest() {

        amazonQueue.pushToQueue("Test");

        boolean hasMessages = true;

        while (hasMessages ) {
            List<Message> messageList  = (List<Message>)this.amazonQueue.popFromQueue();

            hasMessages = messageList != null && messageList.size() > 0;

            for(Message message : messageList) {
                String receiptHandle = message.getReceiptHandle();

                amazonQueue.deleteMessageFromQueue(receiptHandle);
            }
        }

        assertTrue(amazonQueue.isQueueEmpty());
    }
}
