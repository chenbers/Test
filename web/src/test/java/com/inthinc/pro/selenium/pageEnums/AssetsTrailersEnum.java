package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AssetsTrailersEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/assets/trailers"),
	
	TITLE(null, null), //does not have a title yet
	
	ENTRIES_DROPDOWN("", null),
	ENTRIES_TEXT("Show ### entries", null),
	SEARCH_TEXT("Search:", null),
	//SHOW/HIDE COLUMNS SECTION
	SHOW_HIDE_COLUMNS_LINK("Show / Hide Columns", null),
	TRAILER_ID_CHECKBOX(null, null),
    TRAILER_ID_CHECKBOX_LABEL("Trailer ID", null),
	TEAM_CHECKBOX(null, null),
	TEAM_CHECKBOX_LABEL("Team", null),
	STATUS_CHECKBOX(null, null),
    STATUS_CHECKBOX_LABEL("Status", null),	
	VIN_CHECKBOX(null, null),
	VIN_CHECKBOX_LABEL("VIN", null),
	LICENSE_CHECKBOX(null, null),
	LICENSE_CHECKBOX_LABEL("License #", null),
	STATE_CHECKBOX(null, null),
	STATE_CHECKBOX_LABEL("State", null),
	YEAR_CHECKBOX(null, null),
	YEAR_CHECKBOX_LABEL("Year", null),
	MAKE_CHECKBOX(null, null),
	MAKE_CHECKBOX_LABEL("Make", null),
	MODEL_CHECKBOX(null, null),
	MODEL_CHECKBOX_LABEL("Model", null),
	COLOR_CHECKBOX(null, null),
	COLOR_CHECKBOX_LABEL("Color", null),
	WEIGHT_CHECKBOX(null, null),
	WEIGHT_CHECKBOX_LABEL("Weight", null),
	ODOMETER_CHECKBOX(null, null),
	ODOMETER_CHECKBOX_LABEL("Odometer", null),
	RESTORE_ORIGINAL_TEXT("Restore original", null),
	//TABLE ITEMS
	SORT_BY_TRAILERID_LINK("Trailer ID", null),
	SORT_BY_TEAM_LINK("Team", null),
	SORT_BY_STATUS("Status", null),
	SORT_BY_VIN("VIN", null),
	SORT_BY_LICENSE_NUMBER("License #", null),
	SORT_BY_STATE("State", null),
	SORT_BY_YEAR("Year", null),
	SORT_BY_MAKE("Make", null),
	SORT_BY_MODEL("Model", null),
	SORT_BY_COLOR("Color", null),
	SORT_BY_WEIGHT("Weight", null),
	SORT_BY_ODOMETER("Odometer", null),
	//INFORMATION SECTION
	SELECT_A_ROW_TEXT("Select a row to see a detailed view.", null),
	NEW_BUTTON("New", null),
	EDIT_BUTTON("Edit", null),
	//TRAILER INFORMATION SECTION
	TRAILER_INFORMATION_HEADER("Trailer Information", null),
	VIN_TRAILER_INFORMATION_LABEL("VIN", null),
	VIN_TRAILER_INFORMATION_TEXT(null, null),
	VIN_TRAILER_INFORMATION_TEXTFIELD(null, null),
	MAKE_TRAILER_INFORMATION_LABEL("Make", null),
    MAKE_TRAILER_INFORMATION_TEXT(null, null),	
	MAKE_TRAILER_INFORMATION_TEXTFIELD(null, null),
	YEAR_TRAILER_INFORMATION_LABEL("Year", null),
    YEAR_TRAILER_INFORMATION_TEXT(null, null),	
	YEAR_TRAILER_INFORMATION_TEXTFIELD(null, null),
	COLOR_TRAILER_INFORMATION_LABEL("Color", null),
	COLOR_TRAILER_INFORMATION_TEXT(null, null),
	COLOR_TRAILER_INFORMATION_TEXTFIELD(null, null),
	WEIGHT_TRAILER_INFORMATION_LABEL("Weight", null),
	WEIGHT_TRAILER_INFORMATION_TEXT(null, null),
	WEIGHT_TRAILER_INFORMATION_TEXTFIELD(null, null),
	LICENSE_TRAILER_INFORMATION_LABEL("License #", null),
    LICENSE_TRAILER_INFORMATION_TEXT(null, null),	
	LICENSE_TRAILER_INFORMATION_TEXTFIELD(null, null),
	STATE_TRAILER_INFORMATION_LABEL("State", null),
	//TRAILER PROFILE SECTION
	TRAILER_PROFILE_HEADER("Trailer Profile", null),
	TRAILER_ID_LABEL("Trailer ID", null),
	TRAILER_ID_TEXT(null, null),
	STATUS_LABEL("Status", null),
	STATUS_TEXT(null, null),
	STATUS_TEXTFIELD(null, null),
	//TRAILER ASSIGNMENT SECTION
	TRAILER_ASSIGNMENT_HEADER("Trailer Assignment", null),
	TEAM_TRAILER_ASSIGNMENT_LABEL("Team", null),
	TEAM_TRAILER_ASSIGNMENT_TEXT(null, null),
	//DEVICE ASSIGNMENT SECTION
	DEVICE_ASSIGNMENT_HEADER("Device Assignment", null),
	ASSIGNED_DEVICE_LABEL("Assigned Device", null),
	ASSIGNED_DEVICE_TEXT(null, null),
    //VEHICLE ASSIGNMENT SECTION
    VEHICLE_ASSIGNMENT_HEADER("Vehicle Assignment", null),
    ASSIGNED_VEHICLE_LABEL("Assigned Vehicle", null),
    ASSIGNED_VEHICLE_TEXT(null, null),
    //DRIVER ASSIGNMENT SECTION
    DRIVER_ASSIGNMENT_HEADER("Driver Assignment", null),
    ASSIGNED_DRIVER_LABEL("Assigned Driver", null),
    ASSIGNED_DRIVER_TEXT(null, null),
    
	//TABLE ITEMS
    TRAILER_ID_ENTRY(null, null),
    TEAM_ENTRY(null, null),
    STATUS_ENTRY(null, null),
    VIN_ENTRY(null, null),
    LICENSE_ENTRY(null, null),
    STATE_ENTRY(null, null),
    YEAR_ENTRY(null, null),
    MAKE_ENTRY(null, null),
    MODEL_ENTRY(null, null),
    COLOR_ENTRY(null, null),
    WEIGHT_ENTRY(null, null),
    ODOMETER_ENTRY(null, null),
	
    //I'M LEAVING THESE HERE TILL I CAN SEE IF THIS TABLE HAS FILTERS
    GROUP_FILTER(null,"trailers-form:trailers:groupfsp"),
    TRAILER_FILTER(null, "trailers-form:trailers:namefsp"),
    VEHICLE_FILTER(null,"trailers-form:trailers:vehicle_namefsp"),
    YEAR_MAKE_MODEL_FILTER(null,"trailers-form:trailers:makeModelYearfsp"),
    DRIVER_FILTER(null,"trailers-form:trailers:fullNamefsp"), 
    
    PREVIOUS("Previous", "//li[@class='prev']"),
    PAGE_NUMBER(null, "//li[###]/a[@href='#']"),
    NEXT("Next", "//li[@class='next']")
    ;
    
    private String text, url;
    private String[] IDs;
    
    private AssetsTrailersEnum(String url){
    	this.url = url;
    }
    private AssetsTrailersEnum(String text, String ...IDs){
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