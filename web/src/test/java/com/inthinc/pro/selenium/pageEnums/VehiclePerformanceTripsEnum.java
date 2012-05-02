package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum VehiclePerformanceTripsEnum implements SeleniumEnums {

    
    DEFAULT_URL(appUrl + "/vehicle/trips/"),
	
	DATE_ENTRY(null, "tripsTableForm:tripsTable:###:date_column"),
	TIME_ENTRY(null, "tripsTableForm:tripsTable:###:time_column"),
	DISTANCE_ENTRY(null, "tripsTableForm:tripsTable:###:distance_column"),
	END_ADDRESS_ENTRY(null, "tripsTableForm:tripsTable:###:end_column"),
	DURATION_ENTRY(null, "tripsTableForm:tripsTable:###:duration_column"),
	GPS_QUALITY_ENTRY(null, "tripsTableForm:tripsTable:###:quality_column"),
	
	DATE_HEADER("Date", "tripsTableForm:tripsTable:date_columnheader:sortDiv"),
	TIME_HEADER("Time", "tripsTableForm:tripsTable:time_columnheader:sortDiv"),
	DISTANCE_HEADER("Distance", "tripsTableForm:tripsTable:distance_columnheader:sortDiv"),
	END_ADDRESS_HEADER("End Address", "tripsTableForm:tripsTable:end_columnheader:sortDiv"),
	DURATION_HEADER("Duration", "tripsTableForm:tripsTable:duration_columnheader:sortDiv"),
	GPS_QUALITY_HEADER("GPS", "tripsTableForm:tripsTable:quality_columnheader:sortDiv"),
	
	TRIP_ROW(null, "//tbody[@id='tripsTableForm:tripsTable:tb']/tr[###]"),
	
	
	START_DATE(null, "//div[@id='dateForm:calendarStart']/span[1]/input[1]"),
	END_DATE(null, "//div[@id='dateForm:calendarEnd']/span[1]/input[1]"),
	UPDATE_DATE_RANGE("Update Date Range", "dateForm:vehicleTripsDate"),
	DATE_MESSAGE(null, "dateError_body"),

	
	STATS_TITLE(null, "//div[@id='statsPanel_body']/div/div[@class='panel_title']"),
	
	TOTAL_TRIP_DURATION_VALUE(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[1]/td[3]"),
	TOTAL_MILES_DRIVEN_VALUE(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[2]/td[3]"),
	TOTAL_IDLE_TIME_VALUE(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[3]/td[3]"),
	TOTAL_TRIPS_VALUE(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[4]/td[3]"),
	DRIVER_TIME_ZONE_VALUE(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[5]/td[3]"),
	
	
	TOTAL_TRIP_DURATION_LABEL(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[1]/td[1]"),
	TOTAL_MILES_DRIVEN_LABEL(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[2]/td[1]"),
	TOTAL_IDLE_TIME_LABEL(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[3]/td[1]"),
	TOTAL_TRIPS_LABEL(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[4]/td[1]"),
	DRIVER_TIME_ZONE_LABEL(null, "//div[@id='statsPanel']/div/div/div/div/table/tbody/tr[5]/td[1]"),
	
	
	EVENT_ADDRESS_ENTRY(null, "selectedEventsForm:selectedTripTable:###:eventAddress"),
	EVENT_EVENT_ICON_ENTRY(null, "//td[@id='selectedEventsForm:selectedTripTable:###:address_column']/../td[1]"),
	EVENT_DATE_TIME_ENTRY(null, "//td[@id='selectedEventsForm:selectedTripTable:###:address_column']/../td[3]"),
	
	
	SHOW_ENGINE_IDLE("Show engine idle markers on map", "showIdleCheckBox"),
	SHOW_SAFETY_VIOLATION("Show safety violation markers on map", "showWarningsCheckBox"),
	SHOW_TAMPERING("Show device tampering markers on map", "showTamperingCheckBox"),
	
	
	VEHICLE_NAME(null, "//a[contains(@id,'driverTripsDriverPerformance1')]"),
	
	BREADCRUMB(null, "//a[contains(@id,'breadcrumbitem:###:vehicleTrips-dashboard')]"),
	DRIVER_NAME(null, "selectDriverForm:vehicleTrips-driverPerformance"),
	TRIPS_BY(null, "//span[@class='vehicle']"),
	
	;

    private String text, url;
    private String[] IDs;
    
    private VehiclePerformanceTripsEnum(String url){
    	this.url = url;
    }
    private VehiclePerformanceTripsEnum(String text, String ...IDs){
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
