package com.inthinc.device.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.inthinc.device.devices.DeviceBase;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;
    private boolean interrupt = false;
    private LinkedList<AutomationDeviceEvents>[] events2;
    private List<Integer> positions;
    private DeviceState state;
	private boolean locationOn;
    

    @SuppressWarnings("unchecked")
	private TripDriver(){
        events2 = new LinkedList[100];
        positions = new ArrayList<Integer>();
        locationOn = true;
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
    
    public TripDriver(DeviceState state){
    	this();
    	this.state = state;
    	tripTracker = new TripTracker(state);
    }
    
    public TripDriver(TripTracker trips){
    	this();
    	tripTracker = trips;
    	state = trips.getState();
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
                    device.addNote(updateNote(events2[currentPoint].poll().getNote()));
            	}
        		positions.remove(currentPercent);
        		events2[currentPoint] = null;
            } else {
            	int wouldBePosition = Collections.binarySearch(positions, currentPoint); 
            	if ( wouldBePosition < -1 ){
            		int noteN = positions.get(0);
            		if (events2[noteN] == null || events2[noteN].isEmpty()){
            			positions.remove(0);
            			events2[noteN] = null;
            		} else {
            			while(!events2[noteN].isEmpty()){
            				device.addNote(updateNote(events2[noteN].poll().getNote()));
            			}
            			positions.remove(0);
            			events2[noteN] = null;
            		}
            	}
            }
            device.goToNextLocation(speedLimit, false);
        }
        if (!interrupt){
            device.turn_key_off(60);
            device.power_off_device(900);   
        }
    }
    
    public LinkedList<DeviceNote> generateNotes(){
        LinkedList<DeviceNote> notes = new LinkedList<DeviceNote>();
        Iterator<GeoPoint> itr = tripTracker.iterator();
        AutomationCalendar start = tripTracker.getState().getTime();
        notes.add(AutomationDeviceEvents.powerOn(state, tripTracker.currentLocation()).getNote());
        notes.add(AutomationDeviceEvents.ignitionOn(state, tripTracker.currentLocation()).getNote());
        
        int totalNotes = tripTracker.size()*100;
        Double currentPercent;
        int speed = 60;
        while (itr.hasNext()){
            currentPercent = ((tripTracker.currentCount() * 100.0) / totalNotes) * 100;
            int currentPoint = currentPercent.intValue();
            if (events2[currentPoint] != null){
            	while (!events2[currentPoint].isEmpty()){
            		notes.add(updateNote(events2[currentPoint].poll().getNote()));
            	}
        		positions.remove((Object)currentPoint);
        		events2[currentPoint] = null;
            } else {
            	int wouldBePosition = Collections.binarySearch(positions, currentPoint); 
            	if ( wouldBePosition < -1 ){
            		int noteN = positions.get(0);
            		if (events2[noteN] == null || events2[noteN].isEmpty()){
            			positions.remove(0);
            			events2[noteN] = null;
            		} else {
		        		while (!events2[noteN].isEmpty()){
		        			notes.add(updateNote(events2[noteN].poll().getNote()));
		        		}
		    			positions.remove(0);
		    			events2[noteN] = null;
            		}
            	}
            }

            GeoPoint nextLoc = tripTracker.getNextLocation(speed, false); 
            
            if (locationOn) {
            	notes.add(AutomationDeviceEvents.location(state, nextLoc).getNote());
            }
        }
        
        AutomationCalendar stop = tripTracker.getState().getTime();
        state.setTripDuration(stop.getDelta(start));
        state.setPointsPassedTheFilter(90);
        state.setSpeed(0);
        notes.add(AutomationDeviceEvents.ignitionOff(state, tripTracker.currentLocation()).getNote());
        notes.add(AutomationDeviceEvents.powerOff(state, tripTracker.currentLocation()).getNote());
        
        return notes;
    }

	private DeviceNote updateNote(DeviceNote note) {
		note.getLocation().changeLocation(tripTracker.currentLocation());
		note.getTime().setDate(state.getTime());
		note.setOdometer(state.getOdometerX100());
		if (DeviceNote.endOfTripNotes.contains(note.getType())){
			AutomationDeviceEvents.endOfTripAttrs(state, note);
		}
		return note;
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
    
    
    public void addEvent(int percentTimeIn, AutomationDeviceEvents event){
    	if (events2[percentTimeIn] == null){
    		events2[percentTimeIn] = new LinkedList<AutomationDeviceEvents>();
    	}
        events2[percentTimeIn].add(event);
        
        if (!positions.contains(percentTimeIn)){
        	positions.add(Math.abs(Collections.binarySearch(positions, percentTimeIn))-1,percentTimeIn);	
        }
    }
    
    public DeviceState getdeviceState() {
        return state;
    }

	public void isLocationOn(boolean locationOn) {
		this.locationOn  = locationOn;
	}
}
