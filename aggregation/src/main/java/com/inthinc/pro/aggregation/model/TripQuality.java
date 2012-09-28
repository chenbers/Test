package com.inthinc.pro.aggregation.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TripQuality {

	BAD(0),
    GOOD(1),
    UNKNOWN(2),
    WAYSMART(3); // temporary until waysmarts support trip gps quality

	Integer code;
	
	private TripQuality(Integer code) {
		this.code = code;
	}
	
    private static final Map<Integer, TripQuality> lookup = new HashMap<Integer, TripQuality>();
    static
    {
        for (TripQuality p : EnumSet.allOf(TripQuality.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static TripQuality valueOf(Integer code) {
        return lookup.get(code);
    }

	public Integer getCode() {
		return code;
	}


}
