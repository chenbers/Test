package com.inthinc.device.devices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.enums.DeviceForwardCommands;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.emulation.enums.MapSection;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.NoteManager;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.AutomationDeviceEvents.SpeedingEvent;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.SHA1Checksum;
import com.inthinc.pro.automation.utils.StackToString;

public abstract class DeviceBase {

    protected String lastDownload;
    protected MCMProxyObject mcmProxy;
    protected int note_count = 4;
    protected final NoteManager notes;
    protected Addresses portal;
    protected Object reply;
    
    protected Map<Integer, MapSection> sbsModule;

    protected final ArrayList<GeoPoint> speed_loc;
    protected final ArrayList<Integer> speed_points;

    protected DeviceState state;
    protected final TripTracker tripTracker;

    public DeviceBase(String IMEI, Addresses server,
            Map<DeviceProps, String> map, ProductType version) {
        this(IMEI, version, map);
        MasterTest.print(server, Level.DEBUG);
        portal = server;
        initiate_device();
    }

    private DeviceBase(String IMEI, ProductType version,
            Map<DeviceProps, String> settings) {
        state = new DeviceState(IMEI, version);
        state.setSettings(settings);
        tripTracker = new TripTracker(state);
        sbsModule = new HashMap<Integer, MapSection>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<GeoPoint>();
        notes = new NoteManager();
        state.setMapRev(0);
    }

    private DeviceBase ackFwdCmds(List<HashMap<String, Object>> reply) {

        HashMap<String, Object> fwd = new HashMap<String, Object>();

        if (!reply.isEmpty()) {
            Iterator<HashMap<String, Object>> itr = reply.iterator();
            while (itr.hasNext()) {
                fwd = itr.next();
                // if (fwd.get("fwdID").equals(100)||fwd.get("fwdID").equals(1))
                // continue;
                fwd.put("cmd",
                        DeviceForwardCommands.valueOf((Integer) fwd.get("cmd")));
                createAckNote(fwd);
            }
        }
        return this;
    }

    protected abstract void ackFwdCmds(String[] reply);


    public DeviceBase addEvent(AutomationDeviceEvents event){
    	addNote(event.getNote());
    	return this;
    }

    public DeviceBase addLocation(){
        addEvent(AutomationDeviceEvents.location(state, tripTracker.currentLocation()));
        return this;
    }
    
    public DeviceBase addNote(DeviceNote note) {
        notes.addNote(note);
        checkQueue();
        return this;
    }


	protected DeviceBase checkQueue() {
        if (notes.size() >= get_note_count()) {
            send_note();
        }
        return this;
    }


    private DeviceBase configurate_device() {
        if (portal == Addresses.TEEN_PROD) {
            return this;
        }
        dump_settings();
        get_changes();
        return this;
    }

    protected DeviceBase constructNote(DeviceNoteTypes type) {
        return addNote(DeviceNote.constructNote(type, tripTracker.currentLocation(), state));
    }

    protected abstract DeviceBase createAckNote(Map<String, Object> reply);

    public DeviceBase dump_settings() {
		if (portal == Addresses.TEEN_PROD) {
			return this;
		}

		if (state.getWMP() >= 17013) {
			reply = null;
			try {
				MasterTest.print("dumping settings", Level.DEBUG);
				reply = mcmProxy.dumpSet(state,	oursToThiers());
				MasterTest.print(reply, Level.DEBUG);
			} catch (Exception e) {
				MasterTest.print(
						"Error from DumpSet: " + StackToString.toString(e),
						Level.ERROR);
			}
		}
        return this;
    }

    public void firstLocation(GeoPoint geoPoint) {
        tripTracker.addLocation(geoPoint);
    }

    public void flushNotes() {
        while (notes.hasNext()) {
            send_note();
        }
    }

    protected DeviceBase get_changes() {
		if (portal == Addresses.TEEN_PROD) {
			return this;
		}
		if (state.getWMP() >= 17013) {
			try {
				reply = mcmProxy.reqSet(state);
			} catch (Exception e) {
				MasterTest.print(e, Level.ERROR);
			}
			if (reply instanceof HashMap<?, ?>) {
				set_settings(theirsToOurs((HashMap<?, ?>) reply));
			}
		}
        return this;
    }

    protected abstract Integer get_note_count();

    public String get_setting(DeviceProps propertyID) {
        return state.getSettings().get(propertyID);
    }

