package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class PhoneAlertJob extends BaseAlertJob
{
    private static Logger logger = Logger.getLogger(PhoneAlertJob.class);
    
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.debug("PhoneAlertJob: START");
        List<AlertMessageBuilder> messageList = getMessageBuilders(AlertMessageDeliveryType.PHONE);
        
        for (AlertMessageBuilder message : messageList)
        {
            if (message == null) continue;
            
            String text = LocalizedMessage.getStringWithValues(message.getAlertMessageType().toString(),message.getLocale(),(String[])message.getParamterList().toArray(new String[message.getParamterList().size()]));
            logger.debug("PHONE Message: " + message.getAddress() + " " + text);
            
            getPhoneDispatcher().send(message.getAddress(),text, message.getMessageID(), message.getAcknowledge());
        }
        logger.debug("PhoneAlertJob: END");
    }
}
