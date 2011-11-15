package com.inthinc.pro.automation.objects;

import java.util.LinkedList;

import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.GoogleTrips;
import com.inthinc.pro.automation.utils.AutomationCalendar;

public class TripTracker {

    private LinkedList<GeoPoint> trip;
    
    private int currentPoint;
    
    private DeviceState state;
    
    public TripTracker(String startPoint, String endPoint, DeviceState state){
        this(state);
        getTrip(startPoint, endPoint);
    }
    
    public TripTracker(DeviceState state){
        this.state = state;
        currentPoint = 0;
        trip = new LinkedList<GeoPoint>();
    }
    
    public TripTracker getTrip(String startPoint, String endPoint){
        GoogleTrips trips = new GoogleTrips();
        trip = trips.getTrip(startPoint, endPoint);
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
            state.setOdometer(0);
            return next;
        }
        Heading.getHeading(next, last);
        state.setOdometer(last.deltaX(next)*100);
        if (time){
            state.setSpeed(last.speed(value, next));
            state.incrementTime(value);
        } else {
            state.setSpeed(value);
            state.incrementTime(last.deltaT(value, next));
        }
        return next;
    }
//    
//    private void getHeading(GeoPoint next, GeoPoint last){
//        Integer direction = Distance_Calc.get_heading(next,last);
//        Integer[] headers = { 0, 45, 90, 135, 180, 225, 270, 315, 360 };
//
//        for (int heading = 0; heading < headers.length - 1; heading++) {
//            if (direction < headers[heading + 1]) {
//                Integer deltaA = Math.abs(headers[heading] - direction);
//                Integer deltaB = Math.abs(headers[heading + 1] - direction);
//                Integer winner = Math.min(deltaA, deltaB);
//                if (winner == deltaA) {
//                    direction = heading;
//                    break;
//                } else if (winner == deltaB) {
//                    direction = heading + 1;
//                    break;
//                }
//            }
//        }
//        if (direction == 9)
//            direction = 0;
//        state.setHeading(direction);
//    }

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

    public void firstPoint(GeoPoint geoPoint) {
        trip.offer(geoPoint);        
    }
}
