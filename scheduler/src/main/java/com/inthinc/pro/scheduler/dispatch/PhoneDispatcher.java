package com.inthinc.pro.scheduler.dispatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class PhoneDispatcher
{
    private String asteriskScript;
    
    private static Logger logger = Logger.getLogger(PhoneDispatcher.class);

    public boolean send(String phoneNumber, String messageText, Integer msgID, Boolean acknowledge)
    {

        HttpClient httpClient = new HttpClient();
        
        //TODO dispatch to specfic data center based on country code??
        //London Datacenters:
        //http://api.lon.voxeo.net/SessionControl/VoiceXML.start 
        //EU Datacenters:
        //http://session.lon.voxeo.net/SessionControl/4.5.40/VoiceXML.start 

        GetMethod httpMethod = new GetMethod("http://api.voxeo.net/VoiceXML.start");
        NameValuePair[] params=new NameValuePair[7];
        
        params[0] = new NameValuePair("tokenid","724abefcafa4be428524109528252d18defe555564874cd59aed73e84cf16fc6817d99ce6fde0f5aef118e94");
        params[1] = new NameValuePair("callerid","8018662255");
        params[2] = new NameValuePair("numbertodial",phoneNumber);
        params[3] = new NameValuePair("msgID",msgID.toString());
        params[4] = new NameValuePair("msg",messageText);
        params[5] = new NameValuePair("ack",acknowledge?"1":"0");
        params[6] = new NameValuePair("calltimeout","35"); //in seconds, time waits for answer (and blocks!!)
        
        httpMethod.setQueryString(params);
        //TODO HTTPS!!!!

        try 
        {
            boolean callOK=true;
            int httpCode = httpClient.executeMethod(httpMethod);
            if (httpCode!=200)
            {
                callOK = false;
                logger.error("PhoneMessageJob Http Error " + httpCode);
            }
            else
            {
                //warning, this returns "success" even if the service fails to return vxml!
                String body=httpMethod.getResponseBodyAsString();
                logger.debug(body);
                if (!body.startsWith("success"))
                    callOK=false;
            }
            return callOK;
        }
        catch (Throwable e) 
        {
            logger.error("PhoneMessageJob Error " + e);
            return false;
        }
    }
    
    protected void runProcess(String cmdLine[])
    {
        try 
        {
            String str;

            Process proc = Runtime.getRuntime().exec(cmdLine);

            // get its output (your input) stream
            // TODO: is this needed???
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            try 
            {
                while ((str = reader.readLine()) != null) 
                {
                    logger.debug(str);
                }
            } 
            catch (IOException e) 
            {
                logger.debug(e);
            }
        }
        catch (Exception ex)
        {
            logger.debug(ex);
        }
    }
    
    
    public String getAsteriskScript()
    {
        return asteriskScript;
    }

    public void setAsteriskScript(String asteriskScript)
    {
        this.asteriskScript = asteriskScript;
    }


}
