package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.FormsBarEnum;

public abstract class FormsBar extends NavigationBar {

    protected String page = "forms";
    
    protected class FormsBarButtons extends NavigationBarButtons{}
    protected class FormsBarTextFields extends NavigationBarTextFields{}
    protected class FormsBarTexts extends NavigationBarTexts{}
    protected class FormsBarDropDowns extends NavigationBarDropDowns{}
    

    protected class FormsBarLinks extends NavigationBarLinks{
        
        public TextLinkContextSense addForm(){
            return new TextLinkContextSense(FormsBarEnum.ADD_FORM, page);
        }
    
        public TextLinkContextSense submissions() {
            return new TextLinkContextSense(FormsBarEnum.SUBMISSIONS, page);
        }
    }
	
}
