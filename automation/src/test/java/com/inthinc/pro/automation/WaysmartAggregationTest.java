package com.inthinc.pro.automation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.NoteBC;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.models.NoteWS;
import com.inthinc.pro.automation.objects.TripTracker;
import com.inthinc.pro.automation.objects.WaysmartDevice;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartAggregationTest {
    
    private Map<String, LinkedList<Location>> tripsMap;
    private GeoPoint lastLocation;
    
    
    
    public WaysmartAggregationTest(){
        tripsMap = new HashMap<String, LinkedList<Location>>();
        populateBaseline();
    }
    
    private class Location implements DeviceNote{
        
        private final DeviceNote note;
         
        private Location(DeviceNoteTypes type, DeviceState state, GeoPoint location){
            
            if (type.equals(DeviceNoteTypes.LOCATION)){
                note = new NoteBC(type, state, location);
            } else {
                note = new NoteWS(type, state, location);
            }
        }
        

        @Override
        public byte[] Package() {
            return note.Package();
        }

        @Override
        public DeviceNoteTypes getType() {
            return note.getType();
        }

        @Override
        public Long getTime() {
            return note.getTime();
        }

        @Override
        public DeviceNote copy() {
            return note.copy();
        }
    }
    
    
    
    
    private void populateBaseline(){
        LinkedList<DeviceNote> baseline = new LinkedList<DeviceNote>();
        int speed=60;
        DeviceState state = new DeviceState(null, ProductType.WAYSMART);
        TripTracker trips = new TripTracker("100 Hurt St, Columbia, KY 42728", "Cs-1053/Diamond Ct, Prestonsburg, KY 41653", state);
        
    }
    
    
    public void test(int numOfWaysmarts){
        WaysmartDevice[] devices = new WaysmartDevice[numOfWaysmarts];
        for (int i=0;i<numOfWaysmarts;i++){
            String last = String.format("%05d", i);
            MasterTest.print(last);
            devices[i] = new WaysmartDevice("FAKEIMEI"+last , "MCMFAKE" + last, Direction.wifi);
        }
    }
    

    
    public static void main(String[] args){
        
        
    }
}
