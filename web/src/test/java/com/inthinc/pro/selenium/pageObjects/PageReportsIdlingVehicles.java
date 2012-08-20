package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsIdlingVehiclesEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public class PageReportsIdlingVehicles extends ReportsBar {
	private String page = "idling";
	
	public PageReportsIdlingVehicles(){
		checkMe.add(ReportsIdlingVehiclesEnum.IDLING_COUNTER);
		checkMe.add(ReportsIdlingVehiclesEnum.START_DATE);
		checkMe.add(ReportsIdlingVehiclesEnum.END_DATE);
	}
	
	public class IdlingReportTexts extends ReportsBarTexts{
		public TextTable idleSupportValue(){
			return new TextTable(ReportsIdlingVehiclesEnum.IDLE_SUPPORT_VALUE);
		}
		public TextTable durationValue(){
			return new TextTable(ReportsIdlingVehiclesEnum.DURATION_VALUE);
		}
		public TextTable lowIdleHoursValue(){
			return new TextTable(ReportsIdlingVehiclesEnum.LOW_IDLE_VALUE);
		}
		public TextTable highIdleHoursValue(){
			return new TextTable(ReportsIdlingVehiclesEnum.HIGH_IDLE_VALUE);
		}
		public TextTable totalIdleHoursValue(){
			return new TextTable(ReportsIdlingVehiclesEnum.TOTAL_IDLE_VALUE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public Text idlingCounter(){
			return new Text(ReportsIdlingVehiclesEnum.IDLING_COUNTER);
		}
		
	}
	public class IdlingReportDropDowns extends ReportsBarDropDowns{}
	
	public class IdlingReportTextFields extends ReportsBarTextFields{
    
	    public TextField startDate() {
	        return new TextField(ReportsIdlingVehiclesEnum.START_DATE);
	    }
	    
	    public TextField endDate() {
	    	return new TextField(ReportsIdlingVehiclesEnum.END_DATE);
	    }
	    
	    public TextField driverFilter() {
	    	return new TextField(ReportsIdlingVehiclesEnum.DRIVER_FILTER);        
	    }
	    
	    public TextField teamFilter() {
	    	return new TextField(ReportsIdlingVehiclesEnum.GROUP_FILTER);        
	    }    
	    
	}
	
	public class IdlingReportButtons extends ReportsBarButtons{
		
	    public TextButton refresh() {
	        return new TextButton(ReportsIdlingVehiclesEnum.REFRESH);
	    }

		public TextButton exportEmail() {
			return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
		}

		public TextButton exportExcel() {
			return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
		}

		public TextButton exportPDF() {
			return new TextButton(ReportsBarEnum.TOOL_PDF, page);
		}

		public Button tools() {
			return new Button(PopUpEnum.TOOL_BUTTON, page);
		}
	}
    
	public class IdlingReportLinks extends ReportsBarLinks{
		
	    public TextLink editColumns() {
	        return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
	    }
	    
	    public TextLink sortByGroup() {
	        return new TextLink(ReportsIdlingVehiclesEnum.GROUP_SORT);
	    }
	    
	    public TextLink sortByDriver() {
	        return new TextLink(ReportsIdlingVehiclesEnum.DRIVER_SORT);
	    }
	    
        public TextLink sortByVehicle() {
            return new TextLink(ReportsIdlingVehiclesEnum.VEHICLE_SORT);
        }	    
        
        public TextLink sortByIdleSupport() {
            return new TextLink(ReportsIdlingVehiclesEnum.IDLE_SUPPORT_SORT);
        }
        
        public TextLink sortByDuration() {
            return new TextLink(ReportsIdlingVehiclesEnum.DURATION_SORT);
        }
        
        public TextLink sortByLowIdle() {
            return new TextLink(ReportsIdlingVehiclesEnum.LOW_IDLE_SORT);
        }
        
        public TextLink sortByHighIdle() {
            return new TextLink(ReportsIdlingVehiclesEnum.HIGH_IDLE_SORT);
        }
	    
	    public TextTableLink groupValue() {
	        return new TextTableLink(ReportsIdlingVehiclesEnum.GROUP_VALUE);   
	    }  
	    
	    public TextTableLink driverValue() {
	    	return new TextTableLink(ReportsIdlingVehiclesEnum.DRIVER_VALUE); 
	    } 
	    
	    public TextTableLink tripsValue() {
	    	return new TextTableLink(ReportsIdlingVehiclesEnum.TRIPS_LINK); 
	    }
	    
	}   
	
	public class IdlingReportPopUps extends MastheadPopUps{
    	public IdlingReportPopUps(){
    		super(page, Types.REPORT, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    	public EditColumns editColumns(){
    		return new EditColumns();
    	}
    }

	public IdlingReportPopUps _popUp() {
		return new IdlingReportPopUps();
	}
	
	public IdlingReportLinks _link() {
	    return new IdlingReportLinks();
	}
	
	public IdlingReportButtons _button(){
	    return new IdlingReportButtons();
	}
	
	
    @Override
    public SeleniumEnums setUrl() {
        return ReportsIdlingVehiclesEnum.DEFAULT_URL;
    }
    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}
