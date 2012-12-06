package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum VehicleSettingsEnum implements SeleniumEnums {
	
	VEHICLE_ID_SELECT_TEXTFIELD("Select vehicle ID","vehicleID-select"),
	
	GET_SETTINGS_BUTTON("Get Setting","get-settings-button"),
	GET_SETTINGS_ACTION_BUTTON(null,"action-button"),
	
	COLUMN_ID_TEXT("ID","column-vehicle-settingID"),
	COLUMN_NAME_TEXT("Name","column-vehicle-name"),
	COLUMN_CATEGORY_TEXT("Category","column-vehicle-category"),
	COLUMN_UNIT_TEXT("Unit","column-vehicle-unit"),
	COLUMN_ACTUAL_TEXT("Actual","column-vehicle-actual"),
	COLUMN_ACTUAL_VALUES_TEXT("Values","column-vehicle-actual-values"),
	COLUMN_ACTUAL_MODIFIED_TEXT("Modified","column-vehicle-actual-modified"),
	COLUMN_DESIRED_TEXT("Desired","column-vehicle-desired"),
	COLUMN_DESIRED_VALUE_TEXT("Values","column-vehicle-desired-values"),
	COLUMN_DESIRED_VALUE_MODIFIED_TEXT("Modified","column-vehicle-desired-modified")
	
	;

	private String text, url;
    private String[] IDs;
    
    private VehicleSettingsEnum(String url){
    	this.url = url;
    }
    private VehicleSettingsEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public String[] getIDs() {
		return IDs;
	}

	@Override
	public String getURL() {
		return url;
	}

	
	
}
