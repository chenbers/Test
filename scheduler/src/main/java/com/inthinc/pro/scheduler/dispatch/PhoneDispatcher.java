package com.inthinc.pro.scheduler.dispatch;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.inthinc.pro.dao.AlertMessageDAO;

public class PhoneDispatcher {
    private String callerID;
    private String phoneServerURL;
    private String tokenID;
    private AlertMessageDAO alertMessageDAO;
    private Integer maxThreads;
    private ExecutorService executorService;

    private static Logger logger = Logger.getLogger(PhoneDispatcher.class);

    public void init() {
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void send(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge) {

        executorService.submit(new PhoneThread(phoneNumber, messageText, msgID, acknowledge));
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

    public void setTokenID(String tokenid) {
        this.tokenID = tokenid;
    }

    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }
    
    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public class PhoneThread implements Runnable {
        private String phoneNumber;
        private String messageText;
        private Integer msgID;
        private boolean acknowledge;
        
        public PhoneThread(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge)
        {
            this.phoneNumber = phoneNumber;
            this.messageText = messageText;
            this.msgID = msgID;
            this.acknowledge = acknowledge;
        }
        
        @Override
        public void run() {
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
            params[4] = new NameValuePair("msg", messageText);
            params[5] = new NameValuePair("ack", acknowledge ? "1" : "0");
            params[6] = new NameValuePair("calltimeout", "35"); // in seconds, time waits for answer (and blocks!!)

            httpMethod.setQueryString(params);
            // TODO HTTPS!!!!

            try {
                boolean callOK = true;
                int httpCode = httpClient.executeMethod(httpMethod);
                if (httpCode != 200) {
                    callOK = false;
                    logger.error("PhoneMessageJob Http Error " + httpCode);
                } else {
                    // warning, this returns "success" even if the service fails to return vxml!
                    String body = httpMethod.getResponseBodyAsString();
                    logger.debug(body);
                    if (!body.startsWith("success"))
                        callOK = false;
                }
                if (callOK && !acknowledge) // ack now if not an wait for user ack message
                    getAlertMessageDAO().acknowledgeMessage(msgID);
            } catch (Throwable e) {
                logger.error("PhoneMessageJob Error " + e);
            }
        }

    }
}
