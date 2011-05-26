package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsBar extends NavigationBar {
	
	protected String page="redFlags";
	
	protected NotificationsBar setPage(String page){
		this.page = page;
		return this;
	}
	

	protected static SeleniumEnums[] dhxlist = {
		NotificationsBarEnum.LEVEL_FILTER_DHX, 
		NotificationsBarEnum.TIME_FRAME_DHX, 
		NotificationsBarEnum.TEAM_SELECTION_DHX };
	
	protected class NotificationsBarButtons extends NavigationBarButtons{}
	protected class NotificationsBarTextFields extends NavigationBarTextFields{}
	protected class NotificationsBarTexts extends NavigationBarTexts{}
	protected class NotificationsBarDropDowns extends NavigationBarDropDowns{}
	

	protected class NotificationsBarLinks{
		
		
		
		
		public TextLinkContextSense redFlags(){
			return new TextLinkContextSense(NotificationsBarEnum.RED_FLAGS, page);
		}
	
	    public TextLinkContextSense safety() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY, page);
	    }
	
	    public TextLinkContextSense diagnostics() {
	    	return new TextLinkContextSense(NotificationsBarEnum.DIAGNOSTICS, page);
	    }
	
	    public TextLinkContextSense zones() {
	    	return new TextLinkContextSense(NotificationsBarEnum.ZONES, page);
	    }
	
	    public TextLinkContextSense hosExceptions() {
	    	return new TextLinkContextSense(NotificationsBarEnum.HOS_EXCEPTIONS, page);
	    }
	
	    public TextLinkContextSense emergency() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY, page);
	    }
	
	    public TextLinkContextSense crashHistory() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY);
	    }
	}
}
