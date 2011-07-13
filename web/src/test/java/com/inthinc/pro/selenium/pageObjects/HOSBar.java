package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.HosBarEnum;

public class HOSBar extends NotificationsEventsBar {

    public class HOSBarLinks extends NotificationsBarLinks {
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

    public class HOSBarTexts extends NotificationsBarTexts {
    }

    public class HOSBarTextFields extends NotificationsBarTextFields {
    }

    public class HOSBarButtons extends NotificationsBarButtons {
    }

    public class HOSBarDropDowns extends NotificationsBarDropDowns {
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
