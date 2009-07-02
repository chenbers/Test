package com.inthinc.pro.service.note;

public enum AttributeType {
	ATTR_TYPE_TOP_SPEED(1),
	ATTR_TYPE_AVG_SPEED(2),
	ATTR_TYPE_SPEED_LIMIT(3),
	ATTR_TYPE_AVG_RPM(4),
	ATTR_TYPE_RESET_REASON(5),
	ATTR_TYPE_MANRESET_REASON(6),
	ATTR_TYPE_FWDCMD_STATUS(7),
	ATTR_TYPE_SEVERITY(24),
	ATTR_TYPE_DISTANCE(129),
	ATTR_TYPE_MAX_RPM(130),
	ATTR_TYPE_DELTAVX(131),
	ATTR_TYPE_DELTAVY(132),
	ATTR_TYPE_DELTAVZ(133),
	ATTR_TYPE_ZONE_ID(192);
	
	private int code;
	
	private AttributeType(int code)
	{
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
}
