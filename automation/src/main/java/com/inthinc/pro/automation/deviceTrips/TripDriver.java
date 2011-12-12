package com.inthinc.pro.automation.deviceTrips;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.models.AutomationDeviceEvents.AutomationEvents;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TripTracker;
import com.inthinc.pro.model.configurator.ProductType;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;
    private boolean interrupt = false;
    private AutomationEvents[] events;
    private Set<Integer> positions;
    private DeviceState state;
    
    private TripDriver(){
        events = new AutomationEvents[100];
        positions = new HashSet<Integer>();
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
        Double lastPercent=0.0;
        Double currentPercent;
        while (itr.hasNext() && !interrupt){
            currentPercent = totalNotes / tripTracker.currentCount() * 1.0;
            int speedLimit = device.getState().getSpeed_limit().intValue();
            if (events[currentPercent.intValue()]!=null){
                events[currentPercent.intValue()].addEvent(device);
                positions.remove(currentPercent);
            } else {
                for (int event : positions){
                    if (lastPercent < event && event < currentPercent){
                        events[event].addEvent(device);
                        positions.remove(event);
                        break;
                    }
                }
            }
            device.goToNextLocation(speedLimit, false);
            lastPercent = currentPercent;
        }
        if (!interrupt){
            device.turn_key_off(60);
            device.power_off_device(60);   
        }
    }
    
    public LinkedList<DeviceNote> generateNotes(){
        LinkedList<DeviceNote> notes = new LinkedList<DeviceNote>();
        Iterator<GeoPoint> itr = tripTracker.iterator();

        int totalNotes = tripTracker.size()*100;
        Double lastPercent=0.0;
        Double currentPercent;
        int speed = 60;
        while (itr.hasNext()){
            currentPercent = ((tripTracker.currentCount() * 100.0) / totalNotes) * 100;
            
//            if (currentPercent.intValue() == 10){
//                MasterTest.print(currentPercent);
//                MasterTest.print(tripTracker.currentCount());
//                MasterTest.print(totalNotes);
//                throw new NullPointerException();
//            }
            if (events[currentPercent.intValue()]!=null){
                AutomationEvents event = events[currentPercent.intValue()];
                DeviceNote tripEvent = DeviceNote.constructNote(event.getNoteType(), tripTracker.currentLocation(), state);
                event.getNote(tripEvent, state.getProductVersion());
                notes.add(tripEvent);
                positions.remove(currentPercent);
                events[currentPercent.intValue()] = null;
            } else {
                for (int eventPos : positions){
                    if (lastPercent < eventPos && eventPos < currentPercent){
                        AutomationEvents event = events[eventPos];
                        DeviceNote tripEvent = DeviceNote.constructNote(event.getNoteType(), tripTracker.currentLocation(), state);
                        event.getNote(tripEvent, state.getProductVersion());
                        notes.add(tripEvent);
                        positions.remove(eventPos);
                        break;
                    }
                }
            }
            
            notes.add(DeviceNote.constructNote(DeviceNoteTypes.LOCATION, tripTracker.getNextLocation(speed, false), state));

            lastPercent = currentPercent;
        }
//        if (!interrupt){
//            device.turn_key_off(60);
//            device.power_off_device(60);   
//        }
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
        events[percentTimeIn] = event;
        positions.add(percentTimeIn);
    }

    public DeviceState getdeviceState() {
        return state;
    }
}
