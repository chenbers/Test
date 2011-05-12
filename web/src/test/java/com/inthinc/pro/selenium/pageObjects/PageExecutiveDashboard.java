package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TimeLink;
import com.inthinc.pro.selenium.pageEnums.ExecutiveDashBoardEnum;
import com.inthinc.pro.selenium.pageObjects.PopUps.Types;

public class PageExecutiveDashboard extends NavigationBar {
	
	public class DashboardButtons extends NavigationBarButtons {
		
		public Button overviewTools(){
			page = overview;
			type = Types.POPUP;
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_TOOL_BUTTON);
		}
		
		public Button emailOverviewReport(){
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_EMAIL_BUTTON);
		}
		
		public Button exportOverviewPDF(){
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_PDF_BUTTON);
		}
		
		public Button overallTools(){
			page = overall;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button speedingTools(){
			page = speed;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button trendTools(){
			page = trend;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button idlingTools(){
			page = idling;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public Button fuelEfficiencyTools(){
			page = mpg;
			type = Types.SINGLE;
			return new Button(ExecutiveDashBoardEnum.TOOL_BUTTON, page);
		}
		
		public TextButton exportPDF(){
			return new TextButton(ExecutiveDashBoardEnum.TOOL_PDF_BUTTON, page);
		}
		
		public TextButton emailReport(){
			return new TextButton(ExecutiveDashBoardEnum.TOOL_EMAIL_BUTTON, page);
		}
		
		

        public Button fuelEfficiencyExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, mpg);
        }

        public Button liveFleetExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND_LIVE_FLEET);
        }

        public Button overallExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, overall);
        }


        public Button trendExpand() {
            return new Button(ExecutiveDashBoardEnum.EXPAND, trend);
        }

    }
	
	public class DashboardDropDowns extends NavigationBarDropDowns{}
	
	public class DashboardLinks extends NavigationBarLinks {

        public TimeLink fuelEfficiencyDuration() {
            return new TimeLink(ExecutiveDashBoardEnum.FUEL_EFFICIENCY_DURATION);
        }

        public TableTextLink groupName(){
            return new TableTextLink(ExecutiveDashBoardEnum.TREND_GROUP_LINK);
        }

        public TimeLink idlingDuration() {
            return new TimeLink(ExecutiveDashBoardEnum.IDLING_DURATION);
        }

        public TimeLink overallDuration() {
            return new TimeLink(ExecutiveDashBoardEnum.OVERALL_DURATION);
        }

        public TimeLink speedingDuration() {
            return new TimeLink(ExecutiveDashBoardEnum.SPEEDING_DURATION);
        }
        
        public TimeLink trendDuration() {
            return new TimeLink(ExecutiveDashBoardEnum.TREND_DURATION);
        }
    }
	public class DashboardText extends NavigationBarTexts{

        public TableText groupCrash(){
            return new TableText(ExecutiveDashBoardEnum.TREND_GROUP_CRASH_NUMBER);
        }
        
        public TableText groupScore(){
            return new TableText(ExecutiveDashBoardEnum.TREND_GROUP_LINK);
        }
    }
	

    public class DashboardTextField extends NavigationBarTextFields{}

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

    public DashboardText _text(){
        return new DashboardText();
    }

    public DashboardTextField _textField(){
		return new DashboardTextField();
	}
    
    public class DashPopUps extends PopUps{
    	public DashPopUps(){
    		super(page, type, 2);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    }
    
    public DashPopUps _popUp(){
    	return new DashPopUps();
    }

}
