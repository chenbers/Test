package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.services.sqs.model.Message;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for local amazon queue.
 */
public class AmazonQueueImplLocalTest {
    private static AmazonQueueImplLocal localQueue;

    @BeforeClass
    public static void beforeClass() {
        localQueue = new AmazonQueueImplLocal();
        localQueue.setMessageMap(new HashMap<String, String>());
    }

    @Test
    public void pushTest() {
        localQueue.clear();

        assertTrue(localQueue.pushToQueue("Test"));
        assertTrue(!localQueue.isQueueEmpty());
    }

    @Test
    public void popTest() {
        localQueue.clear();

        localQueue.pushToQueue("Test");
        List<Message> msgs = (List<Message>) localQueue.popFromQueue();
        assertNotNull(msgs);
        assertFalse(msgs.isEmpty());
        boolean hasCorrectMsgBody = false;

        for (Message message : msgs) {
            if (message.getBody().equals("Test")) {
                hasCorrectMsgBody = true;
                break;
            }
        }

        assertTrue(hasCorrectMsgBody);
    }

    @Test
    public void deleteTest() {
        localQueue.clear();

        localQueue.pushToQueue("Test");
        boolean hasMessages = true;

        while (hasMessages) {
            List<Message> messageList = (List<Message>) this.localQueue.popFromQueue();
            hasMessages = messageList != null && messageList.size() > 0;

            for (Message message : messageList) {
                String receiptHandle = message.getReceiptHandle();
                localQueue.deleteMessageFromQueue(receiptHandle);
            }
        }

        assertTrue(localQueue.isQueueEmpty());
    }

    @Test
    public void preserveOrderTest() {
        localQueue.clear();

        List<String> messages = Arrays.asList("1", "2", "3", "4", "5", "6");

        // push messages
        for (String msg : messages) {
            localQueue.pushToQueue(msg);
        }

        // walk data and assert that pop is in that order
        for (String revMsg : messages) {
            List<Message> messageList = (List<Message>) this.localQueue.popFromQueue();
            assertTrue(messageList.size() == 1);
            assertTrue(messageList.get(0).getBody().equals(revMsg));
        }
    }
}
