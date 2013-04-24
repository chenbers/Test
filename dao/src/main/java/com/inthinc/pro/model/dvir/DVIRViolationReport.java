package com.inthinc.pro.model.dvir;

import java.util.Date;

import org.joda.time.DateTime;

import com.inthinc.pro.model.event.NoteType;

public class DVIRViolationReport {
	
	private Date dateTime;
	private String driverName;
	private String vehicleName;
	private String groupName;
	private NoteType noteType;
	
	public DVIRViolationReport(){}
	
	public DVIRViolationReport(Date dateTime, String drvierName, String vehicleName, String groupName, NoteType noteType){
		this.dateTime = dateTime;
		this.driverName = drvierName;
		this.vehicleName = vehicleName;
		this.groupName = groupName;
		this.noteType = noteType;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}
}
