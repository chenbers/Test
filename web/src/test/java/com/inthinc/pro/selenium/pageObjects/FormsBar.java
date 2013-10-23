package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.FormsBarEnum;

public abstract class FormsBar extends NavigationBar {
    
    protected static final String page = "forms";
    
    protected class FormsBarLinks extends NavigationBarLinks {
        
        public TextLinkContextSense manage() {
            return new TextLinkContextSense(FormsBarEnum.MANAGE);
        }
        
        public TextLinkContextSense published() {
            return new TextLinkContextSense(FormsBarEnum.PUBLISHED);
        }
        
        public TextLinkContextSense submissions() {
            return new TextLinkContextSense(FormsBarEnum.SUBMISSIONS);
        }
        
        public TextLinkContextSense customers() {
            return new TextLinkContextSense(FormsBarEnum.CUSTOMERS);
        }
    }
    
    public class FormsBarTexts extends NavigationBarTexts {}
    
    public class FormsBarTextFields extends NavigationBarTextFields {}
    
    public class FormsBarButtons extends NavigationBarButtons {}
    
    public class FormsBarDropDowns extends NavigationBarDropDowns {}
    
    // public FormsBarLinks _link() {
    // return new FormsBarLinks();
    // }
    //
    // public FormsBarTexts _text() {
    // return new FormsBarTexts();
    // }
    //
    // public FormsBarButtons _button() {
    // return new FormsBarButtons();
    // }
    //
    // public FormsBarTextFields _textField() {
    // return new FormsBarTextFields();
    // }
    //
    // public FormsBarDropDowns _dropDown() {
    // return new FormsBarDropDowns();
    // }
}
