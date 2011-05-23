package com.inthinc.pro.selenium.pageObjects;


public class PageVehiclePerformanceStyle extends NavigationBar {
	
	public class VehicleStylePopUps extends PopUps{}
	public class VehicleStyleLinks extends NavigationBarLinks{}
	public class VehicleStyleTexts extends NavigationBarTexts{}
	public class VehicleStyleTextFields extends NavigationBarTextFields{}
	public class VehicleStyleDropDowns extends NavigationBarDropDowns{}
	public class VehicleStyleButtons extends NavigationBarButtons{}

	
	public VehicleStyleLinks _link(){
		return new VehicleStyleLinks();
	}
	public VehicleStyleLinks _button(){
		return new VehicleStyleLinks();
	}
	public VehicleStyleLinks _text(){
		return new VehicleStyleLinks();
	}
	public VehicleStyleLinks _textField(){
		return new VehicleStyleLinks();
	}
	public VehicleStyleLinks _dropDown(){
		return new VehicleStyleLinks();
	}
	public VehicleStylePopUps _popUp(){
		return new VehicleStylePopUps();
	}

}
