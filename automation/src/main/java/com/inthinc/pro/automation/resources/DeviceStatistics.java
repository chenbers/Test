package com.inthinc.pro.automation.resources;

import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.StackToString;

public class DeviceStatistics {
    
    private static int hessianCalls = 0;
    private static int connectionTimeouts = 0;
    private static int connectionResets = 0;
    private static AutomationCalendar start = new AutomationCalendar();
    private static AutomationCalendar stop = new AutomationCalendar();
    
    public static boolean addTimeout(){
        connectionTimeouts++;
        return true;
    }
    
    public static boolean addReset(){
        connectionResets++;
        return true;
    }
    
    public static boolean addConnectionError(Throwable e){
        String message = StackToString.toString(e);
        if (message.contains("Connection timed out")){
            addTimeout();
        } else if (message.contains("reset")){
            addReset();
        }
        return true;
    }
    
    
    public static boolean addCall(){
        hessianCalls++;
        updateStop();
        return true;
    }
    
    public static boolean updateStop(){
        stop = new AutomationCalendar();
        return true;
    }
    
    public static Integer getHessianCalls(){
        return hessianCalls;
    }
    
    public static Long getTimeDelta(){
        return stop.getDelta(start)/1000;
    }
    
    public static Long getTimeDeltaL(){
        return stop.getDelta(start);
    }
    
    public static AutomationCalendar getStart(){
        return start.copy();
    }
    
    public static AutomationCalendar getStop(){
        return stop.copy();
    }
    
    public static int getCallsPerMinute(){
        double seconds = getTimeDelta();
        double callsPerSecond = hessianCalls/seconds;
        return ((Double)(callsPerSecond * 60)).intValue();
    }

}
