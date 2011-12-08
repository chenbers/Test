package com.inthinc.pro.automation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.automation.deviceTrips.TripDriver;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.BaseEntity;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartAggregationTest {
    
    private Map<DeviceState, List<DeviceNote>> tripsMap;
    
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<DeviceState, List<DeviceNote>>();
        populateBaseline();
        sendNotes();
    }
    
//    private List<BaseEntity> getAccount(){
//        Map<Class<? extends BaseEntity>, ? extends BaseEntity> map = new HashMap<Class<? extends BaseEntity>, ? extends BaseEntity>();
//        map(Account.class, new Account(398, "WSAgg", Status.ACTIVE));
//        Person
//        
//        return acct;
//    }
    
    
    private void populateBaseline(){
        int i=0;
        LinkedList<DeviceNote> baseline = new LinkedList<DeviceNote>();
        String start = "100 Hurt St, Columbia, KY 42728";
        String stop = "Cs-1053/Diamond Ct, Prestonsburg, KY 41653";
        TripDriver driver = new TripDriver(ProductType.WAYSMART);
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
        Collections.shuffle(randomized);
        tripsMap.put(newState(i++), baseline);
        tripsMap.put(newState(i++), randomized);
        
    }
    
    private DeviceState newState(int number){
        String last = String.format("%05d", number);
        MasterTest.print(last);
        DeviceState state = new DeviceState("FAKEIMEI"+last, ProductType.WAYSMART);
        state.setMcmID("FAKE" + last);
        state.setWaysDirection(Direction.wifi);
        return state;
    }
    
    private void sendNotes(){
        Iterator<DeviceState> itr = tripsMap.keySet().iterator();
        MCMProxyObject proxy = new MCMProxyObject(Addresses.QA);
        while (itr.hasNext()){
            int i = 0;
            DeviceState next = itr.next();
            NoteManager manager = new NoteManager();
            for (DeviceNote note : tripsMap.get(next)){
                manager.addNote(note);
                if (i==4){
                    proxy.sendNotes(next, manager.getNotes(i));
                    i=0;
                } else {
                    i++;
                }
            }
        }
    }
    
    
    public void test(int numOfWaysmarts){
        DeviceState[] states = new DeviceState[numOfWaysmarts];
        for (int i=0;i<numOfWaysmarts;i++){
            String last = String.format("%05d", i);
            MasterTest.print(last);
            DeviceState state = new DeviceState("FAKEIMEI"+last, ProductType.WAYSMART);
            state.setMcmID("FAKE" + last);
            state.setWaysDirection(Direction.wifi);
            states[i] = state;
            
        }
    }
    

    
    public static void main(String[] args){
        WaysmartAggregationTest test = new WaysmartAggregationTest();
    }
}
