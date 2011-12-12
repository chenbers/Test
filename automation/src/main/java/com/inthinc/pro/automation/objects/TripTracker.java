package com.inthinc.pro.automation.objects;

import java.util.Iterator;
import java.util.LinkedList;

import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.GoogleTrips;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;

public class TripTracker implements Iterable<GeoPoint> {

    private LinkedList<GeoPoint> trip;
    
    private int currentPoint;
    
    private DeviceState state;
    
    private class TripIterator implements Iterator<GeoPoint>{

        @Override
        public boolean hasNext() {
            return !trip.isEmpty() && currentPoint+1 < trip.size();
        }

        @Override
        public GeoPoint next() {
            throw new IllegalAccessError("We are not implementing this method, just hasNext");
        }

        @Override
        public void remove() {
            throw new IllegalAccessError("We are not implementing this method, just hasNext");
        }
        
    }
    
    public TripTracker(String startPoint, String endPoint, DeviceState state){
        this(state);
        getTrip(startPoint, endPoint);
    }
    
    public TripTracker(DeviceState state){
        this.state = state;
        currentPoint = 0;
        trip = new LinkedList<GeoPoint>();
    }
    
    @Override
    public Iterator<GeoPoint> iterator(){
        return new TripIterator();
    }
    
    public TripTracker getTrip(String startPoint, String endPoint){
        GoogleTrips trips = new GoogleTrips();
        trip.addAll(trips.getTrip(startPoint, endPoint));
        return this;
    }
    
    public GeoPoint currentLocation(){
        return trip.get(currentPoint);
    }
    
    public GeoPoint lastLocation(){
        if (currentPoint==0){
            return null;
        }
        return trip.get(currentPoint-1);
    }
    
    public GeoPoint getNextLocation(int value, boolean time){
        GeoPoint next = trip.get(++currentPoint);
        GeoPoint last = lastLocation();
        if (last == null){
            state.setHeading(Heading.NORTH);
            state.setSpeed(0);
            if (!state.getProductVersion().equals(ProductType.WAYSMART)){
                state.setOdometer(0);
            }
            return next;
        }
        state.setHeading(Heading.getHeading(next, last));
        if (state.getProductVersion().equals(ProductType.WAYSMART)){
            state.setOdometer(state.getOdometer() + last.deltaX(next) * 100);
        } else {
            state.setOdometer(last.deltaX(next)*100);
        }
        if (time){
            state.setSpeed(last.speed(value, next));
            state.incrementTime(value);
        } else {
            state.setSpeed(value);
            int delta = last.deltaT(value, next);
            state.incrementTime(delta);
        }
        return next;
    }

    public void setNextLocation(GeoPoint next, int value, boolean time) {
        trip.offer(next);
        getNextLocation(value, time);
    }
    
    public void fakeLocationNote(GeoPoint location, AutomationCalendar time, int sats, Heading heading, int speed, int odometer) {
        state.getTime().setDate(time);
        state.setSats(sats);
        state.setHeading(heading);
        state.setSpeed(speed);
        state.setOdometer(odometer);
        trip.offer(location);
    }

    public GeoPoint getFirstLocation() {
        return trip.getFirst();
    }

    public void addLocation(GeoPoint geoPoint) {
        trip.offer(geoPoint);
    }

    public LinkedList<GeoPoint> getGeoPoints() {
        return trip;
    }

    public int size() {
        return trip.size();
    }

    public int currentCount() {
        return currentPoint;
    }

    public DeviceState getState() {
        return state;
    }
}
