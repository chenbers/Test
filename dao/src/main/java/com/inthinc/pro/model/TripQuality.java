package com.inthinc.pro.model;

import java.util.EnumSet;


public enum TripQuality implements BaseEnum {

	// TODO: fix min,max
	BAD(0, 0, 98),
    GOOD(1, 99, 99); 

	Integer code;
	Integer min;
	Integer max;
	
	private TripQuality(Integer code, Integer min, Integer max) {
		this.code = code;
		this.min = min;
		this.max = max;
	}
	
    public static TripQuality valueOf(Integer quality) {
        for (TripQuality type : EnumSet.allOf(TripQuality.class)) {
        	if (quality >= type.min && quality <= type.max)
        		return type;
        }
        
        return null;
    }

	@Override
	public Integer getCode() {
		return code;
	}


}
