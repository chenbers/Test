package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamTripsEnum implements SeleniumEnums {
    

    DEFAULT_URL(appUrl + "/dashboard//tab/teamTrips"),
    
    
    DRIVER_HEADER("Driver Name", Xpath.start().table(Id.id("tripsTableForm:driversTrips")).thead().tr().th("2").toString()),
    
    CHECK_BOX_ENTRY(null, "tripsTableForm:driversTrips:###:checkDriver"),
    DRIVER_NAME(null, "tripsTableForm:driversTrips:###:teamTrips-driverPerformance"),
    DRIVER_LETTER(null, Xpath.start().tr("###").td("3").div().div(Id.clazz("trips_label")).toString()),
    ;
    

    private String text, url;
    private String[] IDs;
    
    private TeamTripsEnum(String url){
    	this.url = url;
    }
    private TeamTripsEnum(String text, String ...IDs){
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
