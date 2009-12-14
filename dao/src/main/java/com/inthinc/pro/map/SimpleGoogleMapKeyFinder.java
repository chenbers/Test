package com.inthinc.pro.map;

public class SimpleGoogleMapKeyFinder implements GoogleMapKeyFinder {

	private String key;
	
	@Override
	public String getKey() {
		
		return key;
	}

	@Override
	public void setKey(String key) {
		
		this.key = key;

	}

}
