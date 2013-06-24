package com.inthinc.pro.scheduler.amazonaws.sqs;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/19/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonQueueImpl implements AmazonQueue {
    private static final Logger logger = Logger.getLogger(AmazonQueueImpl.class);

    //Properties Key Names
    private int maxPop;
    private String queueName;

    //Instance props
    private AmazonSQS amazonSQSClient;
    private MessageSource messageSource;
    private String amazonQueueURL;
    private String propertyfile;

    //Configurable Queue Attributes
    Map<String, String> queueAttributes;

    public AmazonQueueImpl(){
    }

    public AmazonSQS getAmazonSQSClient() {
        return amazonSQSClient;
    }

    public void setAmazonSQSClient(AmazonSQS amazonSQSClient) {
        this.amazonSQSClient = amazonSQSClient;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getAmazonQueueURL() {
        return amazonQueueURL;
    }

    public void setAmazonQueueURL(String amazonQueueURL) {
        this.amazonQueueURL = amazonQueueURL;
    }

    public int getMaxPop() {
        return maxPop;
    }

    public void setMaxPop(int maxPop) {
        this.maxPop = maxPop;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getPropertyfile() {
        return propertyfile;
    }

    public void setPropertyfile(String propertyfile) {
        this.propertyfile = propertyfile;
    }

    public Map<String, String> getQueueAttributes() {
        return queueAttributes;
    }

    public void setQueueAttributes(Map<String, String> queueAttributes) {
        this.queueAttributes = queueAttributes;
    }

    public void init() {
        //Check if client exists if not create it.
        if(!this.isClientCreated()) {
            this.amazonSQSClient = this.createQueueClient();
        }

        // Create and queue if needed.
        if(!this.isQueueCreated()){
           this.createQueue();
        }
    }

    @Override
    public AmazonSQS createQueueClient() {
        /*  Create a client with the credential provided
            in AwsCredentials.properties file found on the class path.
         */
        AmazonSQS client = null;

        client = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider(this.propertyfile));
        logger.info("Amazon client has been created.");

        return client;
    }

    @Override
    public Boolean isClientCreated() {
        if(this.amazonSQSClient == null){
             return false;
        }
        return true;
    }

    @Override
    public CreateQueueResult createQueue() throws AmazonServiceException, AmazonClientException, NoSuchMessageException{
        CreateQueueResult queueResult = null;

        if(!isClientCreated()) {
            logger.error("Cannot create Amazon queue. The client is null.");
            return queueResult;
        }

        CreateQueueRequest createQueueRequest = new CreateQueueRequest(this.queueName);

        if(this.queueAttributes != null) {
            createQueueRequest.setAttributes(queueAttributes);
        }

        queueResult =  amazonSQSClient.createQueue(createQueueRequest);
        logger.info("Amazon Queue " + queueResult.getQueueUrl() + " has been created.");

        if(queueResult != null){
            this.amazonQueueURL = queueResult.getQueueUrl();
            logger.info("The Amazon Queue URL has been set to " + this.amazonQueueURL);
        }

        return  queueResult;
    }

    @Override
    public Boolean deleteQueue() throws AmazonServiceException, AmazonClientException {

        if(!isClientCreated()) {
           logger.error("Cannot delete Amazon queue. The client is null.");
           return false;
        }

        amazonSQSClient.deleteQueue(new DeleteQueueRequest(amazonQueueURL));
        logger.info("Amazon queue " + amazonQueueURL + " has been deleted.");


        return true;
    }

    @Override
    public Boolean isQueueCreated() throws AmazonServiceException, AmazonClientException {
        if(!isClientCreated()) {
            logger.error("Cannot check for Amazon queue. The client is null.");
            return false;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot check for Amazon queue. The Amazon Queue URL is null.");
            return false;
        }

        // Iterate through the ques on for this client.
        for(String queueURL : this.amazonSQSClient.listQueues().getQueueUrls()) {
            if(this.amazonQueueURL.equals(queueURL))
                return true;
        }

        return false;
    }

    @Override
    public Boolean isQueueEmpty() throws AmazonServiceException, AmazonClientException, NumberFormatException, NoSuchMessageException {
        if(!isClientCreated()) {
            logger.error("Cannot check for Amazon queue. The client is null.");
            return true;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot check for Amazon queue. The Amazon Queue URL is null.");
            return true;
        }

        List<Message> messageList = null;

        messageList = this.amazonSQSClient.receiveMessage(new ReceiveMessageRequest(this.amazonQueueURL)
                        .withMaxNumberOfMessages(this.maxPop))
                        .getMessages();

        String messageListSize = "0";

        if( messageList != null )
            messageListSize = String.valueOf(messageList.size());

        logger.info("Messages in Amazon Queue: " + messageListSize);

       return messageList == null || messageList.size() < 1;
    }

    @Override
    public Boolean pushToQueue(String message) throws AmazonServiceException, AmazonClientException {
        if(message == null) {
            logger.error("Cannot push to Amazon queue. The message is null.");
            return false;
        }

        if(!isClientCreated()) {
            logger.error("Cannot push to Amazon queue. The client is null.");
            return false;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot push to Amazon queue. The Amazon Queue URL is null.");
            return false;
        }

        this.amazonSQSClient.sendMessage(new SendMessageRequest(this.amazonQueueURL, message));
        logger.info("Pushed " + "\"" + message +"\"" + " to Amazon queue " + this.amazonQueueURL);

        return true;
    }

    @Override
    public Collection<Message> popFromQueue() throws AmazonServiceException, AmazonClientException {
        Collection<Message> retVal = null;

        if(!isClientCreated()) {
            logger.error("Cannot pull from Amazon queue. The client is null.");
            return retVal;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot pull from Amazon queue. The Amazon Queue URL is null.");
            return retVal;
        }

        ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(this.amazonQueueURL);
        messageRequest.setMaxNumberOfMessages(this.maxPop);

        retVal =  this.amazonSQSClient.receiveMessage(messageRequest).getMessages();
        String messagesPulled = "0";

        if(retVal != null && retVal.size() > 0){
            messagesPulled = String.valueOf(retVal.size());
        }


        logger.info("Pulled from Amazon Queue: " + messagesPulled);

        return retVal;
    }

    @Override
    public Boolean deleteMessageFromQueue(String receiptHandle) throws AmazonServiceException, AmazonClientException {
        if(!isClientCreated()) {
            logger.error("Cannot delete " + receiptHandle + " from Amazon queue. The client is null.");
            return false;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot delete " + receiptHandle + " from Amazon queue. The Amazon Queue URL is null.");
            return false;
        }

        this.amazonSQSClient.deleteMessage(new DeleteMessageRequest(this.amazonQueueURL, receiptHandle));
        logger.info("Deleted " + receiptHandle + " from Amazon queue " + this.amazonQueueURL);

        return true;
    }

    @Override
    public Boolean clearQueue() {
        if(!isClientCreated()) {
            logger.error("Cannot clear Amazon queue. The client is null.");
            return false;
        }

        if(this.amazonQueueURL == null || this.amazonQueueURL.length() < 1) {
            logger.error("Cannot clear Amazon queue. The Amazon Queue URL is null.");
            return false;
        }

        if(!this.deleteQueue())
               return false;

        // After deleting must wait for a Amazon specified time.
        AmazonAwsUtil.amazonWaitRule();

        return this.createQueue() != null;
    }



}
