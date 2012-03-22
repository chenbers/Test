package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextCheckboxLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceTripsEnum;

public class PageVehiclePerformanceTrips extends NavigationBar {
	

	public VehicleTripsPopUps _popUp(){
        return new VehicleTripsPopUps();
    }
    
	public class VehicleTripsPopUps extends NavigationBarPopUps{}


	public class VehicleTripsLinks extends NavigationBarLinks{
		
		public TextLink driverName(){
			return new TextLink(VehiclePerformanceTripsEnum.VEHICLE_NAME);
		}
		
		public TextLink breadcrumb(Integer position){
			return new TextLink(VehiclePerformanceTripsEnum.BREADCRUMB, position);
		}
		
		public TextTableLink tripLink(){
			return new TextTableLink(VehiclePerformanceTripsEnum.TRIP_ROW);
		}
		
		public TextLink vehicleName(){
			return new TextLink(VehiclePerformanceTripsEnum.VEHICLE_NAME);
		}
		
		public TextTableLink eventAddressEntry(){
			return new TextTableLink(VehiclePerformanceTripsEnum.EVENT_ADDRESS_ENTRY);
		}
		
		public TextTableLink eventDateTimeEntry(){
			return new TextTableLink(VehiclePerformanceTripsEnum.EVENT_DATE_TIME_ENTRY);
		}
		
	}
	public class VehicleTripsTexts extends NavigationBarTexts{
		
		public Text dateMessage(){
			return new Text(VehiclePerformanceTripsEnum.DATE_MESSAGE);
		}
		
		public Text tripsBy(){
			return new Text(VehiclePerformanceTripsEnum.TRIPS_BY);
		}
		
		public TextTable dateEntry(){
			return new TextTable(VehiclePerformanceTripsEnum.DATE_ENTRY);
		}
		
		public TextTable timeEntry(){
			return new TextTable(VehiclePerformanceTripsEnum.TIME_ENTRY);
		}
		
		public TextTable distanceEntry(){
			return new TextTable(VehiclePerformanceTripsEnum.DISTANCE_ENTRY);
		}
		
		public TextTable endAddressEntry(){
			return new TextTable(VehiclePerformanceTripsEnum.END_ADDRESS_ENTRY);
		}
		
		public TextTable durationEntry(){
			return new TextTable(VehiclePerformanceTripsEnum.DURATION_ENTRY);
		}
		
		public Text statsTitle(){
			return new Text(VehiclePerformanceTripsEnum.STATS_TITLE);
		}
		
		public Text totalTripDurationLabel(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIP_DURATION_LABEL);
		}
		
		public Text totalMilesLabel(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIP_DURATION_LABEL);
		}
		
		public Text totalIdleLabel(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_IDLE_TIME_LABEL);
		}
		
		public Text totalTripsLabel(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIPS_LABEL);
		}
		
		public Text totalTripDurationValue(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIP_DURATION_VALUE);
		}
		
		public Text totalMilesValue(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIP_DURATION_VALUE);
		}
		
		public Text totalIdleValue(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_IDLE_TIME_VALUE);
		}
		
		public Text totalTripsValue(){
			return new Text(VehiclePerformanceTripsEnum.TOTAL_TRIPS_VALUE);
		}
		
		public TextCheckboxLabel showEngineIdle(){
			return new TextCheckboxLabel(VehiclePerformanceTripsEnum.SHOW_ENGINE_IDLE);
		}
		
		public TextCheckboxLabel showSafetyViolations(){
			return new TextCheckboxLabel(VehiclePerformanceTripsEnum.SHOW_SAFETY_VIOLATION);
		}
		
		public TextCheckboxLabel showTampering(){
			return new TextCheckboxLabel(VehiclePerformanceTripsEnum.SHOW_TAMPERING);
		}
	}
	public class VehicleTripsTextFields extends NavigationBarTextFields{
		
		public TextField startDate(){
			return new TextField(VehiclePerformanceTripsEnum.START_DATE);
		}
		
		public TextField endDate(){
			return new TextField(VehiclePerformanceTripsEnum.END_DATE);
		}
	}
	public class VehicleTripsDropDowns extends NavigationBarDropDowns{}
	public class VehicleTripsButtons extends NavigationBarButtons{
		
		public TextButton updateDateRange(){
			return new TextButton(VehiclePerformanceTripsEnum.UPDATE_DATE_RANGE);
		}
	}

	
	public VehicleTripsLinks _link(){
		return new VehicleTripsLinks();
	}
	public VehicleTripsButtons _button(){
		return new VehicleTripsButtons();
	}
	public VehicleTripsTexts _text(){
		return new VehicleTripsTexts();
	}
	public VehicleTripsTextFields _textField(){
		return new VehicleTripsTextFields();
	}
	public VehicleTripsDropDowns _dropDown(){
		return new VehicleTripsDropDowns();
	}
	
	public class VehicleTripsCheckbox{
		public CheckBox showEngineIdle(){
			return new CheckBox(VehiclePerformanceTripsEnum.SHOW_ENGINE_IDLE);
		}
		
		public CheckBox showSafetyViolations(){
			return new CheckBox(VehiclePerformanceTripsEnum.SHOW_SAFETY_VIOLATION);
		}
		
		public CheckBox showTampering(){
			return new CheckBox(VehiclePerformanceTripsEnum.SHOW_TAMPERING);
		}
	}
	
	public VehicleTripsCheckbox _checkBox() {
		return new VehicleTripsCheckbox();
	}
	
	public class VehicleTripsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public VehicleTripsPager _page(){
        return new VehicleTripsPager();
    }
    @Override
    public SeleniumEnums setUrl() {
        return VehiclePerformanceTripsEnum.DEFAULT_URL;
    }
    
}
