package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.SeleniumEnum;

// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LiveFleetEnum implements SeleniumEnum {
	
    DEFAULT_URL("liveFleet"),
    
	/* Main LiveFleet Page Elements*/
	TABLE_DRIVER_DATA(null, "driversDataTablePanel", null, null),
	LABEL_DISPATCH("Dispatch",null,"//div[@id=wrapper]/table/tbody/tr[1]/td[1]/div/div/span",null ),
	USERNAME_LABEL("User Name:", null, "//form[@id='loginForm']/table/tbody/tr[1]/td[1]", null),
	
	LINK_CHANGE_DEFAULT_VIEW("change", "liveFleetMapForm:liveFleetGo", null, null)
	;
	
	
	private String text, ID, xpath, xpath_alt, url;
	
	private LiveFleetEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
		this.url=null;
	}
	
	private LiveFleetEnum(String url){
	    this.url = url;
	    this.text=null;
	    this.ID=null;
	    this.xpath=null;
	    this.xpath_alt=null;
	}
	
	public String getURL(){
	    return url;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public String getID() {
		return ID;
	}

	public String getXpath() {
		return xpath;
	}

	public String getXpath_alt() {
		return xpath_alt;
	}
	
	public String getError() {
	    return this.name();
	}
}
