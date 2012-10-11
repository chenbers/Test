package com.inthinc.device.emulation;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.devices.DeviceBase;
import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripTracker;

public class SmallAggregationTest {
    
    private static final String startPoint = "4225 W Lake Park Blvd, WVC, Utah 84120";
    private static final String stopPoint = "40.58116,-111.88853";
    
    private static final String waysImei = "";
    private static final String waysMcmid = "";
    private static final Direction waysDirection = Direction.wifi;
    private static final int waysStart = 1334078618; // Needs to be updated after running
                                                     // so trips don't stomp on themselves.
    
    private static final String tiwiImei = "DEVICEDOESNTEXIST";
    private static final int tiwiStart = 1334078618; // Needs to be updated after running
                                                     // so trips don't stomp on themselves.
    
    private static final int idleTime = 60 *  5; // x minutes of idle time
    private static final int unbuckledTime =  7; // location events.
    
    private static final int hardTurnEvent = 40; // hard turn event
    private static final int hardBumpEvent = 54; // hard turn event
    private static final int hardAccelEvent  = 70; // hard turn event
    
    private static final int turnSeverity = 100;
    private static final int bumpSeverity = 100;
    private static final int accelSeverity = 70;
    
    private static final int[] speeding = {20,25,10};
    
    
    
    @Test
    @Ignore
    public void testTiwi(){
        TiwiProDevice tiwi = new TiwiProDevice(tiwiImei);
        tiwi.getState().getTime().setDate(tiwiStart);
        runTrip(tiwi);
    }
    
    @Test
    @Ignore
    public void testWaysmart(){
        WaysmartDevice ways = new WaysmartDevice(waysImei, waysMcmid, waysDirection);
        ways.getState().getTime().setDate(waysStart);
        runTrip(ways);
    }
    
    
    /**
     * Demo trip that includes two Idling, Seatbelt, should have speeding<br />
     * and three aggressive driving events.
     * @param device
     */
    public void runTrip(DeviceBase device){
        TripTracker trip = device.getTripTracker();
        DeviceState state = device.getState();
        trip.getTrip(startPoint, stopPoint);
        trip.getTrip(stopPoint, startPoint);
        Iterator<GeoPoint> itr = trip.iterator();
        
        
        device.power_on_device();
        device.increment_time(60);
        device.turn_key_on(60);
        device.idle(idleTime/2, idleTime/2);
        device.unBuckleSeatbelt();
        int i = 0;
        boolean accel = true, turn = true, bump = true;
        boolean[] speedCats = {true, true, true, true, true};
        while (itr.hasNext()){
            i = trip.percentOfTrip().intValue();
            int speed = trip.getLastSpeedLimit();
            if (i==unbuckledTime && !state.isSeatbeltEngaged()){
                device.buckleSeatbelt();
                device.increment_time(30);
            }
            if (i==hardAccelEvent && accel){
                AutomationDeviceEvents.hardAccel(device, accelSeverity);
                accel = false;
                device.increment_time(30);
            }
            if (i==hardTurnEvent && turn){
                AutomationDeviceEvents.hardLeft(device, turnSeverity);
                turn = false;
                device.increment_time(30);
            }
            if (i==hardBumpEvent && bump){
                AutomationDeviceEvents.hardBump(device, bumpSeverity);
                bump = false;
                device.increment_time(30);
            }
            
            if(i>speeding[0] && i<speeding[1]){
            	speed += speeding[2];
            } else if ((i>10 || speed < 30) && speedCats[0]) {
            	speedCats[0] = false;
            	state.setTopSpeed(29).setSpeedingDistanceX100(50).setAvgSpeed(27).setSpeedingSpeedLimit(25).setSpeed(25);
            	AutomationDeviceEvents.speeding(device);
            } else if ((i>20 || speed > 30 && speed < 41) && speedCats[1]) {
            	speedCats[1] = false;
            	state.setTopSpeed(40).setSpeedingDistanceX100(50).setAvgSpeed(37).setSpeedingSpeedLimit(35).setSpeed(35);
            	AutomationDeviceEvents.speeding(device);
            } else if ((i>30 || speed > 40 && speed < 55) && speedCats[2]) {
            	speedCats[2] = false;
            	state.setTopSpeed(53).setSpeedingDistanceX100(50).setAvgSpeed(47).setSpeedingSpeedLimit(45).setSpeed(45);
            	AutomationDeviceEvents.speeding(device);
            } else if ((i>40 || speed > 54 && speed < 65) && speedCats[3]) {
            	speedCats[3] = false;
            	state.setTopSpeed(64).setSpeedingDistanceX100(50).setAvgSpeed(57).setSpeedingSpeedLimit(55).setSpeed(55);
            	AutomationDeviceEvents.speeding(device);
            } else if ((i>50 || speed > 64) && speedCats[4]) {
            	speedCats[4] = false;
            	state.setTopSpeed(70).setSpeedingDistanceX100(50).setAvgSpeed(68).setSpeedingSpeedLimit(65).setSpeed(65);
            	AutomationDeviceEvents.speeding(device);
            } 
            
            device.goToNextLocation(speed, false);
        }
        
        device.idle(idleTime/4, idleTime/4);
        device.turn_key_off(60);
        device.power_off_device(900);
    }

}

