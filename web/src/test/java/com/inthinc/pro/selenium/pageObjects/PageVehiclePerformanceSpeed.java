package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceSpeedEnum;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;

public class PageVehiclePerformanceSpeed extends NavigationBar {
	
	private static String page = "speedForm";
	private static String page2 = "Speed";
	
	public VehicleSpeedButtons _button(){
		return new VehicleSpeedButtons();
	}
	
	public class VehicleSpeedButtons{
		public Button tools(){
			return new Button(VehiclePerformanceSpeedEnum.OVERALL_TOOLS);
		}
		
		public Button returnToPerformancePage(){
			return new Button(VehiclePerformanceSpeedEnum.RETURN);
		}
		
		public Button exportToPDF(){
			return new Button(VehiclePerformanceSpeedEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(VehiclePerformanceSpeedEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button emailReport(){
			return new Button(VehiclePerformanceSpeedEnum.OVERALL_EMAIL_TOOL);
		}
		
		public Button postedSpeed(){
			return new Button(VehiclePerformanceSpeedEnum.POSTED_ENTRY_SBS_BUTTON);
		}
	}
	
	public VehicleSpeedLinks _link(){
		return new VehicleSpeedLinks();
	}
	
	public class VehicleSpeedLinks{
		
		public TextLink _overall(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_OVERALL_LINK);
		}
		
		public TextLink _1_30(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_1_30_LINK);
		}
		
		public TextLink _31_40(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_31_40_LINK);
		}
		
		public TextLink _41_54(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_41_54_LINK);
		}
		
		
		public TextLink _55_64(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_55_64_LINK);
		}
		
		public TextLink _65_80(){
			return new TextLink(VehiclePerformanceSpeedEnum.BREAKDOWN_65_80_LINK);
		}
		
		public TextTableLink location(){
			return new TextTableLink(VehiclePerformanceSpeedEnum.LOCATION_ENTRY);
		}
		
		public TextLink duration(TimeDuration duration){
			return new TextLink(VehiclePerformanceSpeedEnum.TIME_FRAME_SELECTOR, duration);
		}
		
		public TextTableLink exclude(){
			return new TextTableLink(VehiclePerformanceEnum.EXCLUDE, page2);
		}
		public TextTableLink include(){
			return new TextTableLink(VehiclePerformanceEnum.INCLUDE, page2);
		}
		
		public TextLink vehicleName(){
			return new TextLink(VehiclePerformanceEnum.VEHICLE_NAME_LINK, page2);
		}
		
		public TextLink breadCrumb(Integer selection){
			return new TextLink(VehiclePerformanceEnum.EXPANDED_BREADCRUMB, page2, selection);
		}
		
		public TextLink dateTimeSort(){
			return new TextLink(VehiclePerformanceSpeedEnum.DATE_TIME_HEADER);
		}
		
		public TextLink postedSpeedSort(){
			return new TextLink(VehiclePerformanceSpeedEnum.POSTED_HEADER);
		}
		
		public TextLink avgSpeedSort(){
			return new TextLink(VehiclePerformanceSpeedEnum.AVERAGE_HEADER);
		}
		
		public TextLink topSpeedSort(){
			return new TextLink(VehiclePerformanceSpeedEnum.TOP_HEADER);
		}
		
		public TextLink distanceSort(){
			return new TextLink(VehiclePerformanceSpeedEnum.DISTANCE_HEADER);
		}
		
	}

	public VehicleSpeedTexts _text(){
		return new VehicleSpeedTexts();
	}
	
	public class VehicleSpeedTexts{
		
		public Text mainOverall(){
			return new Text(VehiclePerformanceSpeedEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text mainOverallLabel(){
			return new Text(VehiclePerformanceSpeedEnum.OVERALL_SCORE_LABEL);
		}
		
		
		public Text _overall(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_OVERALL_SCORE);
		}
		
		public Text _1_30(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_1_30_SCORE);
		}
		
		public Text _31_40(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_31_40_SCORE);
		}
		
		public Text _41_54(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_41_54_SCORE);
		}
		
		
		public Text _55_64(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_55_64_SCORE);
		}
		
		public Text _65_80(){
			return new Text(VehiclePerformanceSpeedEnum.BREAKDOWN_65_80_SCORE);
		}
		
		public TextTable dateTime(){
			return new TextTable(VehiclePerformanceSpeedEnum.DATE_TIME_ENTRY);
		}
		
		public TextTable postedSpeed(){
			return new TextTable(VehiclePerformanceSpeedEnum.POSTED_ENTRY_SPEED);
		}
		
		public TextTable postedSpeedUnits(){
			return new TextTable(VehiclePerformanceSpeedEnum.POSTED_ENTRY_SPEED_UNITS);
		}
		
		public TextTable avgSpeed(){
			return new TextTable(VehiclePerformanceSpeedEnum.AVERAGE_ENTRY_SPEED);
		}
		
		public TextTable avgSpeedUnits(){
			return new TextTable(VehiclePerformanceSpeedEnum.AVERAGE_ENTRY_SPEED_UNITS);
		}
		
		public TextTable avgSpeedPlusOrMinus(){
			return new TextTable(VehiclePerformanceSpeedEnum.AVERAGE_ENTRY_PLUSMINUS);
		}
		public TextTable avgSpeedDifference(){
			return new TextTable(VehiclePerformanceSpeedEnum.AVERAGE_ENTRY_DIFF);
		}
		
		public TextTable topSpeed(){
			return new TextTable(VehiclePerformanceSpeedEnum.TOP_ENTRY_SPEED);
		}
		
		public TextTable topSpeedUnits(){
			return new TextTable(VehiclePerformanceSpeedEnum.TOP_ENTRY_SPEED_UNITS);
		}
		
		public TextTable topSpeedPlusOrMinus(){
			return new TextTable(VehiclePerformanceSpeedEnum.TOP_ENTRY_PLUSMINUS);
		}
		
		public TextTable topSpeedDifference(){
			return new TextTable(VehiclePerformanceSpeedEnum.TOP_ENTRY_DIFF);
		}
		
		public TextTable distance(){
			return new TextTable(VehiclePerformanceSpeedEnum.DISTANCE_ENTRY);
		}
		
		public Text counter(){
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}
	}

	public VehicleSpeedTextFields _textFields(){
		return new VehicleSpeedTextFields();
	}
	
	public class VehicleSpeedTextFields{}

	public VehicleSpeedDropDowns _dropDown(){
		return new VehicleSpeedDropDowns();
	}
	
	public class VehicleSpeedDropDowns{}

	
	public class VehicleSpeedPopUp extends MastheadPopUps{
		public VehicleSpeedPopUp(){
			super(page, Types.SINGLE, 3);
		}
		public Email emailReport(){
			return new Email();
		}
	}
	
	public VehicleSpeedPopUp _popUp(){
		return new VehicleSpeedPopUp();
	}

	public class VehicleSpeedPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public VehicleSpeedPager _page(){
        return new VehicleSpeedPager();
    }
    

}
