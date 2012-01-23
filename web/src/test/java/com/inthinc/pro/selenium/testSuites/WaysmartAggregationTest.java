package com.inthinc.pro.selenium.testSuites;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.notes.NoteBC;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.NoteManager;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.AutomationDeviceEvents.InstallEvent;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.emulation.hessian.tcp.HessianException;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.selenium.util.AutomationSiloService;

public class WaysmartAggregationTest {
    private static final int acctID = 398;
    private static final int groupID = 5260;
    private static final int unknownDriverID = 66462;
    private static final Addresses server = Addresses.QA;
    /* AccountID    =   398
     * Account Name = WSAgg
     * GroupID      =  5259
     * PersonID     = 68781
     * UserID       = 43469
     * userName     = dtanner
     * password     = password
     */
    

//    private static final int acctID = 1;
//    private static final int groupID = 2;
//    private static final int unknownDriverID = 1;
//    private final static Addresses server = Addresses.DEV;
    
    private final static State usState = new State(47, "Utah", "UT");
    private GeoPoint installLocation;
    
    private Map<DeviceState, LinkedList<DeviceNote>> tripsMap;
    private Map<DeviceState, Vehicle> vehicleMap;
    private final static int startingTime = 1327017279;
    
    private final static ProductType type = ProductType.WAYSMART;
    
    private int deviceNumber = 0;
	private AutomationSiloService portalProxy;
	

	private final static String start = "4225 W Lake Park Blvd, West Valley City, UT";
	private final static String stop = "350 N 700 W, Springville, UT";
    
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<DeviceState, LinkedList<DeviceNote>>();
        vehicleMap = new HashMap<DeviceState, Vehicle>();
        portalProxy = new AutomationSiloService(server);
        notesOutOfOrder();
        missingNotes();
        backwardOdometer();
        noIgnitionOn();
        longTrips();
        
        
        

