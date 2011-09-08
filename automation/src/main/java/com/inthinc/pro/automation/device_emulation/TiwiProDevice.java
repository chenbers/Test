package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.TiwiAttrs;
import com.inthinc.pro.automation.deviceEnums.TiwiFwdCmds;
import com.inthinc.pro.automation.deviceEnums.TiwiNoteTypes;
import com.inthinc.pro.automation.deviceEnums.TiwiProps;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.FwdCmdStatus;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.ViolationFlags;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.DeviceProperties;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.model.configurator.ProductType;

public class TiwiProDevice extends Base {

    private final static Logger logger = Logger.getLogger(TiwiProDevice.class);

    private final static ProductType productVersion = ProductType.TIWIPRO_R74;
    private HashMap<TiwiAttrs, Integer> attrs;
    private Long trip_start, trip_stop;
    private AutomationHessianFactory getHessian;

    public TiwiProDevice(String IMEI) {
        this(IMEI, Addresses.QA);
    }

    public TiwiProDevice(String IMEI, Addresses server) {
        this(IMEI, server, TiwiProps.STATIC.getDefaultProps());
    }

    public TiwiProDevice(String IMEI, Addresses server, Map<TiwiProps, String> map) {
        super(IMEI, server, map, productVersion);
    }

    @Override
    public TiwiProDevice add_location() {
        timeSinceLastLoc = 0;
        attrs = new HashMap<TiwiAttrs, Integer>();
        if (speeding) {
            attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SPEEDING.getValue());
        }

