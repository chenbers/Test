package com.inthinc.pro.automation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceTrips.TripDriver;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.InstallEvent;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.MCMProxyObject;
import com.inthinc.pro.automation.objects.NoteBC;
import com.inthinc.pro.automation.objects.WaysmartDevice.Direction;
import com.inthinc.pro.automation.utils.AutomationSiloService;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartAggregationTest {
//    private static final int acctID = 398;
//    private static final int groupID = 5260;
//    private static final int unknownDriverID = 66462;
//    private static final Addresses server = Addresses.QA;
    /* AccountID    =   398
     * Account Name = WSAgg
     * GroupID      =  5259
     * PersonID     = 68781
     * UserID       = 43469
     * userName     = dtanner
     * password     = password
     */
    

    private static final int acctID = 1;
    private static final int groupID = 2;
    private static final int unknownDriverID = 1;
    private final static Addresses server = Addresses.DEV;
    
    private final static State usState = new State(47, "Utah", "UT");
    private GeoPoint installLocation;
    
    private Map<DeviceState, LinkedList<DeviceNote>> tripsMap;
    private Map<DeviceState, Vehicle> vehicleMap;
    private final static int startingOdometer = 96020;
    private final static int startingTime = 1325644704;
    
    private final static ProductType type = ProductType.WAYSMART;
    
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<DeviceState, LinkedList<DeviceNote>>();
        vehicleMap = new HashMap<DeviceState, Vehicle>();
        notesOutOfOrder();
        sendNotes();
    }
    
    private void missingNotes(){
    	TripDriver driver = new TripDriver(type);
    	String start = "";
    	String stop = "";
    	
    	driver.addToTrip(start, stop);
        driver.addToTrip(stop, start);
        
        DeviceState state = driver.getdeviceState();
        
        state.setDriverID(unknownDriverID);
        state.setOdometer(startingOdometer);
        state.getTime().setDate(startingTime);
        
        driver.addEvent(25, AutomationDeviceEvents.ignitionOff(50, 95));
    }
    
    
    private void notesOutOfOrder(){
        int i=0;
        
        TripDriver driver = new TripDriver(type);
        
        String start = "4225 W Lake Blvd, West Valley City, UT 84120";
        String stop = "5000 W Lake Blvd, West Valley City, UT 84120";
        driver.addToTrip(start, stop);
        driver.addToTrip(stop, start);
        
        
        DeviceState state = driver.getdeviceState();
        
        state.setDriverID(unknownDriverID);
        state.setOdometer(startingOdometer);
        state.getTime().setDate(startingTime);
        
        driver.addEvent(10, AutomationDeviceEvents.speeding(75, 100, 600, 60, 65, 500));
        driver.addEvent(20, AutomationDeviceEvents.speeding(80, 200, 700, 40, 75, 600));
        driver.addEvent(30, AutomationDeviceEvents.hardAccel(100, 20, 10));
        driver.addEvent(40, AutomationDeviceEvents.hardBrake(100, 20, 10));
        driver.addEvent(50, AutomationDeviceEvents.seatbelt(700, 90, 25, 20, 100, 600));
        driver.addEvent(60, AutomationDeviceEvents.hardDip(10, 20, 110));
        
        LinkedList<DeviceNote> inOrder = driver.generateNotes();
        
        installLocation = inOrder.get(0).getLocation();
        
        MasterTest.print("Final mileage is: %d", Level.INFO, (Object)((NoteBC)inOrder.getLast()).nOdometer);
        state.getTime().addToMinutes(15);
        MasterTest.print("Final noteTime is: %d", Level.INFO, state.getTime().epochSeconds());
        MasterTest.print("Final noteTime is: %s", Level.INFO, state.getTime());
        

        LinkedList<DeviceNote> randomized = new LinkedList<DeviceNote>();
        for (DeviceNote note : inOrder){
            randomized.add(note.copy());
        }
        
        AutomationSiloService portalProxy = new AutomationSiloService(server);
        for (int j=0;j<20;j++){
        	Collections.shuffle(randomized);
        }
        
        state = newState(++i);
        tripsMap.put(state, inOrder);
        createVehicle(i, portalProxy, state);

        state = newState(++i);
        tripsMap.put(state, randomized);
        createVehicle(i, portalProxy, state);
        

        if (true){
        	throw new NullPointerException();
        }
    }


    private void createVehicle(int i, AutomationSiloService portalProxy,
            DeviceState state) {
        Vehicle vehicle;
        String vin = String.format("VEHICLEFORW%05d", i);
        vehicle = new Vehicle(null, groupID, Status.ACTIVE, vin, "Fake",
                "Model", 2011, "White", VehicleType.HEAVY, vin, 9000, "ll33l", usState);
        try {
            vehicle = portalProxy.createVehicle(vehicle.getGroupID(), vehicle);
            if (vehicle.getVIN() == null){
            	vehicle = portalProxy.getVehicle(vin);
            }
        } catch (RemoteServerException e){
            vehicle = portalProxy.getVehicle(vehicle.getVIN());
        }
        vehicleMap.put(state, vehicle);
    }
    
    private DeviceState newState(int number){
        String last = String.format("%05d", number);
        DeviceState state = new DeviceState("30023FKEWS"+last, type);
        state.setMcmID("FKE" + last);
        state.setWaysDirection(Direction.wifi);
        state.setDriverID(unknownDriverID);
        MasterTest.print("Imei:%s, MCMID:%s, DriverID:%d", state.getImei(), state.getMcmID(), state.getDriverID());
        return state;
    }
    
    private void sendNotes(){
        Iterator<DeviceState> itr = tripsMap.keySet().iterator();
        MCMProxyObject proxy = new MCMProxyObject(server);
        while (itr.hasNext()){
            DeviceState next = itr.next();
            installEvent(next, proxy);
            
            NoteManager manager = new NoteManager();
            int noteSize = 4;
            LinkedList<DeviceNote> temp = tripsMap.get(next);

            while (!temp.isEmpty()){
            	DeviceNote note = temp.poll();
                manager.addNote(note);
            }
            while (manager.hasNext()){
            	Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> list = manager.getNotes(noteSize);
            	while (!list.isEmpty()){
            		proxy.sendNotes(next, list);
            	}
        	}
        }
    }
    
    
    private void installEvent(DeviceState state, MCMProxyObject proxy) {
        Vehicle vehicle = vehicleMap.get(state);
        if (vehicle.getDeviceID() != null){
            MasterTest.print("Vehicle: %d, Device: %d", vehicle.getVehicleID(), vehicle.getDeviceID());
            return;
        }
        MasterTest.print(vehicle, Level.DEBUG);
        InstallEvent event = AutomationDeviceEvents.install(vehicle.getName(), state.getMcmID(), acctID);
        proxy.sendNotes(state, event.getNote(installLocation, state));
    }


    public static void main(String[] args){
        WaysmartAggregationTest test = new WaysmartAggregationTest();
        MasterTest.print(test);
    }
}
