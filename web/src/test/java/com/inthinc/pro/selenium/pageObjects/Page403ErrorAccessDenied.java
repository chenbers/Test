package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AccessDenied403Enum;

public class Page403ErrorAccessDenied extends Masthead {

    public Page403ErrorAccessDenied() {
        checkMe.add(AccessDenied403Enum.TITLE);
        checkMe.add(AccessDenied403Enum.RETURN_TO_HOMEPAGE);
        checkMe.add(AccessDenied403Enum.P1);
    }
    
    
    public class Error403Links extends MastheadLinks{
        
        public TextLink returnToHomePage(){
            return new TextLink(AccessDenied403Enum.RETURN_TO_HOMEPAGE);
        }
    }
    public class Error403Texts extends MastheadTexts{
        
        public Text title(){
            return new Text(AccessDenied403Enum.TITLE);
        }
        
        public Text firstParagraph(){
            return new Text(AccessDenied403Enum.P1);
        }
        
        public TextTable lineItem(){
            return new TextTable(AccessDenied403Enum.LINE);
        }
    }
    
    public Error403Links _link(){
        return new Error403Links();
    }
    
    public Error403Texts _text(){
        return new Error403Texts();
    }

    @Override
    public SeleniumEnums setUrl() {
        return null;
    }
        
}
