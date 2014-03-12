package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AssetsDevicesEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/assets/devices"),
	
	TITLE(null, null), //does not have a title yet
	
	ENTRIES_DROPDOWN("10", "//select[@name='deviceTable_length']"),
	ENTRIES_LABEL("Show X entries", "//div[2]/div/div/div/div/div/div/label"),
	SEARCH_LABEL("Search:", "//div[3]/div/label"),
	SEARCH_TEXTFIELD(null, "//input[@aria-controls='deviceTable']"),
	SELECT_ALL_CHECKBOX(null, "//i[contains(@class,'selectColumnButton')]"),
	//SHOW/HIDE COLUMNS SECTION
	SHOW_HIDE_COLUMNS_LINK("Show / Hide Columns", "//button[@class='ColVis_Button TableTools_Button ColVis_MasterButton']"),
	VEHICLE_ID_CHECKBOX(null, "//button[1]/span/span[1]/input"),
	VEHICLE_ID_CHECKBOX_LABEL("Vehicle ID", "//button[1]/span/span[2]"),
	PRODUCT_CHECKBOX(null, "//button[2]/span/span[1]/input"),
	PRODUCT_CHECKBOX_LABEL("Product", "//button[2]/span/span[2]"),
    IMEI_CHECKBOX(null, "//button[3]/span/span[1]/input"),
    IMEI_CHECKBOX_LABEL("IMEI", "//button[3]/span/span[2]"),
    ALTERNATE_IMEI_CHECKBOX(null, "//button[4]/span/span[1]/input"),
    ALTERNATE_IMEI_CHECKBOX_LABEL("Alternate IMEI", "//button[4]/span/span[2]"),
	SIM_CARD_CHECKBOX(null, "//button[5]/span/span[1]/input"),
    SIM_CARD_CHECKBOX_LABEL("SIM Card", "//button[5]/span/span[2]"),	
	SERIAL_NUMBER_CHECKBOX(null, "//button[6]/span/span[1]/input"),
	SERIAL_NUMBER_CHECKBOX_LABEL("Serial Number", "//button[6]/span/span[2]"),
	PHONE_CHECKBOX(null, "//button[7]/span/span[1]/input"),
	PHONE_CHECKBOX_LABEL("Phone", "//button[7]/span/span[2]"),
	STATUS_CHECKBOX(null, "//button[8]/span/span[1]/input"),
	STATUS_CHECKBOX_LABEL("Status", "//button[8]/span/span[2]"),
	MCM_ID_CHECKBOX(null, "//button[9]/span/span[1]/input"),
	MCM_ID_CHECKBOX_LABEL("MCM ID", "//button[9]/span/span[2]"),
	RESTORE_ORIGINAL_LINK("Restore original", "//button[@class='ColVis_Button TableTools_Button ColVis_Restore']"),
	//TABLE ITEMS
	SORT_BY_DEVICE_LINK("Device", "//th[@aria-label='Device: activate to sort column ascending']"),
	SORT_BY_VEHICLE_LINK("Vehicle ID", "//th[@aria-label='Vehicle ID: activate to sort column ascending']"),
	SORT_BY_PRODUCT_LINK("Driver", "//th[@aria-label='Product: activate to sort column ascending']"),
	SORT_BY_IMEI_LINK("IMEI", "//th[@aria-label='IMEI: activate to sort column ascending']"),
	SORT_BY_ALTERNATE_IMEI_LINK("Alternate IMEI", "//th[@aria-label='Alternate IMEI: activate to sort column ascending']"),
	SORT_BY_SIM_CARD_LINK("SIM Card", "//th[@aria-label='SIM Card: activate to sort column ascending']"),
	SORT_BY_SERIAL_NUMBER_LINK("Serial Number", "//th[@aria-label='Serial Number: activate to sort column ascending']"),
	SORT_BY_PHONE_LINK("Phone", "//th[@aria-label='Phone: activate to sort column ascending']"),
	SORT_BY_STATUS_LINK("Status", "//th[@aria-label='Status: activate to sort column ascending']"),
	SORT_BY_MCM_ID_LINK("MCM ID", "//th[@aria-label='MCM ID: activate to sort column ascending']"),
	//INFORMATION SECTION
	EDIT_BUTTON("Edit", "//button[@id='btnDetailEdit']"),
	SAVE_BUTTON("Save", "//button[@id='btnDetailSave']"),
	CANCEL_BUTTON("Cancel", "//button[@id='btnDetailCancel']"),
	DELETE_BUTTON("Delete", "//button[@id='btnDelete']"),
	//DETAILS SECTION
	DEVICE_LABEL("Device", "//div/div/h4"),
	DEVICE_TEXT(null, "deviceIdValue"),
	PRODUCT_LABEL("Product", "//div[3]/label"),
	PRODUCT_TEXT("Product", "deviceType"),
	MCM_ID_LABEL("MCM ID", "//div[4]/label"),
	MCM_ID_TEXT(null, "mcmid"),
	IMEI_LABEL("IMEI", "//div[5]/label"),
	IMEI_TEXT(null, "imei"),
	SIM_CARD_LABEL("SIM Card", "//div[6]/label"),
	SIM_CARD_TEXT(null, "sim"),   
	PHONE_LABEL("Phone", "//div[7]/label"),
	PHONE_TEXT(null, "phone"),
	ALTERNATE_IMEI_LABEL("Alternate IMEI", "//div[8]/label"),
	ALTERNATE_IMEI_TEXT(null, "altimei"), 
	SERIAL_NUMBER_LABEL("Serial Number", "//div[9]/label"),
	SERIAL_NUMBER_TEXT(null, "serialNum"),
	//PROFILE SECTION
	PROFILE_LABEL("Profile", "//form/div[10]/h4"),
	STATUS_LABEL("Status", "//div[11]/label"),
	STATUS_TEXT(null, "status"),
	STATUS_DROPDOWN(null, "statusInput"),
    ACTIVATED_LABEL("Activated", "//div[12]/label"),
    ACTIVATED_TEXT(null, "activated"),
	//ASSIGNMENT SECTION
	ASSIGNMENT_HEADER("Assignment", "//form/div[13]/h4"),
    ASSIGNED_VEHICLE_LABEL("Vehicle ID", "//div[14]/nobr/label"),
    VEHICLE_TEXT(null, "vehicleName"),
    ASSIGNED_VEHICLE_DROPDOWN(null, "assignedVehicleInput"),
	//TABLE ITEMS
    DEVICE_ENTRY_CHECKBOX(null, "//tr[###]/td[1]/input[@type='checkbox']"),
    DEVICE_ENTRY(null, "//tr[###]/td[2]"),
    VEHICLE_ENTRY(null, "//tr[###]/td[3]"),
    PRODUCT_ENTRY(null, "//tr[###]/td[4]"),
    IMEI_ENTRY(null, "//tr[###]/td[5]"),
    ALTERNATE_IMEI_ENTRY(null, "//tr[###]/td[6]"),
    SIM_CARD_ENTRY(null, "//tr[###]/td[7]"),
    SERIAL_NUMBER_ENTRY(null, "//tr[###]/td[8]"),
    PHONE_ENTRY(null, "//tr[###]/td[9]"),
    STATUS_ENTRY(null, "//tr[###]/td[10]"),    
    MCM_ID_ENTRY(null, "//tr[###]/td[11]"),
	
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
    
    private AssetsDevicesEnum(String url){
    	this.url = url;
    }
    private AssetsDevicesEnum(String text, String ...IDs){
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