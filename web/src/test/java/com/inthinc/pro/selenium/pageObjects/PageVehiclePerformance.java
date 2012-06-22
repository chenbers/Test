package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;

public class PageVehiclePerformance extends NavigationBar {
    private String page = "vehicle";
	
    public PageVehiclePerformance(){
        checkMe.add(VehiclePerformanceEnum.VEHICLE_NAME);
        checkMe.add(VehiclePerformanceEnum.VIEW_ALL_TRIPS);
    }
	
	public class VehiclePerformanceButtons extends NavigationBarButtons {
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

	public class VehiclePerformanceDropDowns extends NavigationBarDropDowns {
	}

	public class VehiclePerformanceLinks extends NavigationBarLinks {
		public TextLink breadCrumb(Integer position){
			return new TextLink(VehiclePerformanceEnum.BREADCRUMB_ITEM, position);
		}
		
		public TextLink vehicleName(){
			return new TextLink(VehiclePerformanceEnum.VEHICLE_NAME);
		}
		
		public TextLink viewAllTrips() {
			return new TextLink(VehiclePerformanceEnum.VIEW_ALL_TRIPS);
		}
		
		public TextLink overallDuration(TimeDuration timeFrame){
			TextLink time = new TextLink(PerformanceEnum.OVERALL_TIME_FRAME, timeFrame);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink speedDuration(TimeDuration timeFrame){
			TextLink time = new TextLink(PerformanceEnum.SPEED_TIME_FRAME, timeFrame);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink coachingEventsDuration(TimeDuration timeFrame){
			return new TextLink(PerformanceEnum.COACHING_TIME_FRAME, timeFrame);
		}
		
		public TextLink fuelEfficiencyDuration(TimeDuration timeFrame){
			return new TextLink(PerformanceEnum.MPG_TIME_FRAME, timeFrame);
		}
		
		public TextLink drivingStyleDuration(TimeDuration timeFrame){
			TextLink time = new TextLink(PerformanceEnum.STYLE_TIME_FRAME, timeFrame);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		public TextLink seatBeltDuration(TimeDuration timeFrame){
			TextLink time = new TextLink(PerformanceEnum.SEATBELT_TIME_FRAME, timeFrame);
			time.replaceSubStringInMyEnum("*page*", page);
			return time;
		}
		
		
	}

	public class VehiclePerformanceTextFields extends NavigationBarTextFields {
	}

	public class VehiclePerformanceTexts extends NavigationBarTexts {
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

	public VehiclePerformanceButtons _button() {
		return new VehiclePerformanceButtons();
	}

	public VehiclePerformanceDropDowns _dropDown() {
		return new VehiclePerformanceDropDowns();
	}

	public VehiclePerformanceLinks _link() {
		return new VehiclePerformanceLinks();
	}

	public VehiclePerformanceTexts _text() {
		return new VehiclePerformanceTexts();
	}

	public VehiclePerformanceTextFields _textField() {
		return new VehiclePerformanceTextFields();
	}
	
	public class VehiclePerformancePopUps extends MastheadPopUps{
    	public VehiclePerformancePopUps(){
    		super(page, Types.PERFORMANCE, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    }

	public VehiclePerformancePopUps _popUp() {
		return new VehiclePerformancePopUps();
	}
    @Override
    public PageVehiclePerformance validate() {
        assertStringContains("app/vehicle", getCurrentLocation());
        //TODO: it would be nice if every page had some level of "am I even on the right page" validation
        _text().overallTitle().validateTheDefaultValue();
        return this;
    }

    @Override
    public SeleniumEnums setUrl() {
        return VehiclePerformanceEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return  _link().viewAllTrips().isPresent()  &&
                _link().vehicleName().isPresent();
    }
}
