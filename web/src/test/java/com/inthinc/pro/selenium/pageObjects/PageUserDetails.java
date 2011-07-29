package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;


public class PageUserDetails extends AdminBar {
    
    private static final String page = "person";
    
	//TODO: naming review this pageObject is clearly not complete?  what is it's status in Rally
	public UserDetailsLinks _link(){
		return new UserDetailsLinks();
	}
	public class UserDetailsLinks extends AdminBarLinks{}
	
	public UserDetailsTexts _text(){
		return new UserDetailsTexts();
	}
	public class UserDetailsTexts extends AdminBarTexts{}
	
	public UserDetailsTextFields _textField(){
		return new UserDetailsTextFields();
	}
	public class UserDetailsTextFields extends AdminBarTextFields{}
	
	public UserDetailsButtons _button(){
		return new UserDetailsButtons();
	}
	public class UserDetailsButtons extends AdminBarButtons{
	    
	    public TextButton delete(){
	        return new TextButton(AdminBarEnum.DETAILS_DELETE, page);
	    }
	    
	    public TextButton edit(){
	        return new TextButton(AdminBarEnum.EDIT, page);
	    }
	}
	
	public UserDetailsDropDowns _dropDown(){
		return new UserDetailsDropDowns();
	}
	public class UserDetailsDropDowns extends AdminBarDropDowns{}


	public UserDetailsPopUps _popUp(){
        return new UserDetailsPopUps();
    }
    
	public class UserDetailsPopUps extends AdminBarPopUps{}

}
