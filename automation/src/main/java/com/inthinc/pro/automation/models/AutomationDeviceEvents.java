package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceEnums.ViolationFlags;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.objects.WaysmartDevice;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.EventAttr;

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
                note.addAttr(EventAttr.DELTA_VS, packDeltaVS());

            } else {
                note.addAttr(EventAttr.DELTAV_X, deltaX);
                note.addAttr(EventAttr.DELTAV_Y, deltaY);
                note.addAttr(EventAttr.DELTAV_Z, deltaZ);
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
            	
            	note.addAttr(EventAttr.TOP_SPEED, topSpeed);
            	note.addAttr(EventAttr.DISTANCE, distance / 10);
            	note.addAttr(EventAttr.MAX_RPM, maxRpm);
            	note.addAttr(EventAttr.SPEED_LIMIT, speedLimit);
            	note.addAttr(EventAttr.AVG_SPEED, avgSpeed);
            	note.addAttr(EventAttr.AVG_RPM, avgRpm);
            	
            	note.addAttr(EventAttr.SBS_LINK_ID, state.getSbsLinkID());
            	note.addAttr(EventAttr.ZONE_ID, state.getZoneID());
            	note.addAttr(EventAttr.SPEEDING_TYPE, 3);
            	note.addAttr(EventAttr.SEATBELT_ENGAGED, state.isSeatbeltEngaged());
            	note.addAttr(EventAttr.START_TIME, state.getSpeedingStartTime());
            	note.addAttr(EventAttr.STOP_TIME, state.getSpeedingStopTime());
            	note.addAttr(EventAttr.MAX_TIME, state.getSpeedingStopTime().getDelta(state.getSpeedingStartTime()));
            	note.addAttr(EventAttr.COURSE, state.getCourse());
            	note.addAttr(EventAttr.MAX_SPEED_LIMIT, state.getMaxSpeedLimit());
            	note.addAttr(EventAttr.SBS_SPEED_LIMIT, state.getSbsSpeedLimit());
            	note.addAttr(EventAttr.ZONE_SPEED_LIMIT, state.getZoneSpeedLimit());
            	note.addAttr(EventAttr.WEATHER_SPEED_LIMIT_PERCENT, state.getWeatherSpeedLimitPercent());
            	note.addAttr(EventAttr.SEVERE_SPEED_THRESHOLD, state.getSevereSpeedThreshold());
            	note.addAttr(EventAttr.SPEEDING_BUFFER, state.getSpeedingBuffer());
            	note.addAttr(EventAttr.SPEEDING_GRACE_PERIOD, state.getGracePeriod());
            	note.addAttr(EventAttr.SEVERE_SPEED_SECONDS, state.getSeverSpeedSeconds());
            	note.addAttr(EventAttr.SPEED_MODULE_ENABLED, state.isSpeedModuleEnabled());
            	note.addAttr(EventAttr.SPEED_SOURCE, 1);

            } else {
            	note = DeviceNote.constructNote(noteType, location, state);
                note.addAttr(EventAttr.DISTANCE, distance);
                note.addAttr(EventAttr.TOP_SPEED, topSpeed);
                note.addAttr(EventAttr.AVG_SPEED, avgSpeed);
                note.addAttr(EventAttr.SPEED_ID, 9999);
                note.addAttr(EventAttr.VIOLATION_FLAGS, SpeedingEvent.FLAG);
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
                note.addAttr(EventAttr.TOP_SPEED, topSpeed);
                note.addAttr(EventAttr.DISTANCE, distance);
                note.addAttr(EventAttr.MAX_RPM, maxRpm);

            } else {
                note.addAttr(EventAttr.AVG_RPM, avgRpm);
                note.addAttr(EventAttr.VIOLATION_FLAGS, violationFlag);
                
                note.addAttr(EventAttr.PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED,
                        gpsPercent);
                
                note.addAttr(EventAttr.TOP_SPEED, topSpeed);
                note.addAttr(EventAttr.AVG_SPEED, avgSpeed);
                note.addAttr(EventAttr.DISTANCE, distance);
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
            note.addAttr(EventAttr.VEHICLE_ID_STR, vehicleIDStr);
            note.addAttr(EventAttr.MCM_ID_STR, mcmIDStr);
            note.addAttr(EventAttr.COMPANY_ID, acctID);
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
            		note.addAttr(EventAttr.VIOLATION_FLAGS,flag);
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
//                note.addAttr(EventAttr.DRIVER_STR, employeeID);
//                note.addAttr(EventAttr.DRIVER_ID, driverID);
            } else {
                
            }
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
    }
    public class RFKillEvent implements AutomationEvents{
        public final DeviceNoteTypes noteType = DeviceNoteTypes.RF_KILL;
        @Override
        public DeviceBase addEvent(DeviceBase device) {
            return device.addRFKillEvent(this);
        }

        @Override
        public DeviceNote getNote(GeoPoint location, DeviceState state) {
            DeviceNote note = DeviceNote.constructNote(noteType, location, state);
            addEndOfTripAttributes(note, state);
            return note;
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return noteType;
        }
        
    }
    public static RFKillEvent rfKill(){
        return classes.new RFKillEvent();
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
                note.addAttr(EventAttr.TRIP_DURATION, tripDuration);
                note.addAttr(EventAttr.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
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
    
    public class Tampering implements AutomationEvents {

		@Override
		public DeviceBase addEvent(DeviceBase device) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DeviceNote getNote(GeoPoint location, DeviceState state) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DeviceNoteTypes getNoteType() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
}
