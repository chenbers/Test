package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;


public enum FuelStopsEnum implements SeleniumEnums {
    /* Buttons */
    REFRESH("Refresh", "fuelStops-table-form:fuelStopsTable_refresh"),
    ADD("Add", "fuelStops-table-form:fuelStopsTable-fuelStopsTableAdd"),
    EDIT_COLUMNS(null, "fuelStops-table-form:fuelStopsTable-fuelStopsTableEditColumns"),
    
    /* TextFields */
    VEHICLE_TEXT_FIELD(null, "fuelStops-table-form:vehicleName"),
    VEHICLE_SUGGESTION_BOX(null, "fuelStops-table-form:vehicleSuggestionBoxId:###:vehicleSelected"),
    DATE_START_BOX(null, "fuelStops-table-form:fuelStopsTable_startCalendarInputDate"),
    DATE_STOP_BOX(null, "fuelStops-table-form:fuelStopsTable_endCalendarInputDate"),
    
    
    /* Links */
    SORT_DATE_TIME(null, "fuelStops-table-form:fuelStopsTable:datetimeheader:sortDiv"),
    SORT_VEHICLE(null, "fuelStops-table-form:fuelStopsTable:vehicleheader:sortDiv"),
    SORT_VEHICLE_FUEL(null, "fuelStops-table-form:fuelStopsTable:truckGallonsheader:sortDiv"),
    SORT_TRAILER_FUEL(null, "fuelStops-table-form:fuelStopsTable:trailerGallonsheader:sortDiv"),
    SORT_BEFORE(null, "fuelStops-table-form:fuelStopsTable:odometerBeforeheader:sortDiv"),
    SORT_AFTER(null, "fuelStops-table-form:fuelStopsTable:odometerAfterheader:sortDiv"),
    SORT_TRAILER(null, "fuelStops-table-form:fuelStopsTable:trailerheader:sortDiv"),
    SORT_LOCATION(null, "fuelStops-table-form:fuelStopsTable:locationheader:sortDiv"),
    SORT_EDITED(null, "fuelStops-table-form:fuelStopsTable:editedheader:sortDiv"),
 
    VALUE_EDIT(null, "fuelStops-table-form:fuelStopsTable:###:edit"),
    
    /* Text */
    VALUE_DATE_TIME(null, "fuelStops-table-form:fuelStopsTable:###:datetime"),
    VALUE_VEHICLE(null, "fuelStops-table-form:fuelStopsTable:###:vehicle"),
    VALUE_VEHICLE_FUEL(null, "fuelStops-table-form:fuelStopsTable:###:truckGallons"),
    VALUE_TRAILER_FUEL(null, "fuelStops-table-form:fuelStopsTable:###:trailerGallons"),
    VALUE_BEFORE(null, "fuelStops-table-form:fuelStopsTable:###:odometerBefore"),
    VALUE_AFTER(null, "fuelStops-table-form:fuelStopsTable:###:odometerAfter"),
    VALUE_TRAILER(null, "fuelStops-table-form:fuelStopsTable:###:trailer"),
    VALUE_LOCATION(null, "fuelStops-table-form:fuelStopsTable:###:location"),
    VALUE_EDITED(null, "fuelStops-table-form:fuelStopsTable:###:edited"),
    
    TITLE("Fuel Stops", "//span[@id='fuelStops-table-region:status']/../span[@class='fuelStops']"),
    COUNTER("Showing XXX to YYY of ZZZ records", "fuelStops-table-form:header"),
    
    DATE_RANGE_LABEL("Date Range:", "//span[@id='fuelStops-table-form:fuelStopsTable_startCalendarPopup']/../../td[1]"),
    
    /* CheckBoxes */
    CHECK_ALL(null, "fuelStops-table-form:fuelStopsTable:selectAll"),
    VALUE_CHECK(null, "fuelStops-table-form:fuelStopsTable:###:select"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private FuelStopsEnum(String url){
    	this.url = url;
    }
    private FuelStopsEnum(String text, String ...IDs){
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
