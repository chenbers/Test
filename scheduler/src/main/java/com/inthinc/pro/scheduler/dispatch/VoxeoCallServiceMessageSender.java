package com.inthinc.pro.scheduler.dispatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class VoxeoCallServiceMessageSender implements CallServiceMessageSender {
    
    private String callerID;
    private String phoneServerURL;
    private String tokenID;
    
    private static Logger logger = Logger.getLogger(VoxeoCallServiceMessageSender.class);

    @Override
    public void sendMessage(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge){
        //This starts the process with Voxeo - sends the phone number, token, and parameters for the call to Voxeo
        //voxeo sends response here - and calls our service with the parameters we send here to get the vxml for the call 
        HttpClient httpClient = new HttpClient();

        // TODO dispatch to specfic data center based on country code??
        // London Datacenters:
        // http://api.lon.voxeo.net/SessionControl/VoiceXML.start
        // EU Datacenters:
        // http://session.lon.voxeo.net/SessionControl/4.5.40/VoiceXML.start

        GetMethod httpMethod = new GetMethod(getPhoneServerURL());
        NameValuePair[] params = new NameValuePair[7];

        params[0] = new NameValuePair("tokenid", getTokenID());
        params[1] = new NameValuePair("callerid", getCallerID());
        params[2] = new NameValuePair("numbertodial", phoneNumber);
        params[3] = new NameValuePair("msgID", msgID.toString());
        params[4] = new NameValuePair("msg", cleanMessage(messageText));
        params[5] = new NameValuePair("ack", acknowledge ? "1" : "0");
        params[6] = new NameValuePair("calltimeout", "35"); // in seconds, time waits for answer (and blocks!!)

        httpMethod.setQueryString(params);
        // TODO HTTPS!!!!

        try {
            int httpCode = httpClient.executeMethod(httpMethod);
            if (httpCode != 200) {
                logger.error("PhoneMessageJob Http Error " + httpCode);
            } 
            else {
                // warning, this returns "success" even if the service fails to return vxml!
                String body = httpMethod.getResponseBodyAsString();
                if (!body.startsWith("success"))
                    logger.debug(body+" " + msgID);
            }
        } catch (Throwable e) {
            logger.error("PhoneMessageJob Error " + e);
        }
    }
    
    protected String cleanMessage(String messageText){
    	//Voxeo can't deal with quotes and apostrophes in a message, or with &, <, >
    	return replaceSpecialCharacters(messageText);
    }

    /**
     * Replaces special characters that voxeo can't deal with.
     *
     * @param message message
     * @return replaced message
     */
    private String replaceSpecialCharacters(String message) {
        return message.replaceAll("[\"']", "")
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
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

}
