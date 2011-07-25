package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.HosBarEnum;

public class HOSBar extends NavigationBar {
    

    protected static final String page = "hosReports";

    public class HOSBarLinks extends NavigationBarLinks {
	public TextLink hosDriverLogs(){
	    return new TextLink(HosBarEnum.DRIVER_LOGS);
	}
	
	public TextLink hosReports(){
	    return new TextLink(HosBarEnum.REPORTS);
	}
	
	public TextLink hosFuelStops(){
	    return new TextLink(HosBarEnum.FUEL_STOPS);
	}
    }

    public class HOSBarTexts extends NavigationBarTexts {
    }

    public class HOSBarTextFields extends NavigationBarTextFields {
    }

    public class HOSBarButtons extends NavigationBarButtons {
    }

    public class HOSBarDropDowns extends NavigationBarDropDowns {
    }



    public HOSBarLinks _link() {
	return new HOSBarLinks();
    }

    public HOSBarTexts _text() {
	return new HOSBarTexts();
    }

    public HOSBarButtons _button() {
	return new HOSBarButtons();
    }

    public HOSBarTextFields _textField() {
	return new HOSBarTextFields();
    }

    public HOSBarDropDowns _dropDown() {
	return new HOSBarDropDowns();
    }

}
