package com.inthinc.device.objects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Distance_Calc;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.GoogleTrips;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.sbs.Sbs;
import com.inthinc.sbs.SpeedLimit;

public class TripTracker implements Iterable<GeoPoint> {

    private LinkedList<GeoPoint> trip;
    
    private int currentPoint;
    
    private final DeviceState state;

	private Sbs sbs = null;

	private boolean useSbs = true;

    private int lastSpeedLimit = 70;
    
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
    
    public TripTracker(DeviceState state, Sbs sbs){
    	this(state);
    	this.sbs  = sbs;
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
        setPoints(trips.getTrip(startPoint, endPoint));
        return this;
    }
    
    public TripTracker setPoints(List<GeoPoint> points){
        trip.addAll(points);
        return this;
    }
    
    public GeoPoint currentLocation(){
    	if (trip.get(currentPoint)==null){
    		return new GeoPoint();
    	}
        return trip.get(currentPoint);
    }
    
    public GeoPoint lastLocation(){
        if (currentPoint==0){
            return null;
        }
        return trip.get(currentPoint-1);
    }
    
    public void useSbs(boolean useSbs){
    	this.useSbs  = useSbs;
    }
    
    public GeoPoint getNextLocation(int value, boolean time){
        GeoPoint next = trip.get(++currentPoint);
        GeoPoint last = lastLocation();
        while (next.equals(last) && currentPoint < trip.size()-1){
        	next = trip.get(++currentPoint);
        }
        if (last == null){
            state.setHeading(Heading.NORTH);
            state.setSpeed(0);
            if (!state.getProductVersion().equals(ProductType.WAYSMART)){
                state.setOdometerX100(0);
            }
            return next;
        }
        
        Integer heading = Distance_Calc.get_heading(last, next);
        if (sbs != null && useSbs){
        	SpeedLimit limit = sbs.getSpeedLimit(next, heading*10);
        	if (limit.closestGID == 0){
        		state.getCourse();
        	}
        	lastSpeedLimit = limit.speedLimit/100;
        	state.setSpeedLimit(lastSpeedLimit);
        }
        state.setHeading(Heading.getHeading(heading));
        
        int distTraveled = ((Double)(last.deltaX(next) * 100)).intValue();
        
        state.incrementTripDistanceX100(distTraveled);
        
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
        state.setOdometerX100(odometer);
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
    
    public Double percentOfTrip(){
        Double size = size() * 1.0;
    	Double valueToReturn = ((currentPoint*1.0) / size * 100.0);
    	return valueToReturn;
    }
    
    public Sbs getSbs(){
        return sbs;
    }

    public int getLastSpeedLimit() {
        return lastSpeedLimit ;
    }
}
