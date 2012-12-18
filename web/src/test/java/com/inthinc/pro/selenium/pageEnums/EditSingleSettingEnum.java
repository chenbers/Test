package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum EditSingleSettingEnum implements SeleniumEnums {
	
	ACCOUNT_DROPDOWN("Account:","select-account"),
	GROUP_DROPDOWN("Group","group-dropdown"),
	SETTINGS_DROPDOWN("Settings","settings-dropdown"),
	
	GET_SETTING_LINK("Get", "get-setting"),
	COMMIT_SETTINGS_LINK("Edit", "commit-settings"),
	DELETE_SETTINGS_LINK("Delete", "delete-settings"),
	
	SELECT_ALL_CHECKBOX("Select","select-all"),
	SELECT_CHECKBOX(null,"select-###"),
	
	VEHICLES_TEXT(null,"vehicles-###"),
	DESIRED_VALUES_TEXT(null,"values-###")
	
	
	
	;

	private String text, url;
    private String[] IDs;
    
    private EditSingleSettingEnum(String url){
    	this.url = url;
    }
    private EditSingleSettingEnum(String text, String ...IDs){
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
