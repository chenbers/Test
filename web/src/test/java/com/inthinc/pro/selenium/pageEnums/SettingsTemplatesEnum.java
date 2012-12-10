package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum SettingsTemplatesEnum implements SeleniumEnums {
	
	ACCOUNT_DROPDOWN("Account:","select-account"),
<<<<<<< HEAD
=======
	
>>>>>>> changed line endings
	TEMPLATE_BUTTON("",""),
	
	NEW_TEMPLATE_LINK("New ...","new-template"),
	OPEN_TEMPLATE_LINK("Open ...","edit-template"),
	CLONE_TEMPLATE_LINK("Clone From ...","clone-template"),
	RESET_TEMPLATE_LINK("Reset","settings-reset"),
	DELETE_TEMPLATE_LINK("Delete ...","delete-template"),
	COMMIT_TEMPLATE_LINK("Commit ...","commit-template"),
	
<<<<<<< HEAD
	TEMPLATE_NAME(null,"template-name"),
	TEMPLATE_DESCRIPTION(null,"template-description"),
=======
	TEMPLATE_NAME_TEXTFIELD(null,"template-name"),
	TEMPLATE_DESCRIPTION_TEXTFIELD(null,"template-description"),
>>>>>>> changed line endings
	
	VEHICLE_SETTING_ID_TEXT("ID","column-vehicle-settingID"),
	VEHICLE_NAME_TEXT("Name","column-vehicle-name"),
	VEHICLE_CATEGORY_TEXT("Category","column-vehicle-category"),
<<<<<<< HEAD
	VEHICLE_UNTI_TEXT("Unit","column-vehicle-unit"),
	DESIRED_VALUES_TEXT("Values","column-vehicle-desired-values"),
	DESIRED_MEDIFIED_TEXT("Modified","column-vehicle-desired-modified"),
=======
	VEHICLE_UNIT_TEXT("Unit","column-vehicle-unit"),
	DESIRED_VALUES_TEXT("Values","column-vehicle-desired-values"),
	DESIRED_MODIFIED_TEXT("Modified","column-vehicle-desired-modified"),
>>>>>>> changed line endings
	
	;

	private String text, url;
    private String[] IDs;
    
    private SettingsTemplatesEnum(String url){
    	this.url = url;
    }
    private SettingsTemplatesEnum(String text, String ...IDs){
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
	
	
<<<<<<< HEAD
}
=======
}
>>>>>>> changed line endings
