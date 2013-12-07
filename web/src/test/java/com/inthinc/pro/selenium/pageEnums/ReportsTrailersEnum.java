package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsTrailersEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/reports/trailersReport"),
	
	TITLE("Trailer Report", "//span[@class='trailer']"),
	
    GROUP_FILTER(null,"trailers-form:trailers:groupfsp"),
    TRAILER_FILTER(null, "trailers-form:trailers:namefsp"),
    VEHICLE_FILTER(null,"trailers-form:trailers:vehicle_namefsp"),
    YEAR_MAKE_MODEL_FILTER(null,"trailers-form:trailers:makeModelYearfsp"),
    DRIVER_FILTER(null,"trailers-form:trailers:fullNamefsp"), 

    GROUP_VALUE(null,"trailers-form:trailers:###:group"),
    TRAILERID_VALUE(null, "trailers-form:trailers:###:name"),
    VEHICLE_VALUE(null,"trailers-form:trailers:###:vehicle_name"),
    YEAR_MAKE_MODEL_VALUE(null, "trailers-form:trailers:###:makeModelYear"),
    DRIVER_VALUE(null,"trailers-form:trailers:###:fullName"),
    DISTANCE_DRIVEN_VALUE(null, "trailers-form:trailers:###:distance"),
    ODOMETER_VALUE(null, "trailers-form:trailers:###:odometer"),
    OVERALL_SCORE_VALUE(null,"trailers-form:trailers:###:overallScore"),
    SPEED_SCORE_VALUE(null,"trailers-form:trailers:###:speedScore"),
    STYLE_SCORE_VALUE(null,"trailers-form:trailers:###:styleScore"),
    
    GROUP_SORT(null,"trailers-form:trailers:groupheader:sortDiv"),
    TRAILERID_SORT(null, "trailers-form:trailers:nameheader:sortDiv"),
    VEHICLE_SORT(null,"trailers-form:trailers:vehicle_nameheader:sortDiv"),
    YEAR_MAKE_MODEL(null, "trailers-form:trailers:makeModelYearheader:sortDiv"),
    DRIVER_SORT(null,"trailers-form:trailers:fullNameheader:sortDiv"),
    DISTANCE_DRIVEN_SORT(null, "trailers-form:trailers:distanceheader:sortDiv"),
    ODOMETER_SORT(null, "trailers-form:trailers:odometerheader:sortDiv"),
    OVERALL_SCORE_SORT(null,"trailers-form:trailers:overallScoreheader:sortDiv"),
    SPEED_SCORE_SORT(null,"trailers-form:trailers:speedScoreheader:sortDiv"),
    STYLE_SCORE_SORT(null,"trailers-form:trailers:styleScoreheader:sortDiv"),
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