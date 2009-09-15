package com.inthinc.pro.model;

public class SpeedPercentItem {
	Number miles;
	Number milesSpeeding;
	
	
	public SpeedPercentItem(Number miles, Number milesSpeeding) {
		super();
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
	
}
