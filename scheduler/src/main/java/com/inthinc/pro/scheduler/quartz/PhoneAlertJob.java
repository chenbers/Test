package com.inthinc.pro.scheduler.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.dao.util.PhoneNumberUtil;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class PhoneAlertJob extends BaseAlertJob
{
    private static Logger logger = Logger.getLogger(PhoneAlertJob.class);
    
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.error("BaseAlertJob: THROWAWAY BUILD DOES NOT SEND ALERTS");
    }
    private void unformatPhoneNumbers(List<AlertMessageBuilder> messageList){
        for (AlertMessageBuilder message : messageList){
            message.setAddress(PhoneNumberUtil.formatPhone(message.getAddress(),"{0}{1}{2}"));
        }        
    }
    private void dispatchList(List<AlertMessageBuilder> userList){
        
//        for (AlertMessageBuilder message : userList)
//        {
//            String text = LocalizedMessage.getStringWithValues(message.getAlertMessageType().toString(),message.getLocale(),(String[])message.getParamterList().toArray(new String[message.getParamterList().size()]));
//            logger.debug("PHONE Message: " + message.getAddress() + " " + text);
//            
//            getPhoneDispatcher().send(message.getAddress(),text, message.getMessageID(), message.getAcknowledge());
//        }
        getPhoneDispatcher().sendList(userList);
    }
}
