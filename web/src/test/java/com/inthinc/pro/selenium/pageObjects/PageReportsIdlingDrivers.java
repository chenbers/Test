package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsIdlingDriversEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public class PageReportsIdlingDrivers extends ReportsBar {
	private String page = "idling";
	
	public PageReportsIdlingDrivers(){
		checkMe.add(ReportsIdlingDriversEnum.IDLING_COUNTER);
		checkMe.add(ReportsIdlingDriversEnum.START_DATE);
		checkMe.add(ReportsIdlingDriversEnum.END_DATE);
	}
	
	public class IdlingReportTexts extends ReportsBarTexts{
		public TextTable idleSupportValue(){
			return new TextTable(ReportsIdlingDriversEnum.IDLE_SUPPORT_VALUE);
		}
		public TextTable durationValue(){
			return new TextTable(ReportsIdlingDriversEnum.DURATION_VALUE);
		}
		public TextTable lowIdleHoursValue(){
			return new TextTable(ReportsIdlingDriversEnum.LOW_IDLE_VALUE);
		}
		public TextTable highIdleHoursValue(){
			return new TextTable(ReportsIdlingDriversEnum.HIGH_IDLE_VALUE);
		}
		public TextTable totalIdleHoursValue(){
			return new TextTable(ReportsIdlingDriversEnum.TOTAL_IDLE_VALUE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public Text idlingCounter(){
			return new Text(ReportsIdlingDriversEnum.IDLING_COUNTER);
		}
		
	}
	public class IdlingReportDropDowns extends ReportsBarDropDowns{}
	
	public class IdlingReportTextFields extends ReportsBarTextFields{
    
	    public TextField startDate() {
	        return new TextField(ReportsIdlingDriversEnum.START_DATE);
	    }
	    
	    public TextField endDate() {
	    	return new TextField(ReportsIdlingDriversEnum.END_DATE);
	    }
	    
	    public TextField driverFilter() {
	    	return new TextField(ReportsIdlingDriversEnum.DRIVER_FILTER);        
	    }
	    
	    public TextField teamFilter() {
	    	return new TextField(ReportsIdlingDriversEnum.GROUP_FILTER);        
	    }    
	    
	}
	
	public class IdlingReportButtons extends ReportsBarButtons{
		
	    public TextButton refresh() {
	        return new TextButton(ReportsIdlingDriversEnum.REFRESH);
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
	            return new TextLink(ReportsIdlingDriversEnum.GROUP_SORT);
	        }
	        
	        public TextLink sortByDriver() {
	            return new TextLink(ReportsIdlingDriversEnum.DRIVER_SORT);
	        }  
	        
	        public TextLink sortByIdleSupport() {
	            return new TextLink(ReportsIdlingDriversEnum.IDLE_SUPPORT_SORT);
	        }
	        
	        public TextLink sortByDuration() {
	            return new TextLink(ReportsIdlingDriversEnum.DURATION_SORT);
	        }
	        
	        public TextLink sortByLowIdle() {
	            return new TextLink(ReportsIdlingDriversEnum.LOW_IDLE_SORT);
	        }
	        
	        public TextLink sortByHighIdle() {
	            return new TextLink(ReportsIdlingDriversEnum.HIGH_IDLE_SORT);
	        }
	    
	    public TextTableLink groupValue() {
	        return new TextTableLink(ReportsIdlingDriversEnum.GROUP_VALUE);   
	    }  
	    
	    public TextTableLink driverValue() {
	    	return new TextTableLink(ReportsIdlingDriversEnum.DRIVER_VALUE); 
	    } 
	    
	    public TextTableLink tripsValue() {
	    	return new TextTableLink(ReportsIdlingDriversEnum.TRIPS_LINK); 
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
        return ReportsIdlingDriversEnum.DEFAULT_URL;
    }
    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}
