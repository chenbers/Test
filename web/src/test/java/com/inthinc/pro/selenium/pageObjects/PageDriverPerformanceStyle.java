package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;

public class PageDriverPerformanceStyle extends NavigationBar {
	
	public class DriverStylePopUps extends PopUps{}
	public class DriverStyleLinks extends NavigationBarLinks{
		public TextLink driverName(){
			return new TextLink(DriverPerformanceEnum.EXPANDED_DRIVER_NAME_LINK, "Style");
		}
		
		public TextLink breadCrumb(Integer position){
			return new TextLink(DriverPerformanceEnum.EXPANDED_BREADCRUMB, "Style", position); 
		}
	}
	public class DriverStyleTexts extends NavigationBarTexts{}
	public class DriverStyleTextFields extends NavigationBarTextFields{}
	public class DriverStyleDropDowns extends NavigationBarDropDowns{}
	public class DriverStyleButtons extends NavigationBarButtons{}

	
	public DriverStyleLinks _link(){
		return new DriverStyleLinks();
	}
	public DriverStyleLinks _button(){
		return new DriverStyleLinks();
	}
	public DriverStyleLinks _text(){
		return new DriverStyleLinks();
	}
	public DriverStyleLinks _textField(){
		return new DriverStyleLinks();
	}
	public DriverStyleLinks _dropDown(){
		return new DriverStyleLinks();
	}
	public DriverStylePopUps _popUp(){
		return new DriverStylePopUps();
	}
	
	
	
}
