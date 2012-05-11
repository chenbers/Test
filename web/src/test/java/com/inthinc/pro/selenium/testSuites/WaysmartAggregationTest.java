package com.inthinc.pro.selenium.testSuites;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Ignore;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSState;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.NoteManager;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.AutomationDeviceEvents.InstallEvent;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.selenium.util.AutomationSiloService;

@Ignore
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
    

//    private static final int acctID = 1;
//    private static final int groupID = 2;
//    private static final int unknownDriverID = 1;
//    private final static Addresses server = Addresses.DEV;
    

    private static final int acctID = 184549378;
    private static final int groupID = 2;
    private static final int unknownDriverID = 184549378;
    private final static Addresses server = Addresses.WEATHERFORD;
    
    private final static State usState = new State(47, "Utah", "UT");
    private GeoPoint installLocation;
    
    private Map<DeviceState, LinkedList<DeviceNote>> tripsMap;
    private Map<DeviceState, Vehicle> vehicleMap;
    
    private final static ProductType type = ProductType.WAYSMART;
    
    private int deviceNumber = 0;
	private AutomationSiloService portalProxy;
	
	private int startTime = 1329244373;
	

	private final static String start = "4225 W Lake Park Blvd, West Valley City, UT";
	private final static String stop = "350 N 700 W, Springville, UT";
    
	private final boolean onQA;
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<DeviceState, LinkedList<DeviceNote>>();
        vehicleMap = new HashMap<DeviceState, Vehicle>();
        if (server.equals(Addresses.QA) || server.equals(Addresses.DEV)){
        	onQA = true;
        	portalProxy = new AutomationSiloService(server);
        } else {
        	onQA = false;
        }
        notesOutOfOrder();
