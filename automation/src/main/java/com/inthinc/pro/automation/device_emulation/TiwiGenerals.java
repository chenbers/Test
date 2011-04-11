package com.inthinc.pro.automation.device_emulation;

import java.util.EnumSet;
import java.util.HashMap;

public class TiwiGenerals {

	
	public static enum FwdCmdStatus {
		FWDCMD_PROCESSED(0),
	    FWDCMD_BADDATA(1),
	    FWDCMD_UNKNOWN(2),
	    FWDCMD_RECEIVED(3),
	    FWDCMD_FLASH_SUCCESS(4),
	    FWDCMD_FLASH_FAILED(5),
	    FWDCMD_RESETTING(6),
	    FWDCMD_WRITE_ERROR(7),
	    FWDCMD_SAVED_AD(8),
	    FWDCMD_RESTART_AD(9);
		
		private int code;

	    private FwdCmdStatus(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, FwdCmdStatus> lookupByCode = new HashMap<Integer, FwdCmdStatus>();
	    
	    static {
	        for (FwdCmdStatus p : EnumSet.allOf(FwdCmdStatus.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static FwdCmdStatus valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	}
	
	public static enum MSPPower{
	    /* MSP Power State */
		MSP_PWRSTATE_OFF(0),
		MSP_PWRSTATE_ON(1),        //Ignition on), WMP on
		MPS_PWRSTATE_ON_IGN_OFF(2),
		MSP_PWRSTATE_WMP_LOWPOWER(3),
		MSP_PWRSTATE_WMP_WAKEUP(4),
		MSP_PWRSTATE_UNKNOWN(5),
		MSP_PWRSTATE_WMP_LOWPOWER_LOWBAT(6);
	    
		private int code;

	    private MSPPower(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, MSPPower> lookupByCode = new HashMap<Integer, MSPPower>();
	    
	    static {
	        for (MSPPower p : EnumSet.allOf(MSPPower.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static MSPPower valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	}
	
	public static enum IgnitionState{
		
		IGNITION_ON_EMU(0),
	    IGNITION_ON_DMM_EMU_AVAIL(1),
	    IGNITION_OFF_EMU(2),
	    IGNITION_ON_DMM(3),
	    IGNITION_OFF_DMM(4),
	    IGNITION_OFF_FORCED(5),
	    IGNITION_ON_SPEED(6),
	    IGNITION_OFF_IDLE(7),
	    IGNITION_USING_SPEED(8),
	    IGNITION_EMU_DROPPED_OUT(9),
	    IGNITION_UNKNOWN(10);
		
		private int code;

	    private IgnitionState(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, IgnitionState> lookupByCode = new HashMap<Integer, IgnitionState>();
	    
	    static {
	        for (IgnitionState p : EnumSet.allOf(IgnitionState.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static IgnitionState valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	}
	
	public static enum WarningTypes{
	    /* Warning Types */
		
	    WARNING_SPEEDING(1),
	    WARNING_RPM(2),
	    WARNING_SEATBELT(3),
	    WARNING_NOTE_EVENT(4),
	    WARNING_FULL_EVENT(5),
	    WARNING_MAN_DOWN(6),
	    WARNING_PHONE_HOME(7),
	    WARNING_HEADLIGHT(8),
	    WARNING_NO_DRIVER(12),
	    WARNING_SATELLITE_SWITCH(13),
	    WARNING_SAFETYCHECK(14),
	    WARNING_NO_TRAILER(15),
	    WARNING_NO_HOURS(16),
	    WARNING_NEW_MSG(17),
	    WARNING_INVALID_ID(18),
	    WARNING_SECURE_LOAD(19),
	    WARNING_30MIN(20),
	    WARNING_SAT_ISSUES(21),
	    WARNING_GPS_ISSUES(22),
	    WARNING_LOW_BATTERY(23),
	    WARNING_OFFDUTY_HOURS_VIOLATION(24),
	    WARNING_INVALID_OCCUPANT(25),
	    WARNING_PANIC_NOT_SENT(26),
	    WARNING_OFFDUTY_HOURS_1ST(27),
	    WARNING_OFFDUTY_HOURS_2ND(28);
	    
	    private int code;

	    private WarningTypes(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, WarningTypes> lookupByCode = new HashMap<Integer, WarningTypes>();
	    
	    static {
	        for (WarningTypes p : EnumSet.allOf(WarningTypes.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static WarningTypes valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	}
	
	public static enum Heading{
		/* Heading numbers */

	    HEADING_NORTH(0),
	    HEADING_NORTH_EAST(1),
	    HEADING_EAST(2),
	    HEADING_SOUTH_EAST(3),
	    HEADING_SOUTH(4),
	    HEADING_SOUTH_WEST(5),
	    HEADING_WEST(6),
	    HEADING_NORTH_WEST(7);
	    
	    private int code;

	    private Heading(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, Heading> lookupByCode = new HashMap<Integer, Heading>();
	    
	    static {
	        for (Heading p : EnumSet.allOf(Heading.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static Heading valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	    
	}
	
	public static enum ViolationFlags{
		 /* Violation Flags */

	    VIOLATION_MASK_SPEEDING(1),
	    VIOLATION_MASK_SEATBELT(2),
	    VIOLATION_MASK_RPM(4);
	    
	    private int code;

	    private ViolationFlags(int c) {
	    	code = c;
	    }
	    public int getCode() {
	    	return code;
	    } 
	    
	    private static HashMap<Integer, ViolationFlags> lookupByCode = new HashMap<Integer, ViolationFlags>();
	    
	    static {
	        for (ViolationFlags p : EnumSet.allOf(ViolationFlags.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    public static ViolationFlags valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
	}
}
