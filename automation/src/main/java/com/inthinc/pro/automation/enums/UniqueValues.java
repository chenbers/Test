package com.inthinc.pro.automation.enums;

public enum UniqueValues {
	PERSONID_EMPID("", true),
	USERID("username", true),
	PERSONID_EMAIL("email", true),
	DEVICEID_IMEI("imei", false),
	VEHICLE_VIN("vin", true),
	DEVICEID_SERIAL("serialNum", true),
	DRIVERID("barcode", false),
	DEVICEID_MCMID("mcmid", false);
	
	private String name;
	private Boolean string;
	
	private UniqueValues(String name, Boolean string){
		this.name = name;
		this.string = string;
	}
	
	public String getName(){
		return name;
	}
	
	public Boolean isString(){
		return string;
	}
}
