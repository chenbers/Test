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

import com.inthinc.device.emulation.enums.DeviceForwardCommands;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.NoteManager;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.SHA1Checksum;
import com.inthinc.sbs.Sbs;

public abstract class DeviceBase {

    protected String lastDownload;

    protected MCMProxyObject mcmProxy;
    protected int note_count = 4;
    protected final NoteManager notes;
    protected final AutoServers server = new AutoServers();
    protected Object reply;
    protected final static Sbs sbs = new Sbs("555555555555555", 7);
    

    protected final ArrayList<GeoPoint> speed_loc;
    protected final ArrayList<Integer> speed_points;

    protected final DeviceState state;
    protected final TripTracker tripTracker;
    

    public DeviceBase(String IMEI, ProductType version,
            Map<DeviceProps, String> settings, AutoSilos silo) {
    	this(new DeviceState(IMEI, version), silo);
        
    	state.setSettings(settings);
        initiate_device();
    }

    public DeviceBase(DeviceState state, AutoSilos silo) {
    	this.state = state;
    	set_server(silo);
    	sbs.setDownloadManager(server);
        tripTracker = new TripTracker(state);
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<GeoPoint>();
        notes = new NoteManager();
        state.setMapRev(0);
        initiate_device();
	}

	private DeviceBase ackFwdCmds(List<Map<String, Object>> reply) {

    	Map<String, Object> fwd = new HashMap<String, Object>();

        if (!reply.isEmpty()) {
            Iterator<Map<String, Object>> itr = reply.iterator();
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
        if (server.getSilo().equals(AutoSilos.TEEN_PROD.name().toLowerCase())) {
            return this;
        }
        dump_settings();
        requestSettings();
        return this;
    }

    protected DeviceBase constructNote(DeviceNoteTypes type) {
        return addNote(DeviceNote.constructNote(type, tripTracker.currentLocation(), state));
    }

    protected abstract DeviceBase createAckNote(Map<String, Object> reply);

    public DeviceBase dump_settings() {
    	if (server.getSilo().equals(AutoSilos.TEEN_PROD.name().toLowerCase())) {
			return this;
		}

		if (state.getWMP() >= 17013) {
			reply = null;
			try {
				Log.debug("dumping settings");
				reply = mcmProxy.dumpSet(state,	oursToThiers());
				Log.debug(reply);
			} catch (Exception e) {
				Log.error("Error from DumpSet: %s", e);
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

    public String getLastDownload() {
        return lastDownload;
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
    
    public void idle(int lowIdle, int highIdle) {
        state.setHighIdle(highIdle).setLowIdle(lowIdle);
        increment_time(lowIdle + highIdle);
        AutomationDeviceEvents.idling(this);
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
            Log.debug("Started Speeding at: " + tripTracker.currentLocation());
            
        } else if (state.getSpeed() > state.getSpeedLimit()
                && state.isSpeeding()) {
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            if (state.getSpeed() > state.getTopSpeed()){
            	state.setTopSpeed(state.getSpeed());
            }
            Log.debug("Still Speeding at: " + tripTracker.currentLocation());
            
        } else if (state.getSpeed() < state.getSpeedLimit()
                && state.isSpeeding()) {
        	state.getSpeedingStopTime().setDate(state.getTime());
            state.setSpeeding(false);
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            Log.debug("Stopped Speeding at: " + tripTracker.currentLocation());
            
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
            flushNotes();
        } else {
        	Log.info("The device is already off.");
        }
        Log.info("Power Off note created at: "
                + state.getTime().toInt() + " " + state.getTime());
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
        	Log.info("The device is already on.");
        }
        return this;
    }

    protected abstract Integer processCommand(Map<String, Object> reply);

    public DeviceBase requestSettings() {
    	if (server.getSilo().equals(AutoSilos.TEEN_PROD.name().toLowerCase())) {
			return this;
		}
		if (state.getWMP() >= 17013) {
			try {
				reply = mcmProxy.reqSet(state);
			} catch (HessianException e){
				reply = null;
			} catch (Exception e) {
				Log.error(e);
			}
			if (reply instanceof HashMap<?, ?>) {
				set_settings(theirsToOurs((HashMap<?, ?>) reply));
			}
		}
        return this;
    }

    @SuppressWarnings("unchecked")
    protected DeviceBase send_note() {
        while (notes.hasNext()) {
            Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue = notes
                    .getNotes(note_count);
        	Object[] replies = mcmProxy.sendNotes(state, sendingQueue);
            for (Object reply : replies){
                if (reply instanceof ArrayList<?>) {
                    ackFwdCmds((List<Map<String, Object>>) reply);
                } else if (reply instanceof String[]) {
                    ackFwdCmds((String[]) reply);
                } else if (reply != null) {
                	Log.debug("Reply from Server: " + reply);
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

    public abstract DeviceBase set_server(AutoSilos server);

    public DeviceBase set_settings(DeviceProps key, Integer value) {
        return set_settings(key, value.toString());
    }

    public DeviceBase set_settings(DeviceProps key, String value) {
        Map<DeviceProps, String> change = new HashMap<DeviceProps, String>();
        change.put(key, value);
        set_settings(change);
        if (key.equals(DeviceProps.SPEED_LIMIT_W) || key.equals(DeviceProps.SPEED_LIMIT_T)){
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
        Log.debug("Time = " + time_now);
        return this;
    }


    protected abstract HashMap<DeviceProps, String> theirsToOurs(
            HashMap<?, ?> reply);

    public DeviceBase turn_key_off(Integer time_delta) {
        if (state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
        	Log.info("Vehicle was already turned off");
        }
        return this;
    }

    public DeviceBase turn_key_on(Integer time_delta) {
        if (!state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
        	Log.info("Vehicle was already turned on");
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
        speed_loc.clear();
        speed_points.clear();
        
        state.setSpeedingDistanceX100((int) (speeding_distance * 100));
        
        state.setAvgSpeed(avgSpeed);

        AutomationDeviceEvents.speeding(this);
        return this;
    }

	protected boolean writeTiwiFile(String fileName, Map<String, Object> reply) {
		Log.debug(reply);
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
            Log.debug(
                    "SHA1 Hash is: "
                            + SHA1Checksum.getSHA1Checksum(lastDownload));
            return true;
        } catch (FileNotFoundException e) {
        	Log.error(e);
        } catch (IOException e) {
            Log.error(e);
        } catch (Exception e) {
            Log.error(e);
        }
        return false;
    }

    public DeviceBase unBuckleSeatbelt() {
        state.setSeatbeltEngaged(false);
        return this;
    }
    
    public DeviceBase buckleSeatbelt(){
        state.setSeatbeltEngaged(true);
        if (state.getSeatbeltDistanceX100()!=0) {
        	AutomationDeviceEvents.seatbelt(this);
        	state.setSeatbeltDistanceX100(0);
        }
        return this;
    }



}
