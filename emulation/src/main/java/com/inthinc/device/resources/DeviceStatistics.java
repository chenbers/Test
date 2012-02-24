package com.inthinc.device.resources;

import android.util.Log;

import com.inthinc.pro.automation.objects.AutomationCalendar;

public class DeviceStatistics {
    
    private static int hessianCalls = 0;
    private static AutomationCalendar start = new AutomationCalendar();
    private static AutomationCalendar stop = new AutomationCalendar();
    private static AutomationCalendar last = new AutomationCalendar();
    
    
    public static boolean addCall(){
        hessianCalls++;
        updateStop();
        return true;
    }
    
    public static boolean updateStop(){
        stop.setDate(System.currentTimeMillis());
        if (stop.getDelta(last) > 15000){
        	Log.i("Sent: %d, Time: %d, NotesPerMinute: %d",
                    hessianCalls, getTimeDelta(), getCallsPerMinute());
            last.setDate(stop);
        }
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
