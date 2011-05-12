package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
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
		public Text crashesPerMillionMilesTitle(){
			return new Text(DriverPerformanceEnum.CRASHES_PER_TEXT);
		}
		public Text crashesPerMillionMilesValue(){
			return new Text(DriverPerformanceEnum.CRASHES_PER_NUMBER);
		}
		public Text crashesPerMillionMilesTime(){
			return new Text(DriverPerformanceEnum.CRASHES_PER_TIME_FRAME);
		}
		public Text totalCrashesTitle(){
			return new Text(DriverPerformanceEnum.TOTAL_CRASHES_TEXT);
		}
		public Text totalCrashesValue(){
			return new Text(DriverPerformanceEnum.TOTAL_CRASHES_NUMBER);
		}
		
		public Text overallScoreLabel(){
			return new Text(DriverPerformanceEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScore(){
			return new Text(DriverPerformanceEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text speedScore(){
			return new Text(DriverPerformanceEnum.SPEED_SCORE_BOX);
		}
		
		public Text drivingStyleScore(){
			return new Text(DriverPerformanceEnum.STYLE_SCORE_BOX);
		}
		
		public Text seatBeltScore(){
			return new Text(DriverPerformanceEnum.SEATBELT_SCORE_BOX);
		}
		
		public Text overallTitle(){
			return new Text(DriverPerformanceEnum.OVERALL_TITLE);
		}
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
