package com.inthinc.pro.selenium.pageObjects;


public class PageVehiclePerformanceSeatBelt extends NavigationBar {
	
	public class VehicleSeatBeltPopUps extends PopUps{}
	public class VehicleSeatBeltLinks extends NavigationBarLinks{}
	public class VehicleSeatBeltTexts extends NavigationBarTexts{}
	public class VehicleSeatBeltTextFields extends NavigationBarTextFields{}
	public class VehicleSeatBeltDropDowns extends NavigationBarDropDowns{}
	public class VehicleSeatBeltButtons extends NavigationBarButtons{}

	
	public VehicleSeatBeltLinks _link(){
		return new VehicleSeatBeltLinks();
	}
	public VehicleSeatBeltLinks _button(){
		return new VehicleSeatBeltLinks();
	}
	public VehicleSeatBeltLinks _text(){
		return new VehicleSeatBeltLinks();
	}
	public VehicleSeatBeltLinks _textField(){
		return new VehicleSeatBeltLinks();
	}
	public VehicleSeatBeltLinks _dropDown(){
		return new VehicleSeatBeltLinks();
	}
	public VehicleSeatBeltPopUps _popUp(){
		return new VehicleSeatBeltPopUps();
	}

}
