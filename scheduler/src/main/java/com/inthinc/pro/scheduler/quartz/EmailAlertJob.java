package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class EmailAlertJob extends BaseAlertJob
{
    private static final Logger logger = Logger.getLogger(EmailAlertJob.class);

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.debug("EmailAlertJob: START");
        List<AlertMessageBuilder> messageList = getMessageBuilders(AlertMessageDeliveryType.EMAIL);
        
        for (AlertMessageBuilder message : messageList)
        {
            logger.debug("MessageID: " + message.getMessageID() + " Emailed to: " + message.getAddress());
            String text = LocalizedMessage.getStringWithValues(message.getAlertMessageType().toString(),message.getLocale(),(String[])message.getParamterList().toArray(new String[message.getParamterList().size()]));
            if(getMailDispatcher().send(message.getAddress(), getSubject(message), text))
                getAlertMessageDAO().acknowledgeMessage(message.getMessageID());
        }
        logger.debug("EmailAlertJob: END");

    }
}
