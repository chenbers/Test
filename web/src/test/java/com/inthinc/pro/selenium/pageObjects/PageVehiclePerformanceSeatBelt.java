package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceSeatBeltEnum;


public class PageVehiclePerformanceSeatBelt extends NavigationBar {
	private static String page = "SeatBelt";
	
	public class VehicleSeatBeltPopUps extends MastheadPopUps{

		public VehicleSeatBeltPopUps() {
			super("seatBelt", Types.SINGLE, 3);
		}

		public Email emailReport() {
			return new Email();
		}

	}
	public class VehicleSeatBeltLinks extends NavigationBarLinks{
		public TextLink vehicleName(){
			return new TextLink(VehiclePerformanceEnum.VEHICLE_NAME);
		}
		
		public TextTableLink breadcrumbItem(){
			return new TextTableLink(VehiclePerformanceEnum.EXPANDED_BREADCRUMB, page);
		}
		
		public TextLink duration(TimeDuration duration){
			return new TextLink(VehiclePerformanceSeatBeltEnum.OVERALL_TIME_FRAME_SELECTOR, duration);
		}
		
		public TextTableLink location(){
			return new TextTableLink(VehiclePerformanceSeatBeltEnum.LOCATION_ENTRY);
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
		
		public TextTableLink exclude(){
			return new TextTableLink(VehiclePerformanceEnum.EXCLUDE, page);
		}
		
		public TextTableLink include(){
			return new TextTableLink(VehiclePerformanceEnum.INCLUDE, page);
		}
		
	}
	public class VehicleSeatBeltTexts extends NavigationBarTexts{
		
		public TextTable dateTime(){
			return new TextTable(VehiclePerformanceSeatBeltEnum.DATE_TIME_ENTRY);
		}
		
		public TextTable avgSpeedUnits(){
			return new TextTable(VehiclePerformanceSeatBeltEnum.AVERAGE_SPEED_ENTRY);
		}
		
		public TextTable topSpeedUnits(){
			return new TextTable(VehiclePerformanceSeatBeltEnum.TOP_SPEED_ENTRY);
		}
		
		public TextTable distanceUnits(){
			return new TextTable(VehiclePerformanceSeatBeltEnum.DISTANCE_ENTRY);
		}
		
		public Text overallScoreLabel(){
			return new Text(VehiclePerformanceSeatBeltEnum.OVERALL_SCORE_LABEL);
		}
		
		public Text overallScoreValue(){
			return new Text(VehiclePerformanceSeatBeltEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text records(){
			return new Text(PerformanceEnum.RECORDS);
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
	public VehicleSeatBeltButtons _button(){
		return new VehicleSeatBeltButtons();
	}
	public VehicleSeatBeltTexts _text(){
		return new VehicleSeatBeltTexts();
	}
	public VehicleSeatBeltTextFields _textField(){
		return new VehicleSeatBeltTextFields();
	}
	public VehicleSeatBeltDropDowns _dropDown(){
		return new VehicleSeatBeltDropDowns();
	}
	public VehicleSeatBeltPopUps _popUp(){
		return new VehicleSeatBeltPopUps();
	}

	
	public class VehicleSeatBeltPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public VehicleSeatBeltPager _page(){
        return new VehicleSeatBeltPager();
    }
    @Override
    public SeleniumEnums setUrl() {
        return VehiclePerformanceSeatBeltEnum.DEFAULT_URL;
    }
    @Override
    protected boolean checkIsOnPage() {
        return _button().tools().isPresent() &&
                _link().avgSpeedSort().isPresent();
    }
    
}
