package com.inthinc.pro.automation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceTrips.TripDriver;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.InstallEvent;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.utils.AutomationSiloService;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartAggregationTest {
    private static final int acctID = 398;
    private static final int groupID = 5260;
    private static final int unknownDriverID = 66462;
    /* AccountID    =   398
     * Account Name = WSAgg
     * GroupID      =  5259
     * PersonID     = 68781
     * UserID       = 43469
     * userName     = dtanner
     * password     = password
     */
    
    private final static State usState = new State(47, "Utah", "UT");
    
    private Map<DeviceState, List<DeviceNote>> tripsMap;
    private Map<DeviceState, Vehicle> vehicleMap;
    
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<DeviceState, List<DeviceNote>>();
        vehicleMap = new HashMap<DeviceState, Vehicle>();
        populateBaseline();
        sendNotes();
    }
    
    
    private void populateBaseline(){
        int i=0;
        LinkedList<DeviceNote> baseline = new LinkedList<DeviceNote>();
        String start = "100 Hurt St, Columbia, KY 42728";
        String stop = "Cs-1053/Diamond Ct, Prestonsburg, KY 41653";
        TripDriver driver = new TripDriver(ProductType.WAYSMART);
        driver.getdeviceState().setDriverID(unknownDriverID);
        driver.addToTrip(start, stop);
        driver.addToTrip(stop, start);
        driver.addEvent(10, AutomationDeviceEvents.speeding(75, 100, 600, 60, 65, 500));
        driver.addEvent(20, AutomationDeviceEvents.speeding(80, 200, 700, 40, 75, 600));
        driver.addEvent(30, AutomationDeviceEvents.hardAccel(100, 20, 10));
        driver.addEvent(40, AutomationDeviceEvents.hardBrake(100, 20, 10));
        driver.addEvent(50, AutomationDeviceEvents.seatbelt(700, 90, 25, 20, 100, 600));
        driver.addEvent(60, AutomationDeviceEvents.hardDip(10, 20, 110));
        baseline = driver.generateNotes();
        ArrayList<DeviceNote> randomized = new ArrayList<DeviceNote>();

        for (DeviceNote note : baseline){
            randomized.add(note.copy());
        }
        
        
        AutomationSiloService portalProxy = new AutomationSiloService(Addresses.QA);
        Collections.shuffle(randomized);
        DeviceState state;
        
        state = newState(++i);
        tripsMap.put(state, baseline);
        createVehicle(i, portalProxy, state);

        state = newState(++i);
        tripsMap.put(state, randomized);
        createVehicle(i, portalProxy, state);
    }


    private void createVehicle(int i, AutomationSiloService portalProxy,
            DeviceState state) {
        Vehicle vehicle;
        vehicle = new Vehicle(null, groupID, Status.ACTIVE, String.format("VEHICLEFORW%05d", i), "Fake",
                "Model", 2011, "White", VehicleType.HEAVY, String.format("VEHICLEFORW%05d", i), 9000, "ll33l", usState);
        try {
            vehicle = portalProxy.createVehicle(vehicle.getGroupID(), vehicle);
        } catch (RemoteServerException e){
            vehicle = portalProxy.getVehicle(vehicle.getVIN());
        }
        vehicleMap.put(state, vehicle);
    }
    
    private DeviceState newState(int number){
        String last = String.format("%05d", number);
        DeviceState state = new DeviceState("30023FKEWS"+last, ProductType.WAYSMART);
        state.setMcmID("FKE" + last);
        state.setWaysDirection(Direction.wifi);
        state.setDriverID(unknownDriverID);
        MasterTest.print("Imei:%s, MCMID:%s, DriverID:%d", state.getImei(), state.getMcmID(), state.getDriverID());
        return state;
    }
    
    private void sendNotes(){
        Iterator<DeviceState> itr = tripsMap.keySet().iterator();
        MCMProxyObject proxy = new MCMProxyObject(Addresses.QA);
        while (itr.hasNext()){
            DeviceState next = itr.next();
            installEvent(next, proxy);
            
            NoteManager manager = new NoteManager();
            int i = 0;
            for (DeviceNote note : tripsMap.get(next)){
                manager.addNote(note);
                if (i==4){
                    proxy.sendNotes(next, manager.getNotes(i));
                    if (proxy != null){
                        throw new NullPointerException();
                    }
                    i=0;
                } else {
                    i++;
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
        
        DeviceNote install = DeviceNote.constructNote(DeviceNoteTypes.INSTALL, new GeoPoint(50.0, 50.0), state);
        InstallEvent event = AutomationDeviceEvents.install(vehicle.getName(), state.getMcmID(), acctID);
        event.getNote(install, state.getProductVersion());
        proxy.sendNotes(state, install);
    }


    public static void main(String[] args){
        WaysmartAggregationTest test = new WaysmartAggregationTest();
    }
}
