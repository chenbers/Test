package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceSeatBeltEnum;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;


public class PageDriverPerformanceSeatBelt extends NavigationBar {
	
	public PageDriverPerformanceSeatBelt(){
		url=null;
		checkMe.add(DriverPerformanceSeatBeltEnum.DETAILS_TITLE);
		checkMe.add(DriverPerformanceSeatBeltEnum.DRIVER_NAME_LINK);
	}
	
	public class DriverSeatBeltPopUps extends MastheadPopUps{

		public DriverSeatBeltPopUps() {
			super("seatBelt", Types.SINGLE, 3);
		}

		public Email emailReport() {
			return new Email();
		}

	}
	public class DriverSeatBeltLinks extends NavigationBarLinks{
		
		public TextLink driverName(){
			return new TextLink(DriverPerformanceSeatBeltEnum.DRIVER_NAME_LINK);
		}
		
		public TextLink breadcrumbItem(Integer position){
			return new TextLink(DriverPerformanceSeatBeltEnum.BREADCRUMB, position);
		}
		
		public TextLink duration(TimeDuration duration){
			return new TextLink(DriverPerformanceSeatBeltEnum.OVERALL_TIME_FRAME_SELECTOR);
		}
		
		public TextTableLink location(){
			return new TextTableLink(DriverPerformanceSeatBeltEnum.LOCATION_ENTRY);
		}
		
		public TextLink dateTimeSort(){
			return new TextLink(DriverPerformanceSeatBeltEnum.DATE_TIME_HEADER);
		}
		
		public TextLink avgSpeedSort(){
			return new TextLink(DriverPerformanceSeatBeltEnum.AVERAGE_SPEED_HEADER);
		}
		
		public TextLink topSpeedSort(){
			return new TextLink(DriverPerformanceSeatBeltEnum.TOP_SPEED_HEADER);
		}
		
		public TextLink distanceSort(){
			return new TextLink(DriverPerformanceSeatBeltEnum.DISTANCE_HEADER);
		}
		
		public TextTableLink exclude(){
			return new TextTableLink(DriverPerformanceSeatBeltEnum.EXCLUDE);
		}
		
		public TextTableLink include(){
			return new TextTableLink(DriverPerformanceSeatBeltEnum.INCLUDE);
		}
		
	}
	public class DriverSeatBeltTexts extends NavigationBarTexts{
		
		public TextTable dateTime(){
			return new TextTable(DriverPerformanceSeatBeltEnum.DATE_TIME_ENTRY);
		}
		
		public TextTable avgSpeed(){
			return new TextTable(DriverPerformanceSeatBeltEnum.AVERAGE_SPEED_ENTRY);
		}
		
		public TextTable topSpeed(){
			return new TextTable(DriverPerformanceSeatBeltEnum.TOP_SPEED_ENTRY);
		}
		
		public TextTable distance(){
			return new TextTable(DriverPerformanceSeatBeltEnum.DISTANCE_ENTRY);
		}
		
		public Text overallScoreLabel(){
			return new Text(DriverPerformanceSeatBeltEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScoreValue(){
			return new Text(DriverPerformanceSeatBeltEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text counter(){
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}
	}
	
	public class DriverSeatBeltTextFields extends NavigationBarTextFields{}
	public class DriverSeatBeltDropDowns extends NavigationBarDropDowns{}
	public class DriverSeatBeltButtons extends NavigationBarButtons{
		
		public Button tools(){
			return new Button(DriverPerformanceSeatBeltEnum.OVERALL_TOOLS);
		}
		
		public Button emailReport(){
			return new Button(DriverPerformanceSeatBeltEnum.OVERALL_EMAIL_TOOL);
		}
		
		public Button exportToPDF(){
			return new Button(DriverPerformanceSeatBeltEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(DriverPerformanceSeatBeltEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button returnToPerformancePage(){
			return new Button(DriverPerformanceSeatBeltEnum.RETURN);
		}
	}

	
	public DriverSeatBeltLinks _link(){
		return new DriverSeatBeltLinks();
	}
	public DriverSeatBeltButtons _button(){
		return new DriverSeatBeltButtons();
	}
	public DriverSeatBeltTexts _text(){
		return new DriverSeatBeltTexts();
	}
	public DriverSeatBeltTextFields _textField(){
		return new DriverSeatBeltTextFields();
	}
	public DriverSeatBeltDropDowns _dropDown(){
		return new DriverSeatBeltDropDowns();
	}
	public DriverSeatBeltPopUps _popUp(){
		return new DriverSeatBeltPopUps();
	}
}
