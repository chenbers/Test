package com.inthinc.pro.comm.parser.forwardcommand;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParserType;

public enum ForwardCommand {

	
	SET_AUTO_MANDOWN_WARNING_TIMEOUT(878, AttribParserType.LONG),
	SET_GPRS_APN_STRING(688, AttribParserType.STRING_FIXED_LENGTH32),
	SET_CRASH_DATA_SECONDS(770, AttribParserType.INTEGER),	
	SET_EXTENDED_CRASH_DATA_SECONDS(771, AttribParserType.INTEGER),	
	SET_SEVERE_SPEED_TIMEBUFFER_VARIABLE(788, AttribParserType.INTEGER),
	SET_TRIAX_HARDACCEL_LEVEL(795, AttribParserType.BYTE),
	SET_TRIAX_HARDACCEL_AVERAGE_CHANGE(796, AttribParserType.BYTE),
	SET_TRIAX_HARDACCEL_DELTAV(797, AttribParserType.INTEGER),
	SET_TRIAX_HARDVERT_DMM_TO_CRASH_SENSOR_RATIO_THRESHOLD(799, AttribParserType.INTEGER),  
	SET_TRIAX_HARDVERT_PEAK_TO_PEAK_LEVEL_THRESHOLD(800, AttribParserType.SHORT),
	SET_TRIAX_HARDVERT_SPEED_THRESHOLD(801, AttribParserType.INTEGER),                      
	SET_TRIAX_SEVERE_HARDVERT_LEVEL(802, AttribParserType.INTEGER),
	SET_WITNESS_NOTE_THRESHOLD(167, AttribParserType.INTEGER),
	SET_WITNESS_FULL_THRESHOLD(168, AttribParserType.INTEGER),
	SET_WITNESS_ORIENTATION(204, AttribParserType.BYTE),
	SET_ON_DUTY_TIMEOUT(226, AttribParserType.INTEGER),
	SET_OFF_DUTY_TIMEOUT(227, AttribParserType.INTEGER),
	SET_SLEEPER_BERTH_TIMEOUT(228, AttribParserType.INTEGER),
	SET_CLEAR_DRIVER_TIMEOUT(354, AttribParserType.INTEGER),
	SET_ON_DUTY_IDLE_TIMEOUT(428, AttribParserType.INTEGER),
	SET_TRIAX_Z_LEVEL(449, AttribParserType.BYTE),
	SET_TRIAX_RMS_LEVEL(450, AttribParserType.BYTE),
	SET_TRIAX_RMS_WINDOW(451, AttribParserType.BYTE),
	SET_TRIAX_Y_WINDOW(452, AttribParserType.BYTE),
	SET_TRIAX_Y_LEVEL(453, AttribParserType.BYTE),
	SET_TRIAX_X_ACCEL(454, AttribParserType.BYTE),
	SET_TRIAX_DVX(455, AttribParserType.BYTE),				
	SET_TRIAX_SLOPE(490, AttribParserType.BYTE),
	SET_WITNESSII_G_TRIGGER_LEVEL(539, AttribParserType.BYTE),
	SET_WITNESSII_X_DV_THRESHOLD(541, AttribParserType.BYTE),
	SET_WITNESSII_Y_DV_THRESHOLD(542, AttribParserType.BYTE),
	SET_WITNESSII_Z_DV_THRESHOLD(543, AttribParserType.BYTE),
	SET_WITNESSII_ORIENTATION(554, AttribParserType.BYTE),
	COPY_FILES_TO_THUMB_DRIVE(582, AttribParserType.BYTE),
	SET_SPEED_LIMIT_VARIABLE(629, AttribParserType.INTEGER),
	SET_SEVERE_SPEED_LIMIT_VARIABLE(630, AttribParserType.INTEGER),
	SET_SPEED_BUFFER_VARIABLE(631, AttribParserType.INTEGER),
	SET_AUTO_MANDOWN_TIMEOUT(632, AttribParserType.INTEGER),
	SET_VEHICLE_VIN_NUMBER(671, AttribParserType.STRING_FIXED_LENGTH17),
	SET_007_SMTOOLS_RESET_STATE(643, AttribParserType.BYTE),
	SET_007_SMTOOLS_BUS_TYPE(644, AttribParserType.BYTE),
	SET_TRIAX_X_WINDOW(672, AttribParserType.BYTE),
	SET_TRIAX_YSLOPE(673, AttribParserType.BYTE),
	SET_TRIAX_ZSLOPE(674, AttribParserType.BYTE),
	SET_TRIAX_HARD_ACCEL_SECONDARY_DV_LEVEL(836, AttribParserType.INTEGER),
	SET_TRIAX_HARD_BRAKE_SECONDARY_DV_LEVEL(837, AttribParserType.INTEGER),
	SET_TRIAX_HARD_TURN_SECONDARY_DV_LEVEL(838, AttribParserType.INTEGER),
	SET_TRIAX_HARD_VERTICAL_SECONDARY_PP_LEVEL(839, AttribParserType.INTEGER),
	SET_DOWNLOAD_ATTEMPT_LIMIT(844, AttribParserType.INTEGER),
	SET_WITNESSII_TRACE_UPLOAD_ATTEMPT_LIMIT(845, AttribParserType.INTEGER),
	SET_SBS_EXMAP_DOWNLOAD_ATTEMPT_LIMIT(846, AttribParserType.INTEGER),
	SET_SBS_EXMAP_CHECK_COUNT_LIMIT(847, AttribParserType.INTEGER),
	SET_SBS_EXMAP_CHECK_FREQUENCY(848, AttribParserType.INTEGER);
	
	

	private static final Map<Integer,ForwardCommand> lookup = new HashMap<Integer,ForwardCommand>();

	static {
	     for(ForwardCommand fc : EnumSet.allOf(ForwardCommand.class))
	          lookup.put(fc.getCode(), fc);
	}

	private int code;
	private AttribParserType parserType;
	
	private ForwardCommand(int code, AttribParserType parserType) {
	     this.code = code;
	     this.parserType = parserType;
	}
	
	public int getCode() { return code; }
	
	public static ForwardCommand get(int code) { 
	     return lookup.get(code); 
	}
	public AttribParserType getParserType()
	{
		return parserType;
	}
	
	
}
