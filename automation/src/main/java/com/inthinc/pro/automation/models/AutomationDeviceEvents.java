package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
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
        public DeviceNote addEvent(DeviceBase device);
        public DeviceNote getNote(DeviceNote note, ProductType productVersion);
        public DeviceNoteTypes getNoteType();
    }
    
    public class NoteEvent implements AutomationEvents{
        
        public final int deltaX;
        public final int deltaY;
        public final int deltaZ;
        public final String type;
        
        private NoteEvent(int deltaX, int deltaY, int deltaZ, String type){
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.deltaZ = deltaZ;
            this.type = type;
            MasterTest.print(this);
        }
        
        @Override
        public String toString(){
            return String.format("%s(DeltaX:%4d, DeltaY:%4d, DeltaZ:%4d)", type, deltaX, deltaY, deltaZ);
        }
        
        public long packDeltaVS(){
            Long packedDeltaV = ((-deltaX) + 600l) * 1464100l + // Turn deltaX negative because the DB expects the opposite from waysmarts
                             ((deltaY) + 600l) * 1210l +
                             ((deltaZ) + 600l);
            
            MasterTest.print(this, Level.DEBUG);
            return packedDeltaV;
        }

        @Override
        public DeviceNote addEvent(DeviceBase device) {
            return device.addNoteEvent(this);
        }

        @Override
        public DeviceNote getNote(DeviceNote note, ProductType productVersion) {
            return NoteGenerator.noteEvent(note, this, productVersion);
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return DeviceNoteTypes.NOTE_EVENT;
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
        public DeviceNote addEvent(DeviceBase device) {
            return device.addSpeedingNote(this);
        }

        @Override
        public DeviceNote getNote(DeviceNote note, ProductType productVersion) {
            return NoteGenerator.speedingEvent(note, this, productVersion);
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return DeviceNoteTypes.SPEEDING_EX3;
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
        
        private SeatBeltEvent(int avgRpm, int gpsPercent, int topSpeed, int avgSpeed, int distance, int maxRpm){
            this.avgRpm = Math.abs(avgRpm);
            this.gpsPercent = Math.abs(gpsPercent);
            this.topSpeed = Math.abs(topSpeed);
            this.avgSpeed = Math.abs(avgSpeed);
            this.distance = Math.abs(distance);
            this.maxRpm = Math.abs(maxRpm);
        }

        @Override
        public DeviceNote addEvent(DeviceBase device) {
            return device.addSeatbeltEvent(this);
        }
        
        @Override
        public String toString(){
            return String.format("AverageRpm: %d, GPS PercentUsed: %d, Top Speed: %d, Average Speed: %d, Distance: %d, MaxRPM: %d\n"
                    , avgRpm, gpsPercent, topSpeed, avgSpeed, distance, maxRpm);
        }

        @Override
        public DeviceNote getNote(DeviceNote note, ProductType productVersion) {
            return NoteGenerator.seatbeltEvent(note, this, productVersion);
        }

        @Override
        public DeviceNoteTypes getNoteType() {
            return DeviceNoteTypes.SEATBELT;
        }
        
    }
    
    public static SeatBeltEvent seatbelt(int avgRpm, int gpsPercent, int topSpeed, int avgSpeed, int distance, int maxRpm){
        return classes.new SeatBeltEvent(avgRpm, gpsPercent, topSpeed, avgSpeed, distance, maxRpm);
    }

}
