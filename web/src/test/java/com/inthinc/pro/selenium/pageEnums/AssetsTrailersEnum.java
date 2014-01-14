package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AssetsTrailersEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/assets/trailers"),
	
	TITLE(null, null), //does not have a title yet
	
	ENTRIES_DROPDOWN("10", "//select[@name='trailerTable_length']"),
	ENTRIES_LABEL("Show ### entries", "//div[4]/label"),
	SEARCH_LABEL("Search:", "//div[5]/label"),
	SEARCH_TEXTFIELD(null, "//div/label/input[@type='text']"),
	//SHOW/HIDE COLUMNS SECTION
	SHOW_HIDE_COLUMNS_LINK("Show / Hide Columns", "//button[@class='ColVis_Button TableTools_Button ColVis_MasterButton']"),
	TRAILER_ID_CHECKBOX(null, "//button[1]/span/span[1]/input"),
    TRAILER_ID_CHECKBOX_LABEL("Trailer ID", "//button[1]/span/span[2]"),
	TEAM_CHECKBOX(null, "//button[2]/span/span[1]/input"),
	TEAM_CHECKBOX_LABEL("Team", "//button[2]/span/span[2]"),
	DEVICE_CHECKBOX(null, "//button[3]/span/span[1]/input"),
	DEVICE_CHECKBOX_LABEL("Device", "//button[3]/span/span[2]"),
    VEHICLE_CHECKBOX(null, "//button[4]/span/span[1]/input"),
    VEHICLE_CHECKBOX_LABEL("Vehicle", "//button[4]/span/span[2]"),
    DRIVER_CHECKBOX(null, "//button[5]/span/span[1]/input"),
    DRIVER_CHECKBOX_LABEL("Driver", "//button[5]/span/span[2]"),
	STATUS_CHECKBOX(null, "//button[6]/span/span[1]/input"),
    STATUS_CHECKBOX_LABEL("Status", "//button[6]/span/span[2]"),	
	VIN_CHECKBOX(null, "//button[7]/span/span[1]/input"),
	VIN_CHECKBOX_LABEL("VIN", "//button[7]/span/span[2]"),
	LICENSE_CHECKBOX(null, "//button[8]/span/span[1]/input"),
	LICENSE_CHECKBOX_LABEL("License #", "//button[8]/span/span[2]"),
	STATE_CHECKBOX(null, "//button[9]/span/span[1]/input"),
	STATE_CHECKBOX_LABEL("State", "//button[9]/span/span[2]"),
	YEAR_CHECKBOX(null, "//button[10]/span/span[1]/input"),
	YEAR_CHECKBOX_LABEL("Year", "//button[10]/span/span[2]"),
	MAKE_CHECKBOX(null, "//button[11]/span/span[1]/input"),
	MAKE_CHECKBOX_LABEL("Make", "//button[11]/span/span[2]"),
	MODEL_CHECKBOX(null, "//button[12]/span/span[1]/input"),
	MODEL_CHECKBOX_LABEL("Model", "//button[12]/span/span[2]"),
	COLOR_CHECKBOX(null, "//button[13]/span/span[1]/input"),
	COLOR_CHECKBOX_LABEL("Color", "//button[13]/span/span[2]"),
	WEIGHT_CHECKBOX(null, "//button[14]/span/span[1]/input"),
	WEIGHT_CHECKBOX_LABEL("Weight", "//button[14]/span/span[2]"),
	ODOMETER_CHECKBOX(null, "//button[15]/span/span[1]/input"),
	ODOMETER_CHECKBOX_LABEL("Odometer", "//button[15]/span/span[2]"),
	RESTORE_ORIGINAL_LINK("Restore original", "//button[@class='ColVis_Button TableTools_Button ColVis_Restore']"),
	//TABLE ITEMS
	SORT_BY_TRAILERID_LINK("Trailer ID", "//th[@aria-label='Trailer ID: activate to sort column ascending']"),
	SORT_BY_TEAM_TEXT("Team", "//th[@aria-label='Team']"),
	SORT_BY_DEVICE_TEXT("Device", "//th[@aria-label='Device']"),
	SORT_BY_VEHICLE_TEXT("Vehicle", "//th[@aria-label='Vehicle']"),
	SORT_BY_DRIVER_TEXT("Driver", "//th[@aria-label='Driver']"),
	SORT_BY_STATUS("Status", "//th[@aria-label='Status: activate to sort column ascending']"),
	SORT_BY_VIN("VIN", "//th[@aria-label='VIN: activate to sort column ascending']"),
	SORT_BY_LICENSE_NUMBER("License #", "//th[@aria-label='License #: activate to sort column ascending']"),
	SORT_BY_STATE_TEXT("State", "//th[@aria-label='State']"),
	SORT_BY_YEAR("Year", "//th[@aria-label='Year: activate to sort column ascending']"),
	SORT_BY_MAKE("Make", "//th[@aria-label='Make: activate to sort column ascending']"),
	SORT_BY_MODEL("Model", "//th[@aria-label='Model: activate to sort column ascending']"),
	SORT_BY_COLOR("Color", "//th[@aria-label='Color: activate to sort column ascending']"),
	SORT_BY_WEIGHT("Weight", "//th[@aria-label='Weight: activate to sort column ascending']"),
	SORT_BY_ODOMETER("Odometer", "//th[@aria-label='Odometer: activate to sort column ascending']"),
	//INFORMATION SECTION
	NEW_BUTTON("New", "//button[@id='btnDetailNew']"),
	EDIT_BUTTON("Edit", "//button[@id='btnDetailEdit']"),
	SAVE_BUTTON("Save", "//button[@id='btnDetailSave']"),
	CANCEL_BUTTON("Cancel", "//button[@id='btnDetailCancel']"),
	//DETAILS SECTION
	DETAILS_HEADER("Details", "//form/h4"),
	TRAILER_ID_LABEL("Trailer ID", "//div[2]/label"),
	TRAILER_ID_TEXT(null, "trailerIdValue"),
	TRAILER_ID_TEXTFIELD(null, "trailerNameInput"),
	STATUS_LABEL("Status", "//div[3]/label"),
	STATUS_TEXT(null, "statusValue"),
	STATUS_DROPDOWN(null, "statusInput"),
	VIN_TRAILER_INFORMATION_LABEL("VIN", "//div[4]/label"),
	VIN_TRAILER_INFORMATION_TEXT(null, "vinValue"),
	VIN_TRAILER_INFORMATION_TEXTFIELD(null, "vinInput"),
	MAKE_TRAILER_INFORMATION_LABEL("Make", "//div[5]/label"),
    MAKE_TRAILER_INFORMATION_TEXT(null, "makeValue"),	
	MAKE_TRAILER_INFORMATION_TEXTFIELD(null, "makeInput"),
	YEAR_TRAILER_INFORMATION_LABEL("Year", "//div[6]/label"),
    YEAR_TRAILER_INFORMATION_TEXT(null, "yearValue"),	
	YEAR_TRAILER_INFORMATION_TEXTFIELD(null, "yearInput"),
	COLOR_TRAILER_INFORMATION_LABEL("Color", "//div[7]/label"),
	COLOR_TRAILER_INFORMATION_TEXT(null, "colorValue"),
	COLOR_TRAILER_INFORMATION_TEXTFIELD(null, "colorInput"),
	WEIGHT_TRAILER_INFORMATION_LABEL("Weight", "//div[8]/label"),
	WEIGHT_TRAILER_INFORMATION_TEXT(null, "weightValue"),
	WEIGHT_TRAILER_INFORMATION_TEXTFIELD(null, "weightInput"),
	LICENSE_TRAILER_INFORMATION_LABEL("License #", "//div[9]/label"),
    LICENSE_TRAILER_INFORMATION_TEXT(null, "licenseValue"),	
	LICENSE_TRAILER_INFORMATION_TEXTFIELD(null, "licenseInput"),
	STATE_TRAILER_INFORMATION_LABEL("State", "//div[10]/label"),
	STATE_TRAILER_INFORMATION_TEXT(null, "stateValue"),
	STATE_TRAILER_INFORMATION_DROPDOWN(null, "stateInput"),
	VIN_ERROR_TEXT("The trailer vin exceeds 17 characters.", "//form/div[4]/div/div[2]/label[@class='error']"),
	YEAR_ERROR_TEXT("The trailer year can be empty or contain 4 numbers.", "//form/div[6]/div/div[2]/label[@class='error']"),
	WEIGHT_ERROR_TEXT("The trailer weight can be empty or contain only numbers.", "//form/div[8]/div/div[2]/label[@class='error']"),
	//ASSIGNMENT SECTION
	ASSIGNMENT_HEADER("Assignment", "//div/h4"),
	TEAM_TRAILER_ASSIGNMENT_LABEL("Team", "//div[12]/label"),
	TEAM_TRAILER_ASSIGNMENT_TEXT(null, "teamValue"),
	ASSIGNED_DEVICE_LABEL("Device", "//div[13]/label"),
	DEVICE_TEXT(null, "assignedDeviceValue"),
	ASSIGNED_DEVICE_DROPDOWN(null, "assignedDeviceInput"),
    ASSIGNED_VEHICLE_LABEL("Vehicle", "//div[14]/label"),
    VEHICLE_TEXT(null, "assignedVehicleValue"),
    ASSIGNED_DRIVER_LABEL("Driver", "//div[15]/label"),
    DRIVER_TEXT(null, "assignedDriverValue"),
    
	//TABLE ITEMS
    TRAILER_ID_ENTRY(null, "//tr[###]/td[1]"),
    TEAM_ENTRY(null, "//tr[###]/td[2]"),
    DEVICE_ENTRY(null, "//tr[###]/td[3]"),
    VEHICLE_ENTRY(null, "//tr[###]/td[4]"),
    DRIVER_ENTRY(null, "//tr[###]/td[5]"),
    STATUS_ENTRY(null, "//tr[###]/td[6]"),
    VIN_ENTRY(null, "//tr[###]/td[7]"),
    LICENSE_ENTRY(null, "//tr[###]/td[8]"),
    STATE_ENTRY(null, "//tr[###]/td[9]"),
    YEAR_ENTRY(null, "//tr[###]/td[10]"),
    MAKE_ENTRY(null, "//tr[###]/td[11]"),
    MODEL_ENTRY(null, "//tr[###]/td[12]"),
    COLOR_ENTRY(null, "//tr[###]/td[13]"),
    WEIGHT_ENTRY(null, "//tr[###]/td[14]"),
    ODOMETER_ENTRY(null, "//tr[###]/td[15]"),
	
    //I'M LEAVING THESE HERE TILL I CAN SEE IF THIS TABLE HAS FILTERS
    GROUP_FILTER(null,"trailers-form:trailers:groupfsp"),
    TRAILER_FILTER(null, "trailers-form:trailers:namefsp"),
    VEHICLE_FILTER(null,"trailers-form:trailers:vehicle_namefsp"),
    YEAR_MAKE_MODEL_FILTER(null,"trailers-form:trailers:makeModelYearfsp"),
    DRIVER_FILTER(null,"trailers-form:trailers:fullNamefsp"), 
    
    ENTRIES_TEXT("Showing # to ### of ### enetries (filtered from 1 total entries", "trailerTable_info"),
    PREVIOUS_DISABLED("Previous", "//li[@class='prev disabled']"),
    PREVIOUS("Previous", "//li[@class='prev']"),
    PAGE_NUMBER(null, "//li[###]/a[@href='#']"),
    NEXT("Next", "//li[@class='next']"),
    NEXT_DISABLED("Next", "//li[@class='next disabled']"),
    
    NO_MATCHING_RECORDS_ERROR("No matching records found", "//tbody[@role='alert']")
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