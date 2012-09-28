package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeedPercentItem extends BaseScore implements Comparable<SpeedPercentItem> {
	Number miles;
	Number milesSpeeding;
	
	public SpeedPercentItem(Number miles, Number milesSpeeding, Date date) {
		super(date);
		this.miles = miles;
		this.milesSpeeding = milesSpeeding;
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
