package com.inthinc.pro.automation;

import java.util.LinkedList;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.NoteBC;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.models.NoteWS;
import com.inthinc.pro.automation.objects.WaysmartDevice;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartAggregationTest {
    
    private LinkedList<DeviceNote> baseline;
    
    public WaysmartAggregationTest(){
        baseline = new LinkedList<DeviceNote>();
        populateBaseline();
    }
    
    private class Location {
        
        private final DeviceNote note;
         
        private Location(DeviceNoteTypes type, GeoPoint location, AutomationCalendar time, Heading heading, int sats, int speed, Integer speedLimit, int odometer, int driverID, int duration){
            DeviceState state = new DeviceState(null, ProductType.WAYSMART);
            state.getTime().setDate(time);
            state.setHeading(heading);
            state.setSats(sats);
            state.setSpeed(speed);
            state.setSpeed_limit(speedLimit.doubleValue());
            state.setBoundaryID(0);
            state.setDriverID(driverID);
            state.setOdometer(odometer);
            if (type.equals(DeviceNoteTypes.LOCATION)){
                note = new NoteBC(type, state, location);
            } else {
                note = new NoteWS(type, state, location, duration);
            }
        }
    }
    
    
    private void populateBaseline(){
        
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
        WaysmartAggregationTest test = new WaysmartAggregationTest();
        test.test(5);
    }
}
