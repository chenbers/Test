package com.inthinc.pro.automation.deviceTrips;

import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.MasterTest;

public class VariableTripCreator {

    private final Addresses portal;
    
    private final String address;
    
    private Map<Integer, Map<String, String>> drivers;
    
    public VariableTripCreator(Addresses address){
        portal = address;
        this.address = "records_created_on_" + address.name();
    }
    
    @SuppressWarnings("unchecked")
    public void readDrivers(){
        ObjectReadWrite reader = new ObjectReadWrite();
        drivers = (Map<Integer, Map<String, String>>) reader.readObject(address).get(0);
        MCMProxyObject.processDrivers(drivers);
    }
    
    public void driveTiwis(){
        Iterator<Integer> itr = drivers.keySet().iterator();

        AutomationCalendar initialTime = new AutomationCalendar();
        AutomationCalendar secondTrip = new AutomationCalendar();
        secondTrip.addToDay(1);
        
        MasterTest.print(portal);

        int threads = Thread.activeCount();
        long start = System.currentTimeMillis();
        while (itr.hasNext()){
            Integer next = itr.next();
            new HanSoloTrip().start(drivers.get(next).get("device"), portal, initialTime);
            new HanSoloTrip().start(drivers.get(next).get("device"), portal, secondTrip);
            if (Thread.activeCount() > 3000){
                AutomationThread.pause(500l);
            }
        }

        MasterTest.print("All Trips have been started, took " + (System.currentTimeMillis()-start) + " milliseconds to start it");
        
        while (Thread.activeCount() > threads){
            AutomationThread.pause(1);
        }

        MasterTest.print("Starting time is " + DeviceStatistics.getStart().epochTime());
        MasterTest.print("Ending time is " + DeviceStatistics.getStop().epochTime());
        MasterTest.print("We made " + DeviceStatistics.getHessianCalls());
        MasterTest.print("We took :" + DeviceStatistics.getTimeDeltaL() + " milliSeconds to run");
        MasterTest.print("This is an average of " + DeviceStatistics.getCallsPerMinute() + " calls per minute");
    }
        
    public static void main(String[] args){
        VariableTripCreator test = new VariableTripCreator(Addresses.DEV);
        MCMProxyObject.regularNote=false;
        test.readDrivers();
        test.driveTiwis();
    }
}
