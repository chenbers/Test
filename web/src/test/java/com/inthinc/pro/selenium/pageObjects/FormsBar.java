package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.FormsBarEnum;

public abstract class FormsBar extends NavigationBar {

    protected static final String page = "forms";
  
    protected class FormsBarLinks extends NavigationBarLinks {
        
        public TextLinkContextSense adminForm(){
            return new TextLinkContextSense(FormsBarEnum.ADMIN_FORM);
        }
    
        public TextLinkContextSense submissions() {
            return new TextLinkContextSense(FormsBarEnum.SUBMISSIONS);
        }

    }
    
    public class FormsBarTexts extends NavigationBarTexts {}

    public class FormsBarTextFields extends NavigationBarTextFields {}

    public class FormsBarButtons extends NavigationBarButtons {}

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
