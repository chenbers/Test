package com.inthinc.pro.backing.dao.validator;

public enum ValidatorType {
	
	DEFAULT(0),
	ADDRESS(1),
	CRASH_REPORT(2),
	DEVICE(3),
	DRIVER(4),
	GROUP(5),
	NOTE(6),
	PERSON(7),
	RED_FLAG_ALERT(8),
	REPORT_PREF(9),
	ROLE(10),
	TABLE_PREF(11),
	USER(12),
	VEHICLE(13),
	ZONE_ALERT(14),
	ZONE(15),
	DRIVER_OR_VEHICLE(16);
	
	Integer code;
	
	private ValidatorType(Integer code) {
		this.code = code;
	}
	
    public Integer getCode()
    {
        return code;
    }
	

}
