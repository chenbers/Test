package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeedPercentItem  implements Comparable<SpeedPercentItem> {
	Number miles;
	Number milesSpeeding;
	Date date;
	
	public SpeedPercentItem(Number miles, Number milesSpeeding, Date date) {
		super();
		this.miles = miles;
		this.milesSpeeding = milesSpeeding;
		this.date = date;
	}
	public Number getMiles() {
		return miles;
	}
	public void setMiles(Number miles) {
		this.miles = miles;
	}
	public Number getMilesSpeeding() {
		return milesSpeeding;
	}
	public void setMilesSpeeding(Number milesSpeeding) {
		this.milesSpeeding = milesSpeeding;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int compareTo(SpeedPercentItem o) {
		if (getDate() == null && o.getDate() == null)
			return 0;
		if (getDate() == null)
			return -1;
		if (o.getDate() == null)
			return 1;
			
        return getDate().compareTo(o.getDate());
	}
	
}
