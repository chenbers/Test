package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceSeatBBeltEnum;


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
			return new TextLink(VehiclePerformanceSeatBBeltEnum.DRIVER_NAME_LINK);
		}
		
		public TextLink breadcrumbItem(Integer position){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.BREADCRUMB, position);
		}
		
		public TextLink duration(TimeDuration duration){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.OVERALL_TIME_FRAME_SELECTOR);
		}
		
		public TextLink location(Integer row){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.LOCATION_ENTRY, row);
		}
		
		public TextLink dateTimeSort(){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.DATE_TIME_HEADER);
		}
		
		public TextLink avgSpeedSort(){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.AVERAGE_SPEED_HEADER);
		}
		
		public TextLink topSpeedSort(){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.TOP_SPEED_HEADER);
		}
		
		public TextLink distanceSort(){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.DISTANCE_HEADER);
		}
		
		public TextLink exclude(Integer row){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.EXCLUDE, row);
		}
		
		public TextLink include(Integer row){
			return new TextLink(VehiclePerformanceSeatBBeltEnum.INCLUDE, row);
		}
		
	}
	public class VehicleSeatBeltTexts extends NavigationBarTexts{
		
		public Text dateTime(Integer row){
			return new Text(VehiclePerformanceSeatBBeltEnum.DATE_TIME_ENTRY, row);
		}
		
		public Text avgSpeed(Integer row){
			return new Text(VehiclePerformanceSeatBBeltEnum.AVERAGE_SPEED_ENTRY, row);
		}
		
		public Text topSpeed(Integer row){
			return new Text(VehiclePerformanceSeatBBeltEnum.TOP_SPEED_ENTRY, row);
		}
		
		public Text distance(Integer row){
			return new Text(VehiclePerformanceSeatBBeltEnum.DISTANCE_ENTRY, row);
		}
		
		public Text overallScoreLabel(){
			return new Text(VehiclePerformanceSeatBBeltEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScoreValue(){
			return new Text(VehiclePerformanceSeatBBeltEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text counter(){
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}
	}
	
	public class VehicleSeatBeltTextFields extends NavigationBarTextFields{}
	public class VehicleSeatBeltDropDowns extends NavigationBarDropDowns{}
	public class VehicleSeatBeltButtons extends NavigationBarButtons{
		
		public Button tools(){
			return new Button(VehiclePerformanceSeatBBeltEnum.OVERALL_TOOLS);
		}
		
		public Button emailReport(){
			return new Button(VehiclePerformanceSeatBBeltEnum.OVERALL_EMAIL_TOOL);
		}
		
		public Button exportToPDF(){
			return new Button(VehiclePerformanceSeatBBeltEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(VehiclePerformanceSeatBBeltEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button returnToPerformancePage(){
			return new Button(VehiclePerformanceSeatBBeltEnum.RETURN);
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
