package com.inthinc.pro.scheduler.dispatch;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;

public class MockCallServiceMessageSender implements CallServiceMessageSender {

    private AlertMessageDAO alertMessageDAO;
    private String callerID;
    private String phoneServerURL;
    private String tokenID;
    
    
    private static Logger logger = Logger.getLogger(MockCallServiceMessageSender.class);
    
    @Override
    public void sendMessage(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge){

        //This just logs that a call should have been made, sets it to acknowledged/sent - to avoid making actual calls
        //Configure this in the tiwipro.properties file, 
        //  choose MockCallServiceSender or for real calls choose VoxeoCallServiceMessageSender
        
        StringBuilder sb = new StringBuilder("moxeo: ");
        
        sb.append("tokenid="+tokenID+", ")
        .append("callerid="+callerID+", ")
        .append("numbertodial="+phoneNumber+", ")
        .append("msgID="+msgID+", ")
        .append("msg="+messageText+", ")
        .append("ack="+acknowledge+"");
        
         logger.info(sb);
        
        alertMessageDAO.acknowledgeMessage(msgID);

    }

    public String getCallerID() {
        return callerID;
    }

    public void setCallerID(String callerID) {
        this.callerID = callerID;
    }

    public String getPhoneServerURL() {
        return phoneServerURL;
    }

    public void setPhoneServerURL(String phoneServerURL) {
        this.phoneServerURL = phoneServerURL;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }
    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }

}
