package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ConfiguratorBarEnum implements SeleniumEnums {
	
	EDIT_SINGLE_SETTINGS("Edit Single Settings", "link-edit-single-setting"),
	VEHICLE_SETTINGS("Vehicle Settings", "link-vehicle-settings"),
	SETTING_TEMPLATE("Settings Template", "link-configurator-templates"),
	APPLY_TEMPLATES("Apply Templates", "link-apply-templates"),
	
	LOGOUT("Logout", "template-settings-logout")
	
	;

	private String text, url;
    private String[] IDs;
    
    private ConfiguratorBarEnum(String url){
    	this.url = url;
    }
    private ConfiguratorBarEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public String[] getIDs() {
		// TODO Auto-generated method stub
		return IDs;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return url;
	}
	
}
