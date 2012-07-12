package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsBar extends NavigationBar {
    
    protected String page = "redFlags";
    
    protected class NotificationsBarButtons extends NavigationBarButtons{}
    protected class NotificationsBarTextFields extends NavigationBarTextFields{}
    protected class NotificationsBarTexts extends NavigationBarTexts{}
    protected class NotificationsBarDropDowns extends NavigationBarDropDowns{}
    

    protected class NotificationsBarLinks extends NavigationBarLinks{
        
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
            return new TextLinkContextSense(NotificationsBarEnum.EMERGENCY, page);
        }
    
        public TextLinkContextSense crashHistory() {
            return new TextLinkContextSense(NotificationsBarEnum.CRASH_HISTORY, page);
        }
        
        public TextLink editColumns(){
            return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
        }
    }
}
