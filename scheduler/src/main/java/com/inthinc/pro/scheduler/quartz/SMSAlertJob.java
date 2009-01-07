package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public class SMSAlertJob extends BaseAlertJob
{
    private static final Logger logger = Logger.getLogger(SMSAlertJob.class);

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.debug("SMSAlertJob: START");
        List<AlertMessage> messageList = getMessages(AlertMessageDeliveryType.TEXT_MESSAGE);
        
        for (AlertMessage message : messageList)
        {
            logger.debug("MessageID: " + message.getMessageID() + " Emailed to: " + message.getAddress());
            getMailDispatcher().send(message.getAddress(), getSubject(message), message.getMessage());
            
        }
        logger.debug("SMSAlertJob: END");

    }

}
