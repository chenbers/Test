package com.inthinc.pro.automation.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.Package_Waysmart_Note;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.models.AutomationBridgeFwdCmdParser;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.WaysmartClasses.MultiForwardCmd;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.model.configurator.ProductType;

public class WaysmartDevice extends DeviceBase {
	
	public static enum Direction implements IndexEnum{
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
        setState(RandomValues.newOne().getStateByName("Utah",
                ProductType.WAYSMART));
    }

    public WaysmartDevice(String IMEI, String MCM, Direction comMethod) {
        this(IMEI, MCM, Addresses.QA, comMethod);
    }

    @Override
    protected void ackFwdCmds(String[] reply) {
        List<MultiForwardCmd> commands = new ArrayList<MultiForwardCmd>();
        for (int i = 0; i < reply.length; i++) {
            commands.addAll(AutomationBridgeFwdCmdParser.processCommands(
                    reply[i]));
        }
    }

    /**
     * NEWDRIVER_HOSRULE(116, DeviceAttrs.DRIVER_STR, DeviceAttrs.MCM_RULESET),
     **/
//    public WaysmartDevice addHosStateChange(String driverStr, HOSStatus status) {
//        state.setEmployeeID(driverStr);
//        DeviceNote note = super.constructNote(DeviceNoteTypes.NEWDRIVER_HOSRULE);
//        note.addAttr(DeviceAttrs.DRIVER_STR, driverStr);
//        note.addAttr(DeviceAttrs.MCM_RULESET, status);
//        addNote(note);
//        return this;
//    }



    public WaysmartDevice addInstallEvent() {
        addEvent(AutomationDeviceEvents.install(state, tripTracker.currentLocation()));
        return this;
    }


    protected Package_Waysmart_Note construct_note(DeviceNoteTypes type) {
        return new Package_Waysmart_Note(type, state.getWaysDirection(),
                portal, state.getMcmID(), state.getImei());
    }


    @Override
    protected WaysmartDevice createAckNote(Map<String, Object> reply) {
        return this;
    }

    @Override
    protected Integer get_note_count() {
        return note_count;
    }

    public WaysmartDevice logInDriver(String employeeID) {
        state.setEmployeeID(employeeID);
        return this;
    }

    public WaysmartDevice logInOccupant(String occupantsDriverID) {
        Package_Waysmart_Note note = construct_note(DeviceNoteTypes.A_AND_D_SPACE___HOS_CHANGE_STATE_NO_GPS_LOCK);
        note.setDriverID(occupantsDriverID);
        note.setHosStatus(HOSStatus.ON_DUTY_OCCUPANT);
        MasterTest.print(sendNote(note));
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

    private WaysmartDevice sendNote(Package_Waysmart_Note note) {
        GeoPoint current = tripTracker.currentLocation();
        note.setLatitude(current.getLat());
        note.setLongitude(current.getLng());
        note.setOdometer(state.getOdometerX100());
        note.setTime(state.getTime());
        MasterTest.print(note.sendNote());
        return this;
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

        Package_Waysmart_Note note;
        if (state.getPower_state()) {
            note = construct_note(DeviceNoteTypes.POWER_ON);
        } else {
            note = construct_note(DeviceNoteTypes.LOW_POWER_MODE);
        }
        MasterTest.print(sendNote(note));
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
