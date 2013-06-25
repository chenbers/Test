package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/19/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonQueueImpl implements AmazonQueue {
    private static final Logger logger = Logger.getLogger(AmazonQueueImpl.class);

    //Properties file values
    private String queueName;

    //Instance props
    private AmazonSQS amazonSQSClient;

    public AmazonQueueImpl(){
    }

    public AmazonSQS getAmazonSQSClient() {
        return amazonSQSClient;
    }

    public void setAmazonSQSClient(AmazonSQS amazonSQSClient) {
        this.amazonSQSClient = amazonSQSClient;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String getAmazonQueueURL() {
        return this.amazonSQSClient.getQueueUrl(new GetQueueUrlRequest(this.queueName)).getQueueUrl();
    }

    @Override
    public Boolean pushToQueue(String message) throws AmazonServiceException, AmazonClientException {
        this.amazonSQSClient.sendMessage(new SendMessageRequest(getAmazonQueueURL(), message));
        logger.info("Pushed " + "\"" + message +"\"" + " to Amazon queue " + getAmazonQueueURL());

        return true;
    }

    @Override
    public Collection<Message> popFromQueue() throws AmazonServiceException, AmazonClientException {
        return this.amazonSQSClient.receiveMessage(new ReceiveMessageRequest(getAmazonQueueURL())).getMessages();
    }

    @Override
    public Boolean deleteMessageFromQueue(String receiptHandle) throws AmazonServiceException, AmazonClientException {
        this.amazonSQSClient.deleteMessage(new DeleteMessageRequest(getAmazonQueueURL(), receiptHandle));
        logger.info("Deleted " + receiptHandle + " from Amazon queue " + getAmazonQueueURL());

        return true;
    }

    @Override
    public Boolean isQueueEmpty() {
        List<Message> messageList;
        messageList = this.amazonSQSClient.receiveMessage(new ReceiveMessageRequest(getAmazonQueueURL())).getMessages();

        return messageList == null || messageList.size() < 1;
    }
}
