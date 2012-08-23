package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.FormsBarEnum;

public abstract class FormsBar extends NavigationBar {

    protected String page = "forms";
    
    protected class FormsBarButtons{}
    protected class FormsBarTextFields{}
    protected class FormsBarTexts{}
    protected class FormsBarDropDowns{}
    

    protected class FormsBarLinks {
        
        public TextLinkContextSense addForm(){
            return new TextLinkContextSense(FormsBarEnum.ADD_FORM, page);
        }
    
        public TextLinkContextSense submissions() {
            return new TextLinkContextSense(FormsBarEnum.SUBMISSIONS, page);
        }

    }
	
}
