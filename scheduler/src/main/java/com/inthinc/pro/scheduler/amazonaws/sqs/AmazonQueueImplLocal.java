package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.services.sqs.model.Message;
import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Custom local version of the amazon queue. It has a local map of messages.
 * You can inject the message map through spring or set it manually.
 * <p/>
 * Note: If you configure {@link com.inthinc.pro.scheduler.quartz.EmailReportAmazonPullJob} to run with it then
 * you will be able to debug any report schedule without involving the push job.
 */
public class AmazonQueueImplLocal implements AmazonQueue {
    private static final Logger logger = Logger.getLogger(AmazonQueueImplLocal.class);

    private Map<String, String> messageMap;
    private Deque<String> backingKeyQueue;

    @Override
    public String getAmazonQueueURL() {
        return "local-queue";
    }

    @Override
    public Boolean pushToQueue(String message) {
        try {
            // Generate a distinct key for the new element
            boolean keyDistinct = false;
            String key = null;
            while (key == null || !keyDistinct) {
                key = String.valueOf(new Date().getTime());
                keyDistinct = !messageMap.containsKey(key);
            }

            // add the new element
            messageMap.put(key, message);
            backingKeyQueue.push(key);

            logger.info("Pushed " + "\"" + message + "\"" + " to " + getAmazonQueueURL());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Collection<Message> popFromQueue() {
        List<Message> messageList = new ArrayList<Message>();

        // create a message if you have remaining in the map
        if (!messageMap.isEmpty()) {
            String key = backingKeyQueue.pollLast();
            Message msg = new Message();
            msg.setReceiptHandle(key);
            msg.setMessageId(key);
            msg.setBody(messageMap.remove(key));

            messageList.add(msg);
        }

        return messageList;
    }

    @Override
    public Boolean deleteMessageFromQueue(String receiptHandle) {
        try {
            messageMap.remove(receiptHandle);
            backingKeyQueue.remove(receiptHandle);
            logger.info("Deleted " + receiptHandle + " from " + getAmazonQueueURL());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isQueueEmpty() {
        return messageMap == null || messageMap.isEmpty();
    }

    /**
     * Clears internal data.
     */
    public void clear(){
        messageMap.clear();
        backingKeyQueue.clear();
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, String> messageMap) {

        // first create the backing queue and save the keys
        backingKeyQueue = new ArrayDeque<String>();
        for (Map.Entry<String, String> entry : messageMap.entrySet()) {
            backingKeyQueue.push(entry.getKey());
        }

        this.messageMap = messageMap;
    }
}
