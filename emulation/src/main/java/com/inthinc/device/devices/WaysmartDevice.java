package com.inthinc.device.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSState;
import com.inthinc.device.emulation.enums.DeviceEnums.TripFlags;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.objects.AutomationBridgeFwdCmdParser;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.WaysmartClasses.MultiForwardCmd;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;

public class WaysmartDevice extends DeviceBase {
	
	public static enum Direction implements IndexEnum {
        wifi(3),
        gprs(2),
        sat(1)
        ;
        
        private int direction;
        
        private Direction(int direction){
            this.direction = direction;
        }
        
        @Override
        public Integer getIndex() {
            return direction;
        }
    };

    protected final static ProductType productVersion = ProductType.WAYSMART;


    public WaysmartDevice(String IMEI, String MCM, Addresses server,
            Direction comMethod) {
        this(IMEI, MCM, server, comMethod, DeviceProps.getWaysmartDefaults());
    }

    public WaysmartDevice(String IMEI, String MCM, Addresses server,
            Direction comMethod, Map<DeviceProps, String> settings) {
        super(IMEI, server, settings, productVersion);
        state.setMcmID(MCM);
        portal = server;
        state.setWaysDirection(comMethod);
        setState(147);
    }

    public WaysmartDevice(String IMEI, String MCM, Direction comMethod) {
        this(IMEI, MCM, Addresses.QA, comMethod);
    }

    public WaysmartDevice(DeviceState state, Addresses server) {
    	this(state.getImei(), state.getMcmID(), server, state.getWaysDirection());
    	this.state = state;
	}

	@Override
    protected void ackFwdCmds(String[] reply) {
        List<MultiForwardCmd> commands = new ArrayList<MultiForwardCmd>();
        for (int i = 0; i < reply.length; i++) {
            commands.addAll(AutomationBridgeFwdCmdParser.processCommands(
                    reply[i]));
        }
    }


    @Override
    protected WaysmartDevice createAckNote(Map<String, Object> reply) {
        return this;
    }

    @Override
    protected Integer get_note_count() {
        return note_count;
    }

    public WaysmartDevice changeDriverStatus(String employeeID, HOSState hosState){
    	state.setHosState(hosState).setEmployeeID(employeeID);
    	AutomationDeviceEvents.changeDriverState(this, "");
    	return this;
    }
    

    public WaysmartDevice clearDriver(String employeeID, HOSState hosState){
    	state.setHosState(hosState).setEmployeeID(employeeID).setTripFlags(TripFlags.CLEAR_DRIVER);
    	AutomationDeviceEvents.changeDriverState(this, "");
    	return this;
    }
    
    public WaysmartDevice nonTripNote(GeoPoint location,
            AutomationCalendar time, int sats, Heading heading, int speed,
            int odometer) {
        tripTracker.fakeLocationNote(location, time, sats, heading, speed,
                odometer);
        state.setOdometerX100(odometer);
        return this;
    }

    @Override
    public Integer processCommand(Map<String, Object> reply) {
        return null;
    }

    @Override
    protected WaysmartDevice set_ignition(Integer time_delta) {
        state.setIgnition_state(!state.getIgnition_state());
        state.getTime_last().setDate(state.getTime());
        state.getTime().addToSeconds(time_delta);
        if (state.getIgnition_state()) {
            addEvent(AutomationDeviceEvents.ignitionOn(state, tripTracker.currentLocation()));
        } else {
        	addEvent(AutomationDeviceEvents.ignitionOff(state, tripTracker.currentLocation()));
        }
        return this;

    }

    protected WaysmartDevice set_IMEI(HashMap<DeviceProps, String> settings) {
    	MasterTest.print("IMEI: " + state.getImei() + ", Server: " + portal,
                Level.DEBUG);
        state.setSetting(DeviceProps.MCM_ID, state.getMcmID());
        state.setSetting(DeviceProps.WITNESS_ID, state.getImei());
        return this;
    }

    @Override
    protected WaysmartDevice set_power() {
        state.setPower_state(!state.getPower_state()); // Change the power state between on and off
        if (state.getPower_state()) {
            constructNote(DeviceNoteTypes.POWER_ON);
        } else {
            constructNote(DeviceNoteTypes.LOW_POWER_MODE);
        }
        return this;

    }

    @Override
    protected WaysmartDevice set_server(Addresses server) {
        mcmProxy = new MCMProxyObject(server);
        portal = server;
        String url, port;
        url = server.getMCMUrl();
        port = server.getWaysPort().toString();
        state.setSetting(DeviceProps.SERVER_IP, url + ":" + port);
        state.setSetting(DeviceProps.MAP_SERVER_URL, url);
        state.setSetting(DeviceProps.MAP_SERVER_PORT, server.getMCMPort()
                .toString());
        return this;
    }

    @Override
    public WaysmartDevice set_speed_limit(Integer limit) {
        set_settings(DeviceProps.SPEED_LIMIT, limit);
        return this;

    }

    public void setAccountID(int accountID) {
        state.setAccountID(accountID);
    }

    public WaysmartDevice setBaseOdometer(double odometer) {
        state.setOdometerX100(odometer);
        return this;
    }


    public void setEmployeeID(String employeeID) {
        state.setEmployeeID(employeeID);
    }

    public void setState(int state) {
        this.state.setStateID(state);
    }

    public void setVehicleID(String vehicleID) {
        state.setVehicleID(vehicleID);
    }

    protected HashMap<DeviceProps, String> theirsToOurs(HashMap<?, ?> reply) {
        HashMap<DeviceProps, String> map = new HashMap<DeviceProps, String>();
        Iterator<?> itr = reply.keySet().iterator();
        while (itr.hasNext()) {
            Integer next = (Integer) itr.next();
            String value = reply.get(next).toString();
            map.put(DeviceProps.valueOf(next), (String) value);
        }
        return null;
    }

    @Override
    protected WaysmartDevice was_speeding() {
        return this;
    }


}