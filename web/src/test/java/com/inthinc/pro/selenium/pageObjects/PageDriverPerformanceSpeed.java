package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceSpeedEnum;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.DurationEnumeration;


public class PageDriverPerformanceSpeed extends NavigationBar {
	private String page = "speedForm";
	
	public PageDriverPerformanceSpeed(){
		checkMe.add(DriverPerformanceSpeedEnum.DETAILS_TITLE);
		checkMe.add(DriverPerformanceSpeedEnum.OVERALL_TITLE);
		checkMe.add(DriverPerformanceSpeedEnum.RETURN);
	}
	
	public DriverSpeedButtons _button(){
		return new DriverSpeedButtons();
	}
	
	public class DriverSpeedButtons{
		public Button tools(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_TOOLS);
		}
		
		public Button returnToPerformancePage(){
			return new Button(DriverPerformanceSpeedEnum.RETURN);
		}
		
		public Button exportToPDF(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button emailReport(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_EMAIL_TOOL);
		}
		
		public Button postedSpeed(){
			return new Button(DriverPerformanceSpeedEnum.POSTED_ENTRY_SBS_BUTTON);
		}
	}
	
	public DriverSpeedLinks _link(){
		return new DriverSpeedLinks();
	}
	
	public class DriverSpeedLinks{
		
		public TextLink categoryOverallSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_OVERALL_LINK);
		}
		
		public TextLink categoryOneSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_1_30_LINK);
		}
		
		public TextLink categoryTwoSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_31_40_LINK);
		}
		
		public TextLink categoryThreeSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_41_54_LINK);
		}
				
		public TextLink categoryFourSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_55_64_LINK);
		}
		
		public TextLink categoryFiveSpeeds(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_65_80_LINK);
		}
		
		public TextTableLink location(){
			return new TextTableLink(DriverPerformanceSpeedEnum.LOCATION_ENTRY);
		}
		
		public TextLink duration(DurationEnumeration duration){
			return new TextLink(DriverPerformanceSpeedEnum.TIME_FRAME_SELECTOR, duration);
		}
		
		public TextTableLink exclude(){
			return new TextTableLink(DriverPerformanceSpeedEnum.EXCLUDE);
		}
		public TextTableLink include(){
			return new TextTableLink(DriverPerformanceSpeedEnum.INCLUDE);
		}
		
		public TextLink driverName(){
			return new TextLink(DriverPerformanceEnum.EXPANDED_DRIVER_NAME_LINK, "Speed");
		}
		
		public TextLink breadCrumb(Integer selection){
			return new TextLink(DriverPerformanceEnum.EXPANDED_BREADCRUMB,"Speed",selection);
		}
		
		public TextLink dateTimeSort(){
			return new TextLink(DriverPerformanceSpeedEnum.DATE_TIME_HEADER);
		}
		
		public TextLink postedSpeedSort(){
			return new TextLink(DriverPerformanceSpeedEnum.POSTED_HEADER);
		}
		
		public TextLink avgSpeedSort(){
			return new TextLink(DriverPerformanceSpeedEnum.AVERAGE_HEADER);
		}
		
		public TextLink topSpeedSort(){
			return new TextLink(DriverPerformanceSpeedEnum.TOP_HEADER);
		}
		
		public TextLink distanceSort(){
			return new TextLink(DriverPerformanceSpeedEnum.DISTANCE_HEADER);
		}
		
	}

	public DriverSpeedTexts _text(){
		return new DriverSpeedTexts();
	}
	
	public class DriverSpeedTexts{
		
		public Text mainOverallScore(){
			return new Text(DriverPerformanceSpeedEnum.OVERALL_SCORE_NUMBER);
		}
		
		public Text mainOverallLabel(){
			return new Text(DriverPerformanceSpeedEnum.OVERALL_SCORE_LABEL);
		}
		
		
		public Text categoryOverallSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_OVERALL_SCORE);
		}
		
		public Text categoryOneSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_1_30_SCORE);
		}
		
		public Text categoryTwoSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_31_40_SCORE);
		}
		
		public Text categoryThreeSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_41_54_SCORE);
		}
		
		
		public Text categoryFourSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_55_64_SCORE);
		}
		
		public Text categoryFiveSpeedsScore(){
			return new Text(DriverPerformanceSpeedEnum.BREAKDOWN_65_80_SCORE);
		}
		
		public TextTable dateTime(){
			return new TextTable(DriverPerformanceSpeedEnum.DATE_TIME_ENTRY);
		}
		
		public TextTable postedSpeed(){
			return new TextTable(DriverPerformanceSpeedEnum.POSTED_ENTRY_SPEED);
		}
		
		public TextTable postedSpeedUnits(){
			return new TextTable(DriverPerformanceSpeedEnum.POSTED_ENTRY_SPEED_UNITS);
		}
		
		public TextTable avgSpeed(){
			return new TextTable(DriverPerformanceSpeedEnum.AVERAGE_ENTRY_SPEED);
		}
		
		public TextTable avgSpeedUnits(){
			return new TextTable(DriverPerformanceSpeedEnum.AVERAGE_ENTRY_SPEED_UNITS);
		}
		
		public TextTable avgSpeedPlusOrMinus(){
			return new TextTable(DriverPerformanceSpeedEnum.AVERAGE_ENTRY_PLUSMINUS);
		}
		public TextTable avgSpeedDifference(){
			return new TextTable(DriverPerformanceSpeedEnum.AVERAGE_ENTRY_DIFF);
		}
		
		public TextTable topSpeed(){
			return new TextTable(DriverPerformanceSpeedEnum.TOP_ENTRY_SPEED);
		}
		
		public TextTable topSpeedUnits(){
			return new TextTable(DriverPerformanceSpeedEnum.TOP_ENTRY_SPEED_UNITS);
		}
		
		public TextTable topSpeedPlusOrMinus(){
			return new TextTable(DriverPerformanceSpeedEnum.TOP_ENTRY_PLUSMINUS);
		}
		
		public TextTable topSpeedDifference(){
			return new TextTable(DriverPerformanceSpeedEnum.TOP_ENTRY_DIFF);
		}
		
		public TextTable distance(){
			return new TextTable(DriverPerformanceSpeedEnum.DISTANCE_ENTRY);
		}
		
		public Text counter(){
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}
	}

	public DriverSpeedTextFields _textFields(){
		return new DriverSpeedTextFields();
	}
	
	public class DriverSpeedTextFields{}

	public DriverSpeedDropDowns _dropDown(){
		return new DriverSpeedDropDowns();
	}
	
	public class DriverSpeedDropDowns{}

	
	public class DriverSpeedPopUp extends MastheadPopUps{
		public DriverSpeedPopUp(){
			super(page, Types.SINGLE, 3);
		}
		public Email emailReport(){
			return new Email();
		}
	}
	
	public DriverSpeedPopUp _popUp(){
		return new DriverSpeedPopUp();
	}

	public class DriverSpeedPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DriverSpeedPager _page(){
        return new DriverSpeedPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return DriverPerformanceSpeedEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().categoryOneSpeeds().isPresent() && _link().dateTimeSort().isPresent();
    }
    
}
