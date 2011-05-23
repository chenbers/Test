package com.inthinc.pro.selenium.pageObjects;


public class PageDriverPerformanceSeatBelt extends NavigationBar {
	public class DriverSeatBeltPopUps extends PopUps{}
	public class DriverSeatBeltLinks extends NavigationBarLinks{}
	public class DriverSeatBeltTexts extends NavigationBarTexts{}
	public class DriverSeatBeltTextFields extends NavigationBarTextFields{}
	public class DriverSeatBeltDropDowns extends NavigationBarDropDowns{}
	public class DriverSeatBeltButtons extends NavigationBarButtons{}

	
	public DriverSeatBeltLinks _link(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltLinks _button(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltLinks _text(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltLinks _textField(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltLinks _dropDown(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltPopUps _popUp(){
		return new DriverSeatBeltPopUps();
	}
}