    public boolean getAudioFile(String fileName, int fileVersion, Locales locale) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hardwareVersion", state.getWMP());
        map.put("fileVersion", fileVersion);
        map.put("locale", locale.toString());
        map.put("productVersion", state.getProductVersion().getIndex());
        return writeTiwiFile(fileName,
                mcmProxy.audioUpdate(state.getImei(), map));
    }

    public GeoPoint getCurrentLocation() {
		return tripTracker.currentLocation();
	}

    public NoteManager getNotes() {
        return notes;
    }

    public int getOdometer() {
        return state.getOdometerX100();
    }

    public DeviceState getState() {
        return state;
    }

    public TripTracker getTripTracker() {
        return tripTracker;
    }

    public DeviceBase goToNextLocation(int value, boolean time) {
        tripTracker.getNextLocation(value, time);
        addLocation();
        is_speeding();
        return this;
    }

    public DeviceBase increment_time(Integer increment) {
        state.incrementTime(increment);
        return this;
    }

    private DeviceBase initiate_device() {
        state.setIgnition_state(false);
        state.setSats(8);
        state.setWMP(17014);
        state.setMSP(50);
        state.setSpeed(0);

        state.setIgnition_state(false);
        state.setPower_state(false);
        state.setSpeeding(false);
        state.setExceedingRPMLimit(false);
        state.setSeatbeltEngaged(false);

        // clear_internal_settings();

        set_server(portal);

        return this;
    }
    
    

    private DeviceBase is_speeding() {
        GeoPoint point = tripTracker.currentLocation();
        if (state.getSpeed() > state.getSpeedLimit() && !state.isSpeeding()) {
            state.setSpeeding(true);
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            state.setTopSpeed(state.getSpeed());
            state.getSpeedingStartTime().setDate(state.getTime());
            state.setSpeedingSpeedLimit(state.getSpeedLimit().intValue());
            MasterTest.print("Started Speeding at: " + tripTracker.currentLocation(), Level.DEBUG);
            
        } else if (state.getSpeed() > state.getSpeedLimit()
                && state.isSpeeding()) {
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            if (state.getSpeed() > state.getTopSpeed()){
            	state.setTopSpeed(state.getSpeed());
            }
            MasterTest.print("Still Speeding at: " + tripTracker.currentLocation(), Level.DEBUG);
            
        } else if (state.getSpeed() < state.getSpeedLimit()
                && state.isSpeeding()) {
        	state.getSpeedingStopTime().setDate(state.getTime());
            state.setSpeeding(false);
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            MasterTest.print("Stopped Speeding at: " + tripTracker.currentLocation(), Level.DEBUG);
            
            was_speeding();
        }
        return this;
    }

    public DeviceBase last_location(GeoPoint last, int value) {
        return last_location(last, value, true);
    }

    public DeviceBase last_location(GeoPoint last, int value, boolean time) {
        update_location(last, value, time);
        update_location(last, 0, false);
        return this;
    }

    protected Map<Integer, String> oursToThiers() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        Iterator<?> itr = state.getSettings().keySet().iterator();
        while (itr.hasNext()) {
            DeviceProps next = (DeviceProps) itr.next();
            map.put(next.getIndex(), state.getSettings().get(next));
        }

        return map;
    }

    public DeviceBase power_off_device(Integer time_delta) {
        if (state.getPower_state()) {
            increment_time(time_delta);
            set_power();
        } else {
        	MasterTest.print("The device is already off.");
        }
        MasterTest.print("Power Off note created at: "
                + state.getTime().epochSecondsInt(), Level.INFO);
        return this;
    }

    public DeviceBase power_on_device() {
        power_on_device(state.getTime());
        return this;
    }

    public DeviceBase power_on_device(AutomationCalendar time_now) {
        if (!state.getPower_state()) {
            set_time(time_now);
            set_power();
            configurate_device();
            time_now.addToSeconds(30);
            set_time(time_now);
        } else {
        	MasterTest.print("The device is already on.");
        }
        return this;
    }

    protected abstract Integer processCommand(Map<String, Object> reply);

    @SuppressWarnings("unchecked")
    protected DeviceBase send_note() {
        while (notes.hasNext()) {
            Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue = notes
                    .getNotes(note_count);
            while (!sendingQueue.isEmpty()){
                reply = mcmProxy.sendNotes(state, sendingQueue);
                if (reply instanceof ArrayList<?>) {
                    ackFwdCmds((List<HashMap<String, Object>>) reply);
                } else if (reply instanceof String[]) {
                    ackFwdCmds((String[]) reply);
                } else if (reply != null) {
                	MasterTest.print("Reply from Server: " + reply, Level.DEBUG);
                }
            }
        }
        return this;
    }

    protected abstract DeviceBase set_ignition(Integer time_delta);//

    public DeviceBase set_MSP(Integer version) {
        state.setMSP(version);
        return this;
    }

    public DeviceBase set_MSP(Object version) {
        set_MSP((Integer) version);
        return this;
    }

    protected abstract DeviceBase set_power();

    public DeviceBase set_satelites(Integer satelites) {
        state.setSats(satelites);
        return this;
    }

    protected abstract DeviceBase set_server(Addresses server);

    public DeviceBase set_settings(DeviceProps key, Integer value) {
        return set_settings(key, value.toString());
    }

    public DeviceBase set_settings(DeviceProps key, String value) {
        Map<DeviceProps, String> change = new HashMap<DeviceProps, String>();
        change.put(key, value);
        set_settings(change);
        if (key.equals(DeviceProps.SPEED_LIMIT) || key.equals(DeviceProps.TIWI_SPEED_LIMIT)){
        	state.setSpeedLimit(((Double)Double.parseDouble(value)).intValue());
        }
        return this;
    }

    public DeviceBase set_settings(Map<DeviceProps, String> changes) {

        Iterator<DeviceProps> itr = changes.keySet().iterator();
        while (itr.hasNext()) {
            DeviceProps next = itr.next();
            state.setSetting(next, changes.get(next));
        }
        dump_settings();
        return this;
    }

    public abstract DeviceBase set_speed_limit(Integer limit);

    public DeviceBase set_time(AutomationCalendar time_now) {
        state.getTime_last().setDate(state.getTime());
        state.getTime().setDate(time_now);
        MasterTest.print("Time = " + time_now, Level.DEBUG);
        return this;
    }


    protected abstract HashMap<DeviceProps, String> theirsToOurs(
            HashMap<?, ?> reply);

    public DeviceBase turn_key_off(Integer time_delta) {
        if (state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
        	MasterTest.print("Vehicle was already turned off");
        }
        return this;
    }

    public DeviceBase turn_key_on(Integer time_delta) {
        if (!state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
        	MasterTest.print("Vehicle was already turned on");
        }
        return this;
    }

    public DeviceBase update_location(GeoPoint geoPoint, int i) {
        return update_location(geoPoint, i, true);
    }

    public DeviceBase update_location(GeoPoint next, int value, boolean time) {
        tripTracker.setNextLocation(next, value, time);
        addLocation();
        is_speeding();
        return this;
    }

    protected DeviceBase was_speeding() {
        Integer avgSpeed = 0;
        Double avg = 0.0;
        Double speeding_distance = 0.0;
        for (int i = 0; i < speed_points.size(); i++) {
            int speed = speed_points.get(i);
            avg += speed;
        }

        avg = avg / (speed_points.size());
        avgSpeed = avg.intValue();
        for (int i = 1; i < speed_loc.size(); i++) {
            GeoPoint last = speed_loc.get(i - 1);
            GeoPoint loc = speed_loc.get(i);
            speeding_distance += Math.abs(GeoPoint.Distance_Calc
                    .calc_distance(last, loc));
        }
        
        
        state.setSpeedingDistanceX100((int) (speeding_distance * 100));
        state.setAvgSpeed(avgSpeed);
        
        SpeedingEvent event = AutomationDeviceEvents.speeding(state, tripTracker.currentLocation());
        
        addEvent(event);
        return this;
    }

	protected boolean writeTiwiFile(String fileName, Map<String, Object> reply) {
		MasterTest.print(reply, Level.DEBUG);
        if (!fileName.startsWith("target")) {
            String resourceFile = "target/test/resources/" + state.getImei()
                    + "/" + "downloads/";
            lastDownload = resourceFile + fileName;
        } else {
            lastDownload = fileName;
        }
        try {
            File destination = new File(lastDownload);
            destination.getParentFile().mkdirs();
            destination.createNewFile();
            destination.deleteOnExit();
            FileOutputStream fw = new FileOutputStream(destination);
            fw.write((byte[]) reply.get("f"));
            fw.close();
            MasterTest.print(
                    "SHA1 Hash is: "
                            + SHA1Checksum.getSHA1Checksum(lastDownload),
                    Level.DEBUG);
            return true;
        } catch (FileNotFoundException e) {
        	MasterTest.print(e, Level.ERROR);
        } catch (IOException e) {
        	MasterTest.print(e, Level.ERROR);
        } catch (Exception e) {
        	MasterTest.print(e, Level.ERROR);
        }
        return false;
    }


}
