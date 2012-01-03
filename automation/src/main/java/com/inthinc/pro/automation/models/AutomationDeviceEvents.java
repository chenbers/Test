package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceEnums.ViolationFlags;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.objects.WaysmartDevice;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;

public class AutomationDeviceEvents {
    
    private final static String brake = "Hard Brake";
    private final static String accel = "Hard Accel";
    private final static String left = "Hard Turn Left";
    private final static String right = "Hard Turn Right";
    private final static String dip = "Hard Dip";
    private final static String bump = "Hard Bump";
    
    private final static AutomationDeviceEvents classes = new AutomationDeviceEvents();
    
    public interface AutomationEvents{
        public DeviceBase addEvent(DeviceBase device);
        public DeviceNote getNote(GeoPoint location, DeviceState state);
        public DeviceNoteTypes getNoteType();
    }
    
    public class NoteEvent implements AutomationEvents{
        
        public final int deltaX;
        public final int deltaY;
        public final int deltaZ;
        public final String type;
        public final DeviceNoteTypes noteType = DeviceNoteTypes.NOTE_EVENT;
        
        private NoteEvent(int deltaX, int deltaY, int deltaZ, String type){
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.deltaZ = deltaZ;
            this.type = type;
        }
        
        @Override
        public String toString(){
            return String.format("%s(DeltaX:%4d, DeltaY:%4d, DeltaZ:%4d)", type, deltaX, deltaY, deltaZ);
        }
        
