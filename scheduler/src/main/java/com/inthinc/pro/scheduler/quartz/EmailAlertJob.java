package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public class EmailAlertJob extends BaseAlertJob
{
    private static final Logger logger = Logger.getLogger(EmailAlertJob.class);

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.debug("EmailAlertJob: START");
        List<AlertMessage> messageList = getMessages(AlertMessageDeliveryType.EMAIL);
        
        for (AlertMessage message : messageList)
        {
            logger.debug("MessageID: " + message.getMessageID() + " Emailed to: " + message.getAddress());
            getMailDispatcher().send(message.getAddress(), getSubject(message), message.getMessage());
            
        }
        logger.debug("EmailAlertJob: END");

    }

}
