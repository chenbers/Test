package com.inthinc.device.emulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.inthinc.device.CassandraProperties;
import com.inthinc.device.CassandraPropertiesBean;
import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.resources.DeviceStatistics;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.automation.utils.AutomationThread;

public class VariableTripCreator {

    private final AutoSilos portal;
    
    private final String address;
    
    private Map<Integer, Map<String, String>> drivers;

    private boolean once = false;
    
    public VariableTripCreator(AutoSilos address){
        portal = address;
        this.address = "records_created_on_" + address.name();
    }
    
    @SuppressWarnings("unchecked")
    public void readDrivers(){
        ObjectReadWrite reader = new ObjectReadWrite();
        drivers = (Map<Integer, Map<String, String>>) reader.readObject(address).get(0);
        MCMProxyObject.processDrivers(drivers);
    }
    
    public void driveTiwis(Integer totalTime, Integer maxThreads){
        Iterator<Integer> itr = drivers.keySet().iterator();

        AutomationCalendar initialTime = new AutomationCalendar();
        AutomationCalendar secondTrip = new AutomationCalendar();
        secondTrip.addToDay(1);
        
        Log.debug("portal %s", portal);
        int threads = Thread.activeCount();

        long start = System.currentTimeMillis();
        long runningTime = 0;
        List<TripDriver> trips = new ArrayList<TripDriver>();
        boolean loop = true;
        if (totalTime == 0){
            loop = itr.hasNext();
        } else {
            loop = runningTime < totalTime;
        }
        while (loop){
            if (!itr.hasNext() && !once){
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
            while (Thread.activeCount() > maxThreads){
                AutomationThread.pause(10l);
            }
            if (totalTime == 0){
                loop = itr.hasNext();
            } else {
                loop = runningTime < totalTime;
            }
        }
        int count = Thread.activeCount();
        while (count > threads ){
            Log.info("Still running " + (count - threads) + " active threads");
            count = Thread.activeCount();
            AutomationThread.pause(1);
        }
        
        try {
            MCMProxyObject.closeService();
        } catch (Exception e) {}
        
        Log.info("Starting time is " + DeviceStatistics.getStart().epochTime());
        Log.info("Ending time is " + DeviceStatistics.getStop().epochTime());
        Log.info("We made " + DeviceStatistics.getHessianCalls());
        Log.info("We took :" + DeviceStatistics.getTimeDeltaL() + " milliSeconds to run");
        Log.info("This is an average of " + DeviceStatistics.getCallsPerMinute() + " calls per minute");
    }
        
    public static void main(String[] args){
        CassandraPropertiesBean cpb = CassandraProperties.getPropertyBean();
        
        Integer totalTime = cpb.getMinutes() * 60 + cpb.getSeconds();
        VariableTripCreator test = new VariableTripCreator(AutoSilos.QA);
        if (totalTime == 0){
            test.once  = true;
        }
        MCMProxyObject.regularNote=false;
        test.readDrivers();
        test.driveTiwis(totalTime, cpb.getThreads());
    }
}
