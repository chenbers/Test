package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminUserDetailsEnum implements SeleniumEnums {
    

    DEFAULT_URL(appUrl + "/admin/person"),
    
	
	USER_INFORMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/div[1]"),
	FIRST_NAME(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[1]/td[2]"),
	MIDDLE_NAME(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[2]/td[2]"),
	LAST_NAME(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[3]/td[2]"),
	SUFFIX(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[4]/td[2]"),
	DOB(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[5]/td[2]"),
	GENDER(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[1]/tbody/tr[6]/td[2]"),
	
	DRIVER_INFORMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/div[3]"),
	LICENSE_NUMBER(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[1]/td[2]"),
	CLASS(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]"),
	STATE(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]"),
	EXPIRATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[4]/td[2]"),
	CERTIFICATIONS(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[5]/td[2]"),
	DOT(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[6]/td[2]"),
	TEAM(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[7]/td[2]"),
	DRIVER_STATUS(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[2]/tbody/tr[8]/td[2]"),
	
	RFID_INFORMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/div[5]"),
	BAR_CODE(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[3]/tbody/tr[1]/td[2]"),
	ID_1(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[3]/tbody/tr[2]/td[2]"),
	ID_2(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[1]/table[3]/tbody/tr[3]/td[2]"),
	
	EMPLOYEE_INFORMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/div[1]"),
	EMP_ID(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[1]/td[2]"),
	REPORTS_TO(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[2]/td[2]"),
	TITLE(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[3]/td[2]"),
	LOCALE(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[4]/td[2]"),
	TIME_ZONE(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[5]/td[2]"),
	MEASUREMENT(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[6]/td[2]"),
	FUEL_EFFICIENCY_RATIO(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[7]/td[2]"),
	
	LOGIN_INFOMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/div[3]"),
	USER_NAME(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]"),
	GROUP(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]"),
	ROLES(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]"),
	USER_STATUS(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[2]/tbody/tr[4]/td[2]"),
	
	NOTIFICATIONS(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/div[5]"),
	EMAIL_1(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[1]/td[2]"),
	EMAIL_2(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[2]/td[2]"),
	TEXT_1(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[3]/td[2]"),
	TEXT_2(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[4]/td[2]"),
	PHONE_1(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[5]/td[2]"),
	PHONE_2(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[6]/td[2]"),
	INFORMATION(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[7]/td[2]"),
	WARNING(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[8]/td[2]"),
	CRITICAL(null, "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[3]/tbody/tr[9]/td[2]"), 
	
	;

    private String text, url;
    private String[] IDs;
    
    private AdminUserDetailsEnum(String url){
    	this.url = url;
    }
    private AdminUserDetailsEnum(String text, String ...IDs){
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
