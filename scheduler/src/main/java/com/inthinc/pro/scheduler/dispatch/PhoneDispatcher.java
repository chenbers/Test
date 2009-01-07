package com.inthinc.pro.scheduler.dispatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class PhoneDispatcher
{
    private String asteriskScript;
    
    private static Logger logger = Logger.getLogger(PhoneDispatcher.class);

    public void send(String phoneNumber, String messageText)
    {
        try 
        {
            String[] cmdLine = new String[3];
            cmdLine[0] = getAsteriskScript();
            cmdLine[1] = phoneNumber;
            cmdLine[2] = messageText;
            runProcess(cmdLine);
        }
        catch (Throwable e) 
        {
            logger.error("PhoneMessageJob Error " + e);
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
