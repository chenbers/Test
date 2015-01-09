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
        logger.info("EmailAlertJob: START");
        List<AlertMessageBuilder> messageList = getMessageBuilders(AlertMessageDeliveryType.EMAIL);
        
        for (AlertMessageBuilder message : messageList)
        {
            logger.info("MessageID: " + message.getMessageID() + " Emailed to: " + message.getAddress());
            String text = LocalizedMessage.getStringWithValues(message.getAlertMessageType().toString(),message.getLocale(),(String[])message.getParamterList().toArray(new String[message.getParamterList().size()]));
            
            if (message.isEzCrm()) {
                // Add EzCrm Data
                EzCrmMessageData ezCrm = new EzCrmMessageData(message);
                logger.info("EzCrm params: " + message.getEzParameterList());
                text += ezCrm.getMessage();
                text += LocalizedMessage.getString("EzCrm.Footer", message.getLocale());
            }
            
            if(getMailDispatcher().send(message.getAddress(), getSubject(message), text))
                getAlertMessageDAO().acknowledgeMessage(message.getMessageID());
        }
        logger.info("EmailAlertJob: END");

    }
}
