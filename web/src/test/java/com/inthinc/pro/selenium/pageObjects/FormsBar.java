package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.FormsBarEnum;

public abstract class FormsBar extends NavigationBar {

    protected static final String page = "forms";
  
    protected class FormsBarLinks extends NavigationBarLinks {
        
        public TextLinkContextSense manage(){
            return new TextLinkContextSense(FormsBarEnum.MANAGE);
        }
        
        public TextLinkContextSense published(){
            return new TextLinkContextSense(FormsBarEnum.PUBLISHED);
        }        
    
        public TextLinkContextSense submissions() {
            return new TextLinkContextSense(FormsBarEnum.SUBMISSIONS);
        }

        public TextLinkContextSense customers() {
            return new TextLinkContextSense(FormsBarEnum.CUSTOMERS);
        }
//THESE FOUR CAN BE MOVED UP TO THE MASTHEAD ONCE WE MERGE THE FORMS AND CURRENT PORTAL INTERFACES        
        public TextLink messages() {
        	return new TextLink(FormsBarEnum.MESSAGES);
        }
        
        public TextLink helpLink() {
        	return new TextLink(FormsBarEnum.HELP);
        }
        
        public TextLink account() {
        	return new TextLink(FormsBarEnum.ACCOUNT);
        }
        
        public TextLink logout() {
        	return new TextLink(FormsBarEnum.LOGOUT);
        }
    }
    
    public class FormsBarTexts extends NavigationBarTexts {}

    public class FormsBarTextFields extends NavigationBarTextFields {}

    public class FormsBarButtons extends NavigationBarButtons {
// THIS CAN BE MOVED UP TO THE MASTHEAD ONCE WE MERGE THE FORMS AND CURRENT PORTAL INTERFACES    	
    	public Button account() {
    		return new Button(FormsBarEnum.ACCOUNT_IMAGE);
    	}
    	
    }

    public class FormsBarDropDowns extends NavigationBarDropDowns {}
    
//    public FormsBarLinks _link() {
//	return new FormsBarLinks();
//    }
//
//    public FormsBarTexts _text() {
//	return new FormsBarTexts();
//    }
//
//    public FormsBarButtons _button() {
//	return new FormsBarButtons();
//    }
//
//    public FormsBarTextFields _textField() {
//	return new FormsBarTextFields();
//    }
//
//    public FormsBarDropDowns _dropDown() {
//	return new FormsBarDropDowns();
//    }
}
