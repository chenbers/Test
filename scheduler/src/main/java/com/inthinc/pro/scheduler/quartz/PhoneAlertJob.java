package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public class PhoneAlertJob extends BaseAlertJob
{
    private static Logger logger = Logger.getLogger(PhoneAlertJob.class);
    
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.debug("PhoneAlertJob: START");
        List<AlertMessage> messageList = getMessages(AlertMessageDeliveryType.PHONE);
        
        for (AlertMessage message : messageList)
        {
            logger.debug("PHONE Message: " + message.getAddress() + " " + message.getMessage());
            getPhoneDispatcher().send(message.getAddress(), message.getMessage());
        }
        logger.debug("PhoneAlertJob: END");
    }
}
