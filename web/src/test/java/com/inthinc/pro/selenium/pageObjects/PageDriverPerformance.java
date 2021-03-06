package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;

public class PageDriverPerformance extends NavigationBar {
	private String page = "driver";
	
	public PageDriverPerformance(){
		checkMe.add(DriverPerformanceEnum.DRIVER_NAME);
		checkMe.add(DriverPerformanceEnum.VIEW_ALL_TRIPS);
	}
	
	public class DriverPerformanceButtons extends NavigationBarButtons {
		public Button tools(){
			return new Button(PerformanceEnum.OVERALL_TOOLS);
		}
		
		public Button emailReport(){
			return new Button(PerformanceEnum.OVERALL_EMAIL);
		}
		
		public Button exportPDF(){
			return new Button(PerformanceEnum.OVERALL_PDF);
		}
		
		public Button maximizeSpeed(){
			Button button =new Button(PerformanceEnum.SPEED_DETAILS);
			button.replaceSubStringInMyEnum("*page*", page);
			return button;
		}
		
		public Button maximizeDrivingStyle(){
			Button button = new Button(PerformanceEnum.STYLE_DETAILS);
			button.replaceSubStringInMyEnum("*page*", page);
			return button;
		}
		
		public Button maximizeSeatBelt(){
			Button button = new Button(PerformanceEnum.SEATBELT_DETAILS);
			button.replaceSubStringInMyEnum("*page*", page);
			return button;
		}
	}

	public class DriverPerformanceDropDowns extends NavigationBarDropDowns {
	}

	public class DriverPerformanceLinks extends NavigationBarLinks {
		public TextLink breadCrumb(Integer position){
			return new TextLink(DriverPerformanceEnum.BREADCRUMB_ITEM, position);
		}
		
		public TextLink driverName(){
			return new TextLink(DriverPerformanceEnum.DRIVER_NAME);
		}
		
		public TextLink viewAllTrips() {
			return new TextLink(DriverPerformanceEnum.VIEW_ALL_TRIPS);
		}
		
		public TextLink overallDuration(TimeDuration duration){
			TextLink time = new TextLink(PerformanceEnum.OVERALL_TIME_FRAME, duration);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink speedDuration(TimeDuration duration){
			TextLink time = new TextLink(PerformanceEnum.SPEED_TIME_FRAME, duration);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink coachingEventsDuration(TimeDuration duration){
			return new TextLink(PerformanceEnum.COACHING_TIME_FRAME, duration);
		}
		
		public TextLink fuelEfficiencyDuration(TimeDuration duration){
			return new TextLink(PerformanceEnum.MPG_TIME_FRAME, duration);
		}
		
		public TextLink drivingStyleDuration(TimeDuration duration){
			TextLink time = new TextLink(PerformanceEnum.STYLE_TIME_FRAME, duration);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink seatBeltDuration(TimeDuration duration){
			TextLink time = new TextLink(PerformanceEnum.SEATBELT_TIME_FRAME, duration);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		
	}

	public class DriverPerformanceTextFields extends NavigationBarTextFields {
	}

	public class DriverPerformanceTexts extends NavigationBarTexts {
		public Text crashesPerMillionMilesTitle(){
			return new Text(PerformanceEnum.CRASHES_PER_TEXT);
		}
		public Text crashesPerMillionMilesValue(){
			return new Text(PerformanceEnum.CRASHES_PER_NUMBER);
		}
		public Text crashesPerMillionMilesTime(){
			return new Text(PerformanceEnum.CRASHES_PER_TIME_FRAME);
		}
		public Text totalCrashesTitle(){
			return new Text(PerformanceEnum.TOTAL_CRASHES_TEXT);
		}
		public Text totalCrashesValue(){
			return new Text(PerformanceEnum.TOTAL_CRASHES_NUMBER);
		}
		
		public Text overallScoreLabel(){
			return new Text(PerformanceEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScore(){
			return new Text(PerformanceEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text speedScore(){
			return new Text(PerformanceEnum.SPEED_SCORE_BOX);
		}
		
		public Text drivingStyleScore(){
			return new Text(PerformanceEnum.STYLE_SCORE_BOX);
		}
		
		public Text seatBeltScore(){
			return new Text(PerformanceEnum.SEATBELT_SCORE_BOX);
		}
		
		public Text overallTitle(){
			return new Text(PerformanceEnum.OVERALL_TITLE);
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
	
	public class DriverPerformancePopUps extends MastheadPopUps{
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

    @Override
    public SeleniumEnums setUrl() {
        return DriverPerformanceEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().maximizeDrivingStyle().isPresent() && _link().driverName().isPresent();
    }
	
}
