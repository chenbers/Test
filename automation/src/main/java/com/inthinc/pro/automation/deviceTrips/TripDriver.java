package com.inthinc.pro.automation.deviceTrips;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.AutomationEvents;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TripTracker;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;
    private boolean interrupt = false;
    private LinkedList<AutomationEvents>[] events2;
    private List<Integer> positions;
    private DeviceState state;
    

    @SuppressWarnings("unchecked")
	private TripDriver(){
        events2 = new LinkedList[100];
        positions = new ArrayList<Integer>();
    }

    public TripDriver(DeviceBase device) {
        this();
        this.device = device;
        this.state = device.getState();
        tripTracker = device.getTripTracker();
        
    }
    
    public TripDriver(ProductType productVersion){
        this();
        state = new DeviceState(null, productVersion);
        tripTracker = new TripTracker(state);
    }
    
    @Override
    public void start(){
        super.start();
    }
    
    public boolean addToTrip(String origin, String destination){
        tripTracker.getTrip(origin, destination);
        return true;
    }
    
    @Override
    public void run(){
        Iterator<GeoPoint> itr = tripTracker.iterator();
        if (!interrupt){
            device.power_on_device();
            device.turn_key_on(60);
        }
        int totalNotes = tripTracker.size()*100;
        Double currentPercent;
        while (itr.hasNext() && !interrupt){
            currentPercent = ((tripTracker.currentCount() * 100.0) / totalNotes) * 100;
            int currentPoint = currentPercent.intValue();
            int speedLimit = device.getState().getSpeedLimit().intValue();
            if (events2[currentPoint] != null){
            	while (!events2[currentPoint].isEmpty()){
                    events2[currentPoint].poll().addEvent(device);
            	}
        		positions.remove(currentPercent);
        		events2[currentPoint] = null;
            } else {
            	int wouldBePosition = Collections.binarySearch(positions, currentPoint); 
            	if ( wouldBePosition < -1 ){
            		int noteN = positions.get(0);
            		events2[noteN].poll().addEvent(device);
            		if (events2[noteN].isEmpty()){
            			positions.remove(0);
            			events2[noteN] = null;
            		}
            	}
            }
            device.goToNextLocation(speedLimit, false);
        }
        if (!interrupt){
            device.turn_key_off(60);
            device.power_off_device(60);   
        }
    }
    
    public LinkedList<DeviceNote> generateNotes(){
        LinkedList<DeviceNote> notes = new LinkedList<DeviceNote>();
        Iterator<GeoPoint> itr = tripTracker.iterator();
        AutomationCalendar start = tripTracker.getState().getTime();
        notes.add(AutomationDeviceEvents.ignitionOn().getNote(tripTracker.currentLocation(), state));
        
        int totalNotes = tripTracker.size()*100;
        Double currentPercent;
        int speed = 60;
        while (itr.hasNext()){
            currentPercent = ((tripTracker.currentCount() * 100.0) / totalNotes) * 100;
            int currentPoint = currentPercent.intValue();
            if (events2[currentPoint] != null){
            	while (!events2[currentPoint].isEmpty()){
                    AutomationEvents event = events2[currentPoint].poll();
                    notes.add(event.getNote(tripTracker.currentLocation(), state));
            	}
        		positions.remove((Object)currentPoint);
        		events2[currentPoint] = null;
            } else {
            	int wouldBePosition = Collections.binarySearch(positions, currentPoint); 
            	if ( wouldBePosition < -1 ){
            		int noteN = positions.get(0);

            		AutomationEvents event = events2[noteN].poll();
                    notes.add(event.getNote(tripTracker.currentLocation(), state));
                    
            		if (events2[noteN].isEmpty()){
            			positions.remove(0);
            			events2[noteN] = null;
            		}
            	}
            }
            
            notes.add(DeviceNote.constructNote(DeviceNoteTypes.LOCATION, tripTracker.getNextLocation(speed, false), state));
        }
        
        AutomationCalendar stop = tripTracker.getState().getTime();
        notes.add(AutomationDeviceEvents.ignitionOff(stop.getDelta(start), 90).getNote(tripTracker.currentLocation(), state));
        
        return notes;
    }
    
    @Override
    public void interrupt(){
        interrupt = true;
        device = null;
        throw new NullPointerException("Ending the trip???");
    }

    public void testTrip() {
        tripTracker.addLocation(new GeoPoint(33.0104, -117.111));
        tripTracker.addLocation(new GeoPoint(33.0104, -117.113));
        tripTracker.addLocation(new GeoPoint(33.01, -117.113));
        tripTracker.addLocation(new GeoPoint(33.0097, -117.1153));
        tripTracker.addLocation(new GeoPoint(33.015, -117.116));
        tripTracker.addLocation(new GeoPoint(33.0163, -117.1159));
        tripTracker.addLocation(new GeoPoint(33.018, -117.1153));
        tripTracker.addLocation(new GeoPoint(33.0188, -117.118));
        tripTracker.addLocation(new GeoPoint(33.0192, -117.1199));
        tripTracker.addLocation(new GeoPoint(33.021, -117.119));
        tripTracker.addLocation(new GeoPoint(33.022, -117.114));
        tripTracker.addLocation(new GeoPoint(33.0205, -117.111));
        tripTracker.addLocation(new GeoPoint(33.02, -117.109));
        tripTracker.addLocation(new GeoPoint(33.02, -117.108));
        tripTracker.addLocation(new GeoPoint(33.022, -117.104));
        tripTracker.addLocation(new GeoPoint(33.0217, -117.103));
        tripTracker.addLocation(new GeoPoint(33.0213, -117.1015));
        tripTracker.addLocation(new GeoPoint(33.0185, -117.1019));
        tripTracker.addLocation(new GeoPoint(33.017, -117.102));
        tripTracker.addLocation(new GeoPoint(33.015, -117.1032));
        tripTracker.addLocation(new GeoPoint(33.013, -117.105));
        tripTracker.addLocation(new GeoPoint(33.011, -117.106));
        tripTracker.addLocation(new GeoPoint(33.0108, -117.108));
        tripTracker.addLocation(new GeoPoint(33.0108, -117.109));
        tripTracker.addLocation(new GeoPoint(33.0106, -117.11));
        tripTracker.addLocation(new GeoPoint(33.0104, -117.111));
    }
    
    
    public void addEvent(int percentTimeIn, AutomationEvents event){
    	if (events2[percentTimeIn] == null){
    		events2[percentTimeIn] = new LinkedList<AutomationEvents>();
    	}
        events2[percentTimeIn].add(event);
        
        if (!positions.contains(percentTimeIn)){
        	positions.add(Math.abs(Collections.binarySearch(positions, percentTimeIn))-1,percentTimeIn);	
        }
    }
    
    public DeviceState getdeviceState() {
        return state;
    }
}
