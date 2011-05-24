package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceSeatBeltEnum;


public class PageVehiclePerformanceSeatBelt extends NavigationBar {
	
	public class VehicleSeatBeltPopUps extends PopUps{

		public VehicleSeatBeltPopUps() {
			super("seatBelt", Types.SINGLE, 3);
		}

		public Email emailReport() {
			return new Email();
		}

	}
	public class VehicleSeatBeltLinks extends NavigationBarLinks{
		public TextLink driverName(){
			return new TextLink(VehiclePerformanceSeatBeltEnum.DRIVER_NAME_LINK);
		}
		
		public TextLink breadcrumbItem(Integer position){
			return new TextLink(VehiclePerformanceSeatBeltEnum.BREADCRUMB, position);
		}
		
		public TextLink duration(TimeDuration duration){
			return new TextLink(VehiclePerformanceSeatBeltEnum.OVERALL_TIME_FRAME_SELECTOR);
		}
		
		public TextLink location(Integer row){
			return new TextLink(VehiclePerformanceSeatBeltEnum.LOCATION_ENTRY, row);
		}
		
		public TextLink dateTimeSort(){
			return new TextLink(VehiclePerformanceSeatBeltEnum.DATE_TIME_HEADER);
		}
		
		public TextLink avgSpeedSort(){
			return new TextLink(VehiclePerformanceSeatBeltEnum.AVERAGE_SPEED_HEADER);
		}
		
		public TextLink topSpeedSort(){
			return new TextLink(VehiclePerformanceSeatBeltEnum.TOP_SPEED_HEADER);
		}
		
		public TextLink distanceSort(){
			return new TextLink(VehiclePerformanceSeatBeltEnum.DISTANCE_HEADER);
		}
		
		public TextLink exclude(Integer row){
			return new TextLink(VehiclePerformanceSeatBeltEnum.EXCLUDE, row);
		}
		
		public TextLink include(Integer row){
			return new TextLink(VehiclePerformanceSeatBeltEnum.INCLUDE, row);
		}
		
	}
	public class VehicleSeatBeltTexts extends NavigationBarTexts{
		
		public Text dateTime(Integer row){
			return new Text(VehiclePerformanceSeatBeltEnum.DATE_TIME_ENTRY, row);
		}
		
		public Text avgSpeed(Integer row){
			return new Text(VehiclePerformanceSeatBeltEnum.AVERAGE_SPEED_ENTRY, row);
		}
		
		public Text topSpeed(Integer row){
			return new Text(VehiclePerformanceSeatBeltEnum.TOP_SPEED_ENTRY, row);
		}
		
		public Text distance(Integer row){
			return new Text(VehiclePerformanceSeatBeltEnum.DISTANCE_ENTRY, row);
		}
		
		public Text overallScoreLabel(){
			return new Text(VehiclePerformanceSeatBeltEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScoreValue(){
			return new Text(VehiclePerformanceSeatBeltEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text counter(){
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}
	}
	
	public class VehicleSeatBeltTextFields extends NavigationBarTextFields{}
	public class VehicleSeatBeltDropDowns extends NavigationBarDropDowns{}
	public class VehicleSeatBeltButtons extends NavigationBarButtons{
		
		public Button tools(){
			return new Button(VehiclePerformanceSeatBeltEnum.OVERALL_TOOLS);
		}
		
		public Button emailReport(){
			return new Button(VehiclePerformanceSeatBeltEnum.OVERALL_EMAIL_TOOL);
		}
		
		public Button exportToPDF(){
			return new Button(VehiclePerformanceSeatBeltEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(VehiclePerformanceSeatBeltEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button returnToPerformancePage(){
			return new Button(VehiclePerformanceSeatBeltEnum.RETURN);
		}
	}

	
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
