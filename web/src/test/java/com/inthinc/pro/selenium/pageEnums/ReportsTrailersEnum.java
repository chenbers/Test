package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsTrailersEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/reports/trailersReport"),
	
	TITLE("Trailer Report", "//span[@class='trailer']"),
	
	GROUP_TEXTFIELD(null,"trailers-form:trailers:groupfsp"),
    TRAILER_TEXTFIELD(null, "trailers-form:trailers:namefsp"),
    VEHICLE_TEXTFIELD(null,"trailers-form:trailers:vehicle_namefsp"),
    DRIVER_TEXTFIELD(null,"trailers-form:trailers:fullNamefsp"),

    STATUS_VALUE(null, "trailers-form:trailers:###:status"),
    GROUP_VALUE(null,"trailers-form:trailers:###:group"),
    TRAILERID_VALUE(null, "trailers-form:trailers:###:name"),
    VEHICLE_VALUE(null,"trailers-form:trailers:###:vehicle_name"),
    DRIVER_VALUE(null,"trailers-form:trailers:###:fullName"),
    ASSIGNED_STATUS_VALUE(null,"trailers-form:trailers:###:assignedStatus"),
    ENTRY_METHOD_VALUE(null,"trailers-form:trailers:###:entryMethod"),
    
    STATUS_SORT(null, "trailers-form:trailers:statusheader:sortDiv"),
    GROUP_SORT(null,"trailers-form:trailers:groupheader:sortDiv"),
    TRAILERID_SORT(null, "trailers-form:trailers:nameheader:sortDiv"),
    VEHICLE_SORT(null,"trailers-form:trailers:vehicle_nameheader:sortDiv"),
    DRIVER_SORT(null,"trailers-form:trailers:fullNameheader:sortDiv"),
    ASSIGNED_STATUS_SORT(null, "trailers-form:trailers:assignedStatusheader:sortDiv"),
    OVERALL_SCORE_SORT(null,"trailers-form:trailers:overallScoreheader:sortDiv"),
    ENTRY_METHOD_SORT(null,"trailers-form:trailers:entryMethodheader:sortDiv"),
    ;
    
    private String text, url;
    private String[] IDs;
    
    private ReportsTrailersEnum(String url){
    	this.url = url;
    }
    private ReportsTrailersEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}    