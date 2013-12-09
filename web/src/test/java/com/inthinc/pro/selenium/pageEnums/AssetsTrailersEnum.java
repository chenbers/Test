package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AssetsTrailersEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/assets/trailers"),
	
	TITLE(null, null), //does not have a title yet
	
	ENTRIES_DROPDOWN("10", "trailerTable_length"),
	ENTRIES_LABEL("Show ### entries", "//div[4]/label"),
	SEARCH_LABEL("Search:", "//div[5]/label"),
	SEARCH_TEXTFIELD(null, "trailerTable_filter"),
	//SHOW/HIDE COLUMNS SECTION
	SHOW_HIDE_COLUMNS_LINK("Show / Hide Columns", "ColVis_Button TableTools_Button ColVis_MasterButton"),
	TRAILER_ID_CHECKBOX(null, "//button[1]/span/span[1]/input"),
    TRAILER_ID_CHECKBOX_LABEL("Trailer ID", "//button[1]/span/span[2]"),
	TEAM_CHECKBOX(null, "//button[2]/span/span[1]/input"),
	TEAM_CHECKBOX_LABEL("Team", "//button[2]/span/span[2]"),
	STATUS_CHECKBOX(null, "//button[3]/span/span[1]/input"),
    STATUS_CHECKBOX_LABEL("Status", "//button[3]/span/span[2]"),	
	VIN_CHECKBOX(null, "//button[4]/span/span[1]/input"),
	VIN_CHECKBOX_LABEL("VIN", "//button[4]/span/span[2]"),
	LICENSE_CHECKBOX(null, "//button[5]/span/span[1]/input"),
	LICENSE_CHECKBOX_LABEL("License #", "//button[5]/span/span[2]"),
	STATE_CHECKBOX(null, "//button[6]/span/span[1]/input"),
	STATE_CHECKBOX_LABEL("State", "//button[6]/span/span[2]"),
	YEAR_CHECKBOX(null, "//button[7]/span/span[1]/input"),
	YEAR_CHECKBOX_LABEL("Year", "//button[7]/span/span[2]"),
	MAKE_CHECKBOX(null, "//button[8]/span/span[1]/input"),
	MAKE_CHECKBOX_LABEL("Make", "//button[8]/span/span[2]"),
	MODEL_CHECKBOX(null, "//button[9]/span/span[1]/input"),
	MODEL_CHECKBOX_LABEL("Model", "//button[9]/span/span[2]"),
	COLOR_CHECKBOX(null, "//button[10]/span/span[1]/input"),
	COLOR_CHECKBOX_LABEL("Color", "//button[10]/span/span[2]"),
	WEIGHT_CHECKBOX(null, "//button[11]/span/span[1]/input"),
	WEIGHT_CHECKBOX_LABEL("Weight", "//button[11]/span/span[2]"),
	ODOMETER_CHECKBOX(null, "//button[12]/span/span[1]/input"),
	ODOMETER_CHECKBOX_LABEL("Odometer", "//button[12]/span/span[2]"),
	RESTORE_ORIGINAL_LINK("Restore original", "ColVis_Button TableTools_Button ColVis_Restore"),
	//TABLE ITEMS
	SORT_BY_TRAILERID_LINK("Trailer ID", "Trailer ID: activate to sort column ascending"),
	SORT_BY_TEAM_LINK("Team", "Team: activate to sort column ascending"),
	SORT_BY_STATUS("Status", "Status: activate to sort column ascending"),
	SORT_BY_VIN("VIN", "VIN: activate to sort column ascending"),
	SORT_BY_LICENSE_NUMBER("License #", "License #: activate to sort column ascending"),
	SORT_BY_STATE("State", "State: activate to sort column ascending"),
	SORT_BY_YEAR("Year", "Year: activate to sort column ascending"),
	SORT_BY_MAKE("Make", "Make: activate to sort column ascending"),
	SORT_BY_MODEL("Model", "Model: activate to sort column ascending"),
	SORT_BY_COLOR("Color", "Color: activate to sort column ascending"),
	SORT_BY_WEIGHT("Weight", "Weight: activate to sort column ascending"),
	SORT_BY_ODOMETER("Odometer", "Odometer: activate to sort column ascending"),
	//INFORMATION SECTION
	SELECT_A_ROW_LABEL("Select a row to see a detailed view.", "noneSelected"),
	NEW_BUTTON("New", "btn btn-small pull-right btnDetailNew"),
	EDIT_BUTTON("Edit", "btn btn-small pull-right btnDetailEdit"),
	SAVE_BUTTON("Save", "btn btn-small pull-right btnDetailSave"),
	CANCEL_BUTTON("Cancel", "btn btn-small pull-right btnDetailCancel"),
	//TRAILER INFORMATION SECTION
	TRAILER_INFORMATION_HEADER("Trailer Information", "//div[2]/div/h4"),
	VIN_TRAILER_INFORMATION_LABEL("VIN", "//form/div[3]/div[1]/div"),
	VIN_TRAILER_INFORMATION_TEXT(null, "vinValue"),
	VIN_TRAILER_INFORMATION_TEXTFIELD(null, "vinInput"),
	MAKE_TRAILER_INFORMATION_LABEL("Make", "//form/div[4]/div[1]/div"),
    MAKE_TRAILER_INFORMATION_TEXT(null, "makeValue"),	
	MAKE_TRAILER_INFORMATION_TEXTFIELD(null, "makeInput"),
	YEAR_TRAILER_INFORMATION_LABEL("Year", "//form/div[5]/div[1]/div"),
    YEAR_TRAILER_INFORMATION_TEXT(null, "yearValue"),	
	YEAR_TRAILER_INFORMATION_TEXTFIELD(null, "yearInput"),
	COLOR_TRAILER_INFORMATION_LABEL("Color", "//form/div[6]/div[1]/div"),
	COLOR_TRAILER_INFORMATION_TEXT(null, "colorValue"),
	COLOR_TRAILER_INFORMATION_TEXTFIELD(null, "colorInput"),
	WEIGHT_TRAILER_INFORMATION_LABEL("Weight", "//form/div[7]/div[1]/div"),
	WEIGHT_TRAILER_INFORMATION_TEXT(null, "weightValue"),
	WEIGHT_TRAILER_INFORMATION_TEXTFIELD(null, "weightInput"),
	LICENSE_TRAILER_INFORMATION_LABEL("License #", "//form/div[8]/div[1]/div"),
    LICENSE_TRAILER_INFORMATION_TEXT(null, "licenseValue"),	
	LICENSE_TRAILER_INFORMATION_TEXTFIELD(null, "licenseInput"),
	STATE_TRAILER_INFORMATION_LABEL("State", "//form/div[9]/div[1]/div"),
	STATE_TRAILER_INFORMATION_TEXT(null, "stateValue"),
	//TRAILER PROFILE SECTION
	TRAILER_PROFILE_HEADER("Trailer Profile", "//div[10]/div/h4"),
	TRAILER_ID_LABEL("Trailer ID", "//form/div[11]/div[1]/div"),
	TRAILER_ID_TEXT(null, "trailerIdValue"),
	STATUS_LABEL("Status", "//form/div[12]/div[1]/div"),
	STATUS_TEXT(null, "statusValue"),
	STATUS_TEXTFIELD(null, "statusInput"),
	//TRAILER ASSIGNMENT SECTION
	TRAILER_ASSIGNMENT_HEADER("Trailer Assignment", "//div[13]/div/h4"),
	TEAM_TRAILER_ASSIGNMENT_LABEL("Team", "//form/div[14]/div[1]/div"),
	TEAM_TRAILER_ASSIGNMENT_TEXT(null, "teamValue"),
	//DEVICE ASSIGNMENT SECTION
	DEVICE_ASSIGNMENT_HEADER("Device Assignment", "//div[15]/div/h4"),
	ASSIGNED_DEVICE_LABEL("Assigned Device", "//form/div[16]/div[1]/div"),
	ASSIGNED_DEVICE_TEXT(null, "assignedDeviceValue"),
    //VEHICLE ASSIGNMENT SECTION
    VEHICLE_ASSIGNMENT_HEADER("Vehicle Assignment", "//div[17]/div/h4"),
    ASSIGNED_VEHICLE_LABEL("Assigned Vehicle", "//form/div[18]/div[1]/div"),
    ASSIGNED_VEHICLE_TEXT(null, "assignedVehicleValue"),
    //DRIVER ASSIGNMENT SECTION
    DRIVER_ASSIGNMENT_HEADER("Driver Assignment", "//div[19]/div/h4"),
    ASSIGNED_DRIVER_LABEL("Assigned Driver", "//form/div[20]/div[1]/div"),
    ASSIGNED_DRIVER_TEXT(null, "assignedDriverValue"),
    
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