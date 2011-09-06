package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;


public class AutomationThread {

    public static void pause(Integer timeout_in_seconds, String reasonForPause){
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        pause(timeout_in_seconds, reasonForPause, element);
    }
    
    public static void pause(Integer timeout_in_seconds, String reasonForPause, StackTraceElement element){
        String print = String.format("%3d - Pausing for %d seconds. Reason for pause: %s\n", element.getLineNumber(), timeout_in_seconds, reasonForPause);
        Logger.getLogger(element.getClassName()).debug(print);
        pause(timeout_in_seconds);
    }
    
    public static void pause(Integer timeout_in_seconds) {
        pause((long) (timeout_in_seconds * 1000));
    }
    
    public static void pause(Long timeout_in_milliseconds) {
        try {
            Thread.sleep(timeout_in_milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
