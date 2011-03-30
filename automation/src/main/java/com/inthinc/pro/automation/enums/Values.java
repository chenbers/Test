package com.inthinc.pro.automation.enums;

public enum Values{
	COLOR("colors.dat"), 
	YEAR(""), 
	MAKE("make.dat"), 
	MODEL("model.dat"),
	STATES("states.dat"),
	RFID("rfid.dat");
	
	 private String fileName;

	 private Values(String fileName) {
	   this.fileName = fileName;
	 }

	 public String getFileName() {
	   return this.fileName;
	 }
};
