package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminAddEditUser implements SeleniumEnums {
	/* Entry Labels */ //TODO: dtanner: finish this section up for all of the labels, if we want them
	FIRST_NAME("First Name","//td[@valign='top'][1]/table/tbody/tr[1]/td[@valign='top']/text()[1]"),
	FIRST_NAME_REQUIRED("*","//td[@valign='top'][1]/table/tbody/tr[1]/td[@valign='top']/span"),
	
	/* Drop downs and such */
	DROP_DOWNS(null, "edit-form:editPerson-***"),
	
	DRIVER_TEAM_DHX(null, "/table[@id='driverTable']/tbody/tr[7]/td[2]/span[2]/div/img"),
	USER_GROUP_DHX(null, "/table[@id='userTable']/tbody/tr[5]/td[2]/span[2]/div/img"),
	
	
	;

    private String text, url;
    private String[] IDs;
    
    private AdminAddEditUser(String url){
    	this.url = url;
    }
    private AdminAddEditUser(String text, String ...IDs){
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
