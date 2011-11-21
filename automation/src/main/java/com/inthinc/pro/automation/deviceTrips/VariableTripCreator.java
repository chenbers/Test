package com.inthinc.pro.automation.deviceTrips;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.inthinc.noteservice.NoteService;
import com.inthinc.pro.automation.CassandraPropertiesBean;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.AutomationCassandra;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.objects.TiwiProDevice;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.CassandraProperties;
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
    public void readDrivers(NoteService nodes){
        ObjectReadWrite reader = new ObjectReadWrite();
        drivers = (Map<Integer, Map<String, String>>) reader.readObject(address).get(0);
        MCMProxyObject.processDrivers(drivers, nodes);
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
            while (Thread.activeCount() > 1000 && runningTime < totalTime){
                AutomationThread.pause(1);
            }
            if (runningTime>totalTime){
                for (TripDriver killTrip: trips){
                    try {
                        if (killTrip.isAlive()){
                            killTrip.interrupt();
                            while (killTrip.isAlive()){
                                AutomationThread.pause(1);
                            }
                        } 
                    } catch (Exception e) {
//                            continue;
                    }
                }
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
        CassandraPropertiesBean cpb = CassandraProperties.getPropertyBean();
        MasterTest.print("We will run for: " + cpb.getMinutes() + " minutes and " + cpb.getSeconds() + " seconds");
        Integer totalTime = cpb.getMinutes() * 60 + cpb.getSeconds();
        VariableTripCreator test = new VariableTripCreator(Addresses.DEV);
        MCMProxyObject.regularNote=false;
        test.readDrivers(AutomationCassandra.createNode(cpb.getEc2ip()));
        test.driveTiwis(totalTime);
    }
}
