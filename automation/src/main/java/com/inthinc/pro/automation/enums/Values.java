package com.inthinc.pro.automation.enums;

import java.util.ArrayList;

import com.inthinc.pro.automation.interfaces.ListEnum;
import com.inthinc.pro.automation.resources.FileRW;

public enum Values implements ListEnum{
	COLOR("colors.dat"), 
	YEAR(""), 
	MAKE("make.dat"), 
	MODEL("model.dat"), 
	STATES("states.dat"), 
	RFID("rfid.dat"), 
	TEXT_MESSAGE("textMessages.dat"), 
	EMAIL_DOMAINS("emailDomains.dat"),

	;

	private String fileName;

	private ArrayList<String> list = new ArrayList<String>();

	private static FileRW file = new FileRW();

	private Values(String fileName) {
		this.fileName = fileName;

	}

	public ArrayList<String> getList() {
		if (file == null) {
			file = new FileRW();
		}
		if (list.isEmpty()) {
			list = file.read(fileName);
		}
		return list;
	}
}