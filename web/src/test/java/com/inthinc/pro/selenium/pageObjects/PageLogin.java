package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

/****************************************************************************************
 * Purpose: Includes all methods and variables to process the TiwiPro Login Screen
 * 
 * @author larringt , dtanner Last Update: 11/18/Added comments and made changes to adhere to Java Coding Standards
 */

public class PageLogin extends Masthead {
    private ElementBase validate = new ElementBase();
    
    public class LoginPopUps extends MastheadPopUps{
    	public ForgotPassword forgotPassword(){
    		return new ForgotPassword();
    	}
    	
    	public LoginError loginError(){
    		return new LoginError();
    	}
    	public MessageSent messageSent(){
    	    return new MessageSent();
    	}
    }
    
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
    	public Text header(){
    		return new Text(LoginEnum.USERNAME_FIELD);
    	}
    	
    	public Text userName(){
    		return new Text(LoginEnum.USERNAME_LABEL);
    	}
    	public Text password(){
    		return new Text(LoginEnum.PASSWORD_LABEL);
    	}
    }
    
    public LoginTextFields _textField(){
    	return new LoginTextFields();
    }
    public class LoginTextFields extends MastheadTextFields{
    	public TextField userName(){
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
    	public TextLink forgotUsernamePassword(){
    		return new TextLink(LoginEnum.FORGOT_USERNAME_LINK);
    	}
    }
    

    public PageLogin loginProcess(String username, String password) {
        openLogout();
        new LoginTextFields().userName().type(username);
        new LoginTextFields().password().type(password);
        new LoginButtons().logIn().click();
        return this;
    }
    
    public PageLogin validatePage() {
    	validate.validateTextMatches(LoginEnum.FORGOT_USERNAME_LINK, LoginEnum.LOGIN_HEADER, LoginEnum.USERNAME_LABEL, 
                LoginEnum.PASSWORD_LABEL, LoginEnum.LOGIN_BUTTON);
        return this;
    }
    @Override
    public String getExpectedPath() {
        return LoginEnum.LOGIN_URL.getURL();
    }
    public PageLogin openLogout() {
        selenium.open(LoginEnum.LOGOUT_URL.getURL());//TODO: dtanner: needs to be updated to new way of doing things
        validatePage();
        return this;
    }
    
    public LoginDropDowns _dropDown(){
    	return new LoginDropDowns();
    }
    
    public class LoginDropDowns extends MastheadDropDowns{
    	
    }
}
