package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsBar extends NavigationBar {

	protected class NotificationsBarLinks{
		
		public TextLinkContextSense redFlags(){
			return new TextLinkContextSense(NotificationsBarEnum.RED_FLAGS);
		}
	
	    public TextLinkContextSense safety() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY);
	    }
	
	    public TextLinkContextSense diagnostics() {
	    	return new TextLinkContextSense(NotificationsBarEnum.DIAGNOSTICS);
	    }
	
	    public TextLinkContextSense zones() {
	    	return new TextLinkContextSense(NotificationsBarEnum.ZONES);
	    }
	
	    public TextLinkContextSense hosExceptions() {
	    	return new TextLinkContextSense(NotificationsBarEnum.HOS_EXCEPTIONS);
	    }
	
	    public TextLinkContextSense emergency() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY);
	    }
	
	    public TextLinkContextSense crashHistory() {
	    	return new TextLinkContextSense(NotificationsBarEnum.SAFETY);
	    }
	}
}
