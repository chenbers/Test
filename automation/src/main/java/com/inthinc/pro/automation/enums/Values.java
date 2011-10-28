package com.inthinc.pro.automation.enums;

import java.util.ArrayList;

import com.inthinc.pro.automation.interfaces.ListEnum;
import com.inthinc.pro.automation.resources.FileRW;

public enum Values implements ListEnum{
	COLOR("src/main/resources/values/colors.dat"), 
	YEAR(""), 
	MAKE("src/main/resources/values/make.dat"), 
	MODEL("src/main/resources/values/model.dat"), 
	STATES("src/main/resources/values/states.dat"), 
	RFID("src/main/resources/values/rfid.dat"), 
	TEXT_MESSAGE("src/main/resources/values/textMessages.dat"), 
	EMAIL_DOMAINS("src/main/resources/values/emailDomains.dat"),

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