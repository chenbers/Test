package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextScoreTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ExecutiveDashBoardEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageObjects.PopUps.Types;

public class PageExecutiveDashboard extends NavigationBar {
	
	public PageExecutiveDashboard(){
		url = ExecutiveDashBoardEnum.HOME_PAGE;
		checkMe.add(ExecutiveDashBoardEnum.OVERVIEW_EMAIL_BUTTON);
		checkMe.add(ExecutiveDashBoardEnum.EXPAND);
		checkMe.add(ExecutiveDashBoardEnum.HOME_PAGE);
	}
	
	public class DashboardButtons extends NavigationBarButtons {
		
		public Button overviewEmailReport(){
			return new Button(ExecutiveDashBoardEnum.OVERVIEW_EMAIL_BUTTON);
		}
		
		public TextButton emailReport(){
			return new TextButton(ExecutiveDashBoardEnum.TOOL_EMAIL_BUTTON, page);
		}
		
		public Button overviewExportPDF(){
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
	    
	    public Text overallScore(){
	        return new Text(ExecutiveDashBoardEnum.OVERALL_SCORE);
	    }
	    
	    public Text overallScoreLabel(){
	        return new Text(ExecutiveDashBoardEnum.OVERALL_SCORE_LABEL);
	    }

        public TextTable trendGroupCrash(){
            return new TextTable(ExecutiveDashBoardEnum.TREND_GROUP_CRASH_NUMBER);
        }
        
        public TextScoreTable trendGroupScore(){
            return new TextScoreTable(ExecutiveDashBoardEnum.TREND_GROUP_SCORE);
        }
        
        public Text labelCrashesPerMil(){
            return new Text(ExecutiveDashBoardEnum.CRASHES_PER_TEXT);
        }
        
        public Text numberCrashesPerMil(){
            return new Text(ExecutiveDashBoardEnum.CRASHES_PER_NUMBER);
        }
        
        public Text timeFrameCrashesPerMil(){
            return new Text(ExecutiveDashBoardEnum.CRASHES_PER_TIME_FRAME);
        }
        
        public Text labelDaysSinceLastCrash(){
            return new Text(ExecutiveDashBoardEnum.DAYS_SINCE_TEXT);
        }
        
        public Text numberDaysSinseLastCrash(){
            return new Text(ExecutiveDashBoardEnum.DAYS_SINCE_NUMBER);
        }
        
        public Text labelDistanceSinceLastCrash(){
            return new Text(ExecutiveDashBoardEnum.MILES_SINCE_TEXT);
        }
        
        public Text numberDistanceSinceLastCrash(){
            return new Text(ExecutiveDashBoardEnum.MILES_SINCE_NUMBER);
        }
        
        public Text labelSpeedingTotalDistanceDriven(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_DRIVEN_TEXT);
        }
        
        public Text numberSpeedingTotalDistanceDriven(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_DRIVEN_NUMBER);
        }
        
        public Text labelSpeedingTotalDistanceSpeeding(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_SPEEDING_TEXT);
        }
        
        public Text numberSpeedingTotalDistanceSpeeding(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_SPEEDING_NUMBER);
        }
        
        public Text labelIdlingTotalDuration(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_DURATION_TEXT);
        }
        
        public Text numberIdlingTotalDuration(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_DURATION_VALUE);
        }
        
        public Text labelIdlingTotalTimeIdling(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_TIME_IDLING_TEXT);
        }
        
        public Text numberIdlingTotalTimeIdling(){
            return new TextLabel(ExecutiveDashBoardEnum.TOTAL_TIME_IDLING_VALUE);
        }
        
        public Text titleOverallScore(){
            return new Text(ExecutiveDashBoardEnum.OVERALL_TITLE);
        }
        
        public Text titleCrashStats(){
            return new Text(ExecutiveDashBoardEnum.CRASH_TITLE);
        }
        
        public Text titleTrend(){
            return new Text(ExecutiveDashBoardEnum.TREND_TITLE);
        }
        
        public Text titleSpeeding(){
            return new Text(ExecutiveDashBoardEnum.SPEEDING_TITLE);
        }
        
        public Text titleIdling(){
            return new Text(ExecutiveDashBoardEnum.IDLING_TITLE);
        }
        
        public Text titleFuelEfficiency(){
            return new Text(ExecutiveDashBoardEnum.FUEL_EFFICIENCY_TITLE);
        }
        
        public Text titleLiveFleet(){
            return new Text(ExecutiveDashBoardEnum.LIVE_FLEET_TITLE);
        }
        
        public Text titleFleetLegend(){
            return new Text(ExecutiveDashBoardEnum.FLEET_LEGEND_TITLE);
        }
        
        public TextTable entryFleetLegend(){
            return new TextTable(ExecutiveDashBoardEnum.FLEET_LEGEND_GROUP);
        }
        
        public Text noteIdlingStats(){
            return new Text(ExecutiveDashBoardEnum.IDLING_STATS_NOTE);
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

    @Override
    public SeleniumEnums setUrl() {
        return ExecutiveDashBoardEnum.HOME_PAGE;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }

}
