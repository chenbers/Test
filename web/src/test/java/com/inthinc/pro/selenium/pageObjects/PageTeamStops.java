package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.TeamStopsEnum;

public class PageTeamStops extends TeamBar {
	

	private String stopsScroller = "stopsTripsTableForm:stopsTrips:stopsTripsTableScroller_table";
	private String driverScroller = "stopsTableForm:stopsDrivers:stopsDriverTableScroller_table";
	
	
	public class TeamStopsLinks extends TeamBarLinks{
		
		public TextTableLink driverEntry(){
			return new TextTableLink(TeamStopsEnum.DRIVER_NAME);
		}
	}
	public class TeamStopsTexts extends TeamBarTexts{
		
		/* Column elements in the results table */
		public TextTable zoneEntry(){
			return new TextTable(TeamStopsEnum.ZONE_ENTRY);
		}
		
		public TextTable stopLocationEntry(){
			return new TextTable(TeamStopsEnum.LOCATION_ENTRY);
		}
		
		public TextTable arriveEntry(){
			return new TextTable(TeamStopsEnum.ARRIVE_ENTRY);
		}
		
		public TextTable departLocationEntry(){
			return new TextTable(TeamStopsEnum.DEPART_ENTRY);
		}
		
		public TextTable totalEntry(){
			return new TextTable(TeamStopsEnum.TOTAL_ENTRY);
		}
		
		public TextTable lowIdleEntry(){
			return new TextTable(TeamStopsEnum.LOW_IDLE_ENTRY);
		}
		
		public TextTable highIdleEntry(){
			return new TextTable(TeamStopsEnum.HIGH_IDLE_ENTRY);
		}
		
		public TextTable waitEntry(){
			return new TextTable(TeamStopsEnum.WAIT_ENTRY);
		}
		
		public TextTable durationEntry(){
			return new TextTable(TeamStopsEnum.DURATION_ENTRY);
		}
		
		/* Headers for the columns*/
		public Text zoneHeader(){
			return new Text(TeamStopsEnum.ZONE_HEADER);
		}
		
		public Text stopLocationHeader(){
			return new Text(TeamStopsEnum.LOCATION_HEADER);
		}
		
		public Text arriveHeader(){
			return new Text(TeamStopsEnum.ARRIVE_HEADER);
		}
		
		public Text departLocationHeader(){
			return new Text(TeamStopsEnum.DEPART_HEADER);
		}
		
		public Text totalHeader(){
			return new Text(TeamStopsEnum.TOTAL_HEADER);
		}
		
		public Text lowIdleHeader(){
			return new Text(TeamStopsEnum.LOW_IDLE_HEADER);
		}
		
		public Text highIdleHeader(){
			return new Text(TeamStopsEnum.HIGH_IDLE_HEADER);
		}
		
		public Text waitHeader(){
			return new Text(TeamStopsEnum.WAIT_HEADER);
		}
		
		public Text durationHeader(){
			return new Text(TeamStopsEnum.DURATION_HEADER);
		}
		
		public Text timeAtStopHeader(){
			return new Text(TeamStopsEnum.DETAILED_SUPERHEADER);
		}
		
		/* Overall Total Entries */
		
		public Text overallTotalEntry(){
			return new Text(TeamStopsEnum.TOTAL_TOTAL_ENTRY);
		}
		
		public Text overallLowIdleEntry(){
			return new Text(TeamStopsEnum.TOTAL_LOW_IDLE_ENTRY);
		}
		
		public Text overallHighIdleEntry(){
			return new Text(TeamStopsEnum.TOTAL_HIGH_IDLE_ENTRY);
		}
		
		public Text overallWaitEntry(){
			return new Text(TeamStopsEnum.TOTAL_WAIT_ENTRY);
		}
		
		public Text overallDurationEntry(){
			return new Text(TeamStopsEnum.TOTAL_DURATION_ENTRY);
		}
		
		/* Overall Total Headers */
		
		public Text overallTotalHeader(){
			return new Text(TeamStopsEnum.TOTAL_TOTAL_HEADER);
		}
		
		public Text overallLowIdleHeader(){
			return new Text(TeamStopsEnum.TOTAL_LOW_IDLE_HEADER);
		}
		
		public Text overallHighIdleHeader(){
			return new Text(TeamStopsEnum.TOTAL_HIGH_IDLE_HEADER);
		}
		
		public Text overallWaitHeader(){
			return new Text(TeamStopsEnum.TOTAL_WAIT_HEADER);
		}
		
		public Text overallDurationHeader(){
			return new Text(TeamStopsEnum.TOTAL_DURATION_HEADER);
		}
		
		public Text overallTotalTimeAtStopHeader(){
			return new Text(TeamStopsEnum.TOTAL_SUPERHEADER);
		}
		
	}
	public class TeamStopsTextFields extends TeamBarTextFields{}
	public class TeamStopsButtons extends TeamBarButtons{
		
		public ButtonTable driverRadio(){
			return new ButtonTable(TeamStopsEnum.DRIVER_RADIO_BUTTON);
		}
	}
	public class TeamStopsDropDowns extends TeamBarDropDowns{}
	public class TeamStopsPopUps extends MastheadPopUps{}
	
	public TeamStopsLinks _link(){
        return new TeamStopsLinks();
    }
    
    public TeamStopsTexts _text(){
        return new TeamStopsTexts();
    }
        
    public TeamStopsButtons _button(){
        return new TeamStopsButtons();
    }
    
    public TeamStopsTextFields _textField(){
        return new TeamStopsTextFields();
    }
    
    public TeamStopsDropDowns _dropDown(){
        return new TeamStopsDropDowns();
    }
    
    public TeamStopsPopUps _popUp(){
        return new TeamStopsPopUps();
    }
    
    public TeamPager _page(){
    	return new TeamPager();
    }
    
    public class TeamPager{
    	public Paging drivers(){
    		return new Paging(driverScroller);
    	}
    	
    	public Paging stopsTable(){
    		return new Paging(stopsScroller);
    	}
    }

}
