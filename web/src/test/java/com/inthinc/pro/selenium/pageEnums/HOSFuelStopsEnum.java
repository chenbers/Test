package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;


public enum HOSFuelStopsEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/fuelStops"),
    
    /* Buttons */
    REFRESH("Refresh", "fuelStops-table-form:fuelStopsTable_refresh"),
    ADD("Add", "fuelStops-table-form:fuelStopsTable-fuelStopsTableAdd"),
    EDIT_COLUMNS(null, "fuelStops-table-form:fuelStopsTable-fuelStopsTableEditColumns"),
    DELETE_BUTTON(delete, "fuelStops-table-form:fuelStopsTable-fuelStopsTableDelete"),
    
    /* TextFields */
    VEHICLE_TEXT_FIELD(null, "fuelStops-table-form:vehicleName"),
    VEHICLE_SUGGESTION_BOX(null, "fuelStops-table-form:vehicleSuggestionBoxId:suggest"),
    DATE_START_BOX(null, "fuelStops-table-form:fuelStopsTable_startCalendar"),
    DATE_STOP_BOX(null, "fuelStops-table-form:fuelStopsTable_endCalendar"),
    
    
    /* Links */
    SORT_DATE_TIME(null, "fuelStops-table-form:fuelStopsTable:datetimeheader:sortDiv"),
    SORT_DRIVER(null, "fuelStops-table-form:fuelStopsTable:datetimeheader:sortDiv"),
    SORT_VEHICLE(null, "fuelStops-table-form:fuelStopsTable:vehicleheader:sortDiv"),
    SORT_VEHICLE_FUEL(null, "fuelStops-table-form:fuelStopsTable:truckGallonsheader:sortDiv"),
    SORT_TRAILER_FUEL(null, "fuelStops-table-form:fuelStopsTable:trailerGallonsheader:sortDiv"),
    SORT_BEFORE(null, "fuelStops-table-form:fuelStopsTable:odometerBeforeheader:sortDiv"),
    SORT_AFTER(null, "fuelStops-table-form:fuelStopsTable:odometerAfterheader:sortDiv"),
    SORT_TRAILER(null, "fuelStops-table-form:fuelStopsTable:trailerheader:sortDiv"),
    SORT_LOCATION(null, "fuelStops-table-form:fuelStopsTable:locationheader:sortDiv"),
    SORT_EDITED(null, "fuelStops-table-form:fuelStopsTable:editedheader:sortDiv"),
 
    ENTRY_EDIT(null, "fuelStops-table-form:fuelStopsTable:###:editItem"),
    
    /* Text */
    ENTRY_DATE_TIME(null, "fuelStops-table-form:fuelStopsTable:###:datetime"),
    ENTRY_DRIVER(null, "fuelStops-table-form:fuelStopsTable:###:driver"),
    ENTRY_VEHICLE(null, "fuelStops-table-form:fuelStopsTable:###:vehicle"),
    ENTRY_VEHICLE_FUEL(null, "fuelStops-table-form:fuelStopsTable:###:truckGallons"),
    ENTRY_TRAILER_FUEL(null, "fuelStops-table-form:fuelStopsTable:###:trailerGallons"),
    ENTRY_BEFORE(null, "fuelStops-table-form:fuelStopsTable:###:odometerBefore"),
    ENTRY_AFTER(null, "fuelStops-table-form:fuelStopsTable:###:odometerAfter"),
    ENTRY_TRAILER(null, "fuelStops-table-form:fuelStopsTable:###:trailer"),
    ENTRY_LOCATION(null, "fuelStops-table-form:fuelStopsTable:###:location"),
    ENTRY_EDITED(null, "fuelStops-table-form:fuelStopsTable:###:edited"),
    
    TITLE("Fuel Stops", "//span[@id='fuelStops-table-region:status']/../span[@class='fuelStops']"),
    COUNTER("Showing XXX to YYY of ZZZ records", "fuelStops-table-form:header"),
    
    START_DATE_BOX("Date Range:", "fuelStops-table-form:fuelStopsTable_startCalendar"),
    END_DATE_BOX("Date Range:", "fuelStops-table-form:fuelStopsTable_endCalendar"),    
    
    /* CheckBoxes */
    CHECK_ALL(null, "fuelStops-table-form:fuelStopsTable:selectAll"),
    ENTRY_CHECKBOX(null, "fuelStops-table-form:fuelStopsTable:###:select"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private HOSFuelStopsEnum(String url){
    	this.url = url;
    }
    private HOSFuelStopsEnum(String text, String ...IDs){
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
