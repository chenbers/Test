package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextCheckboxLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceTripsEnum;


public class PageDriverPerformanceTrips extends NavigationBar {
	
	public PageDriverPerformanceTrips(){
		checkMe.add(DriverPerformanceTripsEnum.DATE_ENTRY);
		checkMe.add(DriverPerformanceTripsEnum.DRIVER_NAME);
		checkMe.add(DriverPerformanceTripsEnum.SHOW_TAMPERING);
		
	}

    public DriverTripsPopUps _popUp(){
        return new DriverTripsPopUps();
    }
    
	public class DriverTripsPopUps extends MastheadPopUps{}


	public class DriverTripsLinks extends NavigationBarLinks{
		
		public TextLink driverName(){
			return new TextLink(DriverPerformanceTripsEnum.DRIVER_NAME);
		}
		
		public TextLink breadcrumb(Integer position){
			return new TextLink(DriverPerformanceTripsEnum.BREADCRUMB, position);
		}
		
		public TextTableLink tripLink(){
			return new TextTableLink(DriverPerformanceTripsEnum.TRIP_ROW);
		}
		
		public TextLink vehicleName(){
			return new TextLink(DriverPerformanceTripsEnum.VEHICLE_NAME);
		}
		
		public TextTableLink eventAddressEntry(){
			return new TextTableLink(DriverPerformanceTripsEnum.EVENT_ADDRESS_ENTRY);
		}
		
		public TextTableLink eventDateTimeEntry(){
			return new TextTableLink(DriverPerformanceTripsEnum.EVENT_DATE_TIME_ENTRY);
		}
		
	}
	public class DriverTripsTexts extends NavigationBarTexts{
		
		public Text dateMessage(){
			return new Text(DriverPerformanceTripsEnum.DATE_MESSAGE);
		}
		
		public Text tripsBy(){
			return new Text(DriverPerformanceTripsEnum.TRIPS_BY);
		}
		
		public TextTable dateEntry(){
			return new TextTable(DriverPerformanceTripsEnum.DATE_ENTRY);
		}
		
		public TextTable timeEntry(){
			return new TextTable(DriverPerformanceTripsEnum.TIME_ENTRY);
		}
		
		public TextTable distanceEntry(){
			return new TextTable(DriverPerformanceTripsEnum.DISTANCE_ENTRY);
		}
		
		public TextTable endAddressEntry(){
			return new TextTable(DriverPerformanceTripsEnum.END_ADDRESS_ENTRY);
		}
		
		public TextTable durationEntry(){
			return new TextTable(DriverPerformanceTripsEnum.DURATION_ENTRY);
		}
		
		public Text statsTitle(){
			return new Text(DriverPerformanceTripsEnum.STATS_TITLE);
		}
		
		public Text totalTripDurationLabel(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIP_DURATION_LABEL);
		}
		
		public Text totalMilesLabel(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIP_DURATION_LABEL);
		}
		
		public Text totalIdleLabel(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_IDLE_TIME_LABEL);
		}
		
		public Text totalTripsLabel(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIPS_LABEL);
		}
		
		public Text totalTripDurationValue(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIP_DURATION_VALUE);
		}
		
		public Text totalMilesValue(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIP_DURATION_VALUE);
		}
		
		public Text totalIdleValue(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_IDLE_TIME_VALUE);
		}
		
		public Text totalTripsValue(){
			return new Text(DriverPerformanceTripsEnum.TOTAL_TRIPS_VALUE);
		}
		
		public TextCheckboxLabel showEngineIdle(){
			return new TextCheckboxLabel(DriverPerformanceTripsEnum.SHOW_ENGINE_IDLE);
		}
		
		public TextCheckboxLabel showSafetyViolations(){
			return new TextCheckboxLabel(DriverPerformanceTripsEnum.SHOW_SAFETY_VIOLATION);
		}
		
		public TextCheckboxLabel showTampering(){
			return new TextCheckboxLabel(DriverPerformanceTripsEnum.SHOW_TAMPERING);
		}
	}
	public class DriverTripsTextFields extends NavigationBarTextFields{}
	public class DriverTripsDropDowns extends NavigationBarDropDowns{
	    
	     public DropDown startDate(){
	        return new CalendarObject(DriverPerformanceTripsEnum.START_DATE);
	     }
	        
	     public DropDown endDate(){
	        return new CalendarObject(DriverPerformanceTripsEnum.END_DATE);
	     }
	}
	public class DriverTripsButtons extends NavigationBarButtons{
		
		public TextButton updateDateRange(){
			return new TextButton(DriverPerformanceTripsEnum.UPDATE_DATE_RANGE);
		}
	}

	
	public DriverTripsLinks _link(){
		return new DriverTripsLinks();
	}
	public DriverTripsButtons _button(){
		return new DriverTripsButtons();
	}
	public DriverTripsTexts _text(){
		return new DriverTripsTexts();
	}
	public DriverTripsTextFields _textField(){
		return new DriverTripsTextFields();
	}
	public DriverTripsDropDowns _dropDown(){
		return new DriverTripsDropDowns();
	}
	
	public class DriverTripsCheckbox{
		public CheckBox showEngineIdle(){
			return new CheckBox(DriverPerformanceTripsEnum.SHOW_ENGINE_IDLE);
		}
		
		public CheckBox showSafetyViolations(){
			return new CheckBox(DriverPerformanceTripsEnum.SHOW_SAFETY_VIOLATION);
		}
		
		public CheckBox showTampering(){
			return new CheckBox(DriverPerformanceTripsEnum.SHOW_TAMPERING);
		}
	}
	
	public DriverTripsCheckbox _checkBox() {
		return new DriverTripsCheckbox();
	}
	
	public class DriverTripsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DriverTripsPager _page(){
        return new DriverTripsPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return DriverPerformanceTripsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().updateDateRange().isPresent() && 
               _dropDown().startDate().isPresent();
    }
    

}
