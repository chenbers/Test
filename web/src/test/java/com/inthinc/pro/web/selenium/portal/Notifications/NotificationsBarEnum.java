package com.inthinc.pro.web.selenium.portal.Notifications;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum NotificationsBarEnum implements SeleniumEnums {
	
	/* Navigation Bar for Notifications */
	RED_FLAGS("Red Flags", "**-redFlags", "//div[@class='sub_nav-bg']/ul/li[1]/a", "//li[@id='redflagtab']/a" ),
	SAFETY("Safety","**-safety","//div[@class='sub_nav-bg']/ul/li[2]/a", "//li[@id='safetytab']/a"),
	DIAGNOSTICS("Diagnostics", "**-diagnostics", "//div[@class='sub_nav-bg']/ul/li[3]/a", "//li[@id='diagnosticstab']/a"),
	ZONES("Zones", "**-zoneEvents", "//div[@class='sub_nav-bg']/ul/li[4]/a", "//li[@id='zoneEventstab']"),
	HOS_EXCEPTIONS("HOS Exceptions", "**-hosEvents", "//div[@class='sub_nav-bg']/ul/li[5]/a", "//li[@id='hosEventstab']"),
	EMERGENCY("Emergency", "**-emergency" , "//div[@class='sub_nav-bg']/ul/li[6]/a", "//li[@id='emergencytab']"),
	CRASH_HISTORY("Crash History", "**-crashHistory", "//div[@class='sub_nav-bg']/ul/li[7]/a", "//li[@id='crashhistorytab']"),
	
	/* Masthead of Notifications */
	
	TOOLS(null, null, "//img[@title='Tools Menu']","//span[@class='rich-cm-attached'/img"),
	EDIT_COLUMNS("Edit Columns","**_search:**_editColumns", "//a[@class='ls_tab_edit_columns']", "//a[@title='Edit Columns'"),
	REFRESH("Refresh","**_search:**_refresh", "//li[@class='l select']/button[@class='left']", "//li[@class='l select']/button[@type='button']"),
	TITLE(null, null, "//div[@class='panel_title']/span[1]", null),
	
	;

	
	private String text, ID, xpath, xpath_alt, current="redFlags";
	
	private NotificationsBarEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
	}
	
	public void setCurrent(String current) {
		this.current = current;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public String getID() {
		return ID.replace("**", current);
	}

	public String getXpath() {
		return xpath;
	}

	public String getXpath_alt() {
		return xpath_alt;
	}

}
