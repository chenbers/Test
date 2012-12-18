package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * 
 * @author larringt , dtanner Last Update: 11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class PageLogin extends Masthead {
	public PageLogin(){
		checkMe.add(LoginEnum.LOGIN_BUTTON);
	}
	
    public class LoginPopUps extends MastheadPopUps{}
    
    public LoginPopUps _popUp(){
    	return new LoginPopUps();
    }


    public LoginButtons _button(){
    	return new LoginButtons();
    }
    public class LoginButtons extends MastheadButtons{
    	public TextButton logIn(){
    		return new TextButton(LoginEnum.LOGIN_BUTTON);
    	}
    }
    
    public LoginTexts _text(){
    	return new LoginTexts();
    }
    public class LoginTexts extends MastheadTexts{
    	
    	public TextFieldLabel username(){
    		return new TextFieldLabel(LoginEnum.USERNAME_FIELD);
    	}
    	
    	public TextFieldLabel password(){
    		return new TextFieldLabel(LoginEnum.PASSWORD_FIELD);
    	}
    	
    	public Text error() {
    		return new Text(LoginEnum.ERROR_TEXT);
    	}
    }
    
    public LoginTextFields _textField(){
    	return new LoginTextFields();
    }
    public class LoginTextFields extends MastheadTextFields{
    	public TextField username(){
    		return new TextField(LoginEnum.USERNAME_FIELD);
    	}
    	public TextField password(){
    		return new TextField(LoginEnum.PASSWORD_FIELD);
    	}
    }
    
    public LoginLinks _link(){
    	return new LoginLinks();
    }
    public class LoginLinks extends MastheadLinks{
    	public TextLink forgotUsernameOrPassword(){
    		return new TextLink(LoginEnum.FORGOT_USERNAME_LINK);
    	}
    	
    	public TextLink logo() {
    		return new TextLink(LoginEnum.LOGO_TEXT);
    	}
    }
    
    @Override
    public PageLogin loginProcess(String username, String password) {
        if(!this._textField().username().isPresent())
            openLogout();
        new LoginTextFields().username().type(username);
        new LoginTextFields().password().type(password);
        new LoginButtons().logIn().click();
        if(isOnPage())
            addError("Login unsuccessful", "browser is still on login page. couldn't log in as: "+username+"/"+password + " (which could be expected)", ErrorLevel.PASS);
        return this;
    }
    
    
    public PageLogin openLogout() {
        open(LoginEnum.LOGOUT_URL);
        verifyOnPage();
        return this;
    }
    
    public LoginDropDowns _dropDown(){
    	return new LoginDropDowns();
    }
    
    public class LoginDropDowns extends MastheadDropDowns{
    	
    }

    @Override
    public SeleniumEnums setUrl() {
        return LoginEnum.LOGIN_URL;
    }


    @Override
    protected boolean checkIsOnPage() {
        return _button().logIn().isPresent() && 
        	   _textField().username().isPresent() &&
        	   _textField().password().isPresent();
    }
}
