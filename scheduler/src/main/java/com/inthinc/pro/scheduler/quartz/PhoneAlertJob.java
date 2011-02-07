package com.inthinc.pro.scheduler.quartz;

import java.util.ArrayList;
import java.util.Collections;
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
//        List<AlertMessageBuilder> messageList = getMessageBuilders(AlertMessageDeliveryType.PHONE);
//        //send all messages for one person the same thread so they don't get calls piling up
//        //First sort by address
//        Collections.sort(messageList);
//        
//        List<AlertMessageBuilder> userList =  new ArrayList<AlertMessageBuilder>();
//        String currentAddress = null;
//        for (AlertMessageBuilder message : messageList)
//        {
//            if (message == null) continue;
//            
//            if (message.getAddress().equalsIgnoreCase(currentAddress)){
//                userList.add(message);
//            }
//            else {
//                //dispatch this list
//                dispatchList(userList);
//                //set to get next batch
//                userList = new ArrayList<AlertMessageBuilder>();
//                currentAddress = message.getAddress();
//                userList.add(message);
//            }
//            
//        }
        logger.debug("PhoneAlertJob: END");
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