        else if (rpm_violation) {
            attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_RPM.getValue());
        }

        else if (seatbelt_violation) {
            attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SEATBELT.getValue());
        }

        construct_note(TiwiNoteTypes.NOTE_TYPE_LOCATION, attrs);
        return this;
    }

    public TiwiProDevice add_lowBattery() {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 0);
        construct_note(TiwiNoteTypes.NOTE_TYPE_LOW_BATTERY, attrs);
        return this;
    }

    public TiwiProDevice add_noDriver() {
        construct_note(TiwiNoteTypes.NOTE_TYPE_NO_DRIVER);
        return this;
    }

    protected TiwiProDevice add_note(NoteBuilder note) {
        note_queue.offer(note.Package());
        check_queue();
        return this;
    }

    public TiwiProDevice add_note_event(Integer deltaX, Integer deltaY, Integer deltaZ) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_DELTA_VX, deltaX);
        attrs.put(TiwiAttrs.ATTR_TYPE_DELTA_VY, deltaY);
        attrs.put(TiwiAttrs.ATTR_TYPE_DELTA_VZ, deltaZ);
        construct_note(TiwiNoteTypes.NOTE_TYPE_NOTE_EVENT, attrs);
        return this;
    }

    public TiwiProDevice add_seatBelt(Integer topSpeed, Integer avgSpeed, Integer distance) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_AVG_RPM, 500);
        attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, 2);
        attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED, 50);
        attrs.put(TiwiAttrs.ATTR_TYPE_TOP_SPEED, topSpeed);
        attrs.put(TiwiAttrs.ATTR_TYPE_AVG_SPEED, avgSpeed);
        attrs.put(TiwiAttrs.ATTR_TYPE_DISTANCE, distance);
        construct_note(TiwiNoteTypes.NOTE_TYPE_SEATBELT, attrs);

        return this;
    }

    public TiwiProDevice add_stats() {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_BASE_VER, 0);
        attrs.put(TiwiAttrs.ATTR_TYPE_EMU_HASH_1, -1517168504);
        attrs.put(TiwiAttrs.ATTR_TYPE_EMU_HASH_2, 154129909);
        attrs.put(TiwiAttrs.ATTR_TYPE_EMU_HASH_3, 1825195881);
        attrs.put(TiwiAttrs.ATTR_TYPE_EMU_HASH_4, 1627500918);
        attrs.put(TiwiAttrs.ATTR_TOTAL_AGPS_BYTES, 60000);
        construct_note(TiwiNoteTypes.NOTE_TYPE_STATS, attrs);
        return this;
    }

    public TiwiProDevice addIdlingNote(int lowIdleTime, int highIdleTime) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_LOW_IDLE, lowIdleTime);
        attrs.put(TiwiAttrs.ATTR_TYPE_HIGH_IDLE, highIdleTime);

        construct_note(TiwiNoteTypes.NOTE_TYPE_IDLING, attrs);
        time += lowIdleTime + highIdleTime;
        time_last = time;
        return this;
    }

    public void addIgnitionOffNote(int tripDuration, int percentPointsPassedFilter) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_TRIP_DURATION, tripDuration);
        attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, percentPointsPassedFilter);
        construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_OFF, attrs);
    }

    public void addIgnitionOnNote() {
        construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_ON);
    }

    public void addPowerOffNote(int lowPowerModeSeconds) {
        attrs.put(TiwiAttrs.ATTR_TYPE_LOW_POWER_MODE_TIMEOUT, lowPowerModeSeconds);
        construct_note(TiwiNoteTypes.NOTE_TYPE_LOW_POWER_MODE, attrs);
        flushNotes();
    }

    public TiwiProDevice addPowerOnNote(int WMP, int MSP, int gpsLockTime) {
        attrs.put(TiwiAttrs.ATTR_TYPE_FIRMWARE_VERSION, WMP);
        attrs.put(TiwiAttrs.ATTR_TYPE_DMM_VERSION, MSP);
        attrs.put(TiwiAttrs.ATTR_TYPE_GPS_LOCK_TIME, gpsLockTime);
        construct_note(TiwiNoteTypes.NOTE_TYPE_POWER_ON, attrs);
        return this;
    }

    public TiwiProDevice addSpeedingNote(Integer distance, Integer topSpeed, Integer avgSpeed) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_DISTANCE, distance);
        attrs.put(TiwiAttrs.ATTR_TYPE_TOP_SPEED, topSpeed);
        attrs.put(TiwiAttrs.ATTR_TYPE_AVG_SPEED, avgSpeed);
        attrs.put(TiwiAttrs.ATTR_TYPE_SPEED_ID, 9999);
        attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, 1);

        construct_note(TiwiNoteTypes.NOTE_TYPE_SPEEDING_EX3, attrs);
        return this;
    }

    public void addTamperingNote(int percentPassedFilter) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, percentPassedFilter);
        attrs.put(TiwiAttrs.ATTR_TYPE_BACKUP_BATTERY, 6748);

        construct_note(TiwiNoteTypes.NOTE_TYPE_UNPLUGGED, attrs);
    }

    @Override
    public TiwiProDevice construct_note() {
        return this;
    }

    public TiwiProDevice construct_note(TiwiNoteTypes type) {
        if (productVersion == ProductType.TIWIPRO_R74) {
            attrs = new HashMap<TiwiAttrs, Integer>();
            construct_note(type, attrs);
        }
        check_queue();
        return this;
    }

    public TiwiProDevice construct_note(TiwiNoteTypes type, HashMap<TiwiAttrs, Integer> attrs) {
        try {
            attrs.put(TiwiAttrs.ATTR_TYPE_SPEED_LIMIT, speed_limit.intValue());
        } catch (Exception e) {
            logger.debug(StackToString.toString(e));
            e.printStackTrace();
        }
        Package_tiwiPro_Note note = new Package_tiwiPro_Note(type, time, sats, heading, 1, latitude, longitude, speed, odometer, attrs);
        logger.debug(note.toString());
        clear_internal_settings();
        add_note(note);
        return this;
    }

    @Override
    public TiwiProDevice createAckNote(Map<String, Object> reply) {
        Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
        ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_ID, (Integer) reply.get("fwdID"));
        ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_RECEIVED);
        byte[] packaged = ackNote.Package();
        note_queue.add(packaged);
        processCommand(reply);
        return this;
    }

    public TiwiProDevice enter_zone(Integer zoneID) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_ZONE_ID, zoneID);
        construct_note(TiwiNoteTypes.NOTE_TYPE_WSZONES_ARRIVAL_EX, attrs);
        return this;
    }

    public void flushNotes() {
        if (!note_queue.isEmpty())
            send_note();
    }

    @Override
    protected Integer get_note_count() {
        return Integer.parseInt(Settings.get(TiwiProps.PROPERTY_SET_MSGS_PER_NOTIFICATION));
    }

    public String get_setting(TiwiProps settingID) {
        return Settings.get(settingID.getValue());
    }

    public Integer get_setting_int(TiwiProps settingID) {
        Double valueD = Double.parseDouble(Settings.get(settingID));
        Integer valueI = valueD.intValue();
        return valueI;
    }

    public TiwiProDevice leave_zone(Integer zoneID) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_TYPE_ZONE_ID, zoneID);
        construct_note(TiwiNoteTypes.NOTE_TYPE_WSZONES_DEPARTURE_EX, attrs);
        return this;
    }

    public TiwiProDevice logout_driver(Integer RFID, Integer tripQuality, Integer MPG, Integer MPGOdometer) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.ATTR_LOGOUT_TYPE, 4);
        attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, tripQuality);
        attrs.put(TiwiAttrs.ATTR_TYPE_MPG, MPG);
        attrs.put(TiwiAttrs.ATTR_TYPE_MPG_DISTANCE, MPGOdometer);
        attrs.put(TiwiAttrs.ATTR_TYPE_RFID0, -536362939);
        attrs.put(TiwiAttrs.ATTR_TYPE_RFID1, 1415806888);
        construct_note(TiwiNoteTypes.NOTE_TYPE_STATS, attrs);
        return this;
    }

    public TiwiProDevice nonTripNote(long time, int sats, int heading, Double latitude, Double longitude, int speed, int odometer) {
        this.time = time;
        this.sats = sats;
        this.heading = heading;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.odometer = odometer;
        return this;
    }

    @Override
    protected Integer processCommand(Map<String, Object> reply) {
        logger.debug(reply);
        if (reply.get("fwdCmd") == null)
            return 1;
        TiwiFwdCmds fwdCmd = TiwiFwdCmds.valueOf(reply.get("fwdCmd"));
        HashMap<TiwiProps, String> changes = new HashMap<TiwiProps, String>();
        Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);

        if (fwdCmd == TiwiFwdCmds.FWD_CMD_ASSIGN_DRIVER) {
            String[] values = reply.get("fwdData").toString().split(" ");
            setDeviceDriverID(Integer.parseInt(values[0]));
        } else if (fwdCmd == TiwiFwdCmds.FWD_CMD_DUMP_CONFIGURATION)
            dump_settings();
        else if (fwdCmd == TiwiFwdCmds.FWD_CMD_UPDATE_CONFIGURATION)
            get_changes();
        else if (fwdCmd == TiwiFwdCmds.FWD_CMD_DOWNLOAD_NEW_WITNESSII_FIRMWARE)
            set_MSP(reply.get("fwdData"));
        else if (fwdCmd == TiwiFwdCmds.FWD_CMD_DOWNLOAD_NEW_FIRMWARE)
            set_WMP(reply.get("fwdData"));
        else if (fwdCmd == TiwiFwdCmds.FWD_CMD_SET_SPEED_BUFFER_VALUES) {
            changes.put(TiwiProps.PROPERTY_VARIABLE_SPEED_LIMITS, reply.get("fwdData").toString());
        }

        ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_ID, reply.get("fwdId"));
        ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
        byte[] packaged = ackNote.Package();
        note_queue.add(packaged);

        if (!changes.isEmpty())
            set_settings(changes);
        return 1;
    }

    protected TiwiProDevice set_ignition(Integer time_delta) {
        ignition_state = !ignition_state;
        Long newTime = (Long) (time + time_delta);
        set_time(newTime);
        if (ignition_state) {
            construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_ON);
            trip_start = newTime;
        } else if (!ignition_state) {
            trip_stop = newTime;
            Long tripTime = trip_stop - trip_start;
            addIgnitionOffNote(tripTime.intValue(), 980);
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_power() {

        attrs = new HashMap<TiwiAttrs, Integer>();

        power_state = !power_state; // Change the power state between on and off
        if (power_state) {
            addPowerOnNote(WMP, MSP, 10);

        } else if (!power_state) {
            addPowerOffNote(get_setting_int(TiwiProps.PROPERTY_LOW_POWER_MODE_SECONDS));

            check_queue();
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_server(Addresses server) {
        getHessian = new AutomationHessianFactory();
        logger.info("MCM Server is " + server);
        mcmProxy = getHessian.getMcmProxy(server);
        Settings.put(TiwiProps.PROPERTY_SERVER_PORT, server.getMCMPort().toString());
        Settings.put(TiwiProps.PROPERTY_SERVER_URL, server.getMCMUrl());
        return this;
    }

    public TiwiProDevice set_settings(TiwiProps key, String value) {
        set_settings(key, value);
        return this;
    }

    @Override
    public TiwiProDevice set_speed_limit(Integer limit) {
        Settings.put(TiwiProps.PROPERTY_SPEED_LIMIT, limit.toString());
        return this;
    }

    public void setLogLevel(Level level) {
        logger.setLevel(level);
    }

    public TiwiProDevice tampering(Integer timeDelta) {
        power_state = false;
        ignition_state = false;
        increment_time(timeDelta);

        addTamperingNote(850);

        power_on_device(time);
        turn_key_on(10);
        return this;
    }

    protected HashMap<DeviceProperties, String> theirsToOurs(HashMap<?, ?> reply) {
        HashMap<TiwiProps, String> map = new HashMap<TiwiProps, String>();
        Iterator<?> itr = reply.keySet().iterator();
        while (itr.hasNext()) {
            Integer next = (Integer) itr.next();
            String value = reply.get(next).toString();
            map.put(TiwiProps.STATIC.valueOf(next), (String) value);
        }
        return null;
    }

    protected TiwiProDevice was_speeding() {
        Integer topSpeed = 0;
        Integer avgSpeed = 0;
        Double avg = 0.0;
        Double speeding_distance = 0.0;
        for (int i = 0; i < speed_points.size(); i++) {
            int speed = speed_points.get(i);
            avg += speed;
            if (topSpeed < speed) {
                topSpeed = speed;
            }
        }

        avg = avg / (speed_points.size());
        avgSpeed = avg.intValue();
        for (int i = 1; i < speed_loc.size(); i++) {
            Double[] last = speed_loc.get(i - 1);
            Double[] loc = speed_loc.get(i);
            speeding_distance += Math.abs(calculator.calc_distance(last[0], last[1], loc[0], loc[1]));
        }
        Integer distance = (int) (speeding_distance * 100);
        addSpeedingNote(distance, topSpeed, avgSpeed);
        return this;
    }

}