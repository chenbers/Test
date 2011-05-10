package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;

public class PageDriverPerformance extends NavigationBar {
	public class DriverPerformanceButtons extends NavigationBarButtons {
	}

	public class DriverPerformanceDropDowns extends NavigationBarDropDowns {
	}

	public class DriverPerformanceLinks extends NavigationBarLinks {
		public TextLink viewAllTrips() {
			return new TextLink(DriverPerformanceEnum.VIEW_ALL_TRIPS);
		}
	}

	public class DriverPerformanceTextFields extends NavigationBarTextFields {
	}

	public class DriverPerformanceTexts extends NavigationBarTexts {
	}

	public DriverPerformanceButtons _button() {
		return new DriverPerformanceButtons();
	}

	public DriverPerformanceDropDowns _dropDown() {
		return new DriverPerformanceDropDowns();
	}

	public DriverPerformanceLinks _link() {
		return new DriverPerformanceLinks();
	}

	public DriverPerformanceTexts _text() {
		return new DriverPerformanceTexts();
	}

	public DriverPerformanceTextFields _textField() {
		return new DriverPerformanceTextFields();
	}
}
