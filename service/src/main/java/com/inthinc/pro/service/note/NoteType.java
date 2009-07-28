package com.inthinc.pro.service.note;

public enum NoteType {
	LOCATION(6),
	IGNITION_ON(19),
	IGNITION_OFF(20),
	SPEEDING(93),
	POWER_ON(150),
	CRASH(1),
	ZONES_ARRIVAL(117),
	NOTE(2);
	
	int code;
	
	private NoteType(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}

}
