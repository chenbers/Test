package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.ExecutiveDashBoardEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageObjects.PopUps.Types;

public class PageExecutiveDashboard extends NavigationBar {
	
	public class DashboardButtons extends NavigationBarButtons {
		
		public Button emailOverviewReport(){
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_EMAIL_BUTTON);
		}
		
		public TextButton emailReport(){
			return new TextButton(ExecutiveDashBoardEnum.TOOL_EMAIL_BUTTON, page);
		}
		
		public Button exportOverviewPDF(){
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_PDF_BUTTON);
		}
		
		public TextButton exportPDF(){
			return new TextButton(ExecutiveDashBoardEnum.TOOL_PDF_BUTTON, page);
		}
		
		public Button fuelEfficiencyExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, mpg);
        }
		
		public Button fuelEfficiencyTools(){
			page = mpg;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button idlingTools(){
			page = idling;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button liveFleetExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND_LIVE_FLEET);
        }
		
		public Button overallExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, overall);
        }
		
		public Button overallTools(){
			page = overall;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		

        public Button overviewTools(){
			page = overview;
			type = Types.POPUP;
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_TOOL_BUTTON);
		}

        public Button speedingTools(){
			page = speed;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}

        public Button trendExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, trend);
        }


        public Button trendTools(){
			page = trend;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}

    }
	
	public class DashboardDropDowns extends NavigationBarDropDowns{}
	
	public class DashboardLinks extends NavigationBarLinks {

        public TextLink fuelEfficiencyDuration(TimeDuration duration) {
            return new TextLink(ExecutiveDashBoardEnum.FUEL_EFFICIENCY_DURATION, duration);
        }

        public TextTableLink groupName(){
            return new TextTableLink(ExecutiveDashBoardEnum.TREND_GROUP_LINK);
        }

        public TextLink idlingDuration(TimeDuration duration) {
            return new TextLink(ExecutiveDashBoardEnum.IDLING_DURATION, duration);
        }

        public TextLink overallDuration(TimeDuration duration) {
            return new TextLink(ExecutiveDashBoardEnum.OVERALL_DURATION, duration);
        }

        public TextLink speedingDuration(TimeDuration duration) {
            return new TextLink(ExecutiveDashBoardEnum.SPEEDING_DURATION, duration);
        }
        
        public TextLink trendDuration(TimeDuration duration) {
            return new TextLink(ExecutiveDashBoardEnum.TREND_DURATION, duration);
        }
    }
	public class DashboardText extends NavigationBarTexts{

        public TextTable groupCrash(){
            return new TextTable(ExecutiveDashBoardEnum.TREND_GROUP_CRASH_NUMBER);
        }
        
        public TextTable groupScore(){
            return new TextTable(ExecutiveDashBoardEnum.TREND_GROUP_LINK);
        }
    }
	

    public class DashboardTextField extends NavigationBarTextFields{}

    public class DashPopUps extends MastheadPopUps{
    	public DashPopUps(){
    		super(page, type, 2);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    }
    
    private String overall = "executive-overallScore", speed = "executive-speedPercentagePanel", trend = "executive-trend";
    
    private String idling = "executive-idlingPercentagePanel", mpg = "executive-mpgChart", overview = "overview";
    
    private String page;
    
    private Types type;

    public DashboardButtons _button() {
        return new DashboardButtons();
    }

    public DashboardDropDowns _dropDown(){
		return new DashboardDropDowns();
	}

    public DashboardLinks _link() {
        return new DashboardLinks();
    }

    public DashPopUps _popUp(){
    	return new DashPopUps();
    }
    
    public DashboardText _text(){
        return new DashboardText();
    }
    
    public DashboardTextField _textField(){
		return new DashboardTextField();
	}

}