        sendNotes();
    }
    
    
    private void missingNotes(){
    	DeviceState state;
        state = newState(++deviceNumber);
        createVehicle(deviceNumber, portalProxy, state);
    	TripDriver driver1 = new TripDriver(state);


        state = newState(++deviceNumber);
        createVehicle(deviceNumber, portalProxy, state);
    	TripDriver driver2 = new TripDriver(state);
    	
    	String start = "4225 W Lake Blvd, West Valley City, UT 84120";
    	String stop = "5000 W Lake Blvd, West Valley City, UT 84120";
    	
    	driver1.addToTrip(start, stop);
    	driver2.addToTrip(start, stop);
    	
        driver1.addToTrip(stop, start);
        driver2.addToTrip(stop, start);
        
        state = driver1.getdeviceState();
        
        state.setDriverID(unknownDriverID);
        state.setOdometerX100(500);
        state.getTime().setDate(startingTime);
        
        driver1.addEvent(25, AutomationDeviceEvents.ignitionOff(state, null));
        driver1.addEvent(25, AutomationDeviceEvents.ignitionOn(state, null));

        state.setTopSpeed(60).setSpeed(55);
        driver1.addEvent(30, AutomationDeviceEvents.hardBump(state, null, 120));
        
        
        driver2.addEvent(25, AutomationDeviceEvents.ignitionOff(state, null));
        
        LinkedList<DeviceNote> notes1 = driver1.generateNotes();
        LinkedList<DeviceNote> notes2 = driver2.generateNotes();
        
        double percent = notes2.size() / 100.0;
        Double delFrom = percent * 36;
        Double delTo = percent * 50;
        for (int i = delTo.intValue();i>delFrom;i--){
        	notes2.remove(i);
        }
                

        tripsMap.put(driver1.getdeviceState(), notes1);
        tripsMap.put(driver2.getdeviceState(), notes2);
    }
    
    
    private void backwardOdometer(){
    	
    	String start = "4225 W Lake Park Blvd, West Valley City, UT";
    	String stop = "350 N 700 W, Springville, UT";
    	LinkedList<DeviceNote> list = new LinkedList<DeviceNote>();
    	DeviceState state = newState(++deviceNumber);

		tripsMap.put(state, list);
		createVehicle(deviceNumber, portalProxy, state);
    	
    	state.setOdometerX100(10000);
    	TripTracker trips = new TripTracker(state);
    	trips.getTrip(start, stop);
    	trips.getTrip(stop, start);
    	
    	int lastOdo = 0;
    	list.add(AutomationDeviceEvents.powerOn(state, trips.currentLocation()).getNote());
    	state.incrementTime(90);
    	list.add(AutomationDeviceEvents.ignitionOn(state, trips.currentLocation()).getNote());
    	
    	Iterator<GeoPoint> itr = trips.iterator();
    	
    	while (itr.hasNext()){
	    	lastOdo = state.getOdometerX100();
	    	trips.getNextLocation(65, false);
	    	DeviceNote note = AutomationDeviceEvents.location(state, trips.currentLocation()).getNote();
	    	reverseOdo(state, lastOdo, note); 
	    	list.add(note);
    	}
    	
    	state.incrementTime(60);
    	list.add(AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()).getNote());
    	state.incrementTime(900);
    	list.add(AutomationDeviceEvents.powerOff(state, trips.currentLocation()).getNote());
    }
    
    private void reverseOdo(DeviceState state, int lastOdo, DeviceNote note){
    	int current = state.getOdometerX100();
    	int diff = current - lastOdo;
    	state.setOdometerX100(lastOdo - diff);
    	note.setOdometer(state.getOdometerX100());
    }
    
    private void timeGaps(){
    	// TODO: dtanner
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	
    	TripTracker tracker = new TripTracker(state);
    	tracker.addLocation(new GeoPoint("4225 W Lake Park Blvd, West Valley City, UT"));
    	LinkedList<DeviceNote> list = new LinkedList<DeviceNote>();
    	list.add(AutomationDeviceEvents.powerOn(state, tracker.currentLocation()).getNote());
    	state.incrementTime(100);
    	list.add(AutomationDeviceEvents.ignitionOn(state, tracker.currentLocation()).getNote());
    	
    	state.getTime().addToDay(4);
    	list.add(AutomationDeviceEvents.ignitionOff(state, tracker.currentLocation()).getNote());
    	state.incrementTime(900);
    	list.add(AutomationDeviceEvents.powerOff(state, tracker.currentLocation()).getNote());
    	

    }
    
    
    private void noIgnitionOn(){
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	TripTracker trips = new TripTracker(state);
    	TripDriver driver = new TripDriver(trips);
    	driver.isLocationOn(false);
    	
    	trips.getTrip(start, stop);
    	trips.getTrip(stop, start);
    	
    	driver.addEvent(1, AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()));
    	state.setSpeedingBuffer(5);
    	state.setSpeedingDistanceX100(200);
    	state.setSpeedingSpeedLimit(55);
    	
    	driver.addEvent(10, AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()));
    	state.setSpeed(88).setSpeedingDistanceX100(500).setSpeedingSpeedLimit(50);
    	driver.addEvent(5, AutomationDeviceEvents.speeding(state, trips.currentLocation()));
    	
    	driver.addEvent(10, AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()));
    	state.setSeatbeltViolationDistanceX100(500);
    	driver.addEvent(16, AutomationDeviceEvents.seatbelt(state, trips.currentLocation()));
    	
    	driver.addEvent(20, AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()));
    	driver.addEvent(28, AutomationDeviceEvents.hardBrake(state, trips.currentLocation(), 150));
    	
    	driver.addEvent(50, AutomationDeviceEvents.ignitionOff(state, trips.currentLocation()));
    	
    	driver.addEvent(62, AutomationDeviceEvents.location(state, trips.currentLocation()));

    	driver.addEvent(80, AutomationDeviceEvents.location(state, trips.currentLocation()));
    	driver.addEvent(92, AutomationDeviceEvents.location(state, trips.currentLocation()));
    	LinkedList<DeviceNote> list = driver.generateNotes();
    	
    	tripsMap.put(state, list);
    	
    }
    
    private void lateNotes(){
    	// TODO: dtanner
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    }
    
    private void longTrips(){
    	// TODO: dtanner
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	WaysmartDevice ways = new WaysmartDevice(state, server);
    	
    	String start = "4225 W Lake Park Blvd, West Valley City, UT";
    	String first = "1600 pennsylvania ave, washington dc";
    	String second = "Disney World, Bay Lake, FL";
    	String third = "6458 E Golflinks Rd, Tucson, AZ 85730";
    	String fourth = "5130 Chromite St, El Paso, TX 79932";
    	TripDriver driver = new TripDriver(ways);
    	driver.addToTrip(start, first);
    	driver.addToTrip(first, second);
    	driver.addToTrip(second, third);
    	driver.addToTrip(third, fourth);
    	driver.addToTrip(fourth, start);
    	AutomationDeviceEvents.install(ways);
    	driver.run();
    	
    	
    }
    
    
    private void sameTime(){
    	// TODO: dtanner
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	
    	
    }
    
    private void notesOutOfOrder(){
        
        TripDriver driver = new TripDriver(type);
        
        String start = "4225 W Lake Blvd, West Valley City, UT 84120";
        String stop = "5000 W Lake Blvd, West Valley City, UT 84120";
        driver.addToTrip(start, stop);
        driver.addToTrip(stop, start);
        
        
        DeviceState state = driver.getdeviceState();
        
        state.setDriverID(unknownDriverID);
        state.setOdometerX100(500);
        state.getTime().setDate(startingTime);
        
        state.setTopSpeed(75).setSpeedingDistanceX100(100).setSpeedingSpeedLimit(60).setAvgSpeed(65);
        driver.addEvent(10, AutomationDeviceEvents.speeding(state, null));
        state.setTopSpeed(80).setSpeedingDistanceX100(200).setSpeedingSpeedLimit(40).setAvgSpeed(75);
        driver.addEvent(20, AutomationDeviceEvents.speeding(state, null));
        driver.addEvent(30, AutomationDeviceEvents.hardAccel(state, null, 100));
        driver.addEvent(40, AutomationDeviceEvents.hardBrake(state, null, 100));
        state.setTopSpeed(25).setAvgSpeed(20).setSeatbeltViolationDistanceX100(100);
        driver.addEvent(50, AutomationDeviceEvents.seatbelt(state, null));
        driver.addEvent(60, AutomationDeviceEvents.hardDip(state, null, 110));
        LinkedList<DeviceNote> inOrder = driver.generateNotes();
        
        installLocation = inOrder.get(0).getLocation();
        
        MasterTest.print("Final mileage is: %d", Level.INFO, (Object)((NoteBC)inOrder.getLast()).getOdometer());
        state.getTime().addToMinutes(15);
        MasterTest.print("Final noteTime is: %d", Level.INFO, state.getTime().epochSeconds());
        MasterTest.print("Final noteTime is: %s", Level.INFO, state.getTime());
        

        LinkedList<DeviceNote> randomized = new LinkedList<DeviceNote>();
        for (DeviceNote note : inOrder){
            randomized.add(note.copy());
        }
        
        for (int j=0;j<20;j++){
        	Collections.shuffle(randomized);
        }
        
        state = newState(++deviceNumber);
        tripsMap.put(state, inOrder);
        createVehicle(deviceNumber, portalProxy, state);

        state = newState(++deviceNumber);
        tripsMap.put(state, randomized);
        createVehicle(deviceNumber, portalProxy, state);
        

    }


    private void createVehicle(int i, AutomationSiloService portalProxy,
            DeviceState state) {
        Vehicle vehicle;
        String last = String.format("%05d", i);
        String vin = "VEHICLEFORW" + last;
        vehicle = new Vehicle(null, groupID, Status.ACTIVE, vin, "Fake",
                "Model", 2011, "White", VehicleType.HEAVY, vin, 9000, "ll33l", usState);
        state.setVehicleID(vehicle.getVIN());
        try {
            vehicle = portalProxy.createVehicle(vehicle.getGroupID(), vehicle);
            if (vehicle.getVIN() == null){
            	vehicle = portalProxy.getVehicle(vin);
            }
        } catch (HessianException e){
            vehicle = portalProxy.getVehicle(vehicle.getVIN());
        }
        vehicleMap.put(state, vehicle);
    }
    
    private DeviceState newState(int number){
        String last = String.format("%05d", number);
        DeviceState state = new DeviceState("30023FKEWS"+last, type);
        state.setAccountID(acctID);
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
        InstallEvent event = AutomationDeviceEvents.install(state, installLocation);
        proxy.sendNotes(state, event.getNote());
    }


    public static void main(String[] args){
//        WaysmartAggregationTest test = new WaysmartAggregationTest();
//        MasterTest.print(test);
    	
    	DeviceState state = new DeviceState("888", ProductType.WAYSMART);
    	NoteBC note = new NoteBC(DeviceNoteTypes.LOCATION, state, new GeoPoint("4225 W Lake Park Blvd, West Valley City, UT"));
    	note.addAttr(EventAttr.SPEED_LIMIT, 75);
    	NoteBC test = note.unPackage(note.Package());
    	MasterTest.print(note);
    	MasterTest.print(test);
    	
    }
}
