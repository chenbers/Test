package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/20/13
 * Time: 7:23 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AmazonQueue {
    public AmazonSQS createQueueClient();
    public Boolean isClientCreated();
    public CreateQueueResult createQueue();
    public Boolean deleteQueue();
    public Boolean isQueueCreated();
    public Boolean isQueueEmpty();
    public Boolean pushToQueue(String message);
    public Collection<Message> popFromQueue();
    public Boolean deleteMessageFromQueue(String receiptHandle);
    public Boolean clearQueue();
}
