package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkTime;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;

public class PageDriverPerformance extends NavigationBar {
	private String page = "driver";
	
	
	public class DriverPerformanceButtons extends NavigationBarButtons {
		public Button tools(){
			return new Button(DriverPerformanceEnum.OVERALL_TOOLS);
		}
		
		public Button emailReport(){
			return new Button(DriverPerformanceEnum.OVERALL_EMAIL);
		}
		
		public Button exportPDF(){
			return new Button(DriverPerformanceEnum.OVERALL_PDF);
		}
		
		public Button maximizeSpeed(){
			return new Button(DriverPerformanceEnum.SPEED_DETAILS);
		}
		
		public Button maximizeDrivingStyle(){
			return new Button(DriverPerformanceEnum.STYLE_DETAILS);
		}
		
		public Button maximizeSeatBelt(){
			return new Button(DriverPerformanceEnum.SEATBELT_DETAILS);
		}
	}

	public class DriverPerformanceDropDowns extends NavigationBarDropDowns {
	}

	public class DriverPerformanceLinks extends NavigationBarLinks {
		public TextLink breadCrumb(Integer position){
			return new TextLink(DriverPerformanceEnum.BREADCRUMB_ITEM, position);
		}
		
		public TextLink driverName(){
			return new TextLink(DriverPerformanceEnum.DRIVER_NAME_LINK);
		}
		
		public TextLink viewAllTrips() {
			return new TextLink(DriverPerformanceEnum.VIEW_ALL_TRIPS);
		}
		
		public TextLinkTime overallDuration(){
			return new TextLinkTime(DriverPerformanceEnum.OVERALL_TIME_FRAME);
		}
		
		public TextLinkTime speedDuration(){
			return new TextLinkTime(DriverPerformanceEnum.SPEED_TIME_FRAME);
		}
		
		public TextLinkTime coachingEventsDuration(){
			return new TextLinkTime(DriverPerformanceEnum.COACHING_TIME_FRAME);
		}
		
		public TextLinkTime fuelEfficiencyDuration(){
			return new TextLinkTime(DriverPerformanceEnum.MPG_TIME_FRAME);
		}
		
		public TextLinkTime drivingStyleDuration(){
			return new TextLinkTime(DriverPerformanceEnum.STYLE_TIME_FRAME);
		}
		
		public TextLinkTime seatBeltDuration(){
			return new TextLinkTime(DriverPerformanceEnum.SEATBELT_TIME_FRAME);
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
	
	public class DriverPerformancePopUps extends PopUps{
    	public DriverPerformancePopUps(){
    		super(page, Types.PERFORMANCE, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    }

	public DriverPerformancePopUps _popUp() {
		return new DriverPerformancePopUps();
	}
	
}
