package com.inthinc.pro.automation.resources;

import com.inthinc.pro.automation.utils.AutomationCalendar;

public class DeviceStatistics {
    
    private static int hessianCalls = 0;
    private static AutomationCalendar start = new AutomationCalendar();
    private static AutomationCalendar stop = new AutomationCalendar();
    
    
    
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
        int seconds = getTimeDelta().intValue();
        int minutes = seconds / 60;
        if (minutes == 0){
            return 0;
        } 
        return hessianCalls/minutes;
    }

}
