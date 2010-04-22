package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum TripQuality implements BaseEnum {

	BAD(0),
    GOOD(1),
    UNKNOWN(2); 

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

	@Override
	public Integer getCode() {
		return code;
	}


}
