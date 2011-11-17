package com.inthinc.pro.automation.deviceTrips;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.AutomationCassandra;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.objects.TiwiProDevice;
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
    public void readDrivers(List<Integer> nodes){
        ObjectReadWrite reader = new ObjectReadWrite();
        drivers = (Map<Integer, Map<String, String>>) reader.readObject(address).get(0);
        MCMProxyObject.processDrivers(drivers, AutomationCassandra.Default.createNoteService(nodes));
    }
    
    public void driveTiwis(Integer totalTime){
        Iterator<Integer> itr = drivers.keySet().iterator();

        AutomationCalendar initialTime = new AutomationCalendar();
        AutomationCalendar secondTrip = new AutomationCalendar();
        secondTrip.addToDay(1);
        
        MasterTest.print(portal);

        long start = System.currentTimeMillis();
        long runningTime = 0;
        List<TripDriver> trips = new ArrayList<TripDriver>();
        while (runningTime < totalTime){
            if (!itr.hasNext()){
                itr = drivers.keySet().iterator();
            }
            Integer next = itr.next();
            TiwiProDevice tiwi = new TiwiProDevice(drivers.get(next).get("device"), portal);
            tiwi.set_time(initialTime);
            TripDriver trip = new TripDriver(tiwi);
            trip.testTrip();
            
            trips.add(trip);
            trip.start();
            runningTime = (System.currentTimeMillis() - start) / 1000;
            while (Thread.activeCount() > 3000){
                AutomationThread.pause(1);
            }
        }

        for (TripDriver trip: trips){
            if (trip.isAlive()){
                trip.interrupt();
            }
        }
        try {
            MCMProxyObject.closeService();
        } catch (Exception e) {}
        
        MasterTest.print("Starting time is " + DeviceStatistics.getStart().epochTime());
        MasterTest.print("Ending time is " + DeviceStatistics.getStop().epochTime());
        MasterTest.print("We made " + DeviceStatistics.getHessianCalls());
        MasterTest.print("We took :" + DeviceStatistics.getTimeDeltaL() + " milliSeconds to run");
        MasterTest.print("This is an average of " + DeviceStatistics.getCallsPerMinute() + " calls per minute");
    }
        
    public static void main(String[] args){
        List<Integer> nodes = new ArrayList<Integer>();
        String last = "";
        Integer minutes = 0;
        Integer seconds = 10;
        try {
            boolean readNodes = true;
            for (String string: args){
                if (string.equals("time")){
                    readNodes = false;
                    continue;
                }
                if (readNodes){
                    last = string;
                    Integer next = Integer.parseInt(string);
                    nodes.add(next);
                } else {
                    String[] split = string.split(":");
                    minutes = Integer.parseInt(split[0]);
                    seconds = Integer.parseInt(split[1]);
                }
            }
            
        } catch (Exception e) {
            MasterTest.print(e);
            MasterTest.print(last + " is not a valid Integer parameter, moving on with " + nodes + " nodes, or default");
        }
        if (nodes.isEmpty()){
            MasterTest.print("Using default nodes");
        } else {
            MasterTest.print("Using nodes: " + nodes);
        }
        MasterTest.print("We will run for: " + minutes + " minutes and " + seconds + " seconds");
        Integer totalTime = minutes * 60 + seconds;
        VariableTripCreator test = new VariableTripCreator(Addresses.DEV);
        MCMProxyObject.regularNote=false;
        test.readDrivers(nodes);
        test.driveTiwis(totalTime);
    }
}
