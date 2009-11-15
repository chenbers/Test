package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.util.MessageUtil;

public abstract class NotificationReportItem<T> implements Comparable<T>{

	private String date;
	private String group;
	private String driverName;
	private String vehicleName;
	private String category;
	private String detail;
	private Integer groupID;
	
	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	protected static DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),
																	LocaleBean.getCurrentLocale());

	public NotificationReportItem() {
		super();
	}

	public String getDate() {
	    return date;
	}

	public void setDate(String date) {
	    this.date = date;
	}

	public String getGroup() {
	    return group;
	}

	public void setGroup(String group) {
	    this.group = group;
	}

	public String getCategory() {
	    return category;
	}

	public void setCategory(String category) {
	    this.category = category;
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
	
}