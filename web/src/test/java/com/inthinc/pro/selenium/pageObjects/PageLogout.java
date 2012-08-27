package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.LogoutEnum;


public class PageLogout extends Masthead {
	public PageLogout(){
		checkMe.add(LogoutEnum.RETURN_BUTTON);
	}
    
    public class LogoutButtons extends MastheadButtons{
    	public TextButton returnToLoginPage(){
    		return new TextButton(LogoutEnum.RETURN_BUTTON);
    	}
    }
    
    public class LogoutTexts extends MastheadTexts{
    	
    	public Text successful(){
    		return new Text(LogoutEnum.SUCCESSFUL_TEXT);
    	}
    	public Text exitBrowser(){
    		return new Text(LogoutEnum.EXIT_BROWSER_TEXT);
    	}
    }
    
    public class LogoutPopUps extends MastheadPopUps{}
    
    public class LogoutTextFields extends MastheadTextFields{}
    
    public class LogoutLinks extends MastheadLinks{} 

    public class LogoutDropDowns extends MastheadDropDowns{}
    
    public LogoutPopUps _popUp(){
    	return new LogoutPopUps();
    }

    public LogoutButtons _button(){
    	return new LogoutButtons();
    }
    
    public LogoutTexts _text(){
    	return new LogoutTexts();
    }
    
    public LogoutTextFields _textField(){
    	return new LogoutTextFields();
    }
    
    public LogoutLinks _link(){
    	return new LogoutLinks();
    }
    
    public LogoutDropDowns _dropDown(){
    	return new LogoutDropDowns();
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return LogoutEnum.LOGOUT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().returnToLoginPage().isPresent() && 
        	   _text().successful().isPresent() &&
        	   _text().exitBrowser().isPresent();
    }
}
