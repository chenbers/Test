package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ApplyTemplatesEnum implements SeleniumEnums {
	
	ACCOUNT_DROPDOWN("Account:","select-account"),
	GROUP_NAV_TREE("Group","group-dropdown"),
	TEMPLATE_BUTTON("Template",""),
	
	AUDITION_LINK("Audition ...","audition-template"),
	APPLY_LINK("Apply ...","apply-template"),
	
	ACCEPT_TEMPLATE_VALUE_CHECKBOX("Accept Template Value","select-all"),
	VEHICLE_TEXT("Vehicle","column-setting-vehicleID"),
	SETTING_ID_TEXT("SetingID","column-setting-settingID"),
	SETTING_TEXT("Setting","column-setting-setting"),
	CONFLICTING_SETTINGS_TEXT("Conflicting Settings","column-setting-value"),
	DESIRED_VALUE_TEXT("Desired Value","column-setting-value"),
	TEMPLATE_VALUE_TEXT("Template Value","column-template-value"),
	
	
	;

	private String text, url;
    private String[] IDs;
    
    private ApplyTemplatesEnum(String url){
    	this.url = url;
    }
    private ApplyTemplatesEnum(String text, String ...IDs){
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