//        missingNotes();
//        backwardOdometer();
//        noIgnitionOn();
//        longTrips();
//        timeGaps();
        
        

        sendNotes();
    }
    
    
    private void missingNotes(){
    	DeviceState state;
    	deviceNumber = 2;
    	int startTime = 1329264657;
    	int odo = 37672;
    	
    	// Vehicle ID = 52727, 00003
        state = newState(++deviceNumber);
        createVehicle(deviceNumber, portalProxy, state);
    	TripDriver driver1 = new TripDriver(state);
//    	state.getTime().setDate(startTime);
    	state.setOdometerX100(odo);

    	
    	// Vehicle ID = 52728, 00004
        state = newState(++deviceNumber);
        createVehicle(deviceNumber, portalProxy, state);
    	TripDriver driver2 = new TripDriver(state);
//    	state.getTime().setDate(startTime);
    	state.setOdometerX100(odo);
    	
    	
    	driver1.addToTrip(start, stop);
    	driver2.addToTrip(start, stop);
    	
        driver1.addToTrip(stop, start);
        driver2.addToTrip(stop, start);
        
        driver1.addEvent(25, AutomationDeviceEvents.ignitionOff(driver1.getdeviceState(), null));
        driver1.addEvent(25, AutomationDeviceEvents.ignitionOn(driver1.getdeviceState(), null));

        driver1.getdeviceState().setTopSpeed(60).setSpeed(55);
        driver1.addEvent(30, AutomationDeviceEvents.hardBump(driver1.getdeviceState(), null, 120));
        
        driver2.addEvent(25, AutomationDeviceEvents.ignitionOff(driver2.getdeviceState(), null));
        
        driver2.getdeviceState().setTopSpeed(60).setSpeed(55);
        driver1.addEvent(30, AutomationDeviceEvents.hardBump(driver2.getdeviceState(), null, 120));
        
        LinkedList<DeviceNote> notes1 = driver1.generateNotes();
        LinkedList<DeviceNote> notes2 = driver2.generateNotes();
        
        double percent = notes2.size() / 100.0;
        Double delFrom = percent * 40;
        Double delTo = percent * 60;
        for (int i = delTo.intValue();i>delFrom;i--){
        	notes2.remove(i);
        }
        
        Log.info("Last note for Missing notes is: %s  :: %d", notes1.getLast().getTime(), notes1.getLast().getTime().toInt());
        Log.info("Odo for Missing notes is: %d", notes1.getLast().getOdometer());

        tripsMap.put(driver1.getdeviceState(), notes1);
        tripsMap.put(driver2.getdeviceState(), notes2);
    }
    
    
    private void backwardOdometer(){
    	
    	String start = "4225 W Lake Park Blvd, West Valley City, UT";
    	String stop = "350 N 700 W, Springville, UT";
    	deviceNumber = 4;
    	LinkedList<DeviceNote> list = new LinkedList<DeviceNote>();
    	DeviceState state = newState(++deviceNumber);


		tripsMap.put(state, list);
		createVehicle(deviceNumber, portalProxy, state);

		int startTime = 1329257019 + 900;
		int odo = 0 + 9168;
//		int odo = 37504 + 9168;
//    	state.getTime().setDate(startTime);
    	state.setOdometerX100(odo);
    	Log.info("Highest Mileage for the backwards odometer is : " + state.getOdometerX100());
    	
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
    	Log.info("Backward Odometer last note time is " + state.getTime().toInt());
    	list.add(AutomationDeviceEvents.powerOff(state, trips.currentLocation()).getNote());
    }
    
    private void reverseOdo(DeviceState state, int lastOdo, DeviceNote note){
    	int current = state.getOdometerX100();
    	int diff = current - lastOdo;
    	state.setOdometerX100(lastOdo - diff);
    	note.setOdometer(state.getOdometerX100());
    }
    
    private void timeGaps(){
    	deviceNumber = 7;
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
    	deviceNumber = 5;
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	
//    	state.getTime().setDate(startTime);
    	state.setOdometerX100(27504);
    	
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
    	deviceNumber = 6;
    	DeviceState state = newState(++deviceNumber);
    	createVehicle(deviceNumber, portalProxy, state);
    	WaysmartDevice ways = new WaysmartDevice(state, server);
//    	state.getTime().setDate(1111111);
//    	state.setOdometerX100(0);
    	
    	String start = "4225 W Lake Park Blvd, West Valley City, UT";
    	String first = "1600 pennsylvania ave, washington dc";
    	String second = "Disney World, Bay Lake, FL";
    	String fourth = "6458 E Golflinks Rd, Tucson, AZ 85730";
    	String third = "5130 Chromite St, El Paso, TX 79932";
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
//        deviceNumber = 1000;
        deviceNumber = 0;
        TripDriver driver = new TripDriver(type);
        
//        String start = "4225 W Lake Blvd, West Valley City, UT 84120";
//        String stop = "5000 W Lake Blvd, West Valley City, UT 84120";
        driver.addToTrip(start, stop);
        driver.addToTrip(stop, start);
        
        
        DeviceState state = driver.getdeviceState();
        
        state.setDriverID(unknownDriverID);
        if (server.equals(Addresses.QA)){
	        state.setOdometerX100(45840);
	        state.getTime().setDate(1329183843);
        } else if (server.equals(Addresses.DEV)){
        	state.setOdometerX100(11168);
        	state.getTime().setDate(1329864792);
        } 
        
        if (deviceNumber==1000){
            state.setOdometerX100(36672);
            state.getTime().setDate(1328686629);
        }


        driver.addEvent(2, AutomationDeviceEvents.panic(state, null));
        
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
        
        Log.info("Final mileage is: %d", inOrder.getLast().getOdometer());
        state.getTime().addToMinutes(15);
        Log.info("Final noteTime is: %d", state.getTime().epochSeconds());
        Log.info("Final noteTime is: %s", state.getTime());
        

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
    	if (!onQA){
    		return;
    	}
        Vehicle vehicle;
        String last = String.format("%05d", i);
        String vin = "VEHICLEFORWS" + last;
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
//        SiloService proxy = new AutomationHessianFactory().getPortalProxy(server);
//        Map<String, Object> person = proxy.getPersonByEmpid(acctID, last);
//        Map<String, Object> driver = proxy.getDriverByPersonID((Integer) person.get("personID"));
        
        DeviceState state = new DeviceState("30023FKEWS"+last, type);
        if (state.getVehicleID().equals("0")){
        	state.setVehicleID("FKE" + last);
        }
        state.setSettings(DeviceProps.getWaysmartDefaults());
        state.setAccountID(acctID);
        state.setMcmID("FKE" + last);
        state.setWaysDirection(Direction.wifi);
        state.setDriverID(unknownDriverID);
        state.setEmployeeID(last);
        state.setHosState(HOSState.ON_DUTY_NOT_DRIVING);
        Log.info("Imei:%s, MCMID:%s, DriverID:%d", state.getImei(), state.getMcmID(), state.getDriverID());
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
            manager.addNote(AutomationDeviceEvents.changeDriverState(next, temp.peek().getLocation(), "WVC,UT").getNote());
            Log.info(manager.getNotes());
            while (!temp.isEmpty()){
            	DeviceNote note = temp.poll();
                manager.addNote(note);
                
            }
            while (manager.hasNext()){
            	Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> list = manager.getNotes(noteSize);
            	while (!list.isEmpty()){
            		proxy.sendNotes(next, list);
//            		Log.i("Notes left to send: " + manager.size());
            	}
        	}
        }
    }
    
    
    private void installEvent(DeviceState state, MCMProxyObject proxy) {
        Vehicle vehicle = vehicleMap.get(state);
        if (onQA && vehicle.getDeviceID() != null){
            Log.info("Vehicle: %d, Device: %d", vehicle.getVehicleID(), vehicle.getDeviceID());
            return;
        }
        Log.debug(vehicle);
        state.getTime().addToDay(-1);
        InstallEvent event = AutomationDeviceEvents.install(state, installLocation);
        Log.info(proxy.sendNotes(state, event.getNote()));
    }


    public static void main(String[] args){
        WaysmartAggregationTest test = new WaysmartAggregationTest();
        Log.info(test);
    	
//    	DeviceState state = new DeviceState("888", ProductType.WAYSMART);
//    	NoteBC note = new NoteBC(DeviceNoteTypes.LOCATION, state, new GeoPoint("4225 W Lake Park Blvd, West Valley City, UT"));
//    	note.addAttr(EventAttr.SPEED_LIMIT, 75);
//    	NoteBC test = note.unPackage(note.Package());
//    	Log.i(note);
//    	Log.i(test);
    	
    }
}
