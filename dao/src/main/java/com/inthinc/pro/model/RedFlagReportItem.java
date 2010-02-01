package com.inthinc.pro.model;

public class RedFlagReportItem extends EventReportItem {

	// TODO: should we flatten this out?
	RedFlag redFlag;
	//	RedFlagLevel level;
//	Boolean alert;
	String eventType;
	
	public RedFlagReportItem(RedFlag redFlag, MeasurementType measurementType,
			String dateFormat, String detailsFormat, String mphString) {
		super(redFlag.getEvent(), measurementType, dateFormat, detailsFormat, mphString);
		this.redFlag = redFlag;
//		this.level = redFlag.getLevel();
//		this.alert = redFlag.getAlert();
		this.eventType = redFlag.getEvent().getEventType().toString();

	}
/*
	public RedFlagLevel getLevel() {
		return level;
	}

	public void setLevel(RedFlagLevel level) {
		this.level = level;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}
*/
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public RedFlag getRedFlag() {
		return redFlag;
	}

	public void setRedFlag(RedFlag redFlag) {
		this.redFlag = redFlag;
	}

}
