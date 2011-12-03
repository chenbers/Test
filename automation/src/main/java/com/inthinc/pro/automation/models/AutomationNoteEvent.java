package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.utils.MasterTest;

public class AutomationNoteEvent {
    
    
    public final int deltaX;
    public final int deltaY;
    public final int deltaZ;
    public final String type;
    
    private final static String brake = "Hard Brake";
    private final static String accel = "Hard Accel";
    private final static String left = "Hard Turn Left";
    private final static String right = "Hard Turn Right";
    private final static String dip = "Hard Dip";
    private final static String bump = "Hard Bump";
    
    private AutomationNoteEvent(int deltaX, int deltaY, int deltaZ, String type){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.type = type;
        MasterTest.print(this);
    }
    
    public static AutomationNoteEvent hardBrake(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(-Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), brake);
    }
    
    public static AutomationNoteEvent hardAccel(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), accel);
    }

    public static AutomationNoteEvent hardRight(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(Math.abs(deltaX), -Math.abs(deltaY), Math.abs(deltaZ), right);
    }
    
    public static AutomationNoteEvent hardLeft(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), left);
    }
    
    public static AutomationNoteEvent hardBump(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(Math.abs(deltaX), Math.abs(deltaY), -Math.abs(deltaZ), bump);
    }
    
    public static AutomationNoteEvent hardDip(int deltaX, int deltaY, int deltaZ){
        return new AutomationNoteEvent(Math.abs(deltaX), Math.abs(deltaY), Math.abs(deltaZ), dip);
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

}