        public long packDeltaVS(){
            Long packedDeltaV = ((-deltaX) + 600l) * 1464100l + // Turn deltaX negative because the DB expects the opposite from waysmarts
                             ((deltaY) + 600l) * 1210l +
                             ((deltaZ) + 600l);
            
            MasterTest.print(packedDeltaV, Level.DEBUG);
            return packedDeltaV;
        }

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addNoteEvent(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            if (state.getProductVersion().equals(ProductType.WAYSMART)) {
                note.addAttr(DeviceAttrs.DELTA_VS, packDeltaVS());

            } else {
                note.addAttr(DeviceAttrs.DELTAV_X, deltaX);
                note.addAttr(DeviceAttrs.DELTAV_Y, deltaY);
                note.addAttr(DeviceAttrs.DELTAV_Z, deltaZ);
            }
            MasterTest.print(note, Level.DEBUG);
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        } 
    }
    
    
    public static NoteEvent hardBrake(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(-Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), brake);
    }
    
    public static NoteEvent hardAccel(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), accel);
    }

    public static NoteEvent hardRight(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(Math.abs(deltaX), -Math.abs(deltaY), Math.abs(deltaZ), right);
    }
    
    public static NoteEvent hardLeft(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), left);
    }
    
    public static NoteEvent hardBump(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(Math.abs(deltaX), Math.abs(deltaY), -Math.abs(deltaZ), bump);
    }
    
    public static NoteEvent hardDip(int deltaX, int deltaY, int deltaZ){
        return classes.new NoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), dip);
    }
    
    
    public class SpeedingEvent implements AutomationEvents{
        public static final int FLAG = 1;
        public final int topSpeed;
        public final int distance;
        public final int maxRpm;
        public final int speedLimit;
        public final int avgSpeed;
        public final int avgRpm;
        public final DeviceNoteTypes noteType = DeviceNoteTypes.SPEEDING_EX3;
        public final DeviceNoteTypes noteWSType = DeviceNoteTypes.SPEEDING_EX4;
        
        private SpeedingEvent(int topSpeed, int distance, int maxRpm, int speedLimit, int avgSpeed, int avgRpm){
            this.topSpeed = Math.abs(topSpeed);
            this.distance = Math.abs(distance);
            this.maxRpm = Math.abs(maxRpm);
            this.speedLimit = Math.abs(speedLimit);
            this.avgSpeed = Math.abs(avgSpeed);
            this.avgRpm = Math.abs(avgRpm);
        }
        

        
        @Override
        public String toString(){
            return String.format("TopSpeed: %d, Distance: %d, MaxRpm: %d, SpeedLimit: %d, AverageSpeed: %d, AverageRpm: %d",
                    topSpeed, distance, maxRpm, speedLimit, avgSpeed, avgRpm);
        }

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addSpeedingNote(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note;
            if (state.getProductVersion().equals(ProductType.WAYSMART)) {
            	note = DeviceNote.constructNote(noteWSType, location, state);
            	
            	note.addAttr(DeviceAttrs.TOP_SPEED, topSpeed);
            	note.addAttr(DeviceAttrs.DISTANCE, distance / 10);
            	note.addAttr(DeviceAttrs.MAX_RPM, maxRpm);
            	note.addAttr(DeviceAttrs.SPEED_LIMIT, speedLimit);
            	note.addAttr(DeviceAttrs.AVG_SPEED, avgSpeed);
            	note.addAttr(DeviceAttrs.AVG_RPM, avgRpm);
            	
            	note.addAttr(DeviceAttrs.SBS_LINK_ID, state.getSbsLinkID());
            	note.addAttr(DeviceAttrs.ZONE_ID, state.getZoneID());
            	note.addAttr(DeviceAttrs.SPEEDING_TYPE, 3);
            	note.addAttr(DeviceAttrs.SEATBELT_ENGAGED, state.isSeatbeltEngaged());
            	note.addAttr(DeviceAttrs.START_TIME, state.getSpeedingStartTime());
            	note.addAttr(DeviceAttrs.STOP_TIME, state.getSpeedingStopTime());
            	note.addAttr(DeviceAttrs.MAX_TIME, state.getSpeedingStopTime().getDelta(state.getSpeedingStartTime()));
            	note.addAttr(DeviceAttrs.COURSE, state.getCourse());
            	note.addAttr(DeviceAttrs.MAX_SPEED_LIMIT, state.getMaxSpeedLimit());
            	note.addAttr(DeviceAttrs.SBS_SPEED_LIMIT, state.getSbsSpeedLimit());
            	note.addAttr(DeviceAttrs.ZONE_SPEED_LIMIT, state.getZoneSpeedLimit());
            	note.addAttr(DeviceAttrs.WEATHER_SPEED_LIMIT_PERCENT, state.getWeatherSpeedLimitPercent());
            	note.addAttr(DeviceAttrs.SEVERE_SPEED_THRESHOLD, state.getSevereSpeedThreshold());
            	note.addAttr(DeviceAttrs.SPEEDING_BUFFER, state.getSpeedingBuffer());
            	note.addAttr(DeviceAttrs.SPEEDING_GRACE_PERIOD, state.getGracePeriod());
            	note.addAttr(DeviceAttrs.SEVERE_SPEED_SECONDS, state.getSeverSpeedSeconds());
            	note.addAttr(DeviceAttrs.SPEED_MODULE_ENABLED, state.isSpeedModuleEnabled());
            	note.addAttr(DeviceAttrs.SPEED_SOURCE, 1);

            } else {
            	note = DeviceNote.constructNote(noteType, location, state);
                note.addAttr(DeviceAttrs.DISTANCE, distance);
                note.addAttr(DeviceAttrs.TOP_SPEED, topSpeed);
                note.addAttr(DeviceAttrs.AVG_SPEED, avgSpeed);
                note.addAttr(DeviceAttrs.SPEED_ID, 9999);
                note.addAttr(DeviceAttrs.VIOLATION_FLAGS, SpeedingEvent.FLAG);
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
    }
    
    public static SpeedingEvent speeding(int topSpeed, int distance, int maxRpm, int speedLimit, int avgSpeed, int avgRpm){
        return classes.new SpeedingEvent(topSpeed, distance, maxRpm, speedLimit, avgSpeed, avgRpm);
    }
    
    public static SpeedingEvent speeding(int topSpeed, int distance, int maxRpm, Double speedLimit, int avgSpeed, int avgRpm){
        return speeding(topSpeed, distance, maxRpm, speedLimit.intValue(), avgSpeed, avgRpm);
    }
    

    public class SeatBeltEvent implements AutomationEvents {

        public final int avgRpm;
        public final static int violationFlag = 2;
        public final int gpsPercent;
        public final int topSpeed;
        public final int avgSpeed;
        public final int distance;
        public final int maxRpm;
        public final DeviceNoteTypes noteType = DeviceNoteTypes.SEATBELT;
        
        private SeatBeltEvent(int avgRpm, int gpsPercent, int topSpeed, int avgSpeed, int distance, int maxRpm){
            this.avgRpm = Math.abs(avgRpm);
            this.gpsPercent = Math.abs(gpsPercent);
            this.topSpeed = Math.abs(topSpeed);
            this.avgSpeed = Math.abs(avgSpeed);
            this.distance = Math.abs(distance);
            this.maxRpm = Math.abs(maxRpm);
        }

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addSeatbeltEvent(this);
        }
        
        @Override
        public String toString(){
            return String.format("AverageRpm: %d, GPS PercentUsed: %d, Top Speed: %d, Average Speed: %d, Distance: %d, MaxRPM: %d\n"
                    , avgRpm, gpsPercent, topSpeed, avgSpeed, distance, maxRpm);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            if (state.getProductVersion().equals(ProductType.WAYSMART)) {
                note.addAttr(DeviceAttrs.TOP_SPEED, topSpeed);
                note.addAttr(DeviceAttrs.DISTANCE, distance);
                note.addAttr(DeviceAttrs.MAX_RPM, maxRpm);

            } else {
                note.addAttr(DeviceAttrs.AVG_RPM, avgRpm);
                note.addAttr(DeviceAttrs.VIOLATION_FLAGS, violationFlag);
                
                note.addAttr(DeviceAttrs.PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED,
                        gpsPercent);
                
                note.addAttr(DeviceAttrs.TOP_SPEED, topSpeed);
                note.addAttr(DeviceAttrs.AVG_SPEED, avgSpeed);
                note.addAttr(DeviceAttrs.DISTANCE, distance);
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
        
    }
    
    public static SeatBeltEvent seatbelt(int avgRpm, int gpsPercent, int topSpeed, int avgSpeed, int distance, int maxRpm){
        return classes.new SeatBeltEvent(avgRpm, gpsPercent, topSpeed, avgSpeed, distance, maxRpm);
    }
    
    public class InstallEvent implements AutomationEvents {
        
        public final String vehicleIDStr;
        public final int acctID;
        public final String mcmIDStr;
        public final DeviceNoteTypes noteType = DeviceNoteTypes.INSTALL;
        
        
        private InstallEvent(String vehicleIDStr, String mcmIDStr, int acctID){
            this.vehicleIDStr = vehicleIDStr.toUpperCase();
            this.mcmIDStr = mcmIDStr;
            this.acctID = acctID;
        }
        

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return ((WaysmartDevice)device).addInstallEvent(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            note.addAttr(DeviceAttrs.VEHICLE_ID_STR, vehicleIDStr);
            note.addAttr(DeviceAttrs.MCM_ID_STR, mcmIDStr);
            note.addAttr(DeviceAttrs.COMPANY_ID, acctID);
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
    }
    
    public static InstallEvent install(String vehicleIDStr, String mcmIDStr, int acctID){
        return classes.new InstallEvent(vehicleIDStr, mcmIDStr, acctID);
    }
    
    public static InstallEvent install(DeviceState state){
    	return classes.new InstallEvent(state.getVehicleID(), state.getMcmID(), state.getAccountID());
    }
    
    
    public class LocationEvent implements AutomationEvents {
        
        public final ViolationFlags flag;
        public final DeviceNoteTypes noteType = DeviceNoteTypes.LOCATION;
        
        public LocationEvent(DeviceState state){
            if (state.isSpeeding()) {
                flag = ViolationFlags.VIOLATION_MASK_SPEEDING;
            } else if (state.isExceedingRPMLimit()) {
                flag = ViolationFlags.VIOLATION_MASK_RPM;
            } else if (state.isSeatbeltEngaged()) {
                flag = ViolationFlags.VIOLATION_MASK_SEATBELT;
            } else {
                flag = null;
            }
        }

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addLocation();
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            if (state.getProductVersion().equals(ProductType.WAYSMART)){
                
            } else {
            	if (flag != null){
            		note.addAttr(DeviceAttrs.VIOLATION_FLAGS,flag);
            	}
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
        
    }


    public static LocationEvent location(DeviceState state) {
        return classes.new LocationEvent(state);
    }
    
    public static IgnitionOn ignitionOn(){
        return classes.new IgnitionOn();
    }
    
    public class IgnitionOn implements AutomationEvents{
        
        private final DeviceNoteTypes noteType = DeviceNoteTypes.IGNITION_ON;

        private IgnitionOn(){        }

        
        
        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addIgnitionOnEvent(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            if (state.getProductVersion().equals(ProductType.WAYSMART)){
//                note.addAttr(DeviceAttrs.DRIVER_STR, employeeID);
//                note.addAttr(DeviceAttrs.DRIVER_ID, driverID);
            } else {
                
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
    }
    
    public class IgnitionOffEvent implements AutomationEvents{
        
        public final DeviceNoteTypes noteType = DeviceNoteTypes.IGNITION_OFF;
		private final int tripDuration;
		private final int percentPointsPassedFilter;
		
		public IgnitionOffEvent(int tripDuration, int percentPointsPassedFilter){
			this.tripDuration = tripDuration;
			this.percentPointsPassedFilter = percentPointsPassedFilter;
		}

        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addIgnitionOffEvent(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            if (state.getProductVersion().equals(ProductType.WAYSMART)){
                
            } else {
                note.addAttr(DeviceAttrs.TRIP_DURATION, tripDuration);
                note.addAttr(DeviceAttrs.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
                        percentPointsPassedFilter);
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
    }
    
    public DeviceNote addEndOfTripAttributes(DeviceNote note, DeviceState state){
        if (state.getProductVersion().equals(ProductType.WAYSMART)){
            
        } else {
            
        }
        return note;
    }

    public static IgnitionOffEvent ignitionOff(int tripDuration, int percentPointsPassedFilter) {
        return classes.new IgnitionOffEvent(tripDuration, percentPointsPassedFilter);
    }
    
    public static IgnitionOffEvent ignitionOff(Long tripDuration, int percentPointsPassedFilter) {
        return ignitionOff(tripDuration.intValue(), percentPointsPassedFilter);
    }
}
