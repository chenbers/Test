package com.inthinc.pro.selenium.pageObjects;


public class PageUserDetails extends AdminBar {
	
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
	public class UserDetailsButtons extends AdminBarButtons{}
	
	public UserDetailsDropDowns _dropDown(){
		return new UserDetailsDropDowns();
	}
	public class UserDetailsDropDowns extends AdminBarDropDowns{}


	public UserDetailsPopUps _popUp(){
        return new UserDetailsPopUps();
    }
    
	public class UserDetailsPopUps extends AdminBarPopUps{}

}
